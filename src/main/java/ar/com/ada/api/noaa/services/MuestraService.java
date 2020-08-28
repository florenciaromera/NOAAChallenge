package ar.com.ada.api.noaa.services;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.responses.MuestraAlturaMinResponse;
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
        Boya b = bService.obtenerPorId(boyaId);
        b.setColorLuz(bService.getColor(alturaNivelMar));
        muestra.setBoya(b);
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

    public List<Muestra> obtenerPorColor(String color) {
        List<Muestra> muestras = new ArrayList<>();
        if (color.equalsIgnoreCase("ROJO")) {
            muestras = repo.findAllRed();
        } else if (color.equalsIgnoreCase("AMARILLO")) {
            muestras = repo.findAllYellow();
        } else
            muestras = repo.findAllGreen();
        return muestras;
    }

    public Muestra getAlturaMin(Integer boyaId) {
        return repo.findByAlturaMin(boyaId);
    }

    public Optional<Anomalia> getAnomalia(Integer boyaId) {
        // devolver alturaActual, horarioInicio, horarioFin, tipoAlerta
        Anomalia anomalia = new Anomalia();
        List<Muestra> muestras = repo.findMuestrasAbsolutasByBoyaId(boyaId);
        Muestra mInicial = muestras.get(0);
        Muestra mfinal = repo.ultimaMuestra(boyaId).get(0);
        Boolean setearMuestraInicial = false;
        for (Muestra m : muestras) {
            if (setearMuestraInicial) {
                if (Math.abs(m.getAlturaNivelMar()) >= 200) {
                    mInicial = m;
                    setearMuestraInicial = false;
                }
                continue;
            }

            if (Math.abs(m.getAlturaNivelMar()) < 200) {
                long difHoraria = m.getHorarioMuestra().getTime() - mInicial.getHorarioMuestra().getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(difHoraria);
                if (minutes >= 10) {
                    anomalia.setAlturaMarActual(mfinal.getAlturaNivelMar());
                    anomalia.setHorarioInicio(mInicial.getHorarioMuestra());
                    anomalia.setHorarioFin(m.getHorarioMuestra());
                    anomalia.setTipoAlerta("KAIJUN");
                    break;
                }
                setearMuestraInicial = true;
            }
        }
        return Optional.of(anomalia);
    }
}