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
        List<Muestra> muestrasIMPACTO = new ArrayList<>();
        Muestra mAnterior = new Muestra();
        mAnterior.setAlturaNivelMar(500.0);
        mAnterior.setHorarioMuestra(new Date());
        Muestra mActual = new Muestra();
        mActual.setAlturaNivelMar(-300.0);
        mActual.setHorarioMuestra(new Date());
        muestrasIMPACTO.add(mAnterior);
        muestrasIMPACTO.add(mActual);

        List<Muestra> ultimaIMPACTO = new ArrayList<>();
        ultimaIMPACTO.add(mActual);

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasIMPACTO);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaIMPACTO);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        
        assertEquals("ALERTA DE IMPACTO", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_SUCCESS(){
        List<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>();
        Muestra m1 = new Muestra();
        m1.setAlturaNivelMar(230.0);
        m1.setHorarioMuestra(new Date());
        Muestra m2 = new Muestra();
        m2.setAlturaNivelMar(90.0);
        LocalDateTime dtActualizada = LocalDateTime.of(2021,2,20,21,46,31);
        Date dt = Date.from(dtActualizada.atZone(ZoneId.systemDefault()).toInstant());
        m2.setHorarioMuestra(dt);
        
        muestrasNULL_ANOMALIA.add(m1);
        muestrasNULL_ANOMALIA.add(m2);
        
        List<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>();
        ultimaNULL_ANOMALIA.add(m2);

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);
        
        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        
        assertEquals("ALERTA DE KAIJUN", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_FAILED(){
        List<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>();
        Muestra m1 = new Muestra();
        m1.setAlturaNivelMar(30.0);
        m1.setHorarioMuestra(new Date());
        Muestra m2 = new Muestra();
        m2.setAlturaNivelMar(90.0);
        m2.setHorarioMuestra(new Date());
        muestrasNULL_ANOMALIA.add(m1);
        muestrasNULL_ANOMALIA.add(m2);
        
        List<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>();
        ultimaNULL_ANOMALIA.add(m2);

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);
        
        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        
        assertEquals(false, anomalia.isPresent());
    }
}