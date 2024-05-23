/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.Spinner;
import proyecto.BaseDeDatos;
import proyecto.Sismo;
import proyecto.TFalla;
/**
 *
 * @author Dayron
 *
/**/

public class Cargador {
    //private BaseDeDatos base = new BaseDeDatos();
    
    /**
     * Crea una tabla modelo con todos los datos de los sismos en la base de sismos
     * @param lista ArrayList(Sismo)
     * @return DefaultTableModel
     */
    public DefaultTableModel cargarSismos(ArrayList<Sismo> lista){
        
        Object [] encabezado = {"Fecha", "Hora","Magnitud","Profundidad","Localización","Origen","Provincia","Latitud","Longitud","Mapa","Modificar"};
        int cantidadDeSismos = lista.size();
        DefaultTableModel tabla = new DefaultTableModel(encabezado,cantidadDeSismos){
            
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        
        for (int i = 0; i < tabla.getRowCount(); i++) {
            Sismo cte = lista.get(i);
            tabla.setValueAt(dateToString(cte.getFecha()), i, 0);
            tabla.setValueAt(cte.getHora(), i, 1);
            tabla.setValueAt(String.format("%,.1f",cte.getMagnitud()), i, 2);
            tabla.setValueAt(cte.getProfundidad(), i, 3);
            tabla.setValueAt(cte.getLocalizacion(), i, 4);
            
            tabla.setValueAt(tipoToString(cte.getOrigenFalla()), i, 5);
            tabla.setValueAt(cte.getProvincia(), i, 6);
            tabla.setValueAt(cte.getLatitud(), i, 7);
            tabla.setValueAt(cte.getLongitud(), i, 8);
            
        }
        
        return tabla;
    }
    /**
     * Crea una tabla modelo con los datos de los sismos con su respectiva clasificacion de magnitud
     * @param lista ArrayList(Sismo)
     * @return DefaultTableModel
     */
    public DefaultTableModel tablaValorMagnitud(ArrayList<Sismo> lista){
        
        Object [] encabezado = {"Fecha", "Hora","Magnitud","Profundidad","Localización","Origen","Provincia","Latitud","Longitud","Clasificacion"};
        int cantidadDeSismos = lista.size();
        DefaultTableModel tabla = new DefaultTableModel(encabezado,cantidadDeSismos);
        
        for (int i = 0; i < tabla.getRowCount(); i++) {
            Sismo cte = lista.get(i);
            tabla.setValueAt(dateToString(cte.getFecha()), i, 0);
            tabla.setValueAt(cte.getHora(), i, 1);
            tabla.setValueAt(String.format("%,.1f",cte.getMagnitud()), i, 2);
            tabla.setValueAt(cte.getProfundidad(), i, 3);
            tabla.setValueAt(cte.getLocalizacion(), i, 4);
            
            tabla.setValueAt(tipoToString(cte.getOrigenFalla()), i, 5);
            tabla.setValueAt(cte.getProvincia(), i, 6);
            tabla.setValueAt(cte.getLatitud(), i, 7);
            tabla.setValueAt(cte.getLongitud(), i, 8);
            tabla.setValueAt(valorDeMagnitud(cte.getMagnitud()), i, 9);
        }
        
        return tabla;
    }
    /**
     * Convierte los TFalla en strings
     * @param unOrigen TFalla 
     * @return String
     */
    public String tipoToString(TFalla unOrigen){
        String resultado = "";
        if (unOrigen == TFalla.CHOQUEPLACAS)
            resultado = "Choque de placas";
        else if (unOrigen == TFalla.DEFORMACIONINTERNA)
            resultado = "Deformacion tectonica";
        else if (unOrigen == TFalla.INTRAPLACA)
            resultado = "Intraplaca";
        else if (unOrigen == TFalla.SUBDUCCION)
            resultado = "subduccion";
        else if (unOrigen == TFalla.TECTONICOFALLALOCAL)
            resultado = "Tectonico por falla local";
        
        return resultado;
    }
    /**
     * convierte una fecha a un string
     * @param date Date
     * @return String
     */
    public String dateToString(Date date){
        String stringDate="";
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        stringDate= DateFor.format(date);
        return stringDate;
    }
    
    /**
     *
     * @param base BaseDeDatos
     * @return JFreeChart 
     */
    public JFreeChart graficoDeBarras(BaseDeDatos base){
    
        DefaultCategoryDataset Datos = new DefaultCategoryDataset();
        Datos.addValue(base.cantidadDeSismosPorMes(0), "Sismo", "Enero");
        Datos.addValue(base.cantidadDeSismosPorMes(1), "Sismo", "Febrero");
        Datos.addValue(base.cantidadDeSismosPorMes(2), "Sismo", "Marzo");
        Datos.addValue(base.cantidadDeSismosPorMes(3), "Sismo", "Abril");
        Datos.addValue(base.cantidadDeSismosPorMes(4), "Sismo", "Mayo");
        Datos.addValue(base.cantidadDeSismosPorMes(5), "Sismo", "Junio");
        Datos.addValue(base.cantidadDeSismosPorMes(6), "Sismo", "Julio");
        Datos.addValue(base.cantidadDeSismosPorMes(7), "Sismo", "Agosto");
        Datos.addValue(base.cantidadDeSismosPorMes(8), "Sismo", "Septiembre");
        Datos.addValue(base.cantidadDeSismosPorMes(9), "Sismo", "Octubre");
        Datos.addValue(base.cantidadDeSismosPorMes(10), "Sismo", "Noviembre");
        Datos.addValue(base.cantidadDeSismosPorMes(11), "Sismo", "Diciembre");
        JFreeChart grafica;
        
        grafica = ChartFactory.createBarChart("Sismos por mes",
        "Meses", "Sismos ocurridos", Datos,
        PlotOrientation.VERTICAL, true, true, false);

        return grafica;
    }
    /**
     * crea un grafico en formato pastel con los datos de los simos clasificados por su tipo de origen
     * @param base BaseDeDatos
     * @return JFreeChart
     *
     */
    public JFreeChart graficoPastel(BaseDeDatos base){
        //crea un grafico pastel
   
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Subduccion", base.cantidadDeSismosPorOrigen(TFalla.SUBDUCCION));
        data.setValue("Choque de placas", base.cantidadDeSismosPorOrigen(TFalla.CHOQUEPLACAS));
        data.setValue("Intraplaca", base.cantidadDeSismosPorOrigen(TFalla.INTRAPLACA));
        data.setValue("Tectonico por falla local", base.cantidadDeSismosPorOrigen(TFalla.TECTONICOFALLALOCAL));
        data.setValue("Deformacion interna", base.cantidadDeSismosPorOrigen(TFalla.DEFORMACIONINTERNA));
 
        // Creando el Grafico
        JFreeChart chart = ChartFactory.createPieChart(
        "Simos por tipo de Origen", 
        data,true,true,false);
        
        return chart;
    }
    /**
     * Crea un histograma con la informacion de los sismos segun su provincia
     * @param base BaseDeDatos
     * @return JFreeChart
     */
    public JFreeChart histograma(BaseDeDatos base){
    
        DefaultCategoryDataset Datos = new DefaultCategoryDataset();
        double vector[] = base.cantidadDeSismosPorProvincia();
        
        HistogramDataset datos = new HistogramDataset();
        datos.addSeries("Frecuencias de los ingresos", vector, 8);
        JFreeChart grafica;
        
        grafica = ChartFactory.createHistogram("Simos por provincias",
        "Provincias", "Sismos ocurridos", datos,
        PlotOrientation.VERTICAL,true,true, false);
        
        return grafica;
    }
    /**
     * Crea una tabla para mostrar los rangos asignados en el histograma
     * @return DefaultTableModel
     */
    public DefaultTableModel tablaHistograma(){
    
        Object [] encabezado = {"provincia","rango"};
        DefaultTableModel tabla = new DefaultTableModel(encabezado, 8);
    
        tabla.setValueAt("San Jose", 0, 0);
        tabla.setValueAt("0-1,0", 0, 1);
        tabla.setValueAt("Alajuela", 1, 0);
        tabla.setValueAt("1,0-2,0", 1, 1);
        tabla.setValueAt("Herediia", 2, 0);
        tabla.setValueAt("1,5-2,5", 2, 1);
        tabla.setValueAt("Cartago", 3, 0);
        tabla.setValueAt("2,5-3,5", 3, 1);
        tabla.setValueAt("Guanacaste", 4, 0);
        tabla.setValueAt("3,5-4,5", 4, 1);
        tabla.setValueAt("Puntarenas", 5, 0);
        tabla.setValueAt("4,5-5,5", 5, 1);
        tabla.setValueAt("Limon", 6, 0);
        tabla.setValueAt("5,5-6,0", 6, 1);
        tabla.setValueAt("Zona Maritima", 7, 0);
        tabla.setValueAt("6,0-7,0", 7, 1);
        
        return tabla;
    
    }

    /**
     * Crea una tabla con los sismos filtrados por fecha
     * @param lista ArrayList<Sismo> 
     * @param f1 Date
     * @param f2 Date
     * @return DefaultTableModel 
     */
    public DefaultTableModel sismosPorFecha(ArrayList<Sismo> lista,Date f1,Date f2){
         ArrayList<Sismo> resultado = new ArrayList();
        for(int i = 0;i < lista.size();i++){
            Sismo sismoActual = lista.get(i);
            if (sismoActual.getFecha().after(f1) && sismoActual.getFecha().before(f2)){
                resultado.add(sismoActual);
            }
            
        }
        return cargarSismos(resultado);
    }
    /**
     * Crea 
     * @param lista ArrayList<Sismo> 
     * @param f1 Date
     * @param f2 Date
     * @return ArrayList<Sismo>
     */
    public ArrayList<Sismo> sismosPorFecha2(ArrayList<Sismo> lista,Date f1,Date f2){
        ArrayList<Sismo> resultado = new ArrayList();
        for(int i = 0;i < lista.size();i++){
            Sismo sismoActual = lista.get(i);
            if (sismoActual.getFecha().after(f1) && sismoActual.getFecha().before(f2)){
                resultado.add(sismoActual);
            }
            
        }
        return resultado;
    }
    /**
     * crea un selector con los tipos de origen
     * @param base BaseDeDatos
     * @return SpinnerModel 
     */
    public SpinnerModel selectorDeOrigen(BaseDeDatos base){
        ArrayList<TFalla> lista = new ArrayList();
        lista.add(TFalla.SUBDUCCION);
        lista.add(TFalla.CHOQUEPLACAS);
        lista.add(TFalla.DEFORMACIONINTERNA);
        lista.add(TFalla.INTRAPLACA);
        lista.add(TFalla.TECTONICOFALLALOCAL);
        
        SpinnerListModel selector = new SpinnerListModel(lista); 
        
        return selector;
    }
    /**
     * crea un selector con las provincias
     * @param base BaseDeDatos
     * @return SpinnerModel
     */
    public SpinnerModel selectorDeProvincia(BaseDeDatos base){
        ArrayList<String> lista = new ArrayList();
        lista.add("San José");
        lista.add("Alajuela");
        lista.add("Heredia");
        lista.add("Cartago");
        lista.add("Guanacaste");
        lista.add("Puntarenas");
        lista.add("Limon");
        lista.add("Zona Maritima");
        
        
        SpinnerListModel selector = new SpinnerListModel(lista); 
        
        return selector;
    }    
    /**
     * devuelve un string con el tipo de clasificacaion segun la magnitud dada
     * @param magnitud double
     * @return String
     */
    private String valorDeMagnitud(double magnitud) {
        if (magnitud < 2.0)
            return "Micro";
        else if (magnitud >= 2.0 & magnitud <= 3.9)
            return "Menor";
        else if (magnitud >= 4.0 & magnitud <= 5.9)
            return "Moderado";
        else if (magnitud >= 6.0 & magnitud <= 6.9)
            return "Fuerte";
        else if (magnitud >= 7.0 & magnitud <= 7.9)
            return "Mayor";
        else if (magnitud >= 8.0 & magnitud <= 9.9)
            return "Gran";
        else if (magnitud >= 10.0)
            return "Epico";
        return null;
    }
}


