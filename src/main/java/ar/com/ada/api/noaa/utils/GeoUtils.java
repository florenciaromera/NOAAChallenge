package ar.com.ada.api.noaa.utils;

public class GeoUtils {

    public static boolean chequearRangoPlanetario(Double latitud, Double longitud) {
        if (Math.abs(latitud) > 90 && Math.abs(longitud) > 180) {
            return false;
        } else {
            return true;
        }
    }
}