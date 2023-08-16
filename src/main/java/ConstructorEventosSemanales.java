import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Set;

public class ConstructorEventosSemanales implements ConstructorEventos{

    private final EventoSemanal eventoSemanal;

    public ConstructorEventosSemanales(LocalDateTime fechaInicio){
        this.eventoSemanal = new EventoSemanal(fechaInicio);
    }

    public ConstructorEventosSemanales(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion, Set<DayOfWeek> diasSemana){
        this.eventoSemanal = new EventoSemanal(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion, diasSemana);
    }


    public void setTitulo() {
        this.eventoSemanal.obtenerTitulo();
    }

    public void setDescripcion() {
        this.eventoSemanal.obtenerDescripcion();
    }

    public void setFechaInicio() {
        this.eventoSemanal.obtenerFechaInicio();
    }

    public void setFechaFin() {
        this.eventoSemanal.obtenerFechaFin();
    }

    public void setMaxOcurrencias() {
        this.eventoSemanal.obtenerMaxOcurrencias();
    }

    public void setTipoRepeticion() {
        this.eventoSemanal.obtenerTipoRepeticion();
    }


    public void setDiasSemana() {
        eventoSemanal.obtenerDiasSemana();
    }



    public void setIntervalo() {

    }

    public EventoSemanal obtenerEventoCreado(){
        return this.eventoSemanal;
    }


    //Metodo que crea una nueva instancia del objeto eventoSemanal según la cantidad de veces que indique el tipo de repeticion
    //Por cada fecha en la que el evento se deba repetir, se crea un nuevo objeto con dicha fecha como una nueva fecha de inicio

    //PRE: Recibe una fecha y una lista de objetos evento
    //POS: Segun la repeticion que le corresponda al objeto, crea una nueva instancia de eventoSemanal con la proxima fecha de ocurrencia como nueva fecha de inicio del evento
    //Agrega esta nueva instancia del objeto a la array list y la devuelve
    public ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> proximosEventos) {

        switch (eventoSemanal.obtenerTipoRepeticion()) {

            case INFINITA -> {
                var eventoInfinito = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), eventoSemanal.obtenerFechaInicio(), eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                eventoInfinito.establecerId(eventoSemanal.obtenerId());
                proximosEventos.add(eventoInfinito);

                while (eventoSemanal.obtenerOcurrencias() < eventoSemanal.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoSemanal.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoInfinitoNuevo = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), proximaFecha, eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                    eventoInfinitoNuevo.establecerId(eventoSemanal.obtenerId());
                    proximosEventos.add(eventoInfinitoNuevo);

                    eventoSemanal.sumarOcurrencias();
                }
                return proximosEventos;
            }

            case HASTA_FECHA_FIN -> {

                if (!eventoSemanal.fechaFinNula()) {

                    var eventoFechaFin = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), proximaFecha, eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                    eventoFechaFin.establecerId(eventoSemanal.obtenerId());
                    proximosEventos.add(eventoFechaFin);
                }

                while (proximaFecha.isBefore(eventoSemanal.obtenerFechaFin())) {

                    proximaFecha = eventoSemanal.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoFechaFinNuevo = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), proximaFecha, eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                    eventoFechaFinNuevo.establecerId(eventoSemanal.obtenerId());
                    eventoFechaFinNuevo.establecerFechaInicio(proximaFecha);
                    proximosEventos.add(eventoFechaFinNuevo);

                }
                return proximosEventos;
            }

            case HASTA_OCURRENCIAS -> {

                var eventoOcurrencias = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), proximaFecha, eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                eventoOcurrencias.establecerId(eventoSemanal.obtenerId());
                proximosEventos.add(eventoOcurrencias);

                eventoSemanal.sumarOcurrencias();

                while (eventoSemanal.obtenerOcurrencias() < eventoSemanal.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoSemanal.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoOcurrenciasNuevo = new EventoSemanal(eventoSemanal.obtenerTitulo(), eventoSemanal.obtenerDescripcion(), proximaFecha, eventoSemanal.obtenerFechaFin(), eventoSemanal.obtenerMaxOcurrencias(), eventoSemanal.obtenerTipoRepeticion(), eventoSemanal.obtenerDiasSemana());
                    eventoOcurrenciasNuevo.establecerId(eventoSemanal.obtenerId());
                    eventoOcurrenciasNuevo.establecerFechaInicio(proximaFecha);

                    proximosEventos.add(eventoOcurrenciasNuevo);
                    eventoSemanal.sumarOcurrencias();

                }
                return proximosEventos;
            }

            default -> {
                return proximosEventos;
            }

        }
    }

}
