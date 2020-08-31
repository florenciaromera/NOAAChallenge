package ar.com.ada.api.noaa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.repos.BoyaRepo;
import ar.com.ada.api.noaa.utils.GeoUtils;

@Service
public class BoyaService {
    private final static String ROJO = "ROJO";
    private final static String AMARILLO = "AMARILLO";
    private final static String VERDE = "VERDE";

    @Autowired
    BoyaRepo boyaRepo;

    public Optional<Boya> crearBoya(Double latitud, Double longitud) {
        if (GeoUtils.chequearRangoPlanetario(latitud, longitud)) {
            Boya boya = new Boya(latitud, longitud);
            return Optional.of(boyaRepo.save(boya));
        }
        return Optional.empty();
    }

    public List<Boya> obtenerBoyas() {
        return boyaRepo.findAll();
    }

    public Optional<Boya> obtenerPorId(Integer boyaId) {
        Optional<Boya> bOp = boyaRepo.findById(boyaId);
        if (bOp.isPresent())
            return bOp;
        return Optional.empty();

    }

    public String getColor(double alturaNivelMar) {
        String color = VERDE;
        if (Math.abs(alturaNivelMar) <= Math.abs(100)) {
            color = ROJO;
        } else if ((Math.abs(alturaNivelMar) <= Math.abs(50)) && (Math.abs(alturaNivelMar) > Math.abs(100))) {
            color = AMARILLO;
        }
        return color;
    }

	public Optional<Boya> actualizarColor(Integer id, String color) {
        Optional<Boya> boyaOp = obtenerPorId(id);
        if(boyaOp.isEmpty()){
            return Optional.empty();
        }
        boyaOp.get().setColorLuz(color);
        return Optional.of(boyaRepo.save(boyaOp.get()));
    }
}