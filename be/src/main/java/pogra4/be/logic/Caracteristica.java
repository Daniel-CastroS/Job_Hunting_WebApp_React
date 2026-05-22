package pogra4.be.logic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "caracteristica")
public class Caracteristica {
    @Id
    @Column(name = "caracteristica_id", nullable = false, length = 160)
    private String caracteristicaId;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    private Caracteristica padre;


}