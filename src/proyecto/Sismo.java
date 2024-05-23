package proyecto;

import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dayron
 */
public class Sismo {
    private Date fecha;
    private String hora;
    private double profundidad; 
    private String localizacion;
    private TFalla origenFalla;
    private double magnitud;
    private double latitud;
    private double longitud;
    private String provincia;

    public Sismo() {
    }

    public Sismo(Date fecha, String hora,double magnitud, double profundidad, String localizacion, TFalla origenFalla,String provincia, double latitud, double longitud) {
        this.fecha = fecha;
        this.hora = hora;
        this.profundidad = profundidad;
        this.localizacion = localizacion;
        this.origenFalla = origenFalla;
        this.magnitud = magnitud;
        this.latitud = latitud;
        this.longitud = longitud;
        this.provincia = provincia;
    }
    
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public TFalla getOrigenFalla() {
        return origenFalla;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getProvincia() {
        return provincia;
    }
    
    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public void setOrigenFalla(TFalla origenFalla) {
        this.origenFalla = origenFalla;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public String unidadMagnitud() {
        if (magnitud >= 6.9){
            return "M";
        } else{
            return "Mw";
        }
    }

    @Override
    public String toString() {
       return "-ALERTA DE SISMO-" + "\n"+
                "\n" + "Locacalización: " + localizacion + "\n" +
                "Provincia: " + provincia + "\t" + "Coorenadas:  " + "(" + latitud + " , " + longitud + ")" + "\n" + 
                "\n"+
                "Magnitud: " + magnitud + "\t" + "Profundidad: " + profundidad + unidadMagnitud() + "\t" + "Origen: " + origenFalla + "\n" +
                "\n" +
                "Fecha: " + fecha + "\t" + "Hora: " + hora;
    }
    
    
}
