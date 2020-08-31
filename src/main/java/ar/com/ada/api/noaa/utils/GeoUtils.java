package ar.com.ada.api.noaa.utils;

public class GeoUtils {

    public static boolean chequearRangoPlanetario(Double latitud, Double longitud) {
        return Math.abs(latitud) <= 90 && Math.abs(longitud) <= 180;
    }

    public static String ubicarHemisferioSurNorte(Double latitud, Double longitud){
        if(chequearRangoPlanetario(latitud, longitud)){
            return (latitud >= -180 || latitud < 0) ? "SUR" : "NORTE";
        }
        return "FUERA RANGO PLANETARIO";        
    }

    public static String ubicarHemisferioOesteEste(Double latitud, Double longitud){
        if(chequearRangoPlanetario(latitud, longitud)){
            return (longitud >= -90 || longitud < 0) ? "OESTE" : "ESTE";
        }
        return "FUERA RANGO PLANETARIO";
    }
}