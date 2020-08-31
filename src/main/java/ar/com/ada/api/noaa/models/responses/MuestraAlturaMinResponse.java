package ar.com.ada.api.noaa.models.responses;

import java.util.Date;

public class MuestraAlturaMinResponse {
    public String color;
    public Double alturaNivelMar;
    public Date horario;

    public MuestraAlturaMinResponse(Double alturaNivelMar, Date horario, String color){
        this.alturaNivelMar = alturaNivelMar;
        this.horario = horario;
        this.color = color;
    }
}