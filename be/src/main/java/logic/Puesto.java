package logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "puesto")
public class Puesto {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;

    @Size(max = 45)
    @Column(name = "salario", length = 45)
    private String salario;

    @Column(name = "activo")
    private Byte activo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @Size(max = 45)
    @Column(name = "tipo", length = 45)
    private String tipo;

}