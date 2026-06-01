package logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oferente_has_caracteristica")
public class OferenteHasCaracteristica {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Oferente_id")
    private Oferente oferente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Caracteristica_caracteristica_id")
    private Caracteristica caracteristicaCaracteristica;

    @Column(name = "nivel")
    private Integer nivel;

}