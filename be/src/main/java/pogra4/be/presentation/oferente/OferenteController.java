package pogra4.be.presentation.oferente;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.logic.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OferenteController {

    private final Service service;

    // ── PÚBLICO: registro de oferente ─────────────────────────────────────────
    @PostMapping("/oferentes/registro")
    public void registrar(@RequestBody Oferente oferente) {
        if (oferente.getId() == null || oferente.getId().isBlank()) {
            oferente.setId(UUID.randomUUID().toString());
        }
        oferente.setEstado("pendiente");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        oferente.setContrasenna(encoder.encode(oferente.getContrasenna()));
        try {
            service.oferentesAdd(oferente);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // ── OFERENTE: ver su propio perfil ────────────────────────────────────────
    @GetMapping("/oferente/perfil")
    public Oferente miPerfil(Authentication auth) {
        String id = getIdFromToken(auth);
        Oferente o = service.findOferenteById(id);
        if (o == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return o;
    }

    // ── OFERENTE: ver sus habilidades ─────────────────────────────────────────
    @GetMapping("/oferente/habilidades")
    public Iterable<OferenteHasCaracteristica> misHabilidades(Authentication auth) {
        String id = getIdFromToken(auth);
        return service.habilidadesDeOferente(id);
    }

    // ── OFERENTE: agregar una habilidad ──────────────────────────────────────
    @PostMapping("/oferente/habilidades")
    public void agregarHabilidad(@RequestBody HabilidadRequest req, Authentication auth) {
        String id = getIdFromToken(auth);
        service.agregarHabilidad(id, req.caracteristicaId(), req.nivel());
    }

    // ── OFERENTE: eliminar una habilidad ─────────────────────────────────────
    @DeleteMapping("/oferente/habilidades/{habilidadId}")
    public void eliminarHabilidad(@PathVariable String habilidadId, Authentication auth) {
        service.eliminarHabilidad(habilidadId);
    }

    // ── OFERENTE: subir curriculum PDF ───────────────────────────────────────
    @PostMapping("/oferente/curriculum")
    public void subirCurriculum(@RequestParam("file") MultipartFile file,
                                Authentication auth) throws IOException {
        String id = getIdFromToken(auth);
        if (file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Archivo vacío");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se aceptan archivos PDF");
        }

        // Guardar en carpeta uploads/curriculos/
        Path uploadDir = Paths.get("uploads/curriculos");
        Files.createDirectories(uploadDir);
        String filename = id + "_" + UUID.randomUUID() + ".pdf";
        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        service.actualizarCurriculumOferente(id, filename);
    }

    // ── helper ────────────────────────────────────────────────────────────────
    private String getIdFromToken(Authentication auth) {
        return (String) ((Jwt) auth.getPrincipal()).getClaims().get("id");
    }

    // ── DTOs ──────────────────────────────────────────────────────────────────
    public record HabilidadRequest(String caracteristicaId, int nivel) {}
}
