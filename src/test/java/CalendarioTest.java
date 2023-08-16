import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;


import static org.junit.Assert.*;

public class CalendarioTest {

    //Creadores de los eventos, son la clase "Director" que utilizan el Builder
    CreadorDeEventos eventoDiarioCreado      = new CreadorEventosDiarios();
    CreadorDeEventos eventoSemanalCreado     = new CreadorEventosSemanales();
    CreadorDeEventos eventoMensualCreado     = new CreadorEventosMensuales();
    CreadorDeEventos eventoAnualCreado       = new CreadorEventosAnuales();
    CreadorDeEventos eventoDiaCompletoCreado = new CreadorEventosDiaCompleto();



    @Test
    public void testCrearTarea(){

        var calendario = new Calendario();
        var tarea = new TareaConVencimiento(LocalDateTime.of(2023,5,10,22,0));

        calendario.agregarTarea(tarea);

        assertEquals(1,calendario.obtenerTareas().size());
        assertTrue(calendario.obtenerTareas().contains(tarea));
    }

    @Test
    public void testTareaSeCreaConDatosCorrectos(){

        var calendario = new Calendario();
        var tarea = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        calendario.agregarTarea(tarea);

        assertEquals("Tarea",calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Hacer la tarea",calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,22,23,59,59),calendario.obtenerTareas().get(0).obtenerFechaVencimiento());

    }
    @Test
    public void testCrearVariasTareas(){

        var calendario = new Calendario();

        var tareaUno = new TareaConVencimiento(LocalDateTime.of(2023,5,18,22,0));
        var tareaDos = new TareaDiaCompleto(LocalDate.of(2023,4,18));
        var tareaTres = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        calendario.agregarTarea(tareaTres);

        assertEquals(3,calendario.obtenerTareas().size());
        assertTrue(calendario.obtenerTareas().contains(tareaUno));
        assertTrue(calendario.obtenerTareas().contains(tareaDos));
        assertTrue(calendario.obtenerTareas().contains(tareaTres));
    }
    @Test
    public void testSePuedeCrearTareasIguales(){

        var calendario = new Calendario();
        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);

        assertEquals(2,calendario.obtenerTareas().size());
        assertTrue(calendario.obtenerTareas().contains(tareaUno));
        assertTrue(calendario.obtenerTareas().contains(tareaDos));
        assertNotEquals(calendario.obtenerTareas().get(0).obtenerId(),calendario.obtenerTareas().get(1).obtenerId());

    }
    @Test
    public void testEliminarTarea(){

        var calendario = new Calendario();

        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,11,22));

        int idParaBorrar = tareaUno.obtenerId();

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);

        assertEquals(2,calendario.obtenerTareas().size());

        calendario.eliminarTarea(idParaBorrar);
        assertEquals(1,calendario.obtenerTareas().size());
        assertFalse(calendario.obtenerTareas().contains(tareaUno));

    }
    @Test
    public void testEliminarVariasTareas(){

        var calendario = new Calendario();

        var tareaUno = new TareaConVencimiento(LocalDateTime.of(2023,5,10,22,30));
        int idUno = tareaUno.obtenerId();

        var tareaDos = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        int idDos = tareaDos.obtenerId();

        var tareaTres = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        int idTres = tareaTres.obtenerId();

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        calendario.agregarTarea(tareaTres);

        assertEquals(3,calendario.obtenerTareas().size());
        calendario.eliminarTarea(idUno);
        calendario.eliminarTarea(idDos);
        calendario.eliminarTarea(idTres);

        assertEquals(0,calendario.obtenerTareas().size());
        assertFalse(calendario.obtenerTareas().contains(tareaUno));
        assertFalse(calendario.obtenerTareas().contains(tareaDos));
        assertFalse(calendario.obtenerTareas().contains(tareaTres));

    }
    @Test
    public void testSiHayTareasIgualesBorraLaCorrecta(){

        var calendario = new Calendario();

        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        int idUno = tareaUno.obtenerId();

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        assertEquals(2,calendario.obtenerTareas().size());

        calendario.eliminarTarea(idUno);
        assertEquals(1,calendario.obtenerTareas().size());
        assertFalse(calendario.obtenerTareas().contains(tareaUno));
        assertTrue(calendario.obtenerTareas().contains(tareaDos));

    }
    @Test
    public void testSiNoEncuentraIdNoEliminaNada(){

        var calendario = new Calendario();
        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        assertEquals(2,calendario.obtenerTareas().size());

        calendario.eliminarTarea(9393);
        assertEquals(2,calendario.obtenerTareas().size());
    }
    @Test
    public void testModificarTarea(){

        var calendario = new Calendario();
        var tarea = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        calendario.agregarTarea(tarea);
        int idUno = tarea.obtenerId();

        assertEquals("Tarea",calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Hacer la tarea",calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,22,23,59,59),calendario.obtenerTareas().get(0).obtenerFechaVencimiento());

        calendario.modificarTarea(idUno,"Nuevo Titulo","Nueva Descripcion",LocalDateTime.of(2023,12,24,0,0),LocalDateTime.of(2023,12,24,18,20),null,null);

        assertEquals("Nuevo Titulo",calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Nueva Descripcion",calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,24,0,0),calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,24,18,20),calendario.obtenerTareas().get(0).obtenerFechaVencimiento());

    }
    @Test
    public void testConDosTareasIgualesModificaCorrecta(){
        var calendario = new Calendario();
        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        int idUno = tareaUno.obtenerId();

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        calendario.modificarTarea(idUno,"Nuevo Titulo","Nueva Descripcion",LocalDateTime.of(2023,12,24,0,0),LocalDateTime.of(2023,12,24,18,20), null, null);

        assertEquals("Nuevo Titulo",calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Nueva Descripcion",calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,24,0,0),calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,24,18,20),calendario.obtenerTareas().get(0).obtenerFechaVencimiento());
        assertEquals("Tarea",calendario.obtenerTareas().get(1).obtenerTitulo());
        assertEquals("Hacer la tarea",calendario.obtenerTareas().get(1).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(1).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,22,23,59,59),calendario.obtenerTareas().get(1).obtenerFechaVencimiento());

    }

    @Test
    public void testModificarAlarmaDeTarea() {

        var calendario = new Calendario();
        var tarea = new TareaDiaCompleto("Tarea", "Hacer la tarea", LocalDate.of(2023, 12, 22));
        var notificacion = new Notificacion();
        var alarma = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 12, 22, 0, 0), notificacion);
        tarea.agregarAlarma(alarma);
        calendario.agregarTarea(tarea);
        int idUno = tarea.obtenerId();

        assertEquals("Tarea", calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Hacer la tarea", calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023, 12, 22, 0, 0), calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023, 12, 22, 23, 59, 59), calendario.obtenerTareas().get(0).obtenerFechaVencimiento());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(0).obtenerAlarmas().get(0).obtenerFechaYHora());

        calendario.modificarTarea(idUno, null, null, null,null, calendario.obtenerTareas().get(0).obtenerAlarmas().get(0),LocalDateTime.of(2023, 12, 21, 23, 30));
        assertEquals("Tarea", calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Hacer la tarea", calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023, 12, 22, 0, 0), calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023, 12, 22, 23, 59, 59), calendario.obtenerTareas().get(0).obtenerFechaVencimiento());
        assertEquals(LocalDateTime.of(2023, 12, 21, 23, 30),calendario.obtenerTareas().get(0).obtenerAlarmas().get(0).obtenerFechaYHora());

    }
    @Test
    public void testSiNoEncuentraIdNoModificaNada(){
        var calendario = new Calendario();
        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        calendario.modificarTarea(9999,"Nuevo Titulo","Nueva Descripcion",LocalDateTime.of(2023,12,24,0,0),LocalDateTime.of(2023,12,24,18,20),null,null);

        assertEquals("Tarea",calendario.obtenerTareas().get(0).obtenerTitulo());
        assertEquals("Hacer la tarea",calendario.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,22,23,59,59),calendario.obtenerTareas().get(0).obtenerFechaVencimiento());
        assertEquals("Tarea",calendario.obtenerTareas().get(1).obtenerTitulo());
        assertEquals("Hacer la tarea",calendario.obtenerTareas().get(1).obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,12,22,0,0),calendario.obtenerTareas().get(1).obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,12,22,23,59,59),calendario.obtenerTareas().get(1).obtenerFechaVencimiento());
    }

    @Test
    public void testCreoAlarmasYVeoCualEsLaProximaEnSonar(){
        var calendario = new Calendario();
        var tareaUno = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));
        var notificacion = new Notificacion();
        var alarma = new AlarmaIntervalo( LocalDateTime.of(2023, 5,2 , 0, 0),60,notificacion);
        var alarmaUno = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 5, 1, 0, 0),notificacion);
        var alarmaDos = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 29, 0, 0),notificacion);
        var alarmaTres = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 5, 5, 0, 0),notificacion);

        tareaUno.agregarAlarma(alarma);
        tareaUno.agregarAlarma(alarmaUno);
        tareaDos.agregarAlarma(alarmaDos);
        tareaDos.agregarAlarma(alarmaTres);
        calendario.agregarTarea(tareaUno);
        calendario.agregarTarea(tareaDos);
        assertEquals(alarmaDos,calendario.proximaAlarma(LocalDateTime.of(2023,1,1,0,0)));
    }

    @Test
    public void testCrearEventosYTareas() {

        ConstructorEventos   eventoDiarioConstruido       =   new ConstructorEventosDiarios(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoSemanalConstruido      =   new ConstructorEventosSemanales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoMensualConstruido      =   new ConstructorEventosMensuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoAnualConstruido        =   new ConstructorEventosAnuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoDiaCompletoConstruido  =   new ConstructorEventosDiaCompleto(LocalDate.of(2023,5,10));

        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);


        var tarea = new TareaConVencimiento(LocalDateTime.of(2023,5,10,22,30));
        var tareaDos = new TareaDiaCompleto("Tarea","Hacer la tarea", LocalDate.of(2023,12,22));

        var calendario = new Calendario();

        calendario.agregarTarea(tarea);
        calendario.agregarTarea(tareaDos);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);
        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoDiaCompleto);

        assertEquals(5,calendario.obtenerListaEventosTotales().size());
        assertEquals(2, calendario.obtenerTareas().size());

        assertTrue(calendario.obtenerTareas().contains(tarea));
    }

    @Test
    public void testCrearEventoDefault(){

        //Construyo los objetos con constructor default
        ConstructorEventos   eventoDiarioConstruido       =   new ConstructorEventosDiarios(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoSemanalConstruido      =   new ConstructorEventosSemanales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoMensualConstruido      =   new ConstructorEventosMensuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoAnualConstruido        =   new ConstructorEventosAnuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoDiaCompletoConstruido  =   new ConstructorEventosDiaCompleto(LocalDate.of(2023,5,10));

        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        var calendario = new Calendario();

        //Agrego eventos iguales
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);

        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);

        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoAnual);

        calendario.agregarEvento(eventoDiaCompleto);

        ArrayList<Evento> listaEventos = calendario.obtenerListaEventosTotales();
        int i = 0;
        for(Evento evento : listaEventos){
            assertEquals(evento, calendario.obtenerListaEventosTotales().get(i));
            i++;
        }

        //Verifico que esten los duplicados como instancias unicas
        assertEquals(listaEventos.get(0).getClass(), eventoDiario.getClass());
        assertEquals(listaEventos.get(1).getClass(), eventoDiario.getClass());

        assertEquals(listaEventos.get(2).getClass(), eventoSemanal.getClass());
        assertEquals(listaEventos.get(3).getClass(), eventoMensual.getClass());

        assertEquals(listaEventos.get(4).getClass(), eventoAnual.getClass());
        assertEquals(listaEventos.get(5).getClass(), eventoAnual.getClass());

        assertEquals(listaEventos.get(6).getClass(), eventoDiaCompleto.getClass());

        //Cada evento fue agregado correctamente, incluso duplicados
        assertEquals(7, calendario.obtenerListaEventosTotales().size());

    }

    @Test
    public void testEliminarEventoDefault(){

        //Construyo los objetos con constructor default
        ConstructorEventos   eventoDiarioConstruido       =   new ConstructorEventosDiarios(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoSemanalConstruido      =   new ConstructorEventosSemanales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoMensualConstruido      =   new ConstructorEventosMensuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoAnualConstruido        =   new ConstructorEventosAnuales(LocalDateTime.of(2023,5,10,20,30));
        ConstructorEventos   eventoDiaCompletoConstruido  =   new ConstructorEventosDiaCompleto(LocalDate.of(2023,5,10));

        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        var calendario = new Calendario();

        //Agrego eventos iguales
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);

        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);

        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoAnual);

        calendario.agregarEvento(eventoDiaCompleto);

        ArrayList<Evento> listaEventos = calendario.obtenerListaEventosTotales();

        //Elimino la instancia de EventoDiario y todas sus repeticiones
        calendario.eliminarEvento(listaEventos.get(0));

       for(Evento evento : listaEventos){
           assertNotEquals(evento.getClass() ,eventoDiario.getClass());

       }
       assertEquals(5, listaEventos.size() );

    }

    @Test
    public void testCrearEvento(){

        ConstructorEventos eventoDiarioConstruido      = new ConstructorEventosDiarios("Evento Diario", "Evento Repetido", LocalDateTime.of(2023, 4, 10, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS, 1);
        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Evento Repetido", LocalDateTime.of(2023, 4, 11, 9, 0), LocalDateTime.of(2023, 4, 21, 9, 30), 1, Repeticion.HASTA_FECHA_FIN, Set.of(DayOfWeek.MONDAY));
        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 12, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        ConstructorEventos eventoAnualConstruido       = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_FECHA_FIN, 3);
        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS );


        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        var calendario = new Calendario();

        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);
        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoDiaCompleto);

        ArrayList<Evento> listaEventos = calendario.obtenerListaEventosTotales();
        int i = 0;
        for(Evento evento : listaEventos){
            assertEquals(evento, calendario.obtenerListaEventosTotales().get(i));
            i++;
        }

        //Verifico que esten los duplicados como instancias unicas
        assertEquals(listaEventos.get(0).getClass(), eventoDiario.getClass());
        assertEquals(listaEventos.get(1).getClass(), eventoDiario.getClass());

        assertEquals(listaEventos.get(2).getClass(), eventoSemanal.getClass());
        assertEquals(listaEventos.get(3).getClass(), eventoSemanal.getClass());

        assertEquals(listaEventos.get(4).getClass(), eventoMensual.getClass());
        assertEquals(listaEventos.get(5).getClass(), eventoAnual.getClass());

        assertEquals(listaEventos.get(6).getClass(), eventoDiaCompleto.getClass());

        //Cada evento fue agregado correctamente, incluso duplicados
        assertEquals(7, calendario.obtenerListaEventosTotales().size());
    }

    @Test
    public void testEliminarEvento() {
        ConstructorEventos eventoDiarioConstruido      = new ConstructorEventosDiarios("Evento Diario", "Evento Repetido", LocalDateTime.of(2023, 4, 10, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS, 1);
        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Evento Repetido", LocalDateTime.of(2023, 4, 11, 9, 0), LocalDateTime.of(2023, 4, 21, 9, 30), 1, Repeticion.HASTA_FECHA_FIN, Set.of(DayOfWeek.MONDAY));
        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 12, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        ConstructorEventos eventoAnualConstruido       = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_FECHA_FIN, 3);
        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS );

        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        var calendario = new Calendario();

        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);
        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoDiaCompleto);

        ArrayList<Evento> listaEventos = calendario.obtenerListaEventosTotales();

        //Elimino la instancia de EventoDiario y todas sus repeticiones
        calendario.eliminarEvento(listaEventos.get(0));

        //Elimino la instancia de EventoSemanal y todas sus repeticiones
        calendario.eliminarEvento(listaEventos.get(2));

        for(Evento evento : listaEventos){
            assertNotEquals(evento.getClass() ,eventoDiario.getClass());
            assertNotEquals(evento.getClass() ,eventoSemanal.getClass());
        }

        assertEquals(3, listaEventos.size() );
    }

    //Test con tipo de repeticion HastaOcurrencias
    @Test
    public void testObtenerProximosEventosDiarios(){

        ConstructorEventos eventoDiarioConstruido = new ConstructorEventosDiarios("Evento Diario", "Test para proximas ocurrencias", LocalDateTime.of(2023, 4, 10, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_OCURRENCIAS, 2);
        Evento eventoDiario      =   eventoDiarioCreado.crearEvento(eventoDiarioConstruido);

        assertTrue(eventoDiario.obtenerAlarmasEvento().isEmpty());

        //Verifico que puedo acceder a alarmas
        var Notificacion = new Notificacion();
        var alarma       = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);

        eventoDiario.agregarAlarmaEvento(alarma);
        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiarioConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

        LocalDateTime fecha2Esperada = LocalDateTime.of(2023, 4, 10, 9, 0).plusDays(2);
        LocalDateTime fecha3Esperada = fecha2Esperada.plusDays(2);

        assertEquals(fecha1, LocalDateTime.of(2023, 4, 10, 9, 0));
        assertEquals(fecha2,fecha2Esperada );
        assertEquals(fecha3,fecha3Esperada );

        assertTrue(eventoDiario.obtenerAlarmasEvento().contains(alarma));
        assertEquals(3, todosLosEventos.size());

    }

    //Test con tipo de repeticion HastaFechaFin
    @Test
    public void testObtenerProximosEventosDiariosFechaFin(){

        ConstructorEventos eventoDiarioConstruido = new ConstructorEventosDiarios("Evento Diario", "Test para proximas ocurrencias", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_FECHA_FIN, 1);
        Evento eventoDiario      =   eventoDiarioCreado.crearEvento(eventoDiarioConstruido);

        assertTrue(eventoDiario.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiarioConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        //En este caso, el intervalo de dias es 1, entonces deberia ser un array de 7 elementos (Dia 10 al dia 7).
        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 8 elementos
        assertEquals(8, todosLosEventos.size());
    }

    //Test con tipo de repeticion Infinita
    @Test
    public void testObtenerProximosEventosDiariosInfinito(){

        ConstructorEventos eventoDiarioConstruido = new ConstructorEventosDiarios("Evento Diario", "Test para proximas ocurrencias", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 1);
        //Por temas de testeo, se coloca un numero alto pero manejable

        Evento eventoDiario      =   eventoDiarioCreado.crearEvento(eventoDiarioConstruido);

        assertTrue(eventoDiario.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiarioConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 100 elementos en vez de 99
        assertEquals(100, todosLosEventos.size());
    }

    @Test
    public void testEliminarTodosLosProximosEventosDiarios(){

        String titulo      = "Evento Diario";
        String descripcion = "Evento que se repite hasta ocurrencias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 10;
        int intervalo = 1;

        ConstructorEventos eventoDiarioConstruido = new ConstructorEventosDiarios(titulo,descripcion,fechaInicio,fechaFin,maxOcurrencias,tipoRepeticion,intervalo);
        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 12, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);

        Evento eventoDiario      =   eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     =   eventoSemanalCreado.crearEvento(eventoMensualConstruido);


        var Notificacion = new Notificacion();
        var alarma       = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);

        eventoDiario.agregarAlarmaEvento(alarma);

        var calendario = new Calendario();

        calendario.agregarEvento(eventoSemanal);

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiarioConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        calendario.eliminarEvento(todosLosEventos.get(1));

        //Me aseguro que lo unico que quede en la lista de eventos sea el unico eventoSemanal agregado
       assertEquals(1, todosLosEventos.size());
       assertEquals(todosLosEventos.get(0).getClass(), eventoSemanal.getClass());

    }

    //Test eventosSemanales Hasta FechaFin
    @Test
    public void testObtenerProximosEventosSemanal(){

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 30);

        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Test de ocurrencias", fechaInicio, LocalDateTime.of(2023, 4, 10, 9, 30), 1, Repeticion.HASTA_FECHA_FIN, Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        Evento eventoSemanal      =   eventoSemanalCreado.crearEvento(eventoSemanalConstruido);

        assertTrue(eventoSemanal.obtenerAlarmasEvento().isEmpty());

        //Verifico que puedo acceder a alarmas
        var Notificacion = new Notificacion();

        var alarma       = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);

        eventoSemanal.agregarAlarmaEvento(alarma);
        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoSemanalConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fechaLunes = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fechaMiercoles = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fechaLunes2 = todosLosEventos.get(2).obtenerFechaInicio();

        LocalDateTime fechaMiercolesEsperada = fechaInicio.plusDays(2); //Miercoles 5 de abril
        LocalDateTime fechaLunes2Esperada = fechaMiercolesEsperada.plusDays(5); //Lunes 10 de abril

        assertEquals(fechaLunes, fechaInicio);
        assertEquals(fechaMiercoles,fechaMiercolesEsperada );
        assertEquals(fechaLunes2,fechaLunes2Esperada );

        assertTrue(eventoSemanal.obtenerAlarmasEvento().contains(alarma));

        assertEquals(3, todosLosEventos.size());
    }

    //Test eventosSemanales Hasta MaxOcurrencias
    @Test
    public void testObtenerProximosEventosSemanalHastaOcurrencias(){

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 30);

        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Test de ocurrencias", fechaInicio, LocalDateTime.of(2023, 4, 10, 9, 30), 5, Repeticion.HASTA_OCURRENCIAS, Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Evento eventoSemanal      =   eventoSemanalCreado.crearEvento(eventoSemanalConstruido);

        assertTrue(eventoSemanal.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoSemanalConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fechaLunes    = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fechaViernes  = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fechaLunes2   = todosLosEventos.get(2).obtenerFechaInicio();
        LocalDateTime fechaViernes2 = todosLosEventos.get(3).obtenerFechaInicio();
        LocalDateTime fechaLunes3   = todosLosEventos.get(4).obtenerFechaInicio();

        LocalDateTime fechaViernesEsperada  = fechaInicio.plusDays(4); //Viernes 4 de abril
        LocalDateTime fechaLunes2Esperada   = fechaViernesEsperada.plusDays(3); //Lunes 10 de abril
        LocalDateTime fechaViernes2Esperada = fechaLunes2.plusDays(4); //Viernes 14 de abril
        LocalDateTime fechaLunes3Esperada   = fechaViernes2Esperada.plusDays(3); //Lunes 17 de abril


        assertEquals(fechaLunes, fechaInicio); //Lunes 3 de abril
        assertEquals(fechaViernes, fechaViernesEsperada);
        assertEquals(fechaLunes2, fechaLunes2Esperada);
        assertEquals(fechaViernes2, fechaViernes2Esperada);
        assertEquals(fechaLunes3, fechaLunes3Esperada);

        assertEquals(5, todosLosEventos.size());
    }

    @Test
    public void testObtenerProximosEventosSemanalInfinito(){

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 30);

        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Test de ocurrencias", fechaInicio, LocalDateTime.of(2023, 4, 10, 9, 30), 99, Repeticion.INFINITA, Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Evento eventoSemanal      =   eventoSemanalCreado.crearEvento(eventoSemanalConstruido);

        assertTrue(eventoSemanal.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoSemanalConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 100 elementos en vez de 99
        assertEquals(100, todosLosEventos.size());
    }

    //Test con tipo de repeticion HastaOcurrencias
    @Test
    public void testObtenerProximosEventosMensuales(){

        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_OCURRENCIAS, 2);
        Evento eventoMensual      =   eventoMensualCreado.crearEvento(eventoMensualConstruido);

        assertTrue(eventoMensual.obtenerAlarmasEvento().isEmpty());

        //Verifico que puedo acceder a alarmas
        var Notificacion = new Notificacion();
        var alarma       = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);

        eventoMensual.agregarAlarmaEvento(alarma);
        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoMensualConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

        LocalDateTime fecha2Esperada = LocalDateTime.of(2023, 4, 10, 9, 30).plusMonths(2);
        LocalDateTime fecha3Esperada = fecha2Esperada.plusMonths(2);

        assertEquals(fecha1, LocalDateTime.of(2023, 4, 10, 9, 30));
        assertEquals(fecha2,fecha2Esperada );
        assertEquals(fecha3,fecha3Esperada );

        assertTrue(eventoMensual.obtenerAlarmasEvento().contains(alarma));
        assertEquals(3, todosLosEventos.size());

    }

    //Test con tipo de repeticion HastaFechaFin
    @Test
    public void testObtenerProximosEventosMensualesFechaFin(){

        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_OCURRENCIAS, 2);
        Evento eventoMensual      =   eventoMensualCreado.crearEvento(eventoMensualConstruido);

        assertTrue(eventoMensual.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoMensualConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

        assertEquals(fecha1,  LocalDateTime.of(2023, 4, 10, 9, 30));
        assertEquals(fecha2, fecha1.plusMonths(2));
        assertEquals(fecha3, fecha2.plusMonths(2));

        //En este caso, el intervalo de meses es 1, entonces deberia ser un array de 2 elementos (Mes 4 -> Mes 6).
        //Por temas de claridad visual, se agrega tambien el primer mes, entonces el array pasa a tener 3 elementos
        assertEquals(3, todosLosEventos.size());
    }

    //Test con tipo de repeticion Infinita
    @Test
    public void testObtenerProximosEventosMensualesInfinito(){

        ConstructorEventos eventoMensualConstruido = new ConstructorEventosDiarios("Evento Mensual", "Test para proximas ocurrencias", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 6, 15, 9, 30), 99, Repeticion.INFINITA, 1);
        Evento eventoMensual     =   eventoMensualCreado.crearEvento(eventoMensualConstruido);

        assertTrue(eventoMensual.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();


        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoMensualConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 100 elementos en vez de 99
        assertEquals(100, todosLosEventos.size());
    }


//Test con tipo de repeticion HastaOcurrencias
@Test
public void testObtenerProximosEventosAnuales(){

    ConstructorEventos eventoAnualConstruido     = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_OCURRENCIAS, 2);
    Evento eventoAnual      =   eventoAnualCreado.crearEvento(eventoAnualConstruido);

    assertTrue(eventoAnual.obtenerAlarmasEvento().isEmpty());

    var calendario = new Calendario();

    ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoAnualConstruido);
    calendario.agregarEventosACalendario(proximosEventos);

    ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

    LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
    LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
    LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

    LocalDateTime fecha2Esperada = LocalDateTime.of(2023, 4, 10, 9, 30).plusYears(2);
    LocalDateTime fecha3Esperada = fecha2Esperada.plusYears(2);

    assertEquals(fecha1, LocalDateTime.of(2023, 4, 10, 9, 30));
    assertEquals(fecha2,fecha2Esperada );
    assertEquals(fecha3,fecha3Esperada );


    assertEquals(3, todosLosEventos.size());

}

    //Test con tipo de repeticion HastaFechaFin
    @Test
    public void testObtenerProximosEventosAnualesFechaFin(){

        ConstructorEventos eventoAnualConstruido     = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2027, 4, 10, 9, 30), 3, Repeticion.HASTA_FECHA_FIN, 1);
        Evento eventoAnual      =   eventoAnualCreado.crearEvento(eventoAnualConstruido);

        assertTrue(eventoAnual.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoAnualConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

        assertEquals(fecha1,  LocalDateTime.of(2023, 4, 10, 9, 30));
        assertEquals(fecha2, fecha1.plusYears(1));
        assertEquals(fecha3, fecha2.plusYears(1));

        //En este caso, el intervalo de Anual es 1, entonces deberia ser un array de 4 elementos (Año 2023 -> Año 2027).
        //Por temas de claridad visual, se agrega tambien el primer mes, entonces el array pasa a tener 5 elementos
        assertEquals(5, todosLosEventos.size());
    }

    //Test con tipo de repeticion Infinita
    @Test
    public void testObtenerProximosEventosAnualesInfinito(){

        ConstructorEventos eventoAnualConstruido     = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2027, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        Evento eventoAnual      =   eventoAnualCreado.crearEvento(eventoAnualConstruido);

        assertTrue(eventoAnual.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();


        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoAnualConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 100 elementos en vez de 99
        assertEquals(100, todosLosEventos.size());
    }

///////////////////////////////////////////////////////
//Test con tipo de repeticion HastaOcurrencias
@Test
public void testObtenerProximosEventosDiaCompleto(){

    ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 3, Repeticion.HASTA_OCURRENCIAS );
    Evento eventoDiaCompleto      =   eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

    assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

    //Verifico que puedo acceder a alarmas
    var Notificacion = new Notificacion();
    var alarma       = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);

    eventoDiaCompleto.agregarAlarmaEvento(alarma);
    var calendario = new Calendario();

    ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
    calendario.agregarEventosACalendario(proximosEventos);

    ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

    LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
    LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
    LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

    LocalDateTime fecha2Esperada = LocalDateTime.of(2023, 4, 13, 9, 0).plusDays(1);
    LocalDateTime fecha3Esperada = fecha2Esperada.plusDays(1);

    assertEquals(fecha1, LocalDateTime.of(2023, 4, 13, 9, 0));
    assertEquals(fecha2,fecha2Esperada );
    assertEquals(fecha3,fecha3Esperada );

    assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().contains(alarma));
    assertEquals(3, todosLosEventos.size());

}

    //Test con tipo de repeticion HastaFechaFin
    @Test
    public void testObtenerProximosEventosDiaCompletoFechaFin(){

        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 16, 9, 30), 3, Repeticion.HASTA_FECHA_FIN );
        Evento eventoDiaCompleto      =   eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        LocalDateTime fecha1 = todosLosEventos.get(0).obtenerFechaInicio();
        LocalDateTime fecha2 = todosLosEventos.get(1).obtenerFechaInicio();
        LocalDateTime fecha3 = todosLosEventos.get(2).obtenerFechaInicio();

        assertEquals(fecha1,  LocalDateTime.of(2023, 4, 13, 9, 0));
        assertEquals(fecha2, fecha1.plusDays(1));
        assertEquals(fecha3, fecha2.plusDays(1));

        //En este caso, el intervalo de meses es 1, entonces deberia ser un array de 2 elementos (Mes 4 -> Mes 6).
        //Por temas de claridad visual, se agrega tambien el primer mes, entonces el array pasa a tener 3 elementos
        assertEquals(5, todosLosEventos.size());
    }

    //Test con tipo de repeticion Infinita
    @Test
    public void testObtenerProximosEventosDiaCompletoInfinito(){

        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 16, 9, 30), 99, Repeticion.INFINITA );
        Evento eventoDiaCompleto      =   eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        //Por temas de claridad visual, se agrega tambien el primer dia, entonces el array pasa a tener 100 elementos en vez de 99
        assertEquals(100, todosLosEventos.size());
    }

    @Test
    public void testModificoEventosYSeModificanTodasSusRepeticiones() {

        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 16, 9, 30), 5, Repeticion.INFINITA);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);
        ConstructorEventos eventoAnualConstruido = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2027, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        Evento eventoAnual = eventoAnualCreado.crearEvento(eventoAnualConstruido);


        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        calendario.modificarEvento(eventoDiaCompleto, "Titulo modificado", "Descripcion nueva", null, null, null, null);

        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        assertEquals(6,todosLosEventos.size());
        for (Evento evento : todosLosEventos){
            assertEquals("Titulo modificado",evento.obtenerTitulo());
        }
    }
    @Test
    public void testModificaLasRepeticionesDeElEventoCorrespondiente() {

        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 16, 9, 30), 5, Repeticion.INFINITA);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);
        ConstructorEventos eventoAnualConstruido = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2027, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        Evento eventoAnual = eventoAnualCreado.crearEvento(eventoAnualConstruido);


        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos0 = calendario.proximosEventos(eventoAnualConstruido);
        calendario.agregarEventosACalendario(proximosEventos0);
        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        calendario.modificarEvento(eventoDiaCompleto, "Titulo modificado", "Descripcion nueva", null, null, null, null);


        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();

        assertEquals(106, todosLosEventos.size());
        int contadorEventosModificados = 0;
        //Pruebo solo las modificaciones correspondientes
        for (Evento evento : todosLosEventos) {
            if (evento.obtenerTitulo().equals("Titulo modificado")) {
                contadorEventosModificados++;
            }
        }
        assertEquals(6,contadorEventosModificados);
    }

    @Test
    public void testSiHayEventosDeLaMismaClaseYNoSonRepeticionesNoLosModifica() {

        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 16, 9, 30), 5, Repeticion.INFINITA);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);
        ConstructorEventos eventoDiaCompletoDosConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo 2", "Evento Unico", LocalDateTime.of(2023, 4, 10, 9, 30), LocalDateTime.of(2027, 4, 17, 9, 30), 99, Repeticion.INFINITA);
        Evento eventoDiaCompletoDos = eventoAnualCreado.crearEvento(eventoDiaCompletoDosConstruido);


        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        var calendario = new Calendario();

        ArrayList<Evento> proximosEventos0 = calendario.proximosEventos(eventoDiaCompletoDosConstruido);
        calendario.agregarEventosACalendario(proximosEventos0);
        ArrayList<Evento> proximosEventos = calendario.proximosEventos(eventoDiaCompletoConstruido);
        calendario.agregarEventosACalendario(proximosEventos);

        calendario.modificarEvento(eventoDiaCompleto, "Titulo modificado", "Descripcion nueva", null, null, null, null);


        ArrayList<Evento> todosLosEventos = calendario.obtenerListaEventosTotales();


        assertEquals(106, todosLosEventos.size());
        //Pruebo solo las modificaciones correspondientes
        int contador = 0;
        for (Evento evento : todosLosEventos) {
            if (evento.obtenerTitulo().equals("Titulo modificado")){
                contador++;
            }
        }
        assertEquals(6,contador);

    }

    //Tests para verificar que modificarEvento modifique todas las repeticiones del evento

    @Test
    public void testObtenerEventosSegunFecha(){

        ConstructorEventos eventoDiarioConstruido      = new ConstructorEventosDiarios("Evento Diario", "Evento Repetido", LocalDateTime.of(2023, 4, 10, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS, 1);
        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Evento Repetido", LocalDateTime.of(2023, 4, 11, 9, 0), LocalDateTime.of(2023, 4, 21, 9, 30), 1, Repeticion.HASTA_FECHA_FIN, Set.of(DayOfWeek.MONDAY));
        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 12, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        ConstructorEventos eventoAnualConstruido       = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_FECHA_FIN, 3);
        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 20, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS );

        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        var calendario = new Calendario();

        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoDiario);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoSemanal);
        calendario.agregarEvento(eventoMensual);
        calendario.agregarEvento(eventoAnual);
        calendario.agregarEvento(eventoDiaCompleto);

        ArrayList<Evento> eventosEnRango = calendario.obtenerEventosEntreFechas(LocalDate.of(2023,4,8),LocalDate.of(2023,4,12));
        assertEquals(7,eventosEnRango.size());

    }

    @Test
    public void testSerializarYDeserializarCalendario()   {
        String nombreArchivo = "calendarioCorreccion.json";

        Calendario calendarioOriginal = new Calendario();
        Tarea tarea1 = new TareaDiaCompleto(LocalDate.of(2023, 5, 15));
        Tarea tarea2 = new TareaConVencimiento(LocalDateTime.of(2023,5,18,20,30));
        ConstructorEventos eventoDiarioConstruido      = new ConstructorEventosDiarios("Evento Diario", "Evento Repetido", LocalDateTime.of(2023, 4, 10, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS, 1);
        Evento eventoDiario      = eventoDiarioCreado.crearEvento(eventoDiarioConstruido);
        calendarioOriginal.agregarEvento(eventoDiario);
        calendarioOriginal.agregarTarea(tarea1);
        calendarioOriginal.agregarTarea(tarea2);


        String informacionSerializada = calendarioOriginal.serializarCalendario(calendarioOriginal);

        //Creo el archivo de forma independiente a la serializacion
        calendarioOriginal.escribirEnDocumento(informacionSerializada, nombreArchivo);

        Calendario calendarioSerializado = Calendario.deserializarCalendario(informacionSerializada);

        assert calendarioSerializado != null;
        assertEquals(1,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(eventoDiario.obtenerTitulo(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerTitulo());
        assertEquals(eventoDiario.obtenerDescripcion(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerDescripcion());
        assertEquals(eventoDiario.obtenerFechaInicio(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerFechaInicio());
        assertEquals(eventoDiario.obtenerFechaFin(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerFechaFin());
        assertEquals(eventoDiario.obtenerId(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerId());



        assertEquals(2,calendarioSerializado.obtenerTareas().size());
        assertEquals(tarea1.obtenerTitulo(),calendarioSerializado.obtenerTareas().get(0).obtenerTitulo());
        assertEquals(tarea1.obtenerDescripcion(),calendarioSerializado.obtenerTareas().get(0).obtenerDescripcion());
        assertEquals(tarea1.obtenerFechaInicio(),calendarioSerializado.obtenerTareas().get(0).obtenerFechaInicio());
        assertEquals(tarea1.obtenerFechaVencimiento(),calendarioSerializado.obtenerTareas().get(0).obtenerFechaVencimiento());
        assertEquals(tarea1.obtenerId(),calendarioSerializado.obtenerTareas().get(0).obtenerId());
        assertEquals(tarea1.obtenerAlarmas(),calendarioSerializado.obtenerTareas().get(0).obtenerAlarmas());



        assertEquals(tarea2.obtenerTitulo(),calendarioSerializado.obtenerTareas().get(1).obtenerTitulo());
        assertEquals(tarea2.obtenerDescripcion(),calendarioSerializado.obtenerTareas().get(1).obtenerDescripcion());
        assertEquals(tarea2.obtenerFechaInicio(),calendarioSerializado.obtenerTareas().get(1).obtenerFechaInicio());
        assertEquals(tarea2.obtenerFechaVencimiento(),calendarioSerializado.obtenerTareas().get(1).obtenerFechaVencimiento());
        assertEquals(tarea2.obtenerId(),calendarioSerializado.obtenerTareas().get(1).obtenerId());
        assertEquals(tarea2.obtenerAlarmas(),calendarioSerializado.obtenerTareas().get(1).obtenerAlarmas());
    }
    @Test
    public void testPuedoSerializarTodoTipoDeEvento(){

        ConstructorEventos eventoSemanalConstruido     = new ConstructorEventosSemanales("Evento Semanal", "Evento Repetido", LocalDateTime.of(2023, 4, 11, 9, 0), LocalDateTime.of(2023, 4, 21, 9, 30), 1, Repeticion.HASTA_FECHA_FIN, Set.of(DayOfWeek.MONDAY));
        ConstructorEventos eventoMensualConstruido     = new ConstructorEventosMensuales("Evento Mensual", "Evento Unico", LocalDateTime.of(2023, 4, 12, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 99, Repeticion.INFINITA, 2);
        ConstructorEventos eventoAnualConstruido       = new ConstructorEventosAnuales("Evento Anual", "Evento Unico", LocalDateTime.of(2023, 4, 13, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_FECHA_FIN, 3);
        ConstructorEventos eventoDiaCompletoConstruido = new ConstructorEventosDiaCompleto("Evento Dia Completo", "Evento Unico", LocalDateTime.of(2023, 4, 20, 9, 0), LocalDateTime.of(2023, 4, 17, 9, 30), 10, Repeticion.HASTA_OCURRENCIAS );

        Evento eventoSemanal     = eventoSemanalCreado.crearEvento(eventoSemanalConstruido);
        Evento eventoMensual     = eventoMensualCreado.crearEvento(eventoMensualConstruido);
        Evento eventoAnual       = eventoAnualCreado.crearEvento(eventoAnualConstruido);
        Evento eventoDiaCompleto = eventoDiaCompletoCreado.crearEvento(eventoDiaCompletoConstruido);

        Calendario calendarioOriginal = new Calendario();

        calendarioOriginal.agregarEvento(eventoSemanal);
        calendarioOriginal.agregarEvento(eventoMensual);
        calendarioOriginal.agregarEvento(eventoAnual);
        calendarioOriginal.agregarEvento(eventoDiaCompleto);

        //En este caso, no creo el archivo y pruebo que la serializacion y la deserializacion son independientes del archivo
        String informacionSerializada = calendarioOriginal.serializarCalendario(calendarioOriginal);
        Calendario calendarioSerializado = Calendario.deserializarCalendario(informacionSerializada);

        assert calendarioSerializado != null;
        assertEquals(4,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(eventoSemanal.obtenerTitulo(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerTitulo());
        assertEquals(eventoSemanal.obtenerDescripcion(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerDescripcion());
        assertEquals(eventoSemanal.obtenerFechaInicio(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerFechaInicio());
        assertEquals(eventoSemanal.obtenerFechaFin(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerFechaFin());
        assertEquals(eventoSemanal.obtenerId(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerId());
        assertEquals(eventoSemanal.obtenerAlarmasEvento(),calendarioSerializado.obtenerListaEventosTotales().get(0).obtenerAlarmasEvento());


        assertEquals(4,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(eventoMensual.obtenerTitulo(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerTitulo());
        assertEquals(eventoMensual.obtenerDescripcion(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerDescripcion());
        assertEquals(eventoMensual.obtenerFechaInicio(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerFechaInicio());
        assertEquals(eventoMensual.obtenerFechaFin(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerFechaFin());
        assertEquals(eventoMensual.obtenerId(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerId());
        assertEquals(eventoMensual.obtenerAlarmasEvento(),calendarioSerializado.obtenerListaEventosTotales().get(1).obtenerAlarmasEvento());


        assertEquals(4,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(eventoAnual.obtenerTitulo(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerTitulo());
        assertEquals(eventoAnual.obtenerDescripcion(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerDescripcion());
        assertEquals(eventoAnual.obtenerFechaInicio(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerFechaInicio());
        assertEquals(eventoAnual.obtenerFechaFin(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerFechaFin());
        assertEquals(eventoAnual.obtenerId(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerId());
        assertEquals(eventoAnual.obtenerAlarmasEvento(),calendarioSerializado.obtenerListaEventosTotales().get(2).obtenerAlarmasEvento());



        assertEquals(4,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(eventoDiaCompleto.obtenerTitulo(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerTitulo());
        assertEquals(eventoDiaCompleto.obtenerDescripcion(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerDescripcion());
        assertEquals(eventoDiaCompleto.obtenerFechaInicio(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerFechaInicio());
        assertEquals(eventoDiaCompleto.obtenerFechaFin(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerFechaFin());
        assertEquals(eventoDiaCompleto.obtenerId(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerId());
        assertEquals(eventoDiaCompleto.obtenerAlarmasEvento(),calendarioSerializado.obtenerListaEventosTotales().get(3).obtenerAlarmasEvento());
    }


    @Test
    public void testSerializaYDeserializaTareasYEventosConAlarma(){

        Calendario calendarioOriginal = new Calendario();
        Efecto notificacion = new Notificacion();
        Alarma alarma = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 12, 22, 0, 0), notificacion);
        Alarma alarma1 = new AlarmaIntervalo(LocalDateTime.of(2023,5,22,15,30),30,notificacion);
        Tarea tarea = new TareaDiaCompleto(LocalDate.of(2023, 5, 15));
        tarea.agregarAlarma(alarma);
        tarea.agregarAlarma(alarma1);
        calendarioOriginal.agregarTarea(tarea);

        String informacionSerializada = calendarioOriginal.serializarCalendario(calendarioOriginal);
        Calendario calendarioSerializado = Calendario.deserializarCalendario(informacionSerializada);

        assert calendarioSerializado != null;
        assertEquals(0,calendarioSerializado.obtenerListaEventosTotales().size());
        assertEquals(1,calendarioSerializado.obtenerTareas().size());
        assertEquals(2,calendarioSerializado.obtenerTareas().get(0).obtenerAlarmas().size());
        assertEquals(LocalDateTime.of(2023, 12, 22, 0, 0),calendarioSerializado.obtenerTareas().get(0).obtenerAlarmas().get(0).obtenerFechaYHora());
        assertEquals(LocalDateTime.of(2023, 5, 22, 15, 0),calendarioSerializado.obtenerTareas().get(0).obtenerAlarmas().get(1).obtenerFechaYHora());


    }


    }

