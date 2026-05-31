package pogra4.be.presentation.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pogra4.be.logic.Empresa;
import pogra4.be.logic.Oferente;
import pogra4.be.logic.Service;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private Service service;

    @GetMapping("/empresas/pendientes")
    public Iterable<Empresa> empresasPendientes() {
        return service.empresasPendientes();
    }

    @PostMapping("/empresas/{id}/aprobar")
    public void aprobarEmpresa(@PathVariable String id) {
        service.aprobarEmpresa(id);
    }

    @GetMapping("/oferentes/pendientes")
    public Iterable<Oferente> oferentesPendientes() {
        return service.oferentesPendientes();
    }

    @PostMapping("/oferentes/{id}/aprobar")
    public void aprobarOferente(@PathVariable String id) {
        service.aprobarOferente(id);
    }

    @GetMapping("/caracteristicas")
    public Iterable<?> getRaices() {
        return service.getRaices();
    }

    @GetMapping("/caracteristicas/{padreId}/hijos")
    public Iterable<?> getHijos(@PathVariable String padreId) {
        return service.getHijos(padreId);
    }

    @PostMapping("/caracteristicas")
    public void crearCaracteristica(@RequestBody CaracteristicaRequest req) {
        service.crearCaracteristica(req.getNombre(), req.getPadreId());
    }

    @Getter @Setter
    public static class CaracteristicaRequest {
        private String nombre;
        private String padreId;
    }
}
