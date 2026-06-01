package logic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PuestoHasCaracteristicaId implements Serializable {
    private static final long serialVersionUID = 3252430427099797286L;
    @Size(max = 255)
    @NotNull
    @Column(name = "Puesto_id", nullable = false)
    private String puestoId;

    @Size(max = 160)
    @NotNull
    @Column(name = "Caracteristica_caracteristica_id", nullable = false, length = 160)
    private String caracteristicaCaracteristicaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PuestoHasCaracteristicaId entity = (PuestoHasCaracteristicaId) o;
        return Objects.equals(this.caracteristicaCaracteristicaId, entity.caracteristicaCaracteristicaId) &&
                Objects.equals(this.puestoId, entity.puestoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caracteristicaCaracteristicaId, puestoId);
    }

}