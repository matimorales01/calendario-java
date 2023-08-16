public class CreadorEventosDiarios implements CreadorDeEventos {

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



