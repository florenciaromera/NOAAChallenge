package ar.com.ada.api.noaa.models.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.ada.api.noaa.anomalias.Anomalia;
import ar.com.ada.api.noaa.entities.Muestra;

public class ResponseMethodsMapper {
    public static GenericResponse crearGR(boolean isOk, String mensaje, Integer id) {
        return new GenericResponse(isOk, id, mensaje);
    }

    public static MuestraResponse crearMR(Integer id, String color) {
        return new MuestraResponse(id, color);
    }

    public static List<MuestraColorResponse> crearListaMuestraColorResponse(List<Muestra> listaMuestrasPorColor) {
        List<MuestraColorResponse> listaMuestrasPorColorResponse = new ArrayList<>();
        for (Muestra m : listaMuestrasPorColor) {
            listaMuestrasPorColorResponse.add(new MuestraColorResponse(m.getBoya().getBoyaId(), m.getHorarioMuestra(),
            m.getAlturaNivelMar()));
        }
        return listaMuestrasPorColorResponse;
    }

    public static MuestraAlturaMinResponse crearAlturaMinResponse(Double alturaNivelMar, Date horarioMuestra,
            String color) {
        return new MuestraAlturaMinResponse(alturaNivelMar, horarioMuestra, color);
    }

    public static MuestraAnomaliaResponse crearMuestraAnomaliaResp(Anomalia anomalia) {
        return new MuestraAnomaliaResponse(anomalia.getAlturaMarActual(), anomalia.getHorarioInicio(),
                anomalia.getHorarioFin(), anomalia.getTipoAlerta());
    }
}