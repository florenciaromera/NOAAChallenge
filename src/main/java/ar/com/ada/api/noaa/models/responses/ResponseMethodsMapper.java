package ar.com.ada.api.noaa.models.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.ada.api.noaa.entities.Muestra;

public class ResponseMethodsMapper {
    public static GenericResponse crearGR(boolean isOk, String mensaje, Integer id){
        GenericResponse gR = new GenericResponse();
        gR.isOk = isOk;
        gR.mensaje = mensaje;
        gR.id = id;
        return gR;
    }

    public static MuestraResponse crearMR(Integer id, String color){
        MuestraResponse mR = new MuestraResponse();
        mR.id = id;
        mR.color = color;
        return mR;
    }

    public static List<MuestraColorResponse> crearListaMuestraColorResponse(List<Muestra> listaMuestrasPorColor){
        List<MuestraColorResponse> listaMuestrasPorColorResponse = new ArrayList<>();
        for (Muestra m : listaMuestrasPorColor) {
            MuestraColorResponse mCR = new MuestraColorResponse();
            mCR.boyaId = m.getBoya().getBoyaId();
            mCR.horario = m.getHorarioMuestra();
            mCR.alturaNivelDelMar = m.getAlturaNivelMar();
            listaMuestrasPorColorResponse.add(mCR);
        }
        return listaMuestrasPorColorResponse;
    }

    public static MuestraAlturaMinResponse crearAlturaMinResponse(Double alturaNivelMar, Date horarioMuestra, String color){
        MuestraAlturaMinResponse mAMR = new MuestraAlturaMinResponse();
        mAMR.alturaNivelMar = alturaNivelMar;
        mAMR.horario = horarioMuestra;
        mAMR.color = color;
        return mAMR;
    }

    public static MuestraAnomaliaResponse crearMuestraAnomaliaResp(Double altura, Date horarioInicio, Date horarioFin, String tipoAlerta){
        MuestraAnomaliaResponse mAR = new MuestraAnomaliaResponse();
        mAR.alturaNivelMarActual = altura;
        mAR.horarioInicioAnomalia = horarioInicio;
        mAR.horarioFinAnomalia = horarioFin;
        mAR.tipoAlerta = tipoAlerta;
        return mAR;
    }
}