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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Persona;
import view.EditarPersonaController;
import view.VistaPersonaController;

/**
 *
 * @author dam
 */
public class LibretaDirecciones extends Application {

    private ObservableList datosPersona = FXCollections.observableArrayList();
    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaPersona, editarPersona;
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
    
    //Vista editarPersona
    public boolean muestraEditarPersona(Persona persona) {
        
        //Cargo la vista persona a partir de VistaPersona.fxml
        FXMLLoader loader = new FXMLLoader();
        URL location = LibretaDirecciones.class.getResource("../view/EditarPersona.fxml");
        loader.setLocation(location);
        try {
            editarPersona = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LibretaDirecciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        //Creo el escenario de edición (con modal) y establezco la escena
        Stage escenarioEdicion = new Stage();
        escenarioEdicion.setTitle("Editar Persona");
        escenarioEdicion.initModality(Modality.WINDOW_MODAL);//no puedo acceder a la parent mientras no cierre esta (modal)
        escenarioEdicion.initOwner(escenarioPrincipal);
        Scene escena = new Scene(editarPersona);
        escenarioEdicion.setScene(escena);
        
        //Asigno el escenario de edición y la persona seleccionada al controlador
        EditarPersonaController controller = loader.getController();
        controller.setEscenarioEdicion(escenarioEdicion);
        controller.setPersona(persona);

        //Muestro el diálogo ahjsta que el ussuario lo cierre
        escenarioEdicion.showAndWait();
        
        //devuelvo el botón pulsado
        return controller.isGuardarClicked();
    
    }

    //Invoco el método getPrimaryStage para que devuelva mi escenario principal
    public Stage getPrimaryStage() {
        return escenarioPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
