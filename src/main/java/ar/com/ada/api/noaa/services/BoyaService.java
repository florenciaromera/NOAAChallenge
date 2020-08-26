package ar.com.ada.api.noaa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.repos.BoyaRepo;

@Service
public class BoyaService {
    @Autowired
    BoyaRepo boyaRepo;

    public void crearBoya(Boya boya) {
        boyaRepo.save(boya);
    }

    public List<Boya> obtenerBoyas() {
        return boyaRepo.findAll();
    }

    public Boya obtenerPorId(Integer boyaId) {
        Optional<Boya> b = boyaRepo.findById(boyaId);
        if (b.isPresent())
            return b.get();
        return null;

    }

    public String getColor(double alturaNivelMar) {
        String color = "VERDE";
        if (Math.abs(alturaNivelMar) <= -100 || Math.abs(alturaNivelMar) >= 100) {
            color = "ROJO";
        } else if ((Math.abs(alturaNivelMar) <= -50 && Math.abs(alturaNivelMar) > -100)
                || (Math.abs(alturaNivelMar) >= 50 && Math.abs(alturaNivelMar) < 100)) {
            color = "AMARILLO";
        }
        return color;
    }

}