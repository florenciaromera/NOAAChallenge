package ar.com.ada.api.noaa.utilsTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.utils.GeoUtils;

@SpringBootTest
class GeoUtilsTests {
    
    @Test
	void chequearRangoPlanetarioEsFalsoConLatitudExcedida(){
		boolean fueraRango = GeoUtils.chequearRangoPlanetario(-91.0, 170.0);
		assertFalse(fueraRango);
    }
	
	@Test
	void chequearRangoPlanetarioEsFalsoConLongitudExcedida(){
		boolean fueraRango = GeoUtils.chequearRangoPlanetario(30.0, 190.0);
		assertFalse(fueraRango);
	} 
	
    @Test
	void chequearDentroRangoPlanetario(){
		boolean dentroRango = GeoUtils.chequearRangoPlanetario(80.0, 170.0);
		assertTrue(dentroRango);
    }
    
}