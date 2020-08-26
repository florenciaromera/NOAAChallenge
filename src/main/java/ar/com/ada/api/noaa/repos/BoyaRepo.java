package ar.com.ada.api.noaa.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaa.entities.Boya;

@Repository
public interface BoyaRepo extends JpaRepository<Boya, Integer> {
}