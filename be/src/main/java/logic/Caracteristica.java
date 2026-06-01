package logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "caracteristica")
public class Caracteristica {
    @Id
    @Size(max = 160)
    @Column(name = "caracteristica_id", nullable = false, length = 160)
    private String caracteristicaId;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    private Caracteristica padre;

}