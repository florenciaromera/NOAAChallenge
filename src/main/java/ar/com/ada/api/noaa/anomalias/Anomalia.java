package ar.com.ada.api.noaa.anomalias;

import java.util.Date;

public class Anomalia {
    private Double alturaMarActual;
    private Date horarioInicio;
    private Date horarioFin;
    private String tipoAlerta;

    public Anomalia(){

    }
    
    public Anomalia(Double alturaMarActual, Date horarioInicio, Date horarioFin, String tipoAlerta){
        this.alturaMarActual = alturaMarActual;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.tipoAlerta = tipoAlerta;
    }

    public Double getAlturaMarActual() {
        return alturaMarActual;
    }

    public void setAlturaMarActual(Double alturaMarActual) {
        this.alturaMarActual = alturaMarActual;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Date getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(Date horarioFin) {
        this.horarioFin = horarioFin;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }
}