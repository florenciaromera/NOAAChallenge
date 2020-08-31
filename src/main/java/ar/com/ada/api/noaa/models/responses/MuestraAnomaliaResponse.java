package ar.com.ada.api.noaa.models.responses;

import java.util.Date;

public class MuestraAnomaliaResponse {
    public Double alturaNivelMarActual;
    public Date horarioInicioAnomalia;
    public Date horarioFinAnomalia;
    public String tipoAlerta;

    public MuestraAnomaliaResponse(Double alturaNivelMarActual, Date horarioInicio, Date horarioFin, String tipoAlerta){
        this.alturaNivelMarActual = alturaNivelMarActual;
        this.horarioInicioAnomalia = horarioInicio;
        this.horarioFinAnomalia = horarioFin;
        this.tipoAlerta = tipoAlerta;
    }
}