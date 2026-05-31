package pogra4.be.presentation.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.logic.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final Service service;

    // ── Listar empresas pendientes de aprobación ──────────────────────────────
    @GetMapping("/empresas/pendientes")
    public Iterable<Empresa> empresasPendientes() {
        return service.empresasPendientes();
    }

    // ── Aprobar una empresa ───────────────────────────────────────────────────
    @PutMapping("/empresas/{id}/aprobar")
    public void aprobarEmpresa(@PathVariable String id) {
        try {
            service.aprobarEmpresa(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa no encontrada");
        }
    }

    // ── Listar oferentes pendientes de aprobación ─────────────────────────────
    @GetMapping("/oferentes/pendientes")
    public Iterable<Oferente> oferentesPendientes() {
        return service.oferentesPendientes();
    }

    // ── Aprobar un oferente ───────────────────────────────────────────────────
    @PutMapping("/oferentes/{id}/aprobar")
    public void aprobarOferente(@PathVariable String id) {
        try {
            service.aprobarOferente(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferente no encontrado");
        }
    }

    // ── Listar todas las características (árbol) ──────────────────────────────
    @GetMapping("/caracteristicas")
    public Iterable<Caracteristica> listarCaracteristicas() {
        return service.carecteristicasAll();
    }

    // ── Listar solo características raíz (sin padre) ──────────────────────────
    @GetMapping("/caracteristicas/raices")
    public Iterable<Caracteristica> raices() {
        return service.getRaices();
    }

    // ── Listar hijos de una característica ───────────────────────────────────
    @GetMapping("/caracteristicas/{id}/hijos")
    public Iterable<Caracteristica> hijos(@PathVariable String id) {
        return service.getHijos(id);
    }

    // ── Crear una característica (con o sin padre) ────────────────────────────
    @PostMapping("/caracteristicas")
    public void crearCaracteristica(@RequestBody CaracteristicaRequest req) {
        service.crearCaracteristica(req.nombre(), req.padreId());
    }

    // ── DTO ───────────────────────────────────────────────────────────────────
    public record CaracteristicaRequest(String nombre, String padreId) {}
}
