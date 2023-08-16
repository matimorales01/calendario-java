import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;

public interface GUIObserver {

    void agregarEvento(ConstructorEventos constructorEventos);
    void agregarTarea(Tarea tarea);
    void mostrarMesSiguiente();
    void mostrarMesAnterior();
    void mostrarSemanasConEventosYTareas();
    void mostrarListaTareas(ListView<Tarea>listaTareasTotales);
    void mostrarListaEventos(ListView<Evento> listaEventosTotales);
    void gestionarTipoEvento(String tipoEventoSeleccionado, VBox layout, Button agregarEventoTerminadoButton, TextField intervaloTextField, Label intervaloLabel, Label diaSemanaLabel, Label tipoRepeticionLabel, ChoiceBox<DayOfWeek> diasSemanaChoiceBox, ChoiceBox<DayOfWeek> diasSemanaChoiceBoxDos, ChoiceBox<Repeticion> tipoRepeticionChoiceBox);
    void gestionarRepeticion(Repeticion repeticionSeleccionada, VBox layout, TextField maxOcurrenciasPermitidasTextField, TextField intervaloTextField, Label intervaloLabel, Label maxOcurrenciasPermitidasLabel, Label tipoRepeticionLabel, ChoiceBox<Repeticion> tipoRepeticionChoiceBox);
    void semanaAnterior();
    void semanaSiguiente();
    void cerrarAplicacion();
}
