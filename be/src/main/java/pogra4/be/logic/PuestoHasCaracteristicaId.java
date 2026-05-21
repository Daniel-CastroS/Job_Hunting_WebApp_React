package pogra4.be.logic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PuestoHasCaracteristicaId implements Serializable {
    private static final long serialVersionUID = 5575731714046030496L;
    @Column(name = "Puesto_id", nullable = false)
    private String puestoId;

    @Column(name = "Caracteristica_caracteristica_id", nullable = false, length = 160)
    private String caracteristicaCaracteristicaId;


}