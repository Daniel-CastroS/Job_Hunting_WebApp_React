package pogra4.be.logic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "id", nullable = false, length = 160)
    private String id;

    @Column(name = "correo", length = 45)
    private String correo;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "contrasenna")
    private String contrasenna;


}