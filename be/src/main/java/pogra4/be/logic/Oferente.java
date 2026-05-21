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
@Table(name = "oferente")
public class Oferente {
    @Id
    @Column(name = "id", nullable = false, length = 160)
    private String id;

    @Column(name = "correo", length = 45)
    private String correo;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @Column(name = "primerApellido", length = 45)
    private String primerApellido;

    @Column(name = "ubicacion", length = 45)
    private String ubicacion;

    @Column(name = "nacionalidad", length = 45)
    private String nacionalidad;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "contrasenna")
    private String contrasenna;

    @Column(name = "curriculum")
    private String curriculum;

    @Column(name = "estado", length = 45)
    private String estado;


}