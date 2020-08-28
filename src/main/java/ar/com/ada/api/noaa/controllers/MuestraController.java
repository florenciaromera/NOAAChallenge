package ar.com.ada.api.noaa.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.requests.MuestraRequest;
import ar.com.ada.api.noaa.models.responses.GenericResponse;
import ar.com.ada.api.noaa.models.responses.MuestraAlturaMinResponse;
import ar.com.ada.api.noaa.models.responses.MuestraAnomaliaResponse;
import ar.com.ada.api.noaa.models.responses.MuestraColorResponse;
import ar.com.ada.api.noaa.models.responses.MuestraResponse;
import ar.com.ada.api.noaa.services.BoyaService;
import ar.com.ada.api.noaa.services.MuestraService;

@RestController
public class MuestraController {
    @Autowired
    MuestraService mService;

    @Autowired
    BoyaService bService;

    @PostMapping("/muestras")
    public ResponseEntity<MuestraResponse> registrarMuestra(@RequestBody MuestraRequest mR) {
        Muestra muestra = mService.crearMuestra(mR.boyaId, mR.alturaNivelMar, mR.horario, mR.latitud, mR.longitud,
                mR.matricula);
        if (muestra == null) {
            return ResponseEntity.badRequest().build();
        }
        MuestraResponse mResp = new MuestraResponse();
        mResp.id = muestra.getMuestraId();
        mResp.color = bService.getColor(mR.alturaNivelMar);
        return ResponseEntity.ok(mResp);
    }

    @GetMapping("/muestras/boyas/{boyaId}")
    public ResponseEntity<List<Muestra>> listaMuestrasPorBoya(@PathVariable Integer boyaId) {
        List<Muestra> listaMuestrasPorBoya = bService.obtenerPorId(boyaId).getMuestras();
        return ResponseEntity.ok(listaMuestrasPorBoya);
    }

    @GetMapping("/muestras/colores/{color}")
    public ResponseEntity<List<MuestraColorResponse>> listaMuestrasPorColor(@PathVariable String color) {
        List<MuestraColorResponse> listaMuestrasPorColor = new ArrayList<>();
        for (Muestra m : mService.obtenerPorColor(color)) {
            MuestraColorResponse mCR = new MuestraColorResponse();
            mCR.boyaId = m.getBoya().getBoyaId();
            mCR.horario = m.getHorarioMuestra();
            mCR.alturaNivelDelMar = m.getAlturaNivelMar();
            listaMuestrasPorColor.add(mCR);
        }
        return ResponseEntity.ok(listaMuestrasPorColor);
    }

    @GetMapping("/muestras/minima/{boyaId}")
    public ResponseEntity<MuestraAlturaMinResponse> alturaMarMinima(@PathVariable Integer boyaId) {
        MuestraAlturaMinResponse mAMR = new MuestraAlturaMinResponse();
        Muestra m = mService.getAlturaMin(boyaId);
        mAMR.color = m.getBoya().getColorLuz();
        mAMR.alturaNivelMar = m.getAlturaNivelMar();
        mAMR.horario = m.getHorarioMuestra();
        return ResponseEntity.ok(mAMR);
    }

    @GetMapping("/muestras/anomalias/{boyaId}")
    public ResponseEntity<MuestraAnomaliaResponse> anomalias(@PathVariable Integer boyaId) {
        MuestraAnomaliaResponse mAR = new MuestraAnomaliaResponse();
        Optional<Anomalia> anomalia = mService.getAnomalia(boyaId);
        if (anomalia.isPresent()) {
            mAR.alturaNivelMarActual = anomalia.get().getAlturaMarActual();
            mAR.horarioInicioAnomalia = anomalia.get().getHorarioInicio();
            mAR.horarioFinAnomalia = anomalia.get().getHorarioFin();
            mAR.tipoAlerta = anomalia.get().getTipoAlerta();
            return ResponseEntity.ok(mAR);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/muestras/{id}")
    public ResponseEntity<GenericResponse> borrarColor(@PathVariable Integer id) {
        Muestra muestra = mService.obtenerPorId(id);
        if (muestra == null) {
            return ResponseEntity.notFound().build();
        }
        muestra.getBoya().setColorLuz("AZUL");
        mService.grabar(muestra);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = id;
        gR.mensaje = "La muestra con id " + id + " ha sido eliminada con éxito";
        return ResponseEntity.ok(gR);
    }

}