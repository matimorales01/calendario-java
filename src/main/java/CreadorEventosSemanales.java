public class CreadorEventosSemanales implements CreadorDeEventos{
    public Evento crearEvento(ConstructorEventos constructor) {

        constructor.setTitulo();
        constructor.setDescripcion();
        constructor.setFechaInicio();
        constructor.setFechaFin();
        constructor.setMaxOcurrencias();
        constructor.setTipoRepeticion();
        constructor.setDiasSemana();

        return constructor.obtenerEventoCreado();
    }

}
