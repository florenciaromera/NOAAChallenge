package ar.com.ada.api.noaa.repos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaa.entities.Muestra;

@Repository
public interface MuestraRepo extends JpaRepository<Muestra, Integer> {
    Optional<Muestra> findById(Integer id);

    @Query("select m from Muestra m where m.alturaNivelMar not between -100 and 100")
    List<Muestra> findAllRed();

    @Query("select m from Muestra m where (m.alturaNivelMar between -100 and -50) or (m.alturaNivelMar between 50 and 100)")
    List<Muestra> findAllYellow();

    @Query("select m from Muestra m where m.alturaNivelMar between -50 and 50")
    List<Muestra> findAllGreen();

    @Query("select m from Muestra m where m.boya.boyaId=:boyaId and m.alturaNivelMar=(select min(m.alturaNivelMar) from m where m.boya.boyaId=:boyaId)")
    Muestra findByAlturaMin(Integer boyaId);

    @Query("select m from Muestra m where m.boya.boyaId=:boyaId and m.horarioMuestra between (select min(m.horarioMuestra) from m where m.alturaNivelMar >= 200 "
            + "or m.alturaNivelMar <= -200) and (select max(m.horarioMuestra) from m where m.alturaNivelMar >= 200 or m.alturaNivelMar <= -200)")
    ArrayList<Muestra> findMuestrasAbsolutasByBoyaId(Integer boyaId);

    @Query("select m from Muestra m where m.boya.boyaId=:boyaId order by m.muestraId desc")
    List<Muestra> ultimaMuestra(Integer boyaId);
}