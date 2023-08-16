public class CreadorEventosMensuales implements CreadorDeEventos{
    public Evento crearEvento(ConstructorEventos constructor) {

        constructor.setTitulo();
        constructor.setDescripcion();
        constructor.setFechaInicio();
        constructor.setFechaFin();
        constructor.setMaxOcurrencias();
        constructor.setTipoRepeticion();
        constructor.setIntervalo();

        return constructor.obtenerEventoCreado();
    }
}
