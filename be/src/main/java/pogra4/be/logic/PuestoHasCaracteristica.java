package pogra4.be.logic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "puesto_has_caracteristica")
public class PuestoHasCaracteristica {
    @EmbeddedId
    private PuestoHasCaracteristicaId id;

    @JsonIgnore
    @MapsId("puestoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Puesto_id", nullable = false)
    private Puesto puesto;

    @MapsId("caracteristicaCaracteristicaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Caracteristica_caracteristica_id", nullable = false)
    private Caracteristica caracteristicaCaracteristica;

    @Column(name = "nivel")
    private Integer nivel;


}