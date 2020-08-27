package ar.com.ada.api.noaa.repos;

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

    @Query("select m from Muestra m where (m.alturaNivelMar between -100 and -50) or (m.alturaNivelMar between 500 and 100)")
    List<Muestra> findAllYellow();

    @Query("select m from Muestra m where m.alturaNivelMar between -50 and 50")
    List<Muestra> findAllGreen();
}