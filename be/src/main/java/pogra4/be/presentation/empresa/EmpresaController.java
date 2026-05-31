package pogra4.be.presentation.empresa;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.logic.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EmpresaController {

    private final Service service;

    // ── PÚBLICO: registro de empresa ──────────────────────────────────────────
    @PostMapping("/empresas/registro")
    public void registrar(@RequestBody Empresa empresa) {
        if (empresa.getId() == null || empresa.getId().isBlank()) {
            empresa.setId(UUID.randomUUID().toString());
        }
        empresa.setEstado("pendiente");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        empresa.setContrasenna(encoder.encode(empresa.getContrasenna()));
        try {
            service.empresasAdd(empresa);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // ── EMPRESA: ver sus propios puestos ──────────────────────────────────────
    @GetMapping("/empresa/puestos")
    public Iterable<Puesto> misPuestos(Authentication auth) {
        String empresaId = getIdFromToken(auth);
        return service.puestosPorEmpresa(empresaId);
    }

    // ── EMPRESA: crear un puesto ──────────────────────────────────────────────
    @PostMapping("/empresa/puestos")
    public void crearPuesto(@RequestBody PuestoRequest req, Authentication auth) {
        String empresaId = getIdFromToken(auth);
        service.crearPuesto(
                empresaId,
                req.descripcion(),
                req.salario(),
                req.tipo(),
                req.caracteristicaIds(),
                req.niveles()
        );
    }

    // ── EMPRESA: desactivar un puesto ─────────────────────────────────────────
    @PutMapping("/empresa/puestos/{id}/desactivar")
    public void desactivar(@PathVariable String id, Authentication auth) {
        String empresaId = getIdFromToken(auth);
        Puesto p = service.findPuestoById(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!p.getEmpresa().getId().equals(empresaId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.desactivarPuesto(id);
    }

    // ── EMPRESA: buscar candidatos para un puesto ─────────────────────────────
    @GetMapping("/empresa/puestos/{id}/candidatos")
    public List<CandidatoDTO> candidatos(@PathVariable String id, Authentication auth) {
        String empresaId = getIdFromToken(auth);
        Puesto p = service.findPuestoById(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!p.getEmpresa().getId().equals(empresaId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.buscarCandidatos(id);
    }

    // ── EMPRESA: ver detalle de un oferente ───────────────────────────────────
    @GetMapping("/empresa/oferentes/{id}")
    public Map<String, Object> verOferente(@PathVariable String id) {
        Oferente o = service.findOferenteById(id);
        if (o == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Iterable<OferenteHasCaracteristica> habilidades = service.habilidadesDeOferente(id);
        return Map.of("oferente", o, "habilidades", habilidades);
    }

    // ── helper ────────────────────────────────────────────────────────────────
    private String getIdFromToken(Authentication auth) {
        return (String) ((Jwt) auth.getPrincipal()).getClaims().get("id");
    }

    // ── DTO de entrada para crear puesto ──────────────────────────────────────
    public record PuestoRequest(
            String descripcion,
            String salario,
            String tipo,
            List<String> caracteristicaIds,
            List<Integer> niveles
    ) {}
}
