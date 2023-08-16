import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class EventoMensualTest {
    @Rule
    public ExpectedException error = ExpectedException.none();

    //Testeo constructor default de la clase Evento para eventoMensual
    @Test
    public void testEventoMensualDefault() {

        var eventoMensual = new EventoMensual(LocalDateTime.of(2023,5,10,20,30));

        String titulo =  "Evento sin titulo";
        String descripcion = "-";

        LocalDateTime fechaInicio = LocalDateTime.of(2023,5,10,20,30);
        LocalDateTime fechaFin = fechaInicio.plusDays(1);

        int ocurrenciasRealizadas = 0;
        int maxOcurrencias = 1;
        Repeticion tipoRepeticion = Repeticion.HASTA_FECHA_FIN;

        assertEquals(titulo, eventoMensual.obtenerTitulo());
        assertEquals(descripcion, eventoMensual.obtenerDescripcion());
        assertEquals(fechaInicio, eventoMensual.obtenerFechaInicio());
        assertEquals(fechaFin, eventoMensual.obtenerFechaFin());
        assertEquals(ocurrenciasRealizadas, eventoMensual.obtenerOcurrencias());
        assertEquals(maxOcurrencias, eventoMensual.obtenerMaxOcurrencias());
        assertEquals(tipoRepeticion, eventoMensual.obtenerTipoRepeticion());

        //Para los tests de constructor Default de la clase abstracta Evento se utilizó el metodo .toLocalDate para que el test no falle por milésima de segundos.

    }

    @Test
    public void testObtenerSiguienteOcurrenciaValoresDefault() {

        var eventoMensual = new EventoMensual(LocalDateTime.of(2023,5,10,20,30));
        LocalDateTime FechaEsperada = eventoMensual.obtenerFechaInicio().plusMonths(1);
        LocalDateTime FechaActual = eventoMensual.calcularSiguienteOcurrencia(eventoMensual.obtenerFechaInicio());

        assertEquals(FechaEsperada, FechaActual);

    }

    @Test
    public void testEventoMensual() {

        String titulo = "Evento Mensual";
        String descripcion =   "Evento que se repite al siguiente mes";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin =    LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadMeses = 1;

        var eventoMensual = new EventoMensual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadMeses);

        LocalDateTime FechaEsperada = fechaInicio.plusMonths(cantidadMeses);
        LocalDateTime FechaActual = eventoMensual.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaEsperada, FechaActual);
    }


    @Test
    public void testEventoCada3Meses() {

        String titulo = "Evento Mensual";
        String descripcion =   "Evento que se repite cada 3 meses";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin =    LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadMeses = 3;

        var eventoMensual = new EventoMensual(LocalDateTime.of(2023,5,10,20,30));
        eventoMensual.establecerTitulo(titulo);
        eventoMensual.establecerDescripcion(descripcion);
        eventoMensual.establecerFechaInicio(fechaInicio);
        eventoMensual.establecerFechaFin(fechaFin);
        eventoMensual.establecerMaxOcurrencias(maxOcurrencias);
        eventoMensual.establecerTipoRepeticion(tipoRepeticion);
        eventoMensual.establecerCantidadMeses(cantidadMeses);

        //Creo Varias alarmas
        var Notificacion = new Notificacion();
        var mail = new Email();

        var alarmaNotif = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 15, 10, 0),Notificacion);
        var alarmaMail = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 12, 10, 0),mail);

        eventoMensual.agregarAlarmaEvento(alarmaNotif);
        eventoMensual.agregarAlarmaEvento(alarmaMail);

        ArrayList<Alarma> listaAlarmas = eventoMensual.obtenerAlarmasEvento();

        LocalDateTime fechaEsperada = fechaInicio.plusMonths(3);
        LocalDateTime FechaActual = eventoMensual.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(fechaEsperada, FechaActual);
        assertEquals(cantidadMeses, eventoMensual.obtenerCantidadMeses());
        assertTrue(listaAlarmas.contains(alarmaMail));
        assertTrue(listaAlarmas.contains(alarmaNotif));

    }

    @Test
    public void testCantidadMesesCero() {

        String titulo = "Evento Mensual";
        String descripcion = "Evento con cantidad de meses nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int cantidadMeses = 0;

        var eventoMensual = new EventoMensual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadMeses);

        LocalDateTime FechaActual = eventoMensual.calcularSiguienteOcurrencia(fechaInicio);
        assertEquals(fechaInicio, FechaActual);
    }


    @Test
    public void testCantMesesNegativa() {

        String titulo = "Evento Mensual";
        String descripcion = "Evento que se repite cada 3 dias";

        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;

        int maxOcurrencias = 1;
        int cantidadMeses = -1;

        var eventoMensual = new EventoMensual(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion, cantidadMeses);

        error.expect(RuntimeException.class);
        eventoMensual.calcularSiguienteOcurrencia(fechaInicio);
    }
    @Test
    public void testBorrarAlarmas() {

        String        titulo      = "Evento Mensual";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 4;
        int cantidadMeses = 0;

        var eventoMensual = new EventoMensual(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,cantidadMeses);

        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();

        var alarmaNotificacion = new AlarmaFechaAbsoluta(eventoMensual.obtenerFechaInicio(), notificacion);
        var alarmaSonido = new AlarmaFechaAbsoluta(eventoMensual.obtenerFechaInicio(), sonido);
        var alarmaMail = new AlarmaFechaAbsoluta(eventoMensual.obtenerFechaInicio(), mail);

        eventoMensual.agregarAlarmaEvento(alarmaNotificacion);
        eventoMensual.agregarAlarmaEvento(alarmaSonido);
        eventoMensual.agregarAlarmaEvento(alarmaMail);

        eventoMensual.desactivarAlarmaEvento(alarmaNotificacion);
        eventoMensual.desactivarAlarmaEvento(alarmaSonido);

        ArrayList<Alarma> listaAlarmas = eventoMensual.obtenerAlarmasEvento();

        assertFalse(listaAlarmas.contains(alarmaNotificacion));
        assertFalse(listaAlarmas.contains(alarmaSonido));
        assertTrue(listaAlarmas.contains(alarmaMail));

        assertEquals(1, listaAlarmas.size());
    }

}