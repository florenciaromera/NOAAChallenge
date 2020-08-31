package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Optional<Boya> boya = bService.crearBoya(100.8, 130.0);
        assertTrue(boya.get().getLatitudInstalacion().equals(100.8));
    }

    void crearBoyaConLongitudCorrecta_SUCCESS(){
        Optional<Boya> boya = bService.crearBoya(100.8, 130.0);
        assertEquals(130.0, boya.get().getLongitudInstalacion());
    }

    @Test
    void crearBoyaValidacion_FAILED(){
        Optional<Boya> boyaOp = bService.crearBoya(92.0, 181.0);
        assertEquals(null, boyaOp);
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

}