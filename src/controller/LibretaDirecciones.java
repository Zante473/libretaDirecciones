/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Empaquetador;
import model.Persona;
import view.EditarPersonaController;
import view.VistaEstadisticasController;
import view.VistaPersonaController;
import view.VistaPrincipalController;

/**
 *
 * @author dam
 */
public class LibretaDirecciones extends Application {

    private ObservableList datosPersona = FXCollections.observableArrayList();
    private Stage escenarioPrincipal;
    private BorderPane layoutPrincipal;
    private AnchorPane vistaPersona, editarPersona, vistaEstadisticas;
    private ObservableList FXColection;
    

    //Datos de ejemplo:
    public LibretaDirecciones() {

        datosPersona.add(new Persona("Daniel", "Zamarreño Avendaño"));
        datosPersona.add(new Persona("Mauricio", "Sanchez Moreno"));
        datosPersona.add(new Persona("Pablo", "Prieto Gutierrez"));
        datosPersona.add(new Persona("Alex Daniel", "Tomsa"));
        datosPersona.add(new Persona("Jairo", "Garcia Rincón"));

    }

    //Método para devolver los datos como lista observable
    public ObservableList getDatosPersona() {

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

         //Cargo el layout principal a partir de la vista VistaPrincipal.fxml
        FXMLLoader loader = new FXMLLoader();
        URL location = LibretaDirecciones.class.getResource("../view/VistaPrincipal.fxml");
        loader.setLocation(location);
        try {
            layoutPrincipal = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LibretaDirecciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Cargo la escena que contiene ese layout principal
        Scene escena = new Scene(layoutPrincipal);
        escenarioPrincipal.setScene(escena);
        
        //Doy al controlador acceso a la aplicación principal
        VistaPrincipalController controller = loader.getController();
        controller.setLibretaDirecciones(this);
        
        //Muestro la escena
        escenarioPrincipal.show();
        
        //Intento cargar el último archivo abierto
        File archivo = getRutaArchivoPersonas();
        if (archivo != null){
            cargaPersonas(archivo);
        }

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
    
    public void crearGrafico() {
        
        //Cargo la vista estadísticas
        FXMLLoader loader = new FXMLLoader();
        URL location = LibretaDirecciones.class.getResource("/view/VistaEstadisticas.fxml");
        loader.setLocation(location);
        try {
            vistaEstadisticas = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LibretaDirecciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Inicializo un nuevo escenario y asigno el principal
        Stage escenarioEstadisticas = new Stage();        
        escenarioEstadisticas.setTitle("Estadísticas");
        escenarioEstadisticas.initModality(Modality.WINDOW_MODAL); //Para que mientras yo no cierre esta ventana no pueda acceder a la anterior
        escenarioEstadisticas.initOwner(escenarioPrincipal);
        
        //Cargo la escena que contiene ese layout de estadisticas
        Scene escena = new Scene(vistaEstadisticas);
        escenarioEstadisticas.setScene(escena);
                    
        //Asigno el controlador
        VistaEstadisticasController controller = loader.getController();
        controller.setDatosPersona(datosPersona);

        //Muestro el escenario
        escenarioEstadisticas.show();
        
    }

    //Obtengo la ruta del archivo de la preferencias de usuario en Java
    public File getRutaArchivoPersonas() {

        Preferences prefs = Preferences.userNodeForPackage(LibretaDirecciones.class);
        String rutaArchivo = prefs.get("rutaArchivo", null);
        System.out.println(rutaArchivo);
        if (rutaArchivo != null) {
            return new File(rutaArchivo);
        } else {
            return null;
        }
    }

    //Guardo la ruta del archivo en las preferencias de usuario en Java
    public void setRutaArchivoPersonas(File archivo) {

        Preferences prefs = Preferences.userNodeForPackage(LibretaDirecciones.class);
        if (archivo != null) {
            //Añado la ruta a las preferencias
            prefs.put("rutaArchivo", archivo.getPath());
            //Actualizo el título del escenario a partir del archivo
            escenarioPrincipal.setTitle("Libreta de direcciones - " + archivo.getName());
        } else {
            //Elimino la ruta de las preferencias
            prefs.remove("rutaArchivo");
            //Actualizo el título del escenario quitando el nombre del archivo
            escenarioPrincipal.setTitle("Libreta de direcciones");
        }

    }
    
    //Cargo personas de un fichero, unmarshaling, convierte xml en objetos (leo xml del archivo y hago unmarshal)
    public void cargaPersonas(File archivo){
        
        try {
            //Contexto
            JAXBContext context = JAXBContext.newInstance(Empaquetador.class);
            Unmarshaller um = context.createUnmarshaller();

            //Leo XML del archivo y hago unmarshall
            Empaquetador empaquetador = (Empaquetador) um.unmarshal(archivo);

            //Borro los anteriores
            datosPersona.clear();
            datosPersona.addAll(empaquetador.getPersonas());

            //Guardo la ruta del archivo al registro de preferencias
            setRutaArchivoPersonas(archivo);

        } catch (Exception e) {
            //Muestro alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pueden cargar datos de la ruta "+ archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
            
        }
        
    }
    
    //Guardo personas en un fichero, marshaling, convierte objetos en xml (leo objetos y los escribo en xml, marshal)
    public void guardaPersonas(File archivo) {
        
        try {
            //Contexto
            JAXBContext context = JAXBContext.newInstance(Empaquetador.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Empaqueto los datos de las personas
            Empaquetador empaquetador = new Empaquetador();
            empaquetador.setPersonas(datosPersona);

            //Marshall y guardo XML a archivo
            m.marshal(empaquetador, archivo);

            //Guardo la ruta delk archivo en el registro
            setRutaArchivoPersonas(archivo);
            
        } catch (Exception e) { // catches ANY exception
            //Muestro alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se puede guardar en el archivo "+ archivo.getPath());
            alerta.setContentText(e.toString());
            alerta.showAndWait();
        }
    }

    //Invoco el método getPrimaryStage para que devuelva mi escenario principal
    public Stage getPrimaryStage() {
        return escenarioPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
