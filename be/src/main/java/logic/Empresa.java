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
@Table(name = "empresa")
public class Empresa {
    @Id
    @Size(max = 160)
    @Column(name = "id", nullable = false, length = 160)
    private String id;

    @Size(max = 45)
    @Column(name = "correo", length = 45)
    private String correo;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;

    @Size(max = 120)
    @Column(name = "ubicacion", length = 120)
    private String ubicacion;

    @Column(name = "telefono")
    private Integer telefono;

    @Size(max = 255)
    @Column(name = "contrasenna")
    private String contrasenna;

    @Size(max = 45)
    @Column(name = "estado", length = 45)
    private String estado;

    public boolean isAprobada(){
        return this.estado.equals("aprobada");
    }

}