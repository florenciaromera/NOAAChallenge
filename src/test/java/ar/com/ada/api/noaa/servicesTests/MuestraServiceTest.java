package ar.com.ada.api.noaa.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Boya;
import ar.com.ada.api.noaa.entities.Muestra;
import ar.com.ada.api.noaa.repos.MuestraRepo;
import ar.com.ada.api.noaa.services.MuestraService;

@SpringBootTest
public class MuestraServiceTest {
    private final Boya BOYA = new Boya();

    private final LocalDateTime dt = LocalDateTime.of(2021, 2, 20, 21, 46, 31);

    @InjectMocks
    MuestraService mService;

    @Mock
    MuestraRepo repo;

    @Test
    void getAnomaliaALERTAIMPACTO_SUCCESS() {
        Muestra m1 = new Muestra(BOYA, 500.0, new Date(), 67.0, 120.0, "A34REW5");
        Muestra m2 = new Muestra(BOYA, -300.0, new Date(), 23.0, 78.0, "J763F8");
        ArrayList<Muestra> muestras = new ArrayList<>(List.of(m1, m2));
        ArrayList<Muestra> ultima = new ArrayList<>(List.of(m2));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestras);
        when(repo.ultimaMuestra(1)).thenReturn(ultima);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE IMPACTO", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_SUCCESS() {
        Muestra m1 = new Muestra(BOYA, 230.0, new Date(), 67.0, 120.0, "A34REW5");
        Muestra m2 = new Muestra(BOYA, -290.0, Date.from(dt.atZone(ZoneId.systemDefault()).toInstant()), 67.0, 120.0,
                "A34REW5");
        ArrayList<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>(List.of(m1, m2));
        ArrayList<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>(List.of(m2));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals("ALERTA DE KAIJUN", anomalia.get().getTipoAlerta());
    }

    @Test
    void getAnomaliaKAIJUN_FAILED() {
        Muestra m1 = new Muestra(BOYA, 30.0, new Date(), 67.0, 120.0, "A34REW5");
        Muestra m2 = new Muestra(BOYA, 90.0, new Date(), 67.0, 120.0, "A34REW5");
        ArrayList<Muestra> muestrasNULL_ANOMALIA = new ArrayList<>(List.of(m1, m2));
        ArrayList<Muestra> ultimaNULL_ANOMALIA = new ArrayList<>(List.of(m2));

        when(repo.findMuestrasAbsolutasByBoyaId(1)).thenReturn(muestrasNULL_ANOMALIA);
        when(repo.ultimaMuestra(1)).thenReturn(ultimaNULL_ANOMALIA);

        Optional<Anomalia> anomalia = mService.getAnomalia(1);
        assertEquals(false, anomalia.isPresent());
    }
}