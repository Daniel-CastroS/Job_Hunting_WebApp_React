package pogra4.be.data;

import pogra4.be.logic.OferenteHasCaracteristica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OferenteHasCaracteristicaRepository extends CrudRepository<OferenteHasCaracteristica, String> {
    List<OferenteHasCaracteristica> findByOferenteId(String oferenteId);
}
