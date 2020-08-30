package ar.com.ada.api.noaa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.services.BoyaService;
import ar.com.ada.api.noaa.services.MuestraService;

@SpringBootTest
class NoaaApplicationTests {
	@Autowired
	MuestraService mService;

	@Autowired
	BoyaService bService;

	@Test
	void muestraChequearAlturaRoja() {
		String colorResultante = bService.getColor(200);
		assertEquals("ROJO", colorResultante);
	}

	
	// - Validar que una boya de color ROJO tenga la ultima muestra en -100 o +100
	// - Validar que las coordenadas esten dentro del rango del planeta
	// - Validar que las coordenadas NO esten dentro del rango del planeta
	// - Validar que las coordenadas esten en el hemisferio norte.
}
