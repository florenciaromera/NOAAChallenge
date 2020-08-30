package ar.com.ada.api.noaa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.models.requests.*;
import ar.com.ada.api.noaa.models.responses.*;
import ar.com.ada.api.noaa.services.BoyaService;

@RestController
public class BoyaController {
    @Autowired
    BoyaService bS;

    @PostMapping("/boyas")
    public ResponseEntity<GenericResponse> crearBoya(@RequestBody BoyaRequest bR) {
        Optional<Boya> boyaOp = bS.crearBoya(bR.latitudInstalacion, bR.longitudInstalacion);
        if(boyaOp.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ResponseMethodsMapper.crearGR(true, "Boya creada con exito", boyaOp.get().getBoyaId()));
    }

    @GetMapping("/boyas")
    public ResponseEntity<List<Boya>> listarBoyas() {
        List<Boya> boyas = bS.obtenerBoyas();
        if(boyas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boyas);
    }

    @GetMapping("/boyas/{id}")
    public ResponseEntity<Boya> obtenerInfoBoya(@PathVariable Integer id) {
        Optional<Boya> boyaOp = bS.obtenerPorId(id);
        if(boyaOp.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boyaOp.get());
    }

    @PutMapping("/boyas/{id}")
    public ResponseEntity<GenericResponse> actualizarColor(@PathVariable Integer id,
            @RequestBody BoyaActualizarColor bColor) {
        Optional<Boya> boyaOp = bS.actualizarColor(id, bColor.color);
        if (boyaOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ResponseMethodsMapper.crearGR(true, "Color de boya actualizado con exito", boyaOp.get().getBoyaId()));
    }

}