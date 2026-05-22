package pogra4.be.data;

import pogra4.be.logic.PuestoHasCaracteristica;
import pogra4.be.logic.PuestoHasCaracteristicaId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuestoHasCaracteristicaRepository extends CrudRepository<PuestoHasCaracteristica, PuestoHasCaracteristicaId> {
}