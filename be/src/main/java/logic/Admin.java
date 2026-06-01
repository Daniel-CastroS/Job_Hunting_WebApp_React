package logic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Size(max = 160)
    @Column(name = "id", nullable = false, length = 160)
    private String id;

    @Size(max = 45)
    @Column(name = "correo", length = 45)
    private String correo;

    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "telefono")
    private Integer telefono;

    @Size(max = 255)
    @Column(name = "contrasenna")
    private String contrasenna;

}