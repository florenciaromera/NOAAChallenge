package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.repos.MuestraRepo;
import ar.com.ada.api.noaa.services.MuestraService;

/**
 * TestMethodOrder is implemented bec the value of the variables
 * used in one test are set for the next one
 */
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class MuestraServiceTest {
    private final Boya BOYA = new Boya();
    private final LocalDateTime dt = LocalDateTime.of(2021, 2, 20, 21, 46, 31);

    private Muestra m1;
    private Muestra m2;

    private ArrayList<Muestra> muestras;
    private ArrayList<Muestra> ultima;

    @InjectMocks
    MuestraService mService;

    @Mock
    MuestraRepo repo;

    @BeforeAll
    public void setUp() {
        m1 = new Muestra(BOYA, 500.0, new Date(), 67.0, 120.0, "A34REW5");
        m2 = new Muestra(BOYA, -300.0, new Date(), 23.0, 78.0, "J763F8");
        muestras = new ArrayList<>(List.of(m1, m2));
        ultima = new ArrayList<>(List.of(m2));
    }

    
    @Test
    @Order(1)
    void getAnomaliaALERTAIMPACTO_SUCCESS() {
        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestras);
        when(repo.ultimaMuestra(1)).thenReturn(ultima);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE IMPACTO", anomalia.get().getTipoAlerta());
    }

    @Test
    @Order(2)
    void getAnomaliaKAIJUN_SUCCESS() {
        m1.setAlturaNivelMar(230d);
        m2.setAlturaNivelMar(-290d);
        m2.setHorarioMuestra(Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()));
        m2.setLatitud(67d);
        m2.setLongitud(120d);

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestras);
        when(repo.ultimaMuestra(1)).thenReturn(ultima);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE KAIJUN", anomalia.get().getTipoAlerta());
    }
    /**
     * The value of the m2 variables depends on the previous test 
     */
    @Test
    @Order(3)
    void getAnomaliaKAIJUN_FAILED() {
        m1.setAlturaNivelMar(30d);
        m2.setAlturaNivelMar(90d);
        
        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestras);
        when(repo.ultimaMuestra(1)).thenReturn(ultima);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals(false, anomalia.isPresent());
    }
}