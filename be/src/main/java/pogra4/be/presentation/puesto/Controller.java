package pogra4.be.presentation.puesto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.logic.Puesto;
import pogra4.be.logic.Service;
import java.util.List;

@RestController("puestos")
@RequestMapping("/api/puestos")
public class Controller {

    @Autowired
    private Service service;

    @GetMapping("/ultimos")
    public List<Puesto> getUltimos() {
        return service.getUltimosPuestosPublicos();
    }

    @GetMapping("/caracteristicas")
    public Iterable<?> getCaracteristicas() {
        return service.carecteristicasAll();
    }

    @GetMapping("/buscar")
    public List<Puesto> buscar(@RequestParam(required = false) List<String> caracteristicaIds) {
        if (caracteristicaIds == null || caracteristicaIds.isEmpty()) {
            return List.of();
        }
        return service.buscarPuestosPublicos(caracteristicaIds);
    }
}