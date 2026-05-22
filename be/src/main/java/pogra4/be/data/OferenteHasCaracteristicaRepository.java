package pogra4.be.data;

import pogra4.be.logic.OferenteHasCaracteristica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OferenteHasCaracteristicaRepository extends CrudRepository<OferenteHasCaracteristica, String> {
    Iterable<OferenteHasCaracteristica> findByOferente_Id(String oferenteId);
}