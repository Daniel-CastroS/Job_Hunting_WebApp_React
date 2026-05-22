package pogra4.be.data;
import pogra4.be.logic.Caracteristica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends CrudRepository<Caracteristica, String> {
    Iterable<Caracteristica> findByPadreIsNull();
    Iterable<Caracteristica> findByPadre_CaracteristicaId(String padreId);
}