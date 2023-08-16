import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;


public class GUIControlador implements GUIObserver{

    private Calendario calendario; //Modelo
    private final Tarea tareaDiaCompleto;
    private final Tarea tareaConVencimiento;
    private LocalDate fechaActual;
    private final StringProperty fechaActualString;


    private final GUIVista vista;
    private final ListView<Tarea> listaTareas;
    private final ListView<Evento> listaEventos;

    private final List<Evento> eventosMesActual ;
    private final List<Tarea> tareasMesActual;

    private YearMonth mesAnioActual;
    private final Label mesAnioActualLabel;

    private final GridPane calendarioGrid;


    public GUIControlador(Calendario calendario, GUIVista vista, GridPane calendarioGrid, Label mesAnioActualLabel) {
        this.calendario = calendario;
        this.vista = vista;
        this.vista.setControlador(this);

        this.listaTareas = new ListView<>();
        this.listaEventos = new ListView<>();
        this.eventosMesActual = new ArrayList<>();
        this.tareasMesActual = new ArrayList<>();

        this.tareaDiaCompleto = new TareaDiaCompleto();
        this.tareaConVencimiento = new TareaConVencimiento();

        this.mesAnioActualLabel = mesAnioActualLabel;
        this.calendarioGrid = calendarioGrid;

        this.mesAnioActual = YearMonth.now();

        this.vista.agregarObservador(this);

        this.fechaActual = LocalDate.now();
        this.fechaActualString = new SimpleStringProperty();

    }



    public ListView<Tarea> obtenerListaTareas() {
        this.listaTareas.setItems(FXCollections.observableArrayList(calendario.obtenerTareas()));
        return this.listaTareas;
    }

    public ListView<Evento> obtenerListaEventos() {
        this.listaEventos.setItems(FXCollections.observableArrayList(calendario.obtenerListaEventosTotales()));
        return this.listaEventos;
    }

    public Tarea obtenerObjetoTareaDiaCompleto() {
        return this.tareaDiaCompleto;
    }

    public Tarea obtenerObjetoTareaConVencimiento() {
        return this.tareaConVencimiento;
    }


    @Override
    public void agregarTarea(Tarea tarea) {
        calendario.agregarTarea(tarea);
        vista.mostrarListaTareasPorLista();
        Calendario.guardarCalendarioEnArchivo(calendario, "calendario.json");
    }

    @Override
    public void agregarEvento(ConstructorEventos constructorEventos) {

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(constructorEventos);
        calendario.agregarEventosACalendario(proximosEventos);
        listaEventos.getItems().addAll(calendario.obtenerListaEventosTotales());
        vista.mostarListaEventos();
        Calendario.guardarCalendarioEnArchivo(calendario,"calendario.json");
    }

    public void tareaAgregarAlarma(Tarea tarea, Alarma alarma) {
        tarea.agregarAlarma(alarma);
        alarma.activarAlarma(alarma.obtenerFechaYHora());
    }

    public void eventoAgregarAlarma(Evento evento, Alarma alarma) {
        evento.agregarAlarmaEvento(alarma);
        alarma.activarAlarma(alarma.obtenerFechaYHora());
    }

    @Override
    public void mostrarMesAnterior() {
        mesAnioActual = mesAnioActual.minusMonths(1);
        crearCalendario();
        actualizarLabel();
    }

    @Override
    public void mostrarMesSiguiente() {
        mesAnioActual = mesAnioActual.plusMonths(1);
        crearCalendario();
        actualizarLabel();
    }


    public void crearCalendario() {

        calendarioGrid.getChildren().clear();

        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        IntStream.range(0, 7)
                .forEach(columna -> {
                    Label diaDeSemanaLabel = new Label(diasSemana[columna]);
                    diaDeSemanaLabel.setStyle("-fx-font-weight: bold;");

                    calendarioGrid.add(diaDeSemanaLabel, columna, 0);
                    GridPane.setHalignment(diaDeSemanaLabel, HPos.CENTER);
                });


        LocalDate primerDiaMes = mesAnioActual.atDay(1);

        int comienzoDiaSemana = primerDiaMes.getDayOfWeek().getValue();


        int diaMes = 1;
        int fila = 1;

        while (diaMes <= mesAnioActual.lengthOfMonth()) { //Mientras el día del mes actual sea menor o igual al número total de días en el mes actual

            int columna = (comienzoDiaSemana + diaMes - 2) % 7; //Formatea la columna donde se colocara el dia

            columna = (columna < 0) ? columna + 7 : columna; //Si la operacion anterior dio negativa → diaMes actual está antes del primer día de la semana

            Label diaLabel = new Label(String.valueOf(diaMes));
            calendarioGrid.add(diaLabel, columna, fila);
            GridPane.setHalignment(diaLabel, HPos.CENTER);

            LocalDate fechaDeseada = mesAnioActual.atDay(diaMes);

            if (tieneEventosEnFecha(fechaDeseada)) {
                diaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightsalmon;");
            }

            if (tieneTareasEnFecha(fechaDeseada)) {
                diaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightblue;");
            }

            if (tieneEventosYTareasEnFecha(fechaDeseada)) {
                diaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: greenyellow;");
            }


            int finalDiaMes = diaMes;
            diaLabel.setOnMouseClicked(e -> {
                mostrarEventosDelDia(finalDiaMes);
                mostrarTareasDelDia(finalDiaMes);
            });

            if (columna == 6) {
                fila++;
            }

            diaMes++;
            LocalDate ultimoDia = mesAnioActual.atDay(mesAnioActual.lengthOfMonth());
            crearListaEventosYTareasEnFecha(primerDiaMes,ultimoDia);

        }
    }



    private void calendarioSemanalGrid() {
        calendarioGrid.getChildren().clear();

        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        IntStream.range(0, 7)
                .forEach(columna -> {
                    Label diaDeSemanaLabel = new Label(diasSemana[columna]);
                    diaDeSemanaLabel.setStyle("-fx-font-weight: bold;");

                    calendarioGrid.add(diaDeSemanaLabel, columna, 0);
                    GridPane.setHalignment(diaDeSemanaLabel, HPos.CENTER);
                });


        LocalDate comienzoSemana = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        int fila = 1;

        for (LocalDate fecha = comienzoSemana; fecha.isBefore(finSemana.plusDays(1)); fecha = fecha.plusDays(1)) {
            Label fechaLabel = new Label(fecha.format(DateTimeFormatter.ofPattern("d")));
            calendarioGrid.add(fechaLabel, fecha.getDayOfWeek().getValue() - 1, fila);
            if (tieneEventosEnFecha(fecha)) {
                fechaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightsalmon;");
            }

            if (tieneTareasEnFecha(fecha)) {
                fechaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: lightblue;");
            }

            if (tieneEventosYTareasEnFecha(fecha)) {
                fechaLabel.setStyle("-fx-font-weight: bold; -fx-background-color: greenyellow;");
            }


            fila++;
            int finalDiaSemana = fecha.getDayOfMonth();
            fechaLabel.setOnMouseClicked(e -> {

                mostrarEventosDelDia(finalDiaSemana);
                mostrarTareasDelDia(finalDiaSemana);
            });
        }

    }

    //Muestra las semanas
    @Override
    public void mostrarSemanasConEventosYTareas() {
       calendarioGrid.getChildren().clear();
       calendarioSemanalGrid();
    }

    @Override
    public void semanaAnterior() {
        fechaActual = fechaActual.minusWeeks(1);
        fechaActualString.set(fechaActual.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        mostrarSemanasConEventosYTareas();
    }
    @Override
    public void semanaSiguiente() {
        fechaActual = fechaActual.plusWeeks(1);
        fechaActualString.set(fechaActual.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        mostrarSemanasConEventosYTareas();
    }

    private void crearListaEventosYTareasEnFecha( LocalDate primerDia,LocalDate ultimoDia ){
        actualizarEventosYTareasMesActual();

        eventosMesActual.addAll(calendario.obtenerEventosEntreFechas(primerDia, ultimoDia));
        tareasMesActual.addAll(calendario.obtenerTareasEntreFechas(primerDia, ultimoDia));
    }

    private  List<Evento> obtenerEventosMesActual(){
        return this.eventosMesActual;
    }

    private  List<Tarea> obtenerTareasMesActual(){
        return this.tareasMesActual;
    }

    private void actualizarEventosYTareasMesActual() {
        eventosMesActual.clear();
        eventosMesActual.addAll(obtenerEventosMesActual());

        tareasMesActual.clear();
        tareasMesActual.addAll(obtenerTareasMesActual());
    }

    private boolean tieneEventosEnFecha(LocalDate fechaDeseada) {

        for (Evento evento : listaEventos.getItems()) {
            if (evento.obtenerFechaInicio().toLocalDate().isEqual(fechaDeseada)) {
                return true;
            }
        }
        return false;
    }


    private boolean tieneTareasEnFecha(LocalDate fechaDeseada) {

        for (Tarea tarea : listaTareas.getItems()) {
            if (tarea.obtenerFechaInicio().toLocalDate().isEqual(fechaDeseada)) {
                return true;
            }
        }
        return false;
    }


    private boolean tieneEventosYTareasEnFecha(LocalDate fechaDeseada) {
        boolean tieneEventos = false;
        boolean tieneTareas = false;

        for (Evento evento : listaEventos.getItems()) {
            if (evento.obtenerFechaInicio().toLocalDate().isEqual(fechaDeseada)) {
                tieneEventos = true;
                break;
            }
        }

        for (Tarea tarea : listaTareas.getItems()) {
            if (tarea.obtenerFechaInicio().toLocalDate().isEqual(fechaDeseada)) {
                tieneTareas = true;
                break;
            }
        }

        return tieneEventos && tieneTareas;
    }


    public void actualizarLabel() {

        String mesAnioMensaje = mesAnioActual.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + mesAnioActual.getYear();
        mesAnioActualLabel.setText(mesAnioMensaje);
    }

    private void mostrarEventosDelDia(int diaMes) {

        LocalDate diaSeleccionado = mesAnioActual.atDay(diaMes);
        List<Evento> eventosDelDia = obtenerEventosDelDia(diaSeleccionado);

        if (eventosDelDia.isEmpty()) {
            mostrarMensaje("No hay eventos", "No hay eventos asignados a este dia.");

        } else {
            StringBuilder tituloPopUp = new StringBuilder();

            for (Evento evento : eventosDelDia) {
                tituloPopUp.append(evento.obtenerTitulo()).append("\n");
                tituloPopUp.append(evento.obtenerDescripcion()).append("\n");
                tituloPopUp.append(("Inicio: ")).append(evento.obtenerFechaInicio()).append("\n");
                tituloPopUp.append(("Fin: ")).append(evento.obtenerFechaFin()).append("\n");
            }
            mostrarMensaje("Eventos ", tituloPopUp.toString());

        }
    }

    private void mostrarTareasDelDia(int diaMes) {

        LocalDate diaSeleccionado = mesAnioActual.atDay(diaMes);
        List<Tarea> tareasDelDia = obtenerTareasDelDia(diaSeleccionado);

        if (tareasDelDia.isEmpty()) {
            mostrarMensaje("No hay tareas", "No hay tareas asignadas a este dia.");

        } else {
            StringBuilder tituloPopUp = new StringBuilder();

            for (Tarea tarea : tareasDelDia) {
                tituloPopUp.append(tarea.obtenerTitulo()).append("\n");
                tituloPopUp.append(tarea.obtenerDescripcion()).append("\n");
                tituloPopUp.append(("Inicio: ")).append(tarea.obtenerFechaInicio()).append("\n");
                tituloPopUp.append(("Vencimiento: ")).append(tarea.obtenerFechaVencimiento()).append("\n");

                if (tarea.estaCompleta()) {
                    tituloPopUp.append("Tarea completada \n");
                } else {
                    tituloPopUp.append("Tarea no completada \n");
                }

            }
            mostrarMensaje("Tarea del dia ", tituloPopUp.toString());
        }
    }

    private List<Evento> obtenerEventosDelDia(LocalDate fecha) {
        List<Evento> eventosDia = new ArrayList<>();

        for (Evento evento : listaEventos.getItems()) {
            if (evento.obtenerFechaInicio().toLocalDate().equals(fecha)) {
                eventosDia.add(evento);
            }
        }
        return eventosDia;
    }

    private List<Tarea> obtenerTareasDelDia(LocalDate fecha) {
        List<Tarea> TareasDia = new ArrayList<>();

        for (Tarea tarea : listaTareas.getItems()) {

            if (tarea.obtenerFechaInicio().toLocalDate().equals(fecha)) {
                TareasDia.add(tarea);
            }
        }
        return TareasDia;
    }


    @Override
    public void mostrarListaEventos(ListView<Evento> listaEventosMes){
        listaEventosMes.setCellFactory(event -> new ListCell<>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                } else {
                    setText("Evento:" + "\n" + "Título: " + evento.obtenerTitulo() + "\n" +
                            "Descripción: " + evento.obtenerDescripcion() + "\n" +
                            "Fecha de Inicio: " + evento.obtenerFechaInicio() + "\n" +
                            "Fecha Fin: " + evento.obtenerFechaFin() + "\n" +
                            "Tipo de repeticion: " + evento.obtenerTipoRepeticion() + "\n" +
                            "Alamas:" + evento.obtenerAlarmasEvento() + "\n");
                }
            }
        });
        listaEventosMes.setOnMouseClicked(event -> {
            Evento eventoSeleccionado = listaEventosMes.getSelectionModel().getSelectedItem();
            if (eventoSeleccionado != null) {
                vista.mostrarDetallesEvento(eventoSeleccionado);
            }

        });

    }

    @Override
    public void mostrarListaTareas(ListView<Tarea> listaTareasMes){
        listaTareasMes.setCellFactory(task -> new ListCell<>() {
            @Override
            protected void updateItem(Tarea tarea, boolean empty) {
                super.updateItem(tarea, empty);
                if (empty || tarea == null) {
                    setText(null);
                } else {
                    setText("Tarea:" + "\n" + "Título: " + tarea.obtenerTitulo() + "\n" +
                            "Descripción: " + tarea.obtenerDescripcion() + "\n" +
                            "Fecha de Inicio: " + tarea.obtenerFechaInicio() + "\n" +
                            "Fecha de Vencimiento: " + tarea.obtenerFechaVencimiento() + "\n" +
                            "Esta completa:" + tarea.estaCompleta() + "\n" +
                            "Es de dia completo: " + (tarea.getClass()) + "\n" +
                            "Alamas:" + tarea.obtenerAlarmas() + "\n" );
                }
            }
        });

        listaTareasMes.setOnMouseClicked(event -> {
            Tarea tareaSeleccionada = listaTareasMes.getSelectionModel().getSelectedItem();
            if (tareaSeleccionada != null) {
                vista.mostrarDetallesTarea(tareaSeleccionada);
            }

        });
    }



    public void mostrarVentanaConEventosDelMes(){

        Stage ventanaAgregarEvento = new Stage();
        ventanaAgregarEvento.setTitle("Eventos del mes");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));
        ListView<Evento> listaEventosMes = new ListView<>();
        listaEventosMes.setItems(FXCollections.observableArrayList(obtenerEventosMesActual()));
        mostrarListaEventos(listaEventosMes);
        layout.getChildren().addAll(new Label("Eventos del mes"),listaEventosMes);

        Scene scene = new Scene(layout);
        ventanaAgregarEvento.setScene(scene);
        ventanaAgregarEvento.show();

    }


    public void mostrarVentanaConTareasDelMes(){

        Stage ventanaAgregarTarea = new Stage();
        ventanaAgregarTarea.setTitle("Tareas del mes");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));

        ListView<Tarea> listaTareasMes = new ListView<>();
        listaTareasMes.setItems(FXCollections.observableArrayList(obtenerTareasMesActual()));
        mostrarListaTareas(listaTareasMes);
        layout.getChildren().addAll(new Label("Tareas del mes"),listaTareasMes);

        Scene scene = new Scene(layout);
        ventanaAgregarTarea.setScene(scene);
        ventanaAgregarTarea.show();
    }



    //Logica de agregar alarmas y actualizar el objeto /UI que luego puede ser mostrado
    public void mostrarVentanaAgregarAlarma(Tarea tarea,Evento evento){
        Stage ventanaAgregarEvento= new Stage();
        ventanaAgregarEvento.setTitle("Agregar Alarma");
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(5));
        Button conIntervalo = new Button("Horario absoluto");

        conIntervalo.setOnAction(e-> vista.mostrarVentanaAgregarAlarmaFechaAbsouluta(tarea, evento));

        Button conHoraAbsoluto = new Button("Con Intervalo");
        conHoraAbsoluto.setOnAction(e-> vista.mostrarVentanaAgregarAlarmaIntervalo(tarea, evento));

        layout.getChildren().addAll(conIntervalo,conHoraAbsoluto);

        Scene scene = new Scene(layout);
        ventanaAgregarEvento.setScene(scene);
        ventanaAgregarEvento.show();

    }



    //Logica para mostrar mensajes al usuario en la GUI
    public void mostrarMensajeAlarmaAgregada(){
        Alert alertaAgregada = new Alert(Alert.AlertType.INFORMATION);
        alertaAgregada.setTitle("Alarma agregada");
        alertaAgregada.setHeaderText("La alarma se agrego correctamente");
        alertaAgregada.showAndWait();
    }

    public void mostrarMensajeErrorALarma(){
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error");
        alertaError.setHeaderText("Se produjo un error al agregar la alarma");
        alertaError.setContentText("Por favor, intentalo nuevamente");
        alertaError.showAndWait();
    }

    private void mostrarMensaje(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }


    //Metodos pertenecientes a la serializacion del calendario
    public void iniciarAplicacion() {
        calendario = Calendario.cargarCalendarioDesdeArchivo("calendario.json");
        if (calendario == null) {
            // Si no se pudo cargar desde el archivo, crea un nuevo calendario
            calendario = new Calendario();
        }
    }

    @Override
    public void gestionarRepeticion(Repeticion repeticionSeleccionada, VBox layout, TextField maxOcurrenciasPermitidasTextField, TextField intervaloTextField, Label intervaloLabel, Label maxOcurrenciasPermitidasLabel, Label tipoRepeticionLabel, ChoiceBox<Repeticion> tipoRepeticionChoiceBox) {

        if (repeticionSeleccionada != Repeticion.HASTA_OCURRENCIAS) {

            if (repeticionSeleccionada == Repeticion.INFINITA) {
                maxOcurrenciasPermitidasTextField.setText("99"); //POR TEMAS DE MEMORIA, EL TIPO DE REPETICION INFINITO ES DE 99 OCURRENCIAS

            } else if (repeticionSeleccionada == Repeticion.SIN_REPETICION) {

                intervaloTextField.setText("1");
                maxOcurrenciasPermitidasTextField.setText("1");
                layout.getChildren().removeAll(intervaloLabel, intervaloTextField);
            }
            // En los casos anteriores no se deberian mostrar al usuario, ya que son redundantes, pero se deben setear por temas de parametros de cada objeto evento
            layout.getChildren().removeAll(maxOcurrenciasPermitidasLabel, maxOcurrenciasPermitidasTextField);

        } else {

            if (!layout.getChildren().contains(maxOcurrenciasPermitidasLabel)) {
                int indiceLabel = layout.getChildren().indexOf(tipoRepeticionLabel);
                int indiceText = layout.getChildren().indexOf(tipoRepeticionChoiceBox);
                layout.getChildren().add(indiceLabel, maxOcurrenciasPermitidasLabel);
                layout.getChildren().add(indiceText, maxOcurrenciasPermitidasTextField);
            }
        }
    }
    @Override
    public void gestionarTipoEvento(String tipoEventoSeleccionado, VBox layout, Button agregarEventoTerminadoButton, TextField intervaloTextField, Label intervaloLabel, Label diaSemanaLabel, Label tipoRepeticionLabel, ChoiceBox<DayOfWeek> diasSemanaChoiceBox, ChoiceBox<DayOfWeek> diasSemanaChoiceBoxDos, ChoiceBox<Repeticion> tipoRepeticionChoiceBox) {

        boolean esEventoSemanal = tipoEventoSeleccionado.equals("Evento Semanal");
        boolean esEventoDiaCompleto = tipoEventoSeleccionado.equals("Evento dia completo");

        if (!esEventoSemanal) {

            if (!layout.getChildren().contains(intervaloLabel)) { //Por si primero el usuario setea las repeticiones y se elimina el campo

                int indiceLabel = layout.getChildren().indexOf(tipoRepeticionLabel);
                int indiceText = layout.getChildren().indexOf(tipoRepeticionChoiceBox);
                layout.getChildren().add(indiceLabel, intervaloLabel);
                layout.getChildren().add(indiceText, intervaloTextField);
            }

            layout.getChildren().removeAll(diaSemanaLabel, diasSemanaChoiceBox, diasSemanaChoiceBoxDos);

            if (esEventoDiaCompleto) {
                layout.getChildren().removeAll(intervaloLabel, intervaloTextField);
            }

        } else {
            layout.getChildren().removeAll(intervaloLabel, intervaloTextField);

            if (!layout.getChildren().contains(diaSemanaLabel)) {
                int agregarLabelIndex = layout.getChildren().indexOf(agregarEventoTerminadoButton);
                layout.getChildren().add(agregarLabelIndex, diasSemanaChoiceBoxDos);
                layout.getChildren().add(agregarLabelIndex, diasSemanaChoiceBox);
                layout.getChildren().add(agregarLabelIndex, diaSemanaLabel);
            }
            //Si el usuario pone Evento Semanal, entonces el intervalo no es necesario, ya que simplemente opera con los días de semana a elegir
        }
    }

    @Override
    public void cerrarAplicacion() {
        Calendario.guardarCalendarioEnArchivo(calendario, "calendario.json");
    }

}