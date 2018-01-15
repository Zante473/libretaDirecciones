/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Persona;
import view.VistaPersonaController;

/**
 *
 * @author dam
 */
public class LibretaDirecciones extends Application {

    private ObservableList datosPersona = FXCollections.observableArrayList();
    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaPersona;
    private ObservableList FXColection;

    //Datos de ejemplo:
    
    public LibretaDirecciones(){
        
        datosPersona.add(new Persona("Daniel", "Zamarreño Avendaño"));
        datosPersona.add(new Persona("Mauricio","Sanchez Moreno"));
        datosPersona.add(new Persona("Pablo", "Prieto Gutierrez"));
        datosPersona.add(new Persona("Alex Daniel", "Tomsa"));
        datosPersona.add(new Persona("Jairo", "Garcia Rincón"));
        
    }
    
    //Método para devolver los datos como lista observable
    
    public ObservableList getDatosPersona(){
        
        return datosPersona;
        
    }
    
    @Override
    public void start(Stage escenarioPrincipal) {

        //Escenario en el que cargo las diferentes escenas (vistas)
        this.escenarioPrincipal = escenarioPrincipal;

        //Establezco el título
        this.escenarioPrincipal.setTitle("Libreta de direcciones");

        //Inicializo el layout principal
        initLayoutPrincipal();

        //Muestro la vista persona
        muestraVistaPersona();

    }

    public void initLayoutPrincipal() {

        //Cargo el layout principal a partir de la vista vistaPrincipal.fxml
        FXMLLoader loader = new FXMLLoader();
        URL location = LibretaDirecciones.class.getResource("../view/vistaPrincipal.fxml");
        loader.setLocation(location);

        try {
            layoutPrincipal = loader.load();
        } catch (IOException ex) {

        }

        //Cargo y muestro la escena que contiene ese layout principal
        Scene escena = new Scene(layoutPrincipal);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();

    }

    public void muestraVistaPersona() {

        //Cargo la vista persona a partir de la vista vistaPersona.fxml
        FXMLLoader loader = new FXMLLoader();
        URL location = LibretaDirecciones.class.getResource("../view/VistaPersona.fxml");
        loader.setLocation(location);
        try {
            vistaPersona = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LibretaDirecciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Añado al centro del layoutPrincipal
        layoutPrincipal.setCenter(vistaPersona);
        
        VistaPersonaController controller = loader.getController();
        controller.setLibretaDirecciones(this);

    }

    //Invoco el método getPrimaryStage para que devuelva mi escenario principal
    public Stage getPrimaryStage() {
        return escenarioPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
