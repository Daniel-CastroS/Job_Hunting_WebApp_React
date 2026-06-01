package data;

import logic.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends CrudRepository<Empresa, String> {
    Empresa findByCorreo(String correo);
}
