package pogra4.be.data;

import pogra4.be.logic.Oferente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OferenteRepository extends CrudRepository<Oferente, String> {
    Oferente findByCorreo(String correo);
    Iterable<Oferente> findByEstado(String estado);
}
