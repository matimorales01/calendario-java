import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConstructorEventosMensuales implements ConstructorEventos{
    private final EventoMensual eventoMensual;
    
    public ConstructorEventosMensuales(LocalDateTime fechaInicio){
        this.eventoMensual = new EventoMensual(fechaInicio);
    }

    public ConstructorEventosMensuales(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias,Repeticion tipoRepeticion,int intervalo){
        this.eventoMensual = new EventoMensual(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion, intervalo);
    }


    public void setTitulo() {
        this.eventoMensual.obtenerTitulo();
    }

    public void setDescripcion() {
        this.eventoMensual.obtenerDescripcion();
    }

    public void setFechaInicio() {
        this.eventoMensual.obtenerFechaInicio();
    }

    public void setFechaFin() {
        this.eventoMensual.obtenerFechaFin();
    }

    public void setMaxOcurrencias() {
        this.eventoMensual.obtenerMaxOcurrencias();
    }

    public void setTipoRepeticion() {
        this.eventoMensual.obtenerTipoRepeticion();
    }


    public void setDiasSemana() {
    }


    //Intervalo de meses
    public void setIntervalo() {
        this.eventoMensual.obtenerCantidadMeses();
    }

    public EventoMensual obtenerEventoCreado(){
        return this.eventoMensual;
    }


    //Metodo que crea una nueva instancia del objeto eventoMensual según la cantidad de veces que indique el tipo de repeticion
    //Por cada fecha en la que el evento se deba repetir, se crea un nuevo objeto con dicha fecha como una nueva fecha de inicio

    //PRE: Recibe una fecha y una lista de objetos evento
    //POS: Segun la repeticion que le corresponda al objeto, crea una nueva instancia de eventoMensual con la proxima fecha de ocurrencia como nueva fecha de inicio del evento
    //Agrega esta nueva instancia del objeto a la array list y la devuelve
    public ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> proximosEventos) {

        switch (eventoMensual.obtenerTipoRepeticion()) {

            case INFINITA -> {
                var eventoInfinito = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), eventoMensual.obtenerFechaInicio(), eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                eventoInfinito.establecerId(eventoMensual.obtenerId());
                proximosEventos.add(eventoInfinito);

                while (eventoMensual.obtenerOcurrencias() < eventoMensual.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoMensual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoInfinitoNuevo = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), proximaFecha, eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                    eventoInfinitoNuevo.establecerId(eventoMensual.obtenerId());
                    proximosEventos.add(eventoInfinitoNuevo);

                    eventoMensual.sumarOcurrencias();
                }
                return proximosEventos;
            }

            case HASTA_FECHA_FIN -> {

                if (!eventoMensual.fechaFinNula()) {

                    var eventoFechaFin = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), proximaFecha, eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                    eventoFechaFin.establecerId(eventoMensual.obtenerId());
                    proximosEventos.add(eventoFechaFin);
                }

                while (proximaFecha.isBefore(eventoMensual.obtenerFechaFin())) {

                    proximaFecha = eventoMensual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoFechaFinNuevo = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), proximaFecha, eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                    eventoFechaFinNuevo.establecerId(eventoMensual.obtenerId());
                    eventoFechaFinNuevo.establecerFechaInicio(proximaFecha);
                    proximosEventos.add(eventoFechaFinNuevo);

                }
                return proximosEventos;
            }

            case HASTA_OCURRENCIAS -> {

                var eventoOcurrencias = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), proximaFecha, eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                proximosEventos.add(eventoOcurrencias);

                eventoMensual.sumarOcurrencias();

                while (eventoMensual.obtenerOcurrencias() < eventoMensual.obtenerMaxOcurrencias()) {

                    proximaFecha = eventoMensual.calcularSiguienteOcurrencia(proximaFecha);

                    var eventoOcurrenciasNuevo = new EventoMensual(eventoMensual.obtenerTitulo(), eventoMensual.obtenerDescripcion(), proximaFecha, eventoMensual.obtenerFechaFin(), eventoMensual.obtenerMaxOcurrencias(), eventoMensual.obtenerTipoRepeticion(), eventoMensual.obtenerCantidadMeses());
                    eventoOcurrenciasNuevo.establecerId(eventoMensual.obtenerId());
                    eventoOcurrenciasNuevo.establecerFechaInicio(proximaFecha);

                    proximosEventos.add(eventoOcurrenciasNuevo);
                    eventoMensual.sumarOcurrencias();

                }
                return proximosEventos;
            }

            default -> {
                return proximosEventos;
            }

        }
    }

}
