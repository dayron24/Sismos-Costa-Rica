
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.hssf.usermodel.HeaderFooter.date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Dayron
 */
public class BaseDeDatos {
    
    //FileInputStream f;
    private ArrayList<Sismo> sismos = new ArrayList();  

    public ArrayList<Sismo> getSismos() {
        return sismos;
    }
    /**
     * Cuenta la cantidad de sismos dado un mes (int)
     * @param mes int
     * @return int cantidad de sismo
     *
     */
    public int cantidadDeSismosPorMes(int mes){
        int resultado = 0;
        for (int i=0;i < sismos.size();i++){
            Date fechaActual = sismos.get(i).getFecha();
            if (fechaActual.getMonth() == mes)
                resultado+=1;
        }
        return resultado;
    }
    /**
     * Cuenta la cantidad de sismos dado un mes (int)
     * @param unTipo TFalla
     * @return int cantidad de sismo
     *
     */
    public int cantidadDeSismosPorOrigen(TFalla unTipo){
        int resultado = 0;
        for (int i=0;i < sismos.size();i++){
            TFalla actual = sismos.get(i).getOrigenFalla();
            if (actual == unTipo)
                resultado+=1;
        }
        return resultado;
    }
    /**
     * agrega un sismo a la lista de sismo
     * @param unSismo Sismo
     * @return boolean 
     *
     */
    public boolean agregarSismo(Sismo unSismo){
        sismos.add(unSismo);
        return true;
    }
    /**
     * agrega un sismo en la posicion dada (index) y si habia uno lo sustituye
     * @param unSismo Sismo
     * @param index int
     * @return int cantidad de sismo
     *
     */
    public boolean agregarSismo(Sismo unSismo,int index){
        sismos.remove(index);
        sismos.add(index, unSismo);
        return true;
    }
    /**
     *Crea un vector con doubles asignados a cada provincia, las veces que aparece ese double en el vector es
     * la cantidad de sismos en esa provincoa
     * @return vector de double
     *
     */
    public double[] cantidadDeSismosPorProvincia(){
        double resultado[];
        resultado = new double[sismos.size()];
         
        for (int i=0;i < sismos.size();i++){
            String provincia = sismos.get(i).getProvincia();
            if (provincia.equals("San Jose"))
                resultado[i] = 0;
            else if (provincia.equals("Alajuela"))
                resultado[i] = 1;
            else if (provincia.equals("Heredia"))
                resultado[i] = 2;
            else if (provincia.equals("Cartago"))
                resultado[i] = 3;
            else if (provincia.equals("Guanacaste"))
                resultado[i] = 4;
            else if (provincia.equals("Puntarenas"))
                resultado[i] = 5;
            else if (provincia.equals("Limon"))
                resultado[i] = 6;
            else if (provincia.equals("Zona Maritima"))
                resultado[i] = 7;
            
        }
        return resultado;
    }
    
    @Override
    public String toString() {
        String print = "";
        for (int i=0;i<sismos.size();i++){
            
            print += sismos.get(i).toString() + "\n" + "\n";
        }
        return print;
    }
    
    /**
     * lee el archivo excel y carga los sismos del excel en la lista de sismos 
     *
     */
    public void cargarArchivo(String nombreArchivo){
        
        FileInputStream f;
       
        try {
            f = new FileInputStream("sismos.xlsx");
            XSSFWorkbook libro = new XSSFWorkbook(f);
            XSSFSheet hoja = libro.getSheetAt(0);
       
            Iterator<Row> filas = hoja.iterator();
            Iterator<Cell> celdas;
            Row fila;
            Cell celda;
            int i = 0;
            int j=0;
            while (filas.hasNext()){
       
                fila = filas.next();
                
                celdas = fila.cellIterator();
                Sismo s = new Sismo();
                
                while (celdas.hasNext()){
                    celda = celdas.next();
                    
                    if (j==0 & i>0){
                        
                        s.setFecha(celda.getDateCellValue());
                    }
                    else if (j==1 & i>0){
                        s.setHora(celda.getStringCellValue());
                    }
                    else if (j==2 & i>0){
                        s.setMagnitud(celda.getNumericCellValue());
                    }
                    else if (j==3 & i>0){
                        s.setProfundidad(celda.getNumericCellValue());
                    }
                    else if (j==4 & i>0){
                        s.setLocalizacion(celda.getStringCellValue());
                    }
                    else if (j==5 & i>0){
                        if (celda.getStringCellValue().equals("Subducción")){
                            s.setOrigenFalla(TFalla.SUBDUCCION);
                        }
                        else if (celda.getStringCellValue().equals("Tectónico por falla local")){
                            s.setOrigenFalla(TFalla.TECTONICOFALLALOCAL);
                        }
                        else if (celda.getStringCellValue().equals("Intra Placa")){
                            s.setOrigenFalla(TFalla.INTRAPLACA);
                        }
                        else if (celda.getStringCellValue().equals("Choque de placas")){
                            s.setOrigenFalla(TFalla.CHOQUEPLACAS);
                        }
                        else if (celda.getStringCellValue().equals("Deformación Interna")){
                            s.setOrigenFalla(TFalla.DEFORMACIONINTERNA);
                        }
                    }
                    else if (j==6 & i>0){
                        s.setProvincia(celda.getStringCellValue());
                    }
                    else if (j==7 & i>0){
                        s.setLatitud(celda.getNumericCellValue());
                    }
                    else if (j==8 & i>0){
                        s.setLongitud(celda.getNumericCellValue());
                    }
                    j+=1;
                    
                }
                if (s.getFecha() != null)
                    sismos.add(s);
                //System.out.println(s.toString());
                //System.out.println(" ");
                i+=1;
                j=0;
            }
        } catch (IOException ex) {
            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
