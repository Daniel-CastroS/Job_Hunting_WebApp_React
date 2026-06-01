package data;

import logic.Oferente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OferenteRepository extends CrudRepository<Oferente, String> {
    Oferente findByCorreo(String correo);
}
