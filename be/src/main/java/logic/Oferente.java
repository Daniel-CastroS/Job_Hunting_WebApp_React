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
@Table(name = "oferente")
public class Oferente {
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

    @Size(max = 45)
    @Column(name = "primerApellido", length = 45)
    private String primerApellido;

    @Size(max = 45)
    @Column(name = "ubicacion", length = 45)
    private String ubicacion;

    @Size(max = 45)
    @Column(name = "nacionalidad", length = 45)
    private String nacionalidad;

    @Column(name = "telefono")
    private Integer telefono;

    @Size(max = 255)
    @Column(name = "contrasenna")
    private String contrasenna;

    @Size(max = 255)
    @Column(name = "curriculum")
    private String curriculum;

    @Size(max = 45)
    @Column(name = "estado", length = 45)
    private String estado;

    public boolean isAprobado(){
        return this.estado.equals("aprobado");
    }

}