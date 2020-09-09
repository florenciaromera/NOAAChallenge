package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.services.BoyaService;

@SpringBootTest
public class BoyaServiceTest {
    @Autowired
    BoyaService bService;

    @Test
    void crearBoyaConLatitudCorrecta_SUCCESS(){
        Optional<Boya> boya = bService.crearBoya(89.0, 130.0);
        assertTrue(boya.get().getLatitudInstalacion().equals(89.0));
    }

    @Test
    void crearBoyaConLongitudCorrecta_SUCCESS(){
        Optional<Boya> boya = bService.crearBoya(89.0, 180.0);
        assertEquals(180.0, boya.get().getLongitudInstalacion());
    }

    @Test
    void crearBoyaLatitudIncorrecta_FAILED(){
        Optional<Boya> boyaOp = bService.crearBoya(100.0, 170.0);
        assertEquals(Optional.empty(), boyaOp);
    }

    @Test
    void crearBoyaLongitudIncorrecta_FAILED(){
        Optional<Boya> boyaOp = bService.crearBoya(50.0, 190.0);
        assertEquals(Optional.empty(), boyaOp);
    }

    @Test
    void muestraObtenerRoja_SUCCESS() {
		String colorResultante = bService.getColor(200);
		assertEquals("ROJO", colorResultante);
    }

    @Test
    void muestraObtenerAmarillo_SUCCESS(){
        String colorResultante = bService.getColor(50);
        assertEquals("AMARILLO", colorResultante);
    }

    @Test
    void muestraObtenerAmarillo_FAILED(){
        String colorResultante = bService.getColor(120);
        assertNotEquals("AMARILLO", colorResultante);
    }

    @Test
    void muestraObtenerVerde_SUCCESS(){
        String colorResultante = bService.getColor(20);
        assertEquals("VERDE", colorResultante);
    }

    @Test
    void muestraObtenerVerde_FAILED(){
        String colorResultante = bService.getColor(-60);
        assertNotEquals("VERDE", colorResultante);
    }
}