/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */

public class Usuario {

    private String nombre;
    private String correo;
    private String celular;
    private ArrayList<String> provinciaInteres = new ArrayList();


    public Usuario() {
    }

    public Usuario(String nombre, String correo, String celular){
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
    }
    
    
    
    public String getNombre() { return nombre; }
    
    public void setNombre(String nombre) {this.nombre = nombre; }

    public String getCorreo() { return correo; }
    
    public void setCorreo(String correo) {this.correo = correo; }

    public String getCelular() { return celular; }
    
    public void setCelular(String celular) {this.celular = celular; }

    public String getProvinciaInteresV2(int index) { return provinciaInteres.get(index); }

    public ArrayList<String> getProvinciaInteres() {
        return provinciaInteres;
    }

    public void setProvinciaInteres(String provincia) { this.provinciaInteres.add(provincia); }

    public String toString(){
        return "Nombre: " + nombre + "\n" + "Correo: " + correo + "\n" + "Celular: " + celular + "\n" + "Provincias de interés:";
    }

}
