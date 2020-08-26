package ar.com.ada.api.noaa.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.repos.MuestraRepo;

@Service
public class MuestraService {
    @Autowired
    MuestraRepo repo;

    @Autowired
    BoyaService bService;

    public Muestra crearMuestra(Integer boyaId, Double alturaNivelMar, Date horario, Double latitud, Double longitud,
            String matricula) {
        Muestra muestra = new Muestra();
        muestra.setBoya(bService.obtenerPorId(boyaId));
        muestra.setAlturaNivelMar(alturaNivelMar);
        muestra.setHorarioMuestra(horario);
        muestra.setLatitud(latitud);
        muestra.setLongitud(longitud);
        muestra.setMatriculaEmbarcacion(matricula);
        grabar(muestra);
        return muestra;
    }

    public List<Muestra> obtenerMuestras() {
        return repo.findAll();
    }

    public Muestra obtenerPorId(Integer id) {
        Optional<Muestra> opMuestra = repo.findById(id);
        if (opMuestra.isPresent()) {
            return opMuestra.get();
        }
        return null;
    }

    public void grabar(Muestra muestra) {
        repo.save(muestra);
    }

    // public List<Muestra> obtenerPorColor(String color) {

    // return null;
    // }

}