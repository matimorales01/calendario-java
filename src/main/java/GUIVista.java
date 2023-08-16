import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class GUIVista{
    private GUIControlador controlador;
    private final List<GUIObserver> observadores = new ArrayList<>();

    private final GridPane calendarioGrid;

    private final Label mesAnioActualLabel;
    private final Label tituloLabel;
    private final Label descripcionLabel;
    private final Label fechaInicioLabel;
    private final Label horarioInicioLabel;
    private final Label fechaVencimientoLabel;
    private final Label fechaFinLabel;
    private final Label horarioFinLabel;
    private final Label horarioVencimientoLabel;
    private final Label maxOcurrenciasPermitidasLabel;
    private final Label intervaloLabel;
    private final  Label tipoRepeticionLabel;
    private final Label tipoAlarmaLabel;
    private final Label tipoEventoLabel;
    private final Label horasYMinutosLabel;
    private final Label fechaAlarmaLabel;
    private final Label intervaloAlarmaLabel;

    private final Button agregarTareaConVencimientoButton;
    private final Button agregarEventoTerminadoButton;
    private final Button agregarTareaDiaCompletoButton;
    private final Button agregarAlarmaButton;

    private final TextField intervaloAlarmaTextField;
    private final TextField horaInicioAlarmaTextField;
    private final TextField minutosInicioAlarmaTextField;
    private final TextField tituloField;
    private final TextField descripcionField;
    private final TextField horarioInicioTextField;
    private final TextField minutosInicioTextField;
    private final TextField horarioVencimientoTextField;
    private final TextField minutosVencimientoTextField;
    private final TextField horarioFinTextField;
    private final TextField minutosFinTextField;
    private final TextField maxOcurrenciasPermitidasTextField;
    private final TextField intervaloTextField;

    private final DatePicker fechaInicioPicker;
    private final DatePicker fechaVencimientoSinHoraPicker;
    private final DatePicker fechaFinSinHoraPicker;
    private final DatePicker fechaAlarma;

    private final ChoiceBox<Repeticion> tipoRepeticionChoiceBox;
    private final ChoiceBox<String> tipoEventoChoiceBox;
    private final ChoiceBox <String> tipoAlarmaChoiceBox;

    private final Label diaSemanaLabel;
    private final ChoiceBox<DayOfWeek> diasSemanaChoiceBox;
    private final ChoiceBox<DayOfWeek> diasSemanaChoiceBoxDos;


    public GUIVista( GridPane calendarioGrid, Label mesAnioActualLabel) {

        this.calendarioGrid = calendarioGrid;
        this.mesAnioActualLabel = mesAnioActualLabel;

        this.tituloLabel = new Label("Título:");
        tituloField = new TextField();

        this.descripcionLabel = new Label("Descripción:");
        descripcionField = new TextField();

        this.fechaInicioLabel = new Label("Fecha de Inicio:");
        fechaInicioPicker = new DatePicker();

        this.horarioInicioLabel = new Label("Horario (Incluye horas y minutos):");
        horarioInicioTextField = new TextField();
        minutosInicioTextField = new TextField();

        this.fechaFinLabel = new Label("Fecha de finalizacion del evento:");
        fechaFinSinHoraPicker = new DatePicker();

        this.horarioFinLabel = new Label("Horario (Incluye horas y minutos):");
        horarioFinTextField = new TextField();
        minutosFinTextField = new TextField();

        this.fechaVencimientoLabel = new Label("Fecha de Vencimiento:");
        fechaVencimientoSinHoraPicker = new DatePicker();

        this.horarioVencimientoLabel = new Label("Horario (Incluye horas y minutos):");
        horarioVencimientoTextField = new TextField();
        minutosVencimientoTextField = new TextField();

        this.maxOcurrenciasPermitidasLabel = new Label("Maximas ocurrencias del evento");
        maxOcurrenciasPermitidasTextField = new TextField();

        this.intervaloLabel = new Label("Intervalo:");
        intervaloTextField = new TextField();

        this.tipoRepeticionLabel = new Label("Tipo de repetición:");
        this.tipoRepeticionChoiceBox = new ChoiceBox<>();
        tipoRepeticionChoiceBox.getItems().addAll(Repeticion.values());

        this.tipoEventoLabel = new Label("Tipo de evento");
        tipoEventoChoiceBox = new ChoiceBox<>();
        tipoEventoChoiceBox.getItems().addAll("Evento dia completo","Evento Diario", "Evento Semanal", "Evento Mensual", "Evento Anual");


        this.intervaloAlarmaLabel = new Label("Intervalo en minutos:");
        this.intervaloAlarmaTextField = new TextField();

        this.fechaAlarmaLabel = new Label("Fecha de alarma:");
        this.fechaAlarma = new DatePicker();

        this.tipoAlarmaLabel = new Label(("Tipo de efecto"));
        this.tipoAlarmaChoiceBox = new ChoiceBox<>();
        tipoAlarmaChoiceBox.getItems().addAll("Notificacion","Luz","Sonido");

        this.diaSemanaLabel = new Label("Dias de la semana que se repite (Solo si el evento es semanal):");
        this.diasSemanaChoiceBox = new ChoiceBox<>();
        ObservableList<DayOfWeek> diasSemana = FXCollections.observableArrayList(DayOfWeek.values());
        diasSemanaChoiceBox.setItems(diasSemana);
        this.diasSemanaChoiceBoxDos = new ChoiceBox<>();
        diasSemanaChoiceBoxDos.setItems(diasSemana);

        this.horasYMinutosLabel = new Label("Hora de alarma:");
        this.horaInicioAlarmaTextField = new TextField();
        this.minutosInicioAlarmaTextField = new TextField();


        this.agregarTareaConVencimientoButton = new Button(("Agregar Tarea Con Vencimiento"));
        this.agregarTareaDiaCompletoButton = new Button("Agregar Tarea Día Completo");
        this.agregarEventoTerminadoButton = new Button("Agregar");
        this.agregarAlarmaButton = new Button("Agregar Alarma");
    }


    public void setControlador(GUIControlador controlador) {
        this.controlador = controlador;
    }


    public void agregarObservador(GUIObserver observador) {
        observadores.add(observador);
    }


    private void notificarCambioMesSiguiente() {
        for (GUIObserver observador : observadores) {
            observador.mostrarMesSiguiente();
        }

    }

    private void notificarCambioMesAnterior() {
        for (GUIObserver observador : observadores) {
            observador.mostrarMesAnterior();
        }

    }
    private void notificarVistaASemanal(){
        for (GUIObserver observador : observadores) {
            observador.mostrarSemanasConEventosYTareas();
        }
    }

    private void notificarMostrarListaTareas(ListView<Tarea> listaTareasTotales){
        for (GUIObserver observador : observadores) {
            observador.mostrarListaTareas(listaTareasTotales);
        }
    }

    private void notificarMostrarListaEventos(ListView<Evento> listaEventosTotales){
        for (GUIObserver observador : observadores) {
            observador.mostrarListaEventos(listaEventosTotales);
        }
    }
    private void notificarGestionarTipoEvento(String tipoEventoSeleccionado, VBox layout) {
        for (GUIObserver observador : observadores) {
            observador.gestionarTipoEvento(tipoEventoSeleccionado, layout, agregarEventoTerminadoButton, intervaloTextField, intervaloLabel,diaSemanaLabel, tipoRepeticionLabel, diasSemanaChoiceBox, diasSemanaChoiceBoxDos, tipoRepeticionChoiceBox );
        }
    }
    private void notificarGestionarRepeticion(Repeticion repeticionSeleccionada, VBox layout){
        for (GUIObserver observador : observadores) {
            observador.gestionarRepeticion(repeticionSeleccionada, layout, maxOcurrenciasPermitidasTextField, intervaloTextField, intervaloLabel, maxOcurrenciasPermitidasLabel, tipoRepeticionLabel,  tipoRepeticionChoiceBox);
        }
    }

    private void notificarCerrarAplicacion(){
        for (GUIObserver observador : observadores) {
            observador.cerrarAplicacion();
        }
    }

    private void notificarAgregarTarea(Tarea tarea){
        for (GUIObserver observador : observadores) {
            observador.agregarTarea(tarea);
        }
    }

    private void notificarAgregarEvento(ConstructorEventos constructorEventos){
        for (GUIObserver observador : observadores) {
            observador.agregarEvento(constructorEventos);
        }
    }

    private void notificarSemanaAnterior(){
        for (GUIObserver observador : observadores) {
            observador.semanaAnterior();
        }
    }

    private void notificarSemanaSiguiente(){
        for (GUIObserver observador : observadores) {
            observador.semanaSiguiente();
        }
    }

    
    public void mostrarCalendarioCompleto(Stage primaryStage, ListView<Tarea> listaTareas, ListView<Evento> listEventos) {

        HBox headerBox = new HBox(10);

        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10));

        Button mesAnteriorBoton = new Button("<<");
        mesAnteriorBoton.setOnAction(e -> notificarCambioMesAnterior());

        Button mesSiguienteBoton = new Button(">>");
        mesSiguienteBoton.setOnAction(e -> notificarCambioMesSiguiente());

        Button vistaSemanalButton = new Button("Vista Semanal");
        vistaSemanalButton.setOnAction(e -> notificarVistaASemanal());

        Button anteriorButton = new Button("< Semana Anterior ");
        anteriorButton.setOnAction(event -> notificarSemanaAnterior());

        Button siguienteButton = new Button("Semana Siguiente >");
        siguienteButton.setOnAction(event -> notificarSemanaSiguiente());

        mesAnioActualLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        controlador.actualizarLabel();
        headerBox.getChildren().addAll(mesAnteriorBoton, mesAnioActualLabel, mesSiguienteBoton,vistaSemanalButton);

        Label instruccionesLabel = new Label("Haz clic en un día para obtener informacion sobre los eventos y tareas creados");
        instruccionesLabel.setStyle("-fx-font-size: 14px;");

        Label importanteLabel = new Label("IMPORTANTE: Los eventos y tareas se cargan exitosamente, sin embargo hay que refrescar el mes (avanzando o retrocediendo el mes) para ver el color");
        importanteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: darkslategrey;");

        Label importanteLabel2 = new Label("La lista de eventos y tareas se refresca una vez que se cierra y se vuelve a abrir la ventana");
        importanteLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: darkslategrey;");

        Label tareasLabel = new Label("Las tareas son resaltadas con celeste");
        tareasLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightblue;");

        Label eventosLabel = new Label("Los eventos son resaltados con naranja");
        eventosLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightsalmon;");

        Label tareasYeventosLabel = new Label("Las tareas y eventos en un mismo dia son resaltadas con verde");
        tareasYeventosLabel.setStyle("-fx-font-weight: bold; -fx-background-color: greenyellow;");

        VBox mensajeBox = new VBox(10);
        mensajeBox.setAlignment(Pos.CENTER);

        Button buttonTarea = new Button("Agregar Tarea");
        Button buttonEvento = new Button("Agregar Evento");

        buttonTarea.setOnAction(e-> mostrarVentanaAgregarTarea());
        buttonEvento.setOnAction(e -> mostrarVentanaAgregarEvento());

        ingresarTareaConVencimiento();
        ingresarTareaDiaCompleto();
        ingresarEvento();

        mensajeBox.getChildren().addAll(instruccionesLabel, importanteLabel, importanteLabel2,tareasLabel, eventosLabel, tareasYeventosLabel,buttonTarea,buttonEvento, listaTareas, listEventos, siguienteButton, anteriorButton);

        ListView<Evento> listaEventosTotales = controlador.obtenerListaEventos();
        notificarMostrarListaEventos(listaEventosTotales);

        ListView<Tarea> listaTareasTotales = controlador.obtenerListaTareas();
        notificarMostrarListaTareas(listaTareasTotales);


        Button verListadoTareas = new Button("Ver tareas del mes");
        verListadoTareas.setOnAction( e -> controlador.mostrarVentanaConTareasDelMes());

        Button verListadoEventos = new Button("Ver eventos del mes");
        verListadoEventos.setOnAction(e -> controlador.mostrarVentanaConEventosDelMes());


        VBox contenedorListas = new VBox(10);
        contenedorListas.setAlignment(Pos.CENTER);
        contenedorListas.setPadding(new Insets(10));
        contenedorListas.getChildren().addAll(verListadoEventos,new Label("Todos los eventos"), listaEventosTotales, verListadoTareas,new Label("Todas las tareas"), listaTareasTotales);

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        //Para que la lista de eventos y tareas se añadan al costado y no abajo
        ColumnConstraints columna1 = new ColumnConstraints();
        ColumnConstraints columna2 = new ColumnConstraints();
        columna2.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(columna1, columna2);

        root.add(headerBox, 0, 0);
        root.add(calendarioGrid, 0, 1);
        root.add(mensajeBox, 0, 2); //mensajes abajo del calendario
        root.add(contenedorListas, 1, 0, 1, GridPane.REMAINING); //listas de eventos y tareas

        Scene scene = new Scene(root, 955, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calendario");
        primaryStage.show();


        primaryStage.setOnCloseRequest(event -> notificarCerrarAplicacion());
    }


    public void mostrarVentanaAgregarTarea() {
        Stage ventanaAgregarTarea = new Stage();
        ventanaAgregarTarea.setTitle("Agregar Tarea");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));

        layout.getChildren().addAll(tituloLabel, tituloField, descripcionLabel, descripcionField, fechaInicioLabel,
                fechaInicioPicker, horarioInicioLabel, horarioInicioTextField, minutosInicioTextField,
                fechaVencimientoLabel, fechaVencimientoSinHoraPicker, horarioVencimientoLabel,
                horarioVencimientoTextField, minutosVencimientoTextField, agregarTareaConVencimientoButton,agregarTareaDiaCompletoButton);


        Scene scene = new Scene(layout);
        ventanaAgregarTarea.setScene(scene);
        ventanaAgregarTarea.show();
    }



    public void mostrarVentanaAgregarEvento(){

        Stage ventanaAgregarEvento= new Stage();
        ventanaAgregarEvento.setTitle("Agregar Evento");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));

        layout.getChildren().addAll(tipoEventoLabel,tipoEventoChoiceBox,tituloLabel, tituloField, descripcionLabel, descripcionField, fechaInicioLabel,
                fechaInicioPicker, horarioInicioLabel, horarioInicioTextField, minutosInicioTextField,
                fechaFinLabel, fechaFinSinHoraPicker, horarioFinLabel, horarioFinTextField, minutosFinTextField,tipoRepeticionLabel, tipoRepeticionChoiceBox, maxOcurrenciasPermitidasLabel,
                maxOcurrenciasPermitidasTextField,intervaloLabel, intervaloTextField,
                diaSemanaLabel,diasSemanaChoiceBox,diasSemanaChoiceBoxDos,agregarEventoTerminadoButton);


        tipoEventoChoiceBox.setOnAction(e -> {
            String tipoEventoSeleccionado = tipoEventoChoiceBox.getValue();
            notificarGestionarTipoEvento(tipoEventoSeleccionado, layout);
        });


        //Al usuario solo le interesan las ocurrencias cuando necesita el tipo de repeticion HASTA_OCURRENCIAS
        tipoRepeticionChoiceBox.setOnAction(e -> {
            Repeticion repeticionSeleccionada = tipoRepeticionChoiceBox.getValue();
            notificarGestionarRepeticion(repeticionSeleccionada, layout);
        });


        Scene scene = new Scene(layout);
        ventanaAgregarEvento.setScene(scene);
        ventanaAgregarEvento.show();
    }



    private void ingresarTareaConVencimiento() {

        Tarea tareaConVencimeitno = controlador.obtenerObjetoTareaConVencimiento();

        agregarTareaConVencimientoButton.setOnAction(e -> {

            String titulo = tituloField.getText();
            if (titulo.isEmpty()) {
                titulo = tareaConVencimeitno.obtenerTitulo();
            }

            String descripcion = descripcionField.getText();
            if (descripcion.isEmpty()) {
                descripcion = tareaConVencimeitno.obtenerDescripcion();
            }

            String horarioInicioText = horarioInicioTextField.getText();

            String minutosInicioText = minutosInicioTextField.getText();

            String horarioVencimientoText = horarioVencimientoTextField.getText();

            String minutosVencimientoText = minutosVencimientoTextField.getText();

            int horaInicio;
            int minutosInicio;
            int horaVencimiento;
            int minutosVencimiento;

            try {
                horaInicio = Integer.parseInt(horarioInicioText);
                minutosInicio = Integer.parseInt(minutosInicioText);
                horaVencimiento = Integer.parseInt(horarioVencimientoText);
                minutosVencimiento = Integer.parseInt(minutosVencimientoText);

            } catch (NumberFormatException ex) {
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText("Se produjo un error al agregar la tarea");
                alertaError.setContentText("Por favor, intentalo nuevamente");
                alertaError.showAndWait();
                horarioInicioTextField.clear();
                minutosInicioTextField.clear();
                horarioVencimientoTextField.clear();
                minutosVencimientoTextField.clear();
                return;
            }

            notificarAgregarTarea(new TareaConVencimiento(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaVencimientoSinHoraPicker.getValue().atTime(horaVencimiento, minutosVencimiento)));
            mostrarAlertaEventoTareaAgregado("Tarea","tarea");

            limpiarCampos();
        });

    }

    private void ingresarTareaDiaCompleto() {

        Tarea tareaDiaCompleto = controlador.obtenerObjetoTareaDiaCompleto();

        agregarTareaDiaCompletoButton.setOnAction(e -> {

            String titulo = tituloField.getText();
            if (titulo.isEmpty()) {
                titulo = tareaDiaCompleto.obtenerTitulo();
            }

            String descripcion = descripcionField.getText();
            if (descripcion.isEmpty()) {
                descripcion = tareaDiaCompleto.obtenerDescripcion();
            }

            LocalDate fechaInicio = fechaInicioPicker.getValue();
            controlador.agregarTarea(new TareaDiaCompleto(titulo, descripcion, fechaInicio));
            mostrarAlertaEventoTareaAgregado("Tarea","tarea");

            limpiarCampos();
        });

    }

    private void ingresarEvento(){

        agregarEventoTerminadoButton.setOnAction(e ->{

            String titulo = tituloField.getText();

            String descripcion = descripcionField.getText();

            String horarioInicioText = horarioInicioTextField.getText();

            String minutosInicioText = minutosInicioTextField.getText();

            String horarioFinText = horarioFinTextField.getText();

            String minutosFinText = minutosFinTextField.getText();


            int horaInicio;
            int minutosInicio;
            int horaFin;
            int minutosFin;

            try {
                horaInicio = Integer.parseInt(horarioInicioText);
                minutosInicio = Integer.parseInt(minutosInicioText);
                horaFin = Integer.parseInt(horarioFinText);
                minutosFin = Integer.parseInt(minutosFinText);

            } catch (NumberFormatException ex) {

                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText("Se produjo un error al agregar el evento");
                alertaError.setContentText("Por favor, intentalo nuevamente");
                alertaError.showAndWait();
                horarioInicioTextField.clear();
                minutosInicioTextField.clear();
                horarioVencimientoTextField.clear();
                minutosVencimientoTextField.clear();
                return;
            }
            int ocurrencias = Integer.parseInt(maxOcurrenciasPermitidasTextField.getText());

            Repeticion repeticiones = tipoRepeticionChoiceBox.getValue();
            if (repeticiones == null){
                repeticiones = Repeticion.HASTA_OCURRENCIAS;
            }

            int intervalo = Integer.parseInt(intervaloTextField.getText());
            String tipo = tipoEventoChoiceBox.getValue();
            DayOfWeek diaUno = diasSemanaChoiceBox.getValue();
            DayOfWeek diaDos = diasSemanaChoiceBoxDos.getValue();

            switch (tipo) {

                case ("Evento dia completo") -> {
                    ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaFinSinHoraPicker.getValue().atTime(horaFin, minutosFin), ocurrencias, repeticiones);
                    notificarAgregarEvento(eventoDiaCompletoConstruido);
                    mostrarAlertaEventoTareaAgregado("Evento", "evento");
                    limpiarCampos();
                }
                case ("Evento Diario") -> {
                    ConstructorEventos eventoDiarioConstruido = new ConstructorEventosDiarios(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaFinSinHoraPicker.getValue().atTime(horaFin, minutosFin), ocurrencias, repeticiones, intervalo);
                    notificarAgregarEvento(eventoDiarioConstruido);
                    mostrarAlertaEventoTareaAgregado("Evento", "evento");
                    limpiarCampos();
                }
                case ("Evento Semanal" ) -> {
                    ConstructorEventos eventoSemanalConstruido = new ConstructorEventosSemanales(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaFinSinHoraPicker.getValue().atTime(horaFin, minutosFin), ocurrencias, repeticiones, Set.of(diaUno, diaDos));
                    notificarAgregarEvento(eventoSemanalConstruido);
                    mostrarAlertaEventoTareaAgregado("Evento", "evento");
                    limpiarCampos();
                }
                case ("Evento Mensual") -> {
                    ConstructorEventos eventoMensualConstruido = new ConstructorEventosMensuales(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaFinSinHoraPicker.getValue().atTime(horaFin, minutosFin), ocurrencias, repeticiones, intervalo);
                    notificarAgregarEvento(eventoMensualConstruido);
                    mostrarAlertaEventoTareaAgregado("Evento", "evento");
                    limpiarCampos();
                }
                case ("Evento Anual") -> {
                    ConstructorEventos eventoAnualConstruido = new ConstructorEventosAnuales(titulo, descripcion, fechaInicioPicker.getValue().atTime(horaInicio, minutosInicio), fechaFinSinHoraPicker.getValue().atTime(horaFin, minutosFin), ocurrencias, repeticiones, intervalo);
                    notificarAgregarEvento(eventoAnualConstruido);
                    mostrarAlertaEventoTareaAgregado("Evento", "evento");
                    limpiarCampos();
                }
            }
        });
    }

    public void mostrarAlertaEventoTareaAgregado(String alerta, String eventoOTarea){

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle(alerta + " agregado");
        mensaje.setHeaderText(null);
        mensaje.setContentText("El "+ eventoOTarea +" se ha agregado correctamente.");
        mensaje.showAndWait();

    }


    public void mostarListaEventos() {
        ListView<Evento> listaEventos = controlador.obtenerListaEventos();
        controlador.mostrarListaEventos(listaEventos);
    }


    public void mostrarListaTareasPorLista() {

        ListView<Tarea> listaTareas = controlador.obtenerListaTareas();

        controlador.mostrarListaTareas(listaTareas);
    }

    //POP-UP
    public void mostrarDetallesTarea(Tarea tarea) {

        Stage stage = new Stage();
        stage.setTitle("Detalles de la Tarea");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label tituloLabel = new Label("Título: " + tarea.obtenerTitulo());
        Label descripcionLabel = new Label("Descripción: " + tarea.obtenerDescripcion());
        Label fechaInicioLabel = new Label("Fecha de Inicio: " + tarea.obtenerFechaInicio());
        Label fechaVencimientoLabel = new Label("Fecha de vencimiento: " + tarea.obtenerFechaVencimiento());
        CheckBox estaCompletaCheckBox = new CheckBox("Completada");
        estaCompletaCheckBox.setSelected(tarea.estaCompleta());

        Label alarmas = new Label("Alarmas" + tarea.obtenerAlarmas());

        estaCompletaCheckBox.setOnAction(e -> {
            if (estaCompletaCheckBox.isSelected()) {
                tarea.marcarComoCompleta();
            } else {
                tarea.marcarComoIncompleta();
            }
        });

        agregarAlarmaButton.setOnAction(e -> controlador.mostrarVentanaAgregarAlarma(tarea,null));

        layout.getChildren().addAll(tituloLabel, descripcionLabel, fechaInicioLabel, fechaVencimientoLabel,
                estaCompletaCheckBox,alarmas,agregarAlarmaButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();

    }

    //POP-UP
    public void mostrarDetallesEvento(Evento evento) {

        Stage stage = new Stage();
        stage.setTitle("Detalles de la Tarea");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label tituloLabel = new Label("Título: " + evento.obtenerTitulo());
        Label descripcionLabel = new Label("Descripción: " + evento.obtenerDescripcion());
        Label fechaInicioLabel = new Label("Fecha de Inicio: " + evento.obtenerFechaInicio());
        Label fechaFinLabel = new Label("Fecha Final: " + evento.obtenerFechaFin());
        Label tipoRepeticionLabel = new Label("Tipo de repeticion: " + evento.obtenerTipoRepeticion());
        Label alarmas = new Label("Alarmas" + evento.obtenerAlarmasEvento());

        agregarAlarmaButton.setOnAction(e -> controlador.mostrarVentanaAgregarAlarma(null,evento));
        
        layout.getChildren().addAll(tituloLabel, descripcionLabel, fechaInicioLabel, fechaVencimientoLabel,
                fechaFinLabel,tipoRepeticionLabel,alarmas,agregarAlarmaButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }



    public void mostrarVentanaAgregarAlarmaFechaAbsouluta(Tarea tarea, Evento evento){

        Stage ventanaAgregarEvento= new Stage();
        ventanaAgregarEvento.setTitle("Agregar Alarma");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));
        Button agregar = new Button("Agregar");

        Efecto notificacion = new Notificacion();

        agregar.setOnAction(e -> {

            int minutosInicio = 0;
            int horaInicio = 0;


            try {
                minutosInicio = Integer.parseInt(minutosInicioAlarmaTextField.getText());
                horaInicio = Integer.parseInt(horaInicioAlarmaTextField.getText());

            } catch (NumberFormatException ex) {
                controlador.mostrarMensajeErrorALarma();
            }

            if (evento != null) {
                if (Objects.equals(tipoAlarmaChoiceBox.getValue(), "Notificacion")){
                    Alarma alarma = new AlarmaFechaAbsoluta(fechaAlarma.getValue().atTime(horaInicio,minutosInicio), notificacion);
                    controlador.eventoAgregarAlarma(evento, alarma);
                    controlador.mostrarMensajeAlarmaAgregada();

                    ventanaAgregarEvento.close();
                }


            } else {
                if (Objects.equals(tipoAlarmaChoiceBox.getValue(), "Notificacion")) {
                    Alarma alarma = new AlarmaFechaAbsoluta(fechaAlarma.getValue().atTime(horaInicio,minutosInicio), notificacion);
                    controlador.tareaAgregarAlarma(tarea, alarma);
                    controlador.mostrarMensajeAlarmaAgregada();

                    ventanaAgregarEvento.close();
                }
            }
        });

        layout.getChildren().addAll(tipoAlarmaLabel,tipoAlarmaChoiceBox,fechaAlarmaLabel,fechaAlarma,horasYMinutosLabel,horaInicioAlarmaTextField,minutosInicioAlarmaTextField,agregar);
        Scene scene = new Scene(layout);
        ventanaAgregarEvento.setScene(scene);
        ventanaAgregarEvento.show();
    }



    public void mostrarVentanaAgregarAlarmaIntervalo(Tarea tarea, Evento evento){

        Stage ventanaAgregarAlarma= new Stage();
        ventanaAgregarAlarma.setTitle("Agregar Alarma");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));
        Button agregar = new Button("Agregar");


        agregar.setOnAction(e -> {
            int intervalo = 0;

            Efecto efecto;
            try {
                intervalo = Integer.parseInt(intervaloAlarmaTextField.getText());

            }catch (NumberFormatException nf){
                controlador.mostrarMensajeErrorALarma();
            }

            if (Objects.equals(tipoAlarmaChoiceBox.getValue(), "Notificacion")){
                efecto = new Notificacion();
            } else {
                efecto = null;
            }

            int finalIntervalo = intervalo;

            Alarma alarma;
            if (evento != null) {
                alarma = new AlarmaIntervalo(evento.obtenerFechaInicio(), finalIntervalo, efecto);
                controlador.eventoAgregarAlarma(evento, alarma);

            } else {
                alarma = new AlarmaIntervalo(tarea.obtenerFechaInicio(), finalIntervalo, efecto);
                controlador.tareaAgregarAlarma(tarea, alarma);
            }
            controlador.mostrarMensajeAlarmaAgregada();
            ventanaAgregarAlarma.close();
        });

        layout.getChildren().addAll(tipoAlarmaLabel,tipoAlarmaChoiceBox,intervaloAlarmaLabel,intervaloAlarmaTextField,agregar);
        Scene scene = new Scene(layout);
        ventanaAgregarAlarma.setScene(scene);
        ventanaAgregarAlarma.show();

    }



    private void limpiarCampos() {
        List<TextField> camposTexto = Arrays.asList(tituloField, descripcionField, horarioInicioTextField, minutosInicioTextField, horarioVencimientoTextField, minutosVencimientoTextField,horarioFinTextField,minutosFinTextField);
        camposTexto.forEach(TextField::clear);

        if (tipoRepeticionChoiceBox != null){
            tipoRepeticionChoiceBox.setValue(null);
        }

        if (fechaInicioPicker != null) {
            fechaInicioPicker.setValue(null);
        }

        if (fechaVencimientoSinHoraPicker != null) {
            fechaVencimientoSinHoraPicker.setValue(null);
        }
        if (fechaFinSinHoraPicker != null){
            fechaFinSinHoraPicker.setValue(null);
        }
    }

}