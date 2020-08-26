package ar.com.ada.api.noaa.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaa.entities.Muestra;

@Repository
public interface MuestraRepo extends JpaRepository<Muestra, Integer> {
    Optional<Muestra> findById(Integer id);
}