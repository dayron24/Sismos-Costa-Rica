/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.JFreeChart;
import proyecto.BaseDatosUsuarios;
import proyecto.BaseDeDatos;
import proyecto.JavaMail;
import proyecto.Proyecto;
import proyecto.Sismo;
import proyecto.TFalla;

/**
 *
 * @author Dayron
 */
public class Controlador {
    private BaseDeDatos base = new BaseDeDatos();
    private Cargador cargador = new Cargador();
    private BaseDatosUsuarios usuarios = new BaseDatosUsuarios();
    
    /**
     * este constructor carga los archivos a las bases de datos
     */
    public Controlador (){
        base.cargarArchivo("");
        usuarios.cargarUsuarios();
    }
    /**
     * conexion entre la interfaz y el cargador, retonrna la tabla con calsificacion de magnitud
     * @return DefaultTableModel
     */
    public DefaultTableModel tablaValorMagnitud (){
        return cargador.tablaValorMagnitud(base.getSismos());
    }
    /**
     * conexion entre la interfaz y el cargador
     * @return ArrayList<Sismo>
     */
    public ArrayList<Sismo> getSismos(){
        return base.getSismos();
    }
    /**
     * conexion entre la interfaz y el cargador, retorna la tabla con los sismos
     * @return DefaultTableModel
     */
    public DefaultTableModel tablaDeSismos(){
        return cargador.cargarSismos(this.base.getSismos());
    }
    /**
     * conexion entre la interfaz y el cargador, retorna el grafico de sismos por mes
     * @return JFreeChart
     */
    public JFreeChart graficoSismosPorMes(){
        return cargador.graficoDeBarras(this.base);
    }
    /**
     * conexion entre la interfaz y el cargador, retorna el grafico de sismos por Falla
     * @return JFreeChart
     */
    public JFreeChart graficoSismosPorFalla(){
        return cargador.graficoPastel(this.base);
    }
    /**
     * conexion entre la interfaz y el cargador, retorna el grafico de sismos por provincia
     * @return JFreeChart
     */
    public JFreeChart graficoSismosPorProvincia(){
        return cargador.histograma(this.base);
    }
    /**
     * conexion entre la interfaz y el cargador
     * @return DefaultTableModel
     */
    public DefaultTableModel tablaHistograma(){
        return cargador.tablaHistograma();
    }
    /**
     * conexion entre la interfaz y el cargador
     * @param f1 Date
     * @param f2 Date
     * @return DefaultTableModel
     */
    public DefaultTableModel sismosPorFecha(Date f1,Date f2){
        return cargador.sismosPorFecha(base.getSismos(), f1, f2);
    }
    /**
     * conexion entre la interfaz y el cargador
     * @return SpinnerModel
     */
    public SpinnerModel selectorFallas (){
        return cargador.selectorDeOrigen(base);
    }
    /**
     * conexion entre la interfaz y el cargador
     * @return SpinnerModel
     */
    public SpinnerModel selectorProvincias (){
        return cargador.selectorDeProvincia(base);
    }
    /**
     * conexion entre la interfaz y el cargador, agrega un sismo desde la interfaz
     * @param unSismo Sismo
     * @return boolean 
     */
    public boolean agregarSismo(Sismo unSismo){
        return base.agregarSismo(unSismo);
    }
    /**
     * conexion entre la interfaz y el cargador, agrega un sismo desde la interaz en una posicion especifica en la lista
     * @param unSismo Sismo
     * @param index int 
     * @return boolean 
     */
    public boolean agregarSismo(Sismo unSismo,int index){
        return base.agregarSismo(unSismo,index);
    }
    /**
     * metodo que perimete abrir el navegador dada unas coordanadas se visualiza estas en google maps 
     * @param latitud double
     * @param longitud double
     */
    public void verMapa(double latitud,double longitud){
    
        String coordenadas = String.valueOf(latitud) + "%2C" + String.valueOf(longitud);
    
        try{
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.google.com/maps/search/?api=1&query="+coordenadas);
            desktop.browse(oURL);
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }   
    }
    /**
     * Tranforma la hora en formato militar
     * @param hora Date
     * @return int
     */
    public int formatoMilitar(int hora){
        if (hora<= 12){
            return hora;
        }
        else
            return hora-12;
    }
    /**
     * agrega un AM o PM segun la hora dada
     * @param hora int
     * @return String
     */
    public String ampm(int hora){
        if (hora< 12){
            return " AM";
        }
        else
            return " PM";
    }
    /**
     * crea un boton con el texto dado
     * @param texto String
     * @return JButton
     */
    public JButton crearBoton(String texto){
        JButton btn= new JButton();
        btn.setText(texto);
        btn.setSize(77, 23);
        return btn;
    }
    /**
     * pregunta si hay algun usuario en la base al momento de agregar un nuevo simos para enviar un correo
     * @param provincia String
     * @param nuevoSismo Sismo
     * @throws MessagingException 
     */
    public void consultaCorreo(String provincia, Sismo nuevoSismo) throws MessagingException{
        
        
        for (int i =0; i < usuarios.getUsuario().size(); i++){
            
            for (int j = 0; j < usuarios.getUsuario().get(i).getProvinciaInteres().size();j++){
                if(usuarios.getUsuario().get(i).getProvinciaInteresV2(j).equals(provincia)){
                    JavaMail.enviarCorreo(usuarios.getUsuario().get(i).getCorreo(), nuevoSismo);
                }
            }
        
        }
        
    }
    /**
     * Escribe en el excel los cambios realizados desde la interfaz
     * @param sismo Sismo
     */
    public void escribeExcel(Sismo sismo){
        
        FileInputStream f;
       
        try {
            f = new FileInputStream("sismos.xlsx");
            try (XSSFWorkbook libro = new XSSFWorkbook(f)) {
                XSSFSheet hoja = libro.getSheetAt(0);
                Iterator<Row> filas = hoja.iterator();
                Row fila;
                for (int i = 0; i < base.getSismos().size(); i++){
                    
                    fila = hoja.createRow(i+1);
                        for(int j = 0; j < 9; j++){
                            Cell celda = fila.createCell(j);
                            if (celda.getColumnIndex() == 0){
                                celda.setCellValue(base.getSismos().get(i).getFecha());
                            } else if (celda.getColumnIndex() == 1){
                                celda.setCellValue(base.getSismos().get(i).getHora());
                            } else if (celda.getColumnIndex() == 2){
                                celda.setCellValue(base.getSismos().get(i).getMagnitud());
                            } else if (celda.getColumnIndex() == 3) {
                                celda.setCellValue(base.getSismos().get(i).getProfundidad());
                            } else if (celda.getColumnIndex() == 4) {
                                celda.setCellValue(base.getSismos().get(i).getLocalizacion());
                            } else if (celda.getColumnIndex() == 6) {
                                celda.setCellValue(base.getSismos().get(i).getProvincia());
                            } else if (celda.getColumnIndex() == 5) {
                                if (base.getSismos().get(i).getOrigenFalla() == TFalla.SUBDUCCION){
                                    celda.setCellValue("Subducción");
                                } else if(base.getSismos().get(i).getOrigenFalla() == TFalla.CHOQUEPLACAS){
                                    celda.setCellValue("Choque de placas");
                                } else if(base.getSismos().get(i).getOrigenFalla() == TFalla.TECTONICOFALLALOCAL){
                                    celda.setCellValue("Tectónico por falla local");
                                } else if(base.getSismos().get(i).getOrigenFalla() == TFalla.INTRAPLACA){
                                    celda.setCellValue("Intra Placa");
                                } else if(base.getSismos().get(i).getOrigenFalla() == TFalla.DEFORMACIONINTERNA){
                                    celda.setCellValue("Deformación Interna");
                                }
                            } else if (celda.getColumnIndex() == 7) {
                                celda.setCellValue(base.getSismos().get(i).getLatitud());
                            } else if (celda.getColumnIndex() == 8){
                                celda.setCellValue(base.getSismos().get(i).getLongitud());
                            }
                        }
                }
                        
                try (FileOutputStream outputStream = new FileOutputStream("sismos.xlsx")) {
                    libro.write(outputStream);
                    libro.close();
                } 
                       
            }     
            
        } catch (IOException ex) {
            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
