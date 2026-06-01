package data;

import logic.Puesto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuestoRepository extends CrudRepository<Puesto, String> {
}
