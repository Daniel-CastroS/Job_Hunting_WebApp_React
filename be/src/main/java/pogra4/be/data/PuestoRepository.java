package pogra4.be.data;

import pogra4.be.logic.Puesto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PuestoRepository extends CrudRepository<Puesto, String> {
    List<Puesto> findTop5ByTipoAndActivoOrderByFechaRegistroDesc(String tipo, Byte activo);
    Iterable<Puesto> findByEmpresa_Id(String empresaId);
}