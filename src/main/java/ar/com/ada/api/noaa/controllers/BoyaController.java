package ar.com.ada.api.noaa.controllers;

import java.util.List;

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
        Boya boya;
        boya = bS.crearBoya(bR.latitudInstalacion, bR.longitudInstalacion);
        if(boya == null){
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = ResponseMethodsMapper.crearGR(true, "Boya creada con exito", boya.getBoyaId());
        return ResponseEntity.ok(gR);
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
        Boya boya = bS.obtenerPorId(id);
        if(boya == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boya);
    }

    @PutMapping("/boyas/{id}")
    public ResponseEntity<GenericResponse> actualizarColor(@PathVariable Integer id,
            @RequestBody BoyaActualizarColor bColor) {
        Boya boya = bS.actualizarColor(id, bColor.color);
        if (boya == null) {
            return ResponseEntity.notFound().build();
        }

        GenericResponse gR = ResponseMethodsMapper.crearGR(true, "Color de boya actualizado con exito", boya.getBoyaId());
        return ResponseEntity.ok(gR);
    }

}