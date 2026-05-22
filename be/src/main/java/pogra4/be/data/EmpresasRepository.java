package pogra4.be.data;

import pogra4.be.logic.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends CrudRepository<Empresa, String> {
    Empresa findByCorreo(String correo);
    Iterable<Empresa> findByEstado(String estado);
}
