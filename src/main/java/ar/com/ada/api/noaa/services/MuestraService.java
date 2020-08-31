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
    @Autowired
    MuestraRepo repo;

    @Autowired
    BoyaService bService;

    public Optional<Muestra> crearMuestra(Integer boyaId, Double alturaNivelMar, Date horario, Double latitud, Double longitud,
            String matricula) {
        Optional<Boya> bOp = bService.obtenerPorId(boyaId);
        if(bOp.isPresent()){
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

    public boolean borrarMuestra(Integer id){
        Optional<Muestra> mOp = obtenerPorId(id);
        if(mOp.isEmpty()){
            return false;
        }
        mOp.get().getBoya().setColorLuz("AZUL");
        grabar(mOp.get());
        return true;
    }

    public Optional<Anomalia> getAnomalia(Integer boyaId) {
        Anomalia anomalia = null;

        List<Muestra> muestras = repo.findMuestrasAbsolutasByBoyaId(boyaId);
        Muestra mInicial = muestras.get(0);
        Muestra mfinal = repo.ultimaMuestra(boyaId).get(0);
        
        Boolean setearMuestraInicial = false;
        Muestra mAnterior = null;
        Muestra ultimaMuestra = muestras.get(muestras.size()-1);
        
        for (Muestra m : muestras) {
            if (mAnterior == null) {
                mAnterior = m;
            } else if (nivelMarMayor500(mAnterior.getAlturaNivelMar(), m.getAlturaNivelMar())) {
                anomalia = new Anomalia(mfinal.getAlturaNivelMar(), mAnterior.getHorarioMuestra(),
                        m.getHorarioMuestra(), "ALERTA DE IMPACTO");
                break;
            } else {
                mAnterior = m;
            }

            if (setearMuestraInicial) {
                if (Math.abs(m.getAlturaNivelMar()) >= 200) {
                    mInicial = m;
                    setearMuestraInicial = false;
                }
                continue;
            }

            if ((Math.abs(m.getAlturaNivelMar()) < 200) || m.equals(ultimaMuestra)) {
                long difHoraria = m.getHorarioMuestra().getTime() - mInicial.getHorarioMuestra().getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(difHoraria);
                if (minutes >= 10) {
                    anomalia = new Anomalia(mfinal.getAlturaNivelMar(), mInicial.getHorarioMuestra(),
                            m.getHorarioMuestra(), "KAIJUN");
                    break;
                }
                setearMuestraInicial = true;
            }
        }       
        return anomalia != null ? Optional.of(anomalia) : Optional.empty();
    }

    private boolean nivelMarMayor500 (Double alturaAnterior, Double alturaActual){
        return (Math.abs(alturaAnterior) + Math.abs(alturaActual) >= 500) ? true : false;
    }

}