package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.repos.MuestraRepo;
import ar.com.ada.api.noaa.services.MuestraService;

@SpringBootTest
public class MuestraServiceTest {
    @InjectMocks
    MuestraService mService;

    @Mock
    MuestraRepo repo;
    
    @Test
    void getAnomaliaALERTAIMPACTO_SUCCESS(){
        Muestra mAnterior = new Muestra(500.0, new Date());
        Muestra mActual = new Muestra(-300.0, new Date());
        List<Muestra> muestrasIMPACTO = new ArrayList<>(List.of(mAnterior, mActual));
        List<Muestra> ultimaIMPACTO = new ArrayList<>(List.of(mActual));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasIMPACTO);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaIMPACTO);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE IMPACTO", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_SUCCESS(){
        Muestra m1 = new Muestra(230.0, new Date());
        LocalDateTime dtActualizada = LocalDateTime.of(2021,2,20,21,46,31);
        Muestra m2 = new Muestra(90.0, Date.from(dtActualizada.atZone(ZoneId.systemDefault()).toInstant()));
        List<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>(List.of(m1,m2));        
        List<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>(List.of(m2));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);
        
        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE KAIJUN", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_FAILED(){
        Muestra m1 = new Muestra(30.0, new Date());
        Muestra m2 = new Muestra(90.0, new Date());
        List<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>(List.of(m1,m2));
        List<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>(List.of(m2));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);
        
        Optional<Anomalia> anomalia = mService.getAnomalia(1);        
        assertEquals(false, anomalia.isPresent());
    }
}