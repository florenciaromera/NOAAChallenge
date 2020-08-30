package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.services.BoyaService;

@SpringBootTest
public class BoyaServiceTest {
    @Autowired
    BoyaService bService;

    @Test
    void muestraObtenerRoja() {
		String colorResultante = bService.getColor(200);
		assertEquals("ROJO", colorResultante);
    }

    @Test
    void muestraObtenerAmarillo(){
        String colorResultante = bService.getColor(50);
        assertEquals("AMARILLO", colorResultante);
    }

    @Test
    void muestraNoObtenerAmarillo(){
        String colorResultante = bService.getColor(120);
        assertNotEquals("AMARILLO", colorResultante);
    }

}