package ar.com.ada.api.noaa.anomalias;

import java.util.Date;

public class Anomalia {
    private final Double alturaMarActual;
    private final Date horarioInicio;
    private final Date horarioFin;
    private final String tipoAlerta;

    public Anomalia(Double alturaMarActual, Date horarioInicio, Date horarioFin, String tipoAlerta) {
        this.alturaMarActual = alturaMarActual;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.tipoAlerta = tipoAlerta;
    }

    public Double getAlturaMarActual() {
        return alturaMarActual;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public Date getHorarioFin() {
        return horarioFin;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }
}