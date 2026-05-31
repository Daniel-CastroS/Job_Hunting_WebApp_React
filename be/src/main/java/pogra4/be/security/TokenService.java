package pogra4.be.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtConfig jwtConfig;

    /**
     * Genera un JWT con:
     *  - scope: rol del usuario (EMPRESA, OFERENTE, ADMIN)
     *  - id:    identificador usado para hacer login
     *  - name:  nombre para mostrar en el frontend
     */
    public String generateToken(String id, String rol, String name) {
        var header = new JWSHeader.Builder(jwtConfig.getAlgorithm())
                .type(JOSEObjectType.JWT)
                .build();

        Instant now = Instant.now();
        var claims = new JWTClaimsSet.Builder()
                .issuer("InfoEmpleo")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(jwtConfig.getJwtExpiration(), ChronoUnit.MILLIS)))
                .claim("scope", List.of(rol))
                .claim("id", id)
                .claim("name", name)
                .build();

        var jwt = new SignedJWT(header, claims);
        try {
            jwt.sign(new MACSigner(jwtConfig.getSecretKey()));
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating JWT", e);
        }
        return jwt.serialize();
    }
}
