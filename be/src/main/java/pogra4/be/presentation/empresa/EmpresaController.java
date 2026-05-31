package pogra4.be.presentation.empresa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.logic.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EmpresaController {

    @Autowired
    private Service service;

    @PostMapping("/api/empresas/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public void registro(@RequestBody RegistroRequest req) {
        Empresa e = new Empresa();
        e.setId(UUID.randomUUID().toString());
        e.setNombre(req.getNombre());
        e.setCorreo(req.getCorreo());
        e.setContrasenna(req.getContrasenna());
        e.setUbicacion(req.getUbicacion());
        e.setTelefono(req.getTelefono());
        e.setDescripcion(req.getDescripcion());
        e.setEstado("pendiente");
        service.empresasAdd(e);
    }

    @GetMapping("/api/empresa/puestos")
    public Iterable<Puesto> misPuestos(Authentication auth) {
        return service.puestosPorEmpresa(auth.getName());
    }

    @PostMapping("/api/empresa/puestos")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPuesto(@RequestBody PuestoRequest req, Authentication auth) {
        service.crearPuesto(
                auth.getName(),
                req.getDescripcion(),
                req.getSalario(),
                req.getTipo(),
                req.getCaracteristicaIds(),
                req.getNiveles()
        );
    }

    @PostMapping("/api/empresa/puestos/{id}/desactivar")
    public void desactivarPuesto(@PathVariable String id, Authentication auth) {
        Puesto p = service.findPuestoById(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!p.getEmpresa().getId().equals(auth.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.desactivarPuesto(id);
    }

    @GetMapping("/api/empresa/puestos/{id}/candidatos")
    public List<CandidatoDTO> candidatos(@PathVariable String id, Authentication auth) {
        Puesto p = service.findPuestoById(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!p.getEmpresa().getId().equals(auth.getName()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.buscarCandidatos(id);
    }

    @Getter @Setter
    public static class RegistroRequest {
        private String nombre;
        private String correo;
        private String contrasenna;
        private String ubicacion;
        private Integer telefono;
        private String descripcion;
    }

    @Getter @Setter
    public static class PuestoRequest {
        private String descripcion;
        private String salario;
        private String tipo;
        private List<String> caracteristicaIds;
        private List<Integer> niveles;
    }
}
