package pogra4.be.presentation.login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pogra4.be.data.AdminRepository;
import pogra4.be.data.EmpresasRepository;
import pogra4.be.data.OferenteRepository;
import pogra4.be.logic.Admin;
import pogra4.be.logic.Empresa;
import pogra4.be.logic.Oferente;
import pogra4.be.security.TokenService;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

    private final EmpresasRepository empresasRepository;
    private final OferenteRepository oferenteRepository;
    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    /**
     * Body esperado:
     * {
     *   "correo": "...",      (para empresa u oferente)
     *   "id": "...",          (para admin, usa su id)
     *   "contrasenna": "...",
     *   "tipo": "EMPRESA" | "OFERENTE" | "ADMIN"
     * }
     *
     * Respuesta exitosa:
     * {
     *   "token": "<JWT>",
     *   "id": "...",
     *   "nombre": "...",
     *   "tipo": "EMPRESA" | "OFERENTE" | "ADMIN"
     * }
     */
    @PostMapping
    public Map<String, String> login(@RequestBody LoginRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        try {
            switch (request.tipo().toUpperCase()) {

                case "EMPRESA" -> {
                    Empresa e = empresasRepository.findByCorreo(request.correo());
                    if (e == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    if (!e.getEstado().equalsIgnoreCase("aprobada"))
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Empresa no aprobada");
                    if (!encoder.matches(request.contrasenna(), e.getContrasenna()))
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    String token = tokenService.generateToken(e.getId(), "EMPRESA", e.getNombre());
                    return Map.of("token", token, "id", e.getId(), "nombre", e.getNombre(), "tipo", "EMPRESA");
                }

                case "OFERENTE" -> {
                    Oferente o = oferenteRepository.findByCorreo(request.correo());
                    if (o == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    if (!o.getEstado().equalsIgnoreCase("aprobado"))
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Oferente no aprobado");
                    if (!encoder.matches(request.contrasenna(), o.getContrasenna()))
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    String token = tokenService.generateToken(o.getId(), "OFERENTE", o.getNombre());
                    return Map.of("token", token, "id", o.getId(), "nombre", o.getNombre(), "tipo", "OFERENTE");
                }

                case "ADMIN" -> {
                    Admin a = adminRepository.findById(request.id())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                    if (!encoder.matches(request.contrasenna(), a.getContrasenna()))
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    String token = tokenService.generateToken(a.getId(), "ADMIN", a.getNombre());
                    return Map.of("token", token, "id", a.getId(), "nombre", a.getNombre(), "tipo", "ADMIN");
                }

                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo inválido");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    // DTO de entrada como Java record (limpio, sin clase extra)
    public record LoginRequest(
            String correo,
            String id,
            String contrasenna,
            String tipo
    ) {}
}
