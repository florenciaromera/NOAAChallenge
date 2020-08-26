package ar.com.ada.api.noaa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.models.requests.BoyaActualizarColor;
import ar.com.ada.api.noaa.models.requests.BoyaRequest;
import ar.com.ada.api.noaa.models.responses.GenericResponse;
import ar.com.ada.api.noaa.services.BoyaService;

@RestController
public class BoyaController {
    @Autowired
    BoyaService bS;

    @PostMapping("/boyas")
    public ResponseEntity<GenericResponse> crearBoya(@RequestBody BoyaRequest bR) {
        Boya boya = new Boya();
        boya.setLongitudInstalacion(bR.longitudInstalacion);
        boya.setLatitudInstalacion(bR.latitudInstalacion);
        bS.crearBoya(boya);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = boya.getBoyaId();
        gR.mensaje = "Boya creada con exito";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/boyas")
    public ResponseEntity<List<Boya>> listarBoyas() {
        return ResponseEntity.ok(bS.obtenerBoyas());
    }

    @GetMapping("/boyas/{id}")
    public ResponseEntity<Boya> obtenerInfoBoya(@PathVariable Integer id) {
        return ResponseEntity.ok(bS.obtenerPorId(id));
    }

    @PutMapping("/boyas/{id}")
    public ResponseEntity<GenericResponse> actualizarColor(@PathVariable Integer id,
            @RequestBody BoyaActualizarColor bColor) {
        Boya boya = bS.obtenerPorId(id);
        if (boya == null) {
            return ResponseEntity.notFound().build();
        }
        boya.setColorLuz(bColor.color);
        bS.crearBoya(boya);

        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.mensaje = "Color de boya actualizado con éxito";
        gR.id = boya.getBoyaId();
        return ResponseEntity.ok(gR);
    }

}