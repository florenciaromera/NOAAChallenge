package ar.com.ada.api.noaa.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.models.requests.MuestraRequest;
import ar.com.ada.api.noaa.models.responses.*;
import ar.com.ada.api.noaa.services.*;

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
        return ResponseEntity
                .ok(ResponseMethodsMapper.crearMR(muestra.getMuestraId(), bService.getColor(mR.alturaNivelMar)));
    }

    @GetMapping("/muestras/boyas/{boyaId}")
    public ResponseEntity<List<Muestra>> listaMuestrasPorBoya(@PathVariable Integer boyaId) {
        List<Muestra> listaMuestrasPorBoya = bService.obtenerPorId(boyaId).getMuestras();
        if (listaMuestrasPorBoya.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaMuestrasPorBoya);
    }

    @GetMapping("/muestras/colores/{color}")
    public ResponseEntity<List<MuestraColorResponse>> listaMuestrasPorColor(@PathVariable String color) {
        List<Muestra> listaMuestrasPorColor = mService.obtenerPorColor(color);
        if (listaMuestrasPorColor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<MuestraColorResponse> listaMuestrasPorColorResponse = ResponseMethodsMapper
                .crearListaMuestraColorResponse(listaMuestrasPorColor);
        return ResponseEntity.ok(listaMuestrasPorColorResponse);
    }

    @GetMapping("/muestras/minima/{boyaId}")
    public ResponseEntity<MuestraAlturaMinResponse> alturaMarMinima(@PathVariable Integer boyaId) {
        Muestra m = mService.getAlturaMin(boyaId);
        if (m == null) {
            return ResponseEntity.notFound().build();
        }
        MuestraAlturaMinResponse mAMR = ResponseMethodsMapper.crearAlturaMinResponse(m.getAlturaNivelMar(),
                m.getHorarioMuestra(), m.getBoya().getColorLuz());
        return ResponseEntity.ok(mAMR);
    }

    @GetMapping("/muestras/anomalias/{boyaId}")
    public ResponseEntity<MuestraAnomaliaResponse> anomalias(@PathVariable Integer boyaId) {
        Optional<Anomalia> anomalia = mService.getAnomalia(boyaId);
        if (anomalia.isPresent()) {
            MuestraAnomaliaResponse mAR = ResponseMethodsMapper.crearMuestraAnomaliaResp(
                    anomalia.get().getAlturaMarActual(), anomalia.get().getHorarioInicio(),
                    anomalia.get().getHorarioFin(), anomalia.get().getTipoAlerta());
            return ResponseEntity.ok(mAR);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/muestras/{id}")
    public ResponseEntity<GenericResponse> borrarColor(@PathVariable Integer id) {
        if (mService.borrarMuestra(id)) {
            return ResponseEntity.ok(ResponseMethodsMapper.crearGR(true,
                    "La muestra con id " + id + " ha sido eliminada con éxito", id));
        }
        return ResponseEntity.notFound().build();
    }
}