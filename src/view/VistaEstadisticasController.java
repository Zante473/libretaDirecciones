/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Persona;

/**
 *
 * @author dam
 */
public class VistaEstadisticasController {
    
   @FXML
    private BarChart<String, Integer> graficoBarras; //Strings para las x, numeros enteros para las y

    @FXML
    private CategoryAxis ejeX;
    
    @FXML
    private NumberAxis ejeY;

    private ObservableList<String> nombreMeses = FXCollections.observableArrayList(); 
    
     //Se invoca justo después de que se ha cargado el archivo FXML
    @FXML
    private void initialize() {
        
        //Array de nombre de meses
        String[] meses = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
        
        //Los convierto a lista obervable
        nombreMeses.addAll(Arrays.asList(meses));

        //Asigno los nombres de meses a categorías
        ejeX.setCategories(nombreMeses);
        
        //Etiquetas de los ejes
        ejeX.setLabel("Mes de nacimiento");
        ejeY.setLabel("Número de personas");
    }

    
    //Set mes de cada persona para el eje Y
    public void setDatosPersona(List<Persona> personas) {
        
        //Array con cantidad de personas por mes de nacimiento
        int[] numMes = new int[12];
        for (Persona p : personas) {
            int mes = p.getFechaDeNacimiento().getMonthValue() - 1; //porque empiezan del 1 al 12, y los arrays comienzan en el 0
            numMes[mes]++;
        }

        //Genero la serie de y asociadas a x
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Mes de nacimiento");
        for (int i = 0; i < numMes.length; i++) {
            series.getData().add(new XYChart.Data<>(nombreMeses.get(i), numMes[i]));
        }

        //Añado la serie al gráfico
        graficoBarras.getData().add(series);
        
    }
    
}
