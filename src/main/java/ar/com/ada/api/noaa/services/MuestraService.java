package ar.com.ada.api.noaa.services;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.repos.MuestraRepo;

@Service
public class MuestraService {
    private final static String ROJO = "ROJO";
    private final static String AMARILLO = "AMARILLO";
    private final static String AZUL = "AZUL";
    private final static String ALERTA_IMPACTO = "ALERTA DE IMPACTO";
    private final static String ALERTA_KAIJUN = "ALERTA DE KAIJUN";
    private final static Integer IMPACTO = 500;

    @Autowired
    MuestraRepo repo;

    @Autowired
    BoyaService bService;

    public Optional<Muestra> crearMuestra(Integer boyaId, Double alturaNivelMar, Date horario, Double latitud,
            Double longitud, String matricula) {
        Optional<Boya> bOp = bService.obtenerPorId(boyaId);
        if (bOp.isPresent()) {
            bOp.get().setColorLuz(bService.getColor(alturaNivelMar));
            Muestra muestra = new Muestra(bOp.get(), alturaNivelMar, horario, latitud, longitud, matricula);
            grabar(muestra);
            return Optional.of(muestra);
        }
        return Optional.empty();

    }

    public List<Muestra> obtenerMuestras() {
        return repo.findAll();
    }

    public Optional<Muestra> obtenerPorId(Integer id) {
        Optional<Muestra> opMuestra = repo.findById(id);
        if (opMuestra.isPresent()) {
            return opMuestra;
        }
        return Optional.empty();
    }

    public void grabar(Muestra muestra) {
        repo.save(muestra);
    }

    public List<Muestra> obtenerPorColor(String color) {
        List<Muestra> muestras;
        if (color.equalsIgnoreCase(ROJO)) {
            muestras = repo.findAllRed();
        } else if (color.equalsIgnoreCase(AMARILLO)) {
            muestras = repo.findAllYellow();
        } else
            muestras = repo.findAllGreen();
        return muestras;
    }

    public Muestra getAlturaMin(Integer boyaId) {
        return repo.findByAlturaMin(boyaId);
    }

    public boolean borrarMuestra(Integer id) {
        Optional<Muestra> mOp = obtenerPorId(id);
        if (mOp.isEmpty()) {
            return false;
        }
        mOp.get().getBoya().setColorLuz(AZUL);
        grabar(mOp.get());
        return true;
    }

    public Optional<Anomalia> getAnomalia(Integer boyaId) {
        ArrayList<Muestra> muestras = repo.findMuestrasAbsolutasByBoyaId(boyaId);
        if (muestras.size() < 2) {
            return Optional.empty();
        }

        Anomalia anomalia = null;

        Muestra mfinal = repo.ultimaMuestra(boyaId).get(0);

        for (int i = 0; i < muestras.size() - 1; i++) {
            if (nivelMarMayor500(muestras.get(i), muestras.get(i + 1))) {
                anomalia = new Anomalia(mfinal.getAlturaNivelMar(), muestras.get(i + 1).getHorarioMuestra(),
                        muestras.get(i).getHorarioMuestra(), ALERTA_IMPACTO);
                break;
            }
        }

        Muestra primeraMuestraMayor200 = null;
        for (Muestra m : muestras) {
            if (Math.abs(m.getAlturaNivelMar()) >= 200) {
                if (primeraMuestraMayor200 == null) {
                    primeraMuestraMayor200 = m;
                } else {
                    // pasar a función y que devuelva un boolean para el if
                    final long difHoraria = m.getHorarioMuestra().getTime()
                            - primeraMuestraMayor200.getHorarioMuestra().getTime();
                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(difHoraria);
                    if (minutes >= 10) {
                        anomalia = new Anomalia(mfinal.getAlturaNivelMar(), primeraMuestraMayor200.getHorarioMuestra(),
                                m.getHorarioMuestra(), ALERTA_KAIJUN);
                        break;
                    }
                }
            } else {
                primeraMuestraMayor200 = null;
            }
        }

        return anomalia != null ? Optional.of(anomalia) : Optional.empty();
    }

    private boolean nivelMarMayor500(Muestra muestraAnterior, Muestra muestraActual) {
        return Math.abs(muestraAnterior.getAlturaNivelMar()) + Math.abs(muestraActual.getAlturaNivelMar()) >= IMPACTO;
    }
}