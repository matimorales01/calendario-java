import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConstructorEventosAnuales implements ConstructorEventos{

    private final EventoAnual eventoAnual;


    public ConstructorEventosAnuales(LocalDateTime fechaInicio){

        this.eventoAnual = new EventoAnual(fechaInicio);

    }

    public ConstructorEventosAnuales(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion, int intervalo){
        this.eventoAnual = new EventoAnual(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion, intervalo);

    }


    public void setTitulo() {
        this.eventoAnual.obtenerTitulo();
    }

    public void setDescripcion() {
        this.eventoAnual.obtenerDescripcion();
    }

    public void setFechaInicio() {
        this.eventoAnual.obtenerFechaInicio();
    }

    public void setFechaFin() {
        this.eventoAnual.obtenerFechaFin();
    }

    public void setMaxOcurrencias() {
        this.eventoAnual.obtenerMaxOcurrencias();
    }

    public void setTipoRepeticion() {
        this.eventoAnual.obtenerTipoRepeticion();
    }


    public void setDiasSemana() {
    }


    //Intervalo de años
    public void setIntervalo() {
        this.eventoAnual.obtenerCantidadAnios();
    }

    public EventoAnual obtenerEventoCreado(){
        return this.eventoAnual;
    }


    //Metodo que crea una nueva instancia del objeto eventoAnual según la cantidad de veces que indique el tipo de repeticion
    //Por cada fecha en la que el evento se deba repetir, se crea un nuevo objeto con dicha fecha como una nueva fecha de inicio

    //PRE: Recibe una fecha y una lista de objetos evento
    //POS: Segun la repeticion que le corresponda al objeto, crea una nueva instancia de eventoAnual con la proxima fecha de ocurrencia como nueva fecha de inicio del evento
    //Agrega esta nueva instancia del objeto a la array list y la devuelve
    public ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> proximosEventos) {

        switch (eventoAnual.obtenerTipoRepeticion()) {

            case INFINITA -> {
                var eventoInfinito = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), eventoAnual.obtenerFechaInicio(), eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                eventoInfinito.establecerId(eventoAnual.obtenerId());

                proximosEventos.add(eventoInfinito);

                while (eventoAnual.obtenerOcurrencias() < eventoAnual.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoAnual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoInfinitoNuevo = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), proximaFecha, eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                    eventoInfinitoNuevo.establecerId(eventoAnual.obtenerId());

                    proximosEventos.add(eventoInfinitoNuevo);

                    eventoAnual.sumarOcurrencias();
                }
                return proximosEventos;
            }

            case HASTA_FECHA_FIN -> {

                if (!eventoAnual.fechaFinNula()) {

                    var eventoFechaFin = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), proximaFecha, eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                    eventoFechaFin.establecerId(eventoAnual.obtenerId());

                    proximosEventos.add(eventoFechaFin);
                }

                while (proximaFecha.isBefore(eventoAnual.obtenerFechaFin())) {

                    proximaFecha = eventoAnual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoFechaFinNuevo = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), proximaFecha, eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                    eventoFechaFinNuevo.establecerId(eventoAnual.obtenerId());

                    eventoFechaFinNuevo.establecerFechaInicio(proximaFecha);
                    proximosEventos.add(eventoFechaFinNuevo);

                }
                return proximosEventos;
            }

            case HASTA_OCURRENCIAS -> {

                var eventoOcurrencias = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), proximaFecha, eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                eventoOcurrencias.establecerId(eventoAnual.obtenerId());
                proximosEventos.add(eventoOcurrencias);

                eventoAnual.sumarOcurrencias();

                while (eventoAnual.obtenerOcurrencias() < eventoAnual.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoAnual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoOcurrenciasNuevo = new EventoAnual(eventoAnual.obtenerTitulo(), eventoAnual.obtenerDescripcion(), proximaFecha, eventoAnual.obtenerFechaFin(), eventoAnual.obtenerMaxOcurrencias(), eventoAnual.obtenerTipoRepeticion(), eventoAnual.obtenerCantidadAnios());
                    eventoOcurrenciasNuevo.establecerId(eventoAnual.obtenerId());

                    eventoOcurrenciasNuevo.establecerFechaInicio(proximaFecha);

                    proximosEventos.add(eventoOcurrenciasNuevo);
                    eventoAnual.sumarOcurrencias();

                }
                return proximosEventos;
            }

            default -> {
                return proximosEventos;
            }

        }
    }

}
