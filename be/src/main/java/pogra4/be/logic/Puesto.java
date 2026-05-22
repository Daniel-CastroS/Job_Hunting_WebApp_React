package pogra4.be.logic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "puesto")
public class Puesto {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "descripcion", length = 45)
    private String descripcion;

    @Column(name = "salario", length = 45)
    private String salario;

    @Column(name = "activo")
    private Byte activo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @Column(name = "tipo", length = 45)
    private String tipo;

    @OneToMany(mappedBy = "puesto", fetch = FetchType.EAGER)
    private List<PuestoHasCaracteristica> caracteristicas = new ArrayList<>();


}