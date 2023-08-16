import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class EventoAnualTest {
    @Rule
    public ExpectedException error = ExpectedException.none();

    //Testeo constructor default de la clase Evento para eventoMensual
    @Test
    public void testEventoMensualDefault() {

        var eventoAnual = new EventoAnual(LocalDateTime.of(2023,5,10,23,30));

        String titulo =  "Evento sin titulo";
        String descripcion = "-";
        LocalDateTime fechaInicio = LocalDateTime.of(2023,5,10,23,30);
        LocalDateTime fechaFin = fechaInicio.plusDays(1);
        Repeticion tipoRepeticion = Repeticion.HASTA_FECHA_FIN;
        int ocurrenciasRealizadas = 0;
        int maxOcurrencias = 1;

        assertEquals(titulo, eventoAnual.obtenerTitulo());
        assertEquals(descripcion, eventoAnual.obtenerDescripcion());
        assertEquals(fechaInicio, eventoAnual.obtenerFechaInicio());
        assertEquals(fechaFin, eventoAnual.obtenerFechaFin());
        assertEquals(ocurrenciasRealizadas, eventoAnual.obtenerOcurrencias());
        assertEquals(maxOcurrencias, eventoAnual.obtenerMaxOcurrencias());
        assertEquals(tipoRepeticion, eventoAnual.obtenerTipoRepeticion());

        //Para los tests de constructor Default de la clase abstracta Evento se utilizó el metodo .toLocalDate para que el test no falle por milésima de segundos.

    }

    @Test
    public void testObtenerSiguienteOcurrenciaValoresDefault() {

        var eventoAnual = new EventoAnual(LocalDateTime.of(2023,5,15,23,30));

        LocalDateTime FechaEsperada = eventoAnual.obtenerFechaInicio().plusYears(1);
        LocalDateTime FechaActual = eventoAnual.calcularSiguienteOcurrencia(eventoAnual.obtenerFechaInicio());

        assertEquals(FechaEsperada, FechaActual);

    }

    @Test
    public void testEventoAnual() {

        String titulo      = "Evento Anual";
        String descripcion =   "Evento que se repite al siguiente mes";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin    =    LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadAnios = 1;

        var eventoAnual = new EventoAnual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadAnios);

        LocalDateTime FechaEsperada = fechaInicio.plusYears(cantidadAnios);
        LocalDateTime FechaActual = eventoAnual.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaEsperada, FechaActual);
    }


    @Test
    public void testEventoCada3Anios() {

        String titulo      = "Evento Anual";
        String descripcion =   "Evento que se repite cada 3 años";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadAnios  = 3;

        var eventoAnual = new EventoAnual(LocalDateTime.of(2023,12,1,23,0));

        eventoAnual.establecerTitulo(titulo);
        eventoAnual.establecerDescripcion(descripcion);
        eventoAnual.establecerFechaInicio(fechaInicio);
        eventoAnual.establecerFechaFin(fechaFin);
        eventoAnual.establecerMaxOcurrencias(maxOcurrencias);
        eventoAnual.establecerTipoRepeticion(tipoRepeticion);
        eventoAnual.establecerCantidadAnios(cantidadAnios);

        //Creo Varias alarmas
        var Notificacion = new Notificacion();
        var mail = new Email();

        var alarmaNotif = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 15, 10, 0),Notificacion);
        var alarmaMail = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 12, 10, 0),mail);

        eventoAnual.agregarAlarmaEvento(alarmaNotif);
        eventoAnual.agregarAlarmaEvento(alarmaMail);

        ArrayList<Alarma> listaAlarmas = eventoAnual.obtenerAlarmasEvento();

        LocalDateTime fechaEsperada = fechaInicio.plusYears(3);
        LocalDateTime FechaActual = eventoAnual.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(fechaEsperada, FechaActual);
        assertEquals(cantidadAnios, eventoAnual.obtenerCantidadAnios());

        assertTrue(listaAlarmas.contains(alarmaMail));
        assertTrue(listaAlarmas.contains(alarmaNotif));
    }

    @Test
    public void testcantidadAniosCero() {

        String titulo      = "Evento Anual";
        String descripcion = "Evento con cantidad de meses nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadAnios = 0;

        var eventoAnual = new EventoAnual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadAnios);

        LocalDateTime FechaActual = eventoAnual.calcularSiguienteOcurrencia(fechaInicio);
        assertEquals(fechaInicio, FechaActual);
    }

    @Test
    public void testCantAniosNegativa() {

        String titulo      = "Evento Anual";
        String descripcion = "Evento que se repite cada 3 dias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadAnios = -1;

        var eventoAnual = new EventoAnual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadAnios);

        error.expect(RuntimeException.class);
        eventoAnual.calcularSiguienteOcurrencia(fechaInicio);
    }
    @Test
    public void testBorrarAlarmas() {

        String        titulo      = "Evento Anual";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 4;
        int intervalo = 0;

        var eventoAnual = new EventoAnual(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,intervalo);

        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();

        var alarmaNotificacion = new AlarmaFechaAbsoluta(eventoAnual.obtenerFechaInicio(), notificacion);
        var alarmaSonido = new AlarmaFechaAbsoluta(eventoAnual.obtenerFechaInicio(), sonido);
        var alarmaMail = new AlarmaFechaAbsoluta(eventoAnual.obtenerFechaInicio(), mail);

        eventoAnual.agregarAlarmaEvento(alarmaNotificacion);
        eventoAnual.agregarAlarmaEvento(alarmaSonido);
        eventoAnual.agregarAlarmaEvento(alarmaMail);

        eventoAnual.desactivarAlarmaEvento(alarmaNotificacion);
        eventoAnual.desactivarAlarmaEvento(alarmaSonido);

        ArrayList<Alarma> listaAlarmas = eventoAnual.obtenerAlarmasEvento();

        assertFalse(listaAlarmas.contains(alarmaNotificacion));
        assertFalse(listaAlarmas.contains(alarmaSonido));
        assertTrue(listaAlarmas.contains(alarmaMail));

        assertEquals(1, listaAlarmas.size());
    }

}