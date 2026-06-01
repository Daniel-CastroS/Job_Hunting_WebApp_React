package logic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "puesto_has_caracteristica")
public class PuestoHasCaracteristica {
    @EmbeddedId
    private PuestoHasCaracteristicaId id;

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