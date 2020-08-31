package ar.com.ada.api.noaa.models.responses;

import java.util.Date;

public class MuestraColorResponse {
    public Integer boyaId;
    public Date horario;
    public Double alturaNivelDelMar;

    public MuestraColorResponse(Integer boyaId, Date horario, Double alturaNivelDelMar){
        this.boyaId = boyaId;
        this.horario = horario;
        this.alturaNivelDelMar = alturaNivelDelMar;
    }
}