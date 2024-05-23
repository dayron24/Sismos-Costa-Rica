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
 * @author Sebastian
 */
public class BaseDatosUsuarios {
    
    private ArrayList<Usuario> usuarios = new ArrayList();
    
    public ArrayList<Usuario> getUsuario(){
        return usuarios;
    }

    public String toString() {
        String print = "";
        for (int i=0;i<usuarios.size();i++){
            
            print += usuarios.get(i).toString() + "\n" + "\n";
        }
        return print;
    }
    
    
    /**
     * lee el excel con los usuarios y los carga en la lista de 
     * 
     */
    public void cargarUsuarios(){
       
        FileInputStream fileUsuarios;
       
        try {
            fileUsuarios = new FileInputStream("Usuarios.xlsx");
            XSSFWorkbook libro = new XSSFWorkbook(fileUsuarios);
            XSSFSheet hoja = libro.getSheetAt(0);
       
            Iterator<Row> filas = hoja.iterator();
            Iterator<Cell> celdas;
            Row fila;
            Row fila2;
            Cell celda;
            int i = 0;
            int j=0;
            while (filas.hasNext()){
                
                fila = filas.next();
                
                celdas = fila.cellIterator();
                Usuario newUser = new Usuario();
               
                while(celdas.hasNext()){
                    
                    
                    celda = celdas.next();
                    
                    
                    if (j==0 & i>0){
                        newUser.setNombre(celda.getStringCellValue());
                    } else if (j==1 & i>0){
                        newUser.setCorreo(celda.getStringCellValue());
                    } else if (j==2 & i>0){
                        newUser.setCelular(celda.getStringCellValue());
                    } else if(j==3 & i>0){
                        String[] division = celda.getStringCellValue().split(",");
                        for (int x=0; x<division.length; x++) {
                            newUser.setProvinciaInteres(division[x]);
                        }
                    }
                    j+=1;
                }
                if (newUser.getNombre() != null)
                    usuarios.add(newUser);
                i+=1;
                j=0;
              
            }
        } catch (IOException ex) {
            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
