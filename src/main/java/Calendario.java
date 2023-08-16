import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.core.JsonProcessingException;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Calendario implements Serializable {


    @JsonProperty("eventos")
    private final ArrayList<Evento> eventos;
    @JsonProperty("tareas")
    private ArrayList<Tarea> tareas;


    public Calendario() {
        this.tareas = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }


    public void agregarEvento(Evento evento) {

        this.eventos.add(evento);
    }

    //PRE: Recibe una lista de objetos Evento
    //POS: Agrega toda la lista de los eventos a la clase calendario
    public void agregarEventosACalendario(ArrayList<Evento> listaEventos) {

        this.eventos.addAll(listaEventos);
    }

    public ArrayList<Evento> proximosEventos(ConstructorEventos constructor) {

        ArrayList<Evento> OcurrenciasEventos = new ArrayList<>();

        // Añade la primera fecha ingresada

        OcurrenciasEventos.add(constructor.obtenerEventoCreado());
        constructor.obtenerEventoCreado().sumarOcurrencias();

        LocalDateTime proximaFecha = constructor.obtenerEventoCreado().calcularSiguienteOcurrencia(constructor.obtenerEventoCreado().obtenerFechaInicio());

        return constructor.repeticionEvento(proximaFecha, OcurrenciasEventos);
    }

    //PRE: -
    //POS: Devuelve la lista de objetos evento creada en el calendario
    public ArrayList<Evento> obtenerListaEventosTotales() {
        return this.eventos;
    }


    //PRE: Recibe una el objeto Evento a eliminar
    //POS: Itera por toda la lista de los eventos totales almacenados en la clase calendario. Si las clases del elemento a eliminar y el
    // evento encontrado en la lista coinciden, entonces lo elimina. Esto asegura que se eliminen todas las ocurrencias de dicho objeto

    public void eliminarEvento(Evento eventoAEliminar) {

        Iterator<Evento> iterador = obtenerListaEventosTotales().iterator();

        while (iterador.hasNext()) {
            Evento evento = iterador.next();

            if (evento.getClass().equals(eventoAEliminar.getClass()) && evento.obtenerTitulo().equals(eventoAEliminar.obtenerTitulo())) {
                iterador.remove();
            }
        }
    }


    public void modificarEvento(Evento eventoOriginal, String nuevoTitulo, String nuevaDescripcion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin, Alarma alarma, LocalDateTime nuevaFechaAlarma) {

        for (Evento evento : eventos) {

            if (eventoOriginal.obtenerId() == evento.obtenerId()) {
                evento.establecerTitulo(nuevoTitulo != null ? nuevoTitulo : evento.obtenerTitulo());
                evento.establecerDescripcion(nuevaDescripcion != null ? nuevaDescripcion : evento.obtenerDescripcion());
                evento.establecerFechaInicio(nuevaFechaInicio != null ? nuevaFechaInicio : evento.obtenerFechaInicio());
                evento.establecerFechaFin(nuevaFechaFin != null ? nuevaFechaFin : evento.obtenerFechaFin());

                if (alarma != null) {
                    evento.modificarAlarmaEvento(alarma, nuevaFechaAlarma);
                }
            }
        }


    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public void modificarTarea(int id, String nuevoTitulo, String nuevaDescripcion, LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaVencimiento, Alarma alarma, LocalDateTime nuevaFechaAlarma) {

        boolean encontrada = false;
        for (int i = 0; i < tareas.size() && !encontrada; i++) {
            Tarea tarea = tareas.get(i);
            if (tarea.obtenerId() == id) {
                tarea.establecerTitulo(nuevoTitulo != null ? nuevoTitulo : tarea.obtenerTitulo());
                tarea.establecerDescripcion(nuevaDescripcion != null ? nuevaDescripcion : tarea.obtenerDescripcion());
                tarea.establecerFechaInicio(nuevaFechaInicio != null ? nuevaFechaInicio : tarea.obtenerFechaInicio());
                tarea.establecerFechaVencimiento(nuevaFechaVencimiento != null ? nuevaFechaVencimiento : tarea.obtenerFechaVencimiento());
                if (alarma != null) {
                    tarea.modificarAlarma(alarma, nuevaFechaAlarma);
                }
                encontrada = true;
            }
        }

    }

    public void eliminarTarea(int id) {

        boolean encontrada = false;
        for (int i = 0; i < tareas.size() && !encontrada; i++) {

            Tarea tarea = tareas.get(i);

            if (tarea.obtenerId() == id) {
                tareas.remove(i);
                encontrada = true;

            }
        }
    }

    public ArrayList<Tarea> obtenerTareas() {
        return tareas;
    }

    Alarma proximaAlarma(LocalDateTime fechaReferencia) {
        ArrayList<Alarma> alarmas = new ArrayList<>();
        for (Evento evento : eventos) {
            alarmas.addAll(evento.obtenerAlarmasEvento());
        }
        for (Tarea tarea : tareas) {
            alarmas.addAll(tarea.obtenerAlarmas());
        }
        Alarma recorrerLista = null;
        for (Alarma alarma : alarmas) {
            if (recorrerLista == null || alarma.obtenerFechaYHora().isBefore(recorrerLista.obtenerFechaYHora())) {
                // la alarma actual es la próxima en sonar
                if (fechaReferencia.isBefore(alarma.obtenerFechaYHora())) {
                    // solo si la alarma es futura
                    recorrerLista = alarma;
                }
            }
        }
        return recorrerLista;
    }


// Método para obtener los eventos correspondientes entre dos fechas

    public ArrayList<Evento> obtenerEventosEntreFechas(LocalDate fechaA, LocalDate fechaB) {
        ArrayList<Evento> eventosEnRango = new ArrayList<>();
        for (Evento evento : this.eventos) {
            LocalDate fechaEvento = evento.obtenerFechaInicio().toLocalDate();
            if (fechaEvento.isAfter(fechaA) && fechaEvento.isBefore(fechaB)) {
                eventosEnRango.add(evento);
            }
        }
        return eventosEnRango;
    }

    public ArrayList<Tarea> obtenerTareasEntreFechas(LocalDate fechaA, LocalDate fechaB) {
        ArrayList<Tarea> tareasEnRango = new ArrayList<>();
        for (Tarea tarea : this.tareas) {
            LocalDate fechaTareas = tarea.obtenerFechaInicio().toLocalDate();
            if (fechaTareas.isAfter(fechaA) && fechaTareas.isBefore(fechaB)) {
                tareasEnRango.add(tarea);
            }
        }
        return tareasEnRango;
    }

    public static String serializarCalendario(Calendario calendario) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.writeValueAsString(calendario);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void escribirEnDocumento(String informacionSerializada, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(nombreArchivo)) {
            writer.write(informacionSerializada);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Calendario deserializarCalendario(String informacionSerializada) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(informacionSerializada, Calendario.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Calendario cargarCalendarioDesdeArchivo(String nombreArchivo) {
        try {
            String informacionSerializada = Files.readString(Paths.get(nombreArchivo));
            return deserializarCalendario(informacionSerializada);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void guardarCalendarioEnArchivo(Calendario calendario, String nombreArchivo) {
        String informacionSerializada = Calendario.serializarCalendario(calendario);
        if (informacionSerializada != null) {
            try {
                Files.write(Paths.get(nombreArchivo), informacionSerializada.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//METODOS DE SERIALIZACION/DESERIALIZACION QUE UTILIZAN EL ARCHIVO
//    public void serializarCalendario(String nombreArchivo) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        try {
//            objectMapper.writeValue(new File(nombreArchivo), this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Calendario deserializarCalendario(String nombreArchivo) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        File file = new File(nombreArchivo);
//        if (file.exists()) {
//            try {
//                return objectMapper.readValue(file, Calendario.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }










