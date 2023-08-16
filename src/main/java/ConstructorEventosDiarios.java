import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConstructorEventosDiarios implements ConstructorEventos{
    private final EventoDiario eventoDiario;

    public ConstructorEventosDiarios(LocalDateTime fechaInicio){
        this.eventoDiario = new EventoDiario(fechaInicio);
    }

    public ConstructorEventosDiarios(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias,Repeticion tipoRepeticion,int intervalo){
        this.eventoDiario = new EventoDiario(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion, intervalo);
    }

    public void setTitulo() {
        this.eventoDiario.obtenerTitulo();
    }

    public void setDescripcion() {
        this.eventoDiario.obtenerDescripcion();
    }

    public void setFechaInicio() {
        this.eventoDiario.obtenerFechaInicio();
    }

    public void setFechaFin() {
        this.eventoDiario.obtenerFechaFin();
    }

    public void setMaxOcurrencias() {
        this.eventoDiario.obtenerMaxOcurrencias();
    }

    public void setTipoRepeticion() {
        this.eventoDiario.obtenerTipoRepeticion();
    }

    public void setDiasSemana() {
    }

    public void setIntervalo() {
        this.eventoDiario.obtenerIntervalo();
    }

    public EventoDiario obtenerEventoCreado(){
        return this.eventoDiario;
    }


    //Metodo que crea una nueva instancia del objeto EventoDiario según la cantidad de veces que indique el tipo de repeticion
    //Por cada fecha en la que el evento se deba repetir, se crea un nuevo objeto con dicha fecha como una nueva fecha de inicio

    //PRE: Recibe una fecha y una lista de objetos evento
    //POS: Segun la repeticion que le corresponda al objeto, crea una nueva instancia de EventoDiario con la proxima fecha de ocurrencia como nueva fecha de inicio del evento
    //Agrega esta nueva instancia del objeto a la array list y la devuelve
    public ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> proximosEventos) {

        switch (eventoDiario.obtenerTipoRepeticion()) {

            case INFINITA -> {
                var eventoInfinito = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), eventoDiario.obtenerFechaInicio(), eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                eventoInfinito.establecerId(eventoDiario.obtenerId());
                proximosEventos.add(eventoInfinito);

                while (eventoDiario.obtenerOcurrencias() < eventoDiario.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoDiario.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoInfinitoNuevo = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), proximaFecha, eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                    eventoInfinitoNuevo.establecerId(eventoDiario.obtenerId());
                    proximosEventos.add(eventoInfinitoNuevo);

                    eventoDiario.sumarOcurrencias();
                }
                return proximosEventos;
            }

            case HASTA_FECHA_FIN -> {

                if (!eventoDiario.fechaFinNula()) {

                    var eventoFechaFin = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), proximaFecha, eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                    eventoFechaFin.establecerId(eventoDiario.obtenerId());

                    proximosEventos.add(eventoFechaFin);
                }

                while (proximaFecha.isBefore(eventoDiario.obtenerFechaFin())) {

                    proximaFecha = eventoDiario.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoFechaFinNuevo = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), proximaFecha, eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                    eventoFechaFinNuevo.establecerId(eventoDiario.obtenerId());

                    eventoFechaFinNuevo.establecerFechaInicio(proximaFecha);
                    proximosEventos.add(eventoFechaFinNuevo);

                }
                return proximosEventos;
            }

            case HASTA_OCURRENCIAS -> {

                var eventoOcurrencias = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), proximaFecha, eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                eventoOcurrencias.establecerId(eventoDiario.obtenerId());

                proximosEventos.add(eventoOcurrencias);

                eventoDiario.sumarOcurrencias();

                while (eventoDiario.obtenerOcurrencias() < eventoDiario.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoDiario.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoOcurrenciasNuevo = new EventoDiario(eventoDiario.obtenerTitulo(), eventoDiario.obtenerDescripcion(), proximaFecha, eventoDiario.obtenerFechaFin(), eventoDiario.obtenerMaxOcurrencias(), eventoDiario.obtenerTipoRepeticion(), eventoDiario.obtenerIntervalo());
                    eventoOcurrenciasNuevo.establecerId(eventoDiario.obtenerId());

                    eventoOcurrenciasNuevo.establecerFechaInicio(proximaFecha);

                    proximosEventos.add(eventoOcurrenciasNuevo);
                    eventoDiario.sumarOcurrencias();

                }
                return proximosEventos;
            }
            default -> {
                return proximosEventos;
            }

        }
    }
}
