package ar.com.ada.api.noaa.models.responses;

public class GenericResponse {
    public boolean isOk;
    public Integer id;
    public String mensaje;

    public GenericResponse(boolean isOk, Integer id, String mensaje){
        this.isOk = isOk;
        this.id = id;
        this.mensaje = mensaje;
    }
}