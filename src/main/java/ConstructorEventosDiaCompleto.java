import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConstructorEventosDiaCompleto implements ConstructorEventos {

    private  final EventoDiaCompleto eventoDiaCompleto;

    public ConstructorEventosDiaCompleto(LocalDate fechaInicio){
        this.eventoDiaCompleto = new EventoDiaCompleto(fechaInicio);

    }

    public ConstructorEventosDiaCompleto(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion){
        this.eventoDiaCompleto = new EventoDiaCompleto(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion);

    }

    public void setTitulo() {
        this.eventoDiaCompleto.obtenerTitulo();
    }

    public void setDescripcion() {
        this.eventoDiaCompleto.obtenerDescripcion();
    }

    public void setFechaInicio() {
        this.eventoDiaCompleto.obtenerFechaInicio();
    }

    public void setFechaFin() {
        this.eventoDiaCompleto.obtenerFechaFin();
    }

    public void setMaxOcurrencias() {
        this.eventoDiaCompleto.obtenerMaxOcurrencias();
    }

    public void setTipoRepeticion() {
        this.eventoDiaCompleto.obtenerTipoRepeticion();
    }

    public void setDiasSemana() {
    }

    public void setIntervalo() {
    }

    public EventoDiaCompleto obtenerEventoCreado(){
        return this.eventoDiaCompleto;
    }


    //Metodo que crea una nueva instancia del objeto eventoDiaCompleto según la cantidad de veces que indique el tipo de repeticion
    //Por cada fecha en la que el evento se deba repetir, se crea un nuevo objeto con dicha fecha como una nueva fecha de inicio

    //PRE: Recibe una fecha y una lista de objetos evento
    //POS: Segun la repeticion que le corresponda al objeto, crea una nueva instancia de eventoDiaCompleto con la proxima fecha de ocurrencia como nueva fecha de inicio del evento
    //Agrega esta nueva instancia del objeto a la array list y la devuelve
    public ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> proximosEventos) {

        switch (eventoDiaCompleto.obtenerTipoRepeticion()) {

            case INFINITA -> {
                var eventoInfinito = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), eventoDiaCompleto.obtenerFechaInicio(), proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                eventoInfinito.establecerId(eventoDiaCompleto.obtenerId());
                proximosEventos.add(eventoInfinito);

                while (eventoDiaCompleto.obtenerOcurrencias() < eventoDiaCompleto.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoDiaCompleto.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoInfinitoNuevo = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), proximaFecha,proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                    eventoInfinitoNuevo.establecerId(eventoDiaCompleto.obtenerId());
                    proximosEventos.add(eventoInfinitoNuevo);

                    eventoDiaCompleto.sumarOcurrencias();
                }
                return proximosEventos;
            }

            case HASTA_FECHA_FIN -> {

                if (!eventoDiaCompleto.fechaFinNula()) {

                    var eventoFechaFin = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), proximaFecha, proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                    eventoFechaFin.establecerId(eventoDiaCompleto.obtenerId());
                    proximosEventos.add(eventoFechaFin);
                }

                while (proximaFecha.isBefore(eventoDiaCompleto.obtenerFechaFin())) {

                    proximaFecha = eventoDiaCompleto.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoFechaFinNuevo = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), proximaFecha, proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                    eventoFechaFinNuevo.establecerId(eventoDiaCompleto.obtenerId());
                    eventoFechaFinNuevo.establecerFechaInicio(proximaFecha);
                    proximosEventos.add(eventoFechaFinNuevo);

                }
                return proximosEventos;
            }

            case HASTA_OCURRENCIAS -> {

                var eventoOcurrencias = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), proximaFecha, proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                eventoOcurrencias.establecerId(eventoDiaCompleto.obtenerId());
                proximosEventos.add(eventoOcurrencias);

                eventoDiaCompleto.sumarOcurrencias();

                while (eventoDiaCompleto.obtenerOcurrencias() < eventoDiaCompleto.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoDiaCompleto.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoOcurrenciasNuevo = new EventoDiaCompleto(eventoDiaCompleto.obtenerTitulo(), eventoDiaCompleto.obtenerDescripcion(), proximaFecha,proximaFecha.plusDays(1), eventoDiaCompleto.obtenerMaxOcurrencias(), eventoDiaCompleto.obtenerTipoRepeticion());
                    eventoOcurrenciasNuevo.establecerId(eventoDiaCompleto.obtenerId());
                    eventoOcurrenciasNuevo.establecerFechaInicio(proximaFecha);

                    proximosEventos.add(eventoOcurrenciasNuevo);
                    eventoDiaCompleto.sumarOcurrencias();

                }
                return proximosEventos;
            }

            default -> {
                return proximosEventos;
            }

        }
    }




}
