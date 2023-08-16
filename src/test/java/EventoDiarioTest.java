import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventoDiarioTest {

    //Chequeo caso intervalo negativo
    @Rule
    public ExpectedException error = ExpectedException.none();

    //Testeo constructor default de la clase Evento para eventoDiario
    @Test
    public void testEventoDiarioDefault() {

        var eventoDiario = new EventoDiario(LocalDateTime.of(2023,5,10,20,30));

        String titulo =  "Evento sin titulo";
        String descripcion = "-";
        LocalDateTime fechaInicio = LocalDateTime.of(2023,5,10,20,30);
        LocalDateTime fechaFin = fechaInicio.plusDays(1);
        int ocurrenciasRealizadas = 0;
        int maxOcurrencias = 1;
        Repeticion tipoRepeticion = Repeticion.HASTA_FECHA_FIN;

        int intervalo = 1;

        assertEquals(titulo, eventoDiario.obtenerTitulo());
        assertEquals(descripcion, eventoDiario.obtenerDescripcion());
        assertEquals(fechaInicio, eventoDiario.obtenerFechaInicio());
        assertEquals(fechaFin, eventoDiario.obtenerFechaFin());
        assertEquals(ocurrenciasRealizadas, eventoDiario.obtenerOcurrencias());
        assertEquals(maxOcurrencias, eventoDiario.obtenerMaxOcurrencias());
        assertEquals(tipoRepeticion, eventoDiario.obtenerTipoRepeticion());
        assertEquals(intervalo, eventoDiario.obtenerIntervalo());
        assertTrue(eventoDiario.obtenerAlarmasEvento().isEmpty());

        //Para los tests de constructor Default de la clase abstracta Evento se utilizó el metodo .toLocalDate para que el test no falle por milésima de segundos.
    }

    @Test
    public void testObtenerSiguienteOcurrenciaValoresDefault() {

        var eventoDiario = new EventoDiario(LocalDateTime.of(2023,5,10,20,30));

        //Por default, el intervalo = 1
        LocalDateTime FechaEsperada = eventoDiario.obtenerFechaInicio().plusDays(eventoDiario.obtenerIntervalo());
        LocalDateTime FechaActual = eventoDiario.calcularSiguienteOcurrencia(eventoDiario.obtenerFechaInicio());

        assertEquals(FechaEsperada, FechaActual);

    }
    @Test
    public void testObtenerSiguienteOcurrencia() {

        String      titulo = "Evento Diario";
        String descripcion =  "Evento que se repite todos los dias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    =    LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int intervalo = 1;

        var eventoDiario = new EventoDiario(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion ,intervalo);

        LocalDateTime FechaEsperada = fechaInicio.plusDays(intervalo);
        LocalDateTime FechaActual   = eventoDiario.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaEsperada, FechaActual);
    }

    @Test
    public void testEventoCadaTresDias() {

        String titulo      = "Evento Cada 3 Dias";
        String descripcion = "Evento que se repite cada 3 dias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int intervalo = 3;

        var eventoDiario = new EventoDiario(LocalDateTime.of(2023,5,10,20,30));

        eventoDiario.establecerTitulo(titulo);
        eventoDiario.establecerDescripcion(descripcion);
        eventoDiario.establecerFechaInicio(fechaInicio);
        eventoDiario.establecerFechaFin(fechaFin);
        eventoDiario.establecerMaxOcurrencias(maxOcurrencias);
        eventoDiario.establecerTipoRepeticion(tipoRepeticion);
        eventoDiario.establecerIntervalo(intervalo);

        //Creo Varias alarmas
        var Notificacion = new Notificacion();
        var mail = new Email();

        var alarmaNotif = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 15, 10, 0),Notificacion);
        var alarmaMail = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 12, 10, 0),mail);

        eventoDiario.agregarAlarmaEvento(alarmaNotif);
        eventoDiario.agregarAlarmaEvento(alarmaMail);

        LocalDateTime fechaEsperada = fechaInicio.plusDays(3);
        LocalDateTime FechaActual = eventoDiario.calcularSiguienteOcurrencia(fechaInicio);

        ArrayList<Alarma> listaAlarmas = eventoDiario.obtenerAlarmasEvento();

        assertEquals(fechaEsperada, FechaActual);
        assertEquals(intervalo, eventoDiario.obtenerIntervalo());

        assertTrue(listaAlarmas.contains(alarmaMail));
        assertTrue(listaAlarmas.contains(alarmaNotif));
    }

    @Test
    public void testIntervaloCero() {

        String      titulo = "Evento Diario";
        String descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime    fechaFin = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int intervalo = 0;

        var eventoDiario = new EventoDiario(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,intervalo);


        LocalDateTime FechaActual = eventoDiario.calcularSiguienteOcurrencia(fechaInicio);
        assertEquals(fechaInicio, FechaActual);
    }

    @Test
    public void testIntervaloNegativo() {

        String      titulo = "Evento Cada 3 Dias";
        String descripcion = "Evento que se repite cada 3 dias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;
        int intervalo      = -1;

        var eventoDiario = new EventoDiario(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,intervalo);

        error.expect(RuntimeException.class);
        eventoDiario.calcularSiguienteOcurrencia(fechaInicio);
    }
    @Test
    public void testBorrarAlarmas() {

        String        titulo      = "Evento Diario";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 4;
        int intervalo = 0;

        var eventoDiario = new EventoDiario(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,intervalo);

        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();

        var alarmaNotificacion = new AlarmaFechaAbsoluta(eventoDiario.obtenerFechaInicio(), notificacion);
        var alarmaSonido = new AlarmaFechaAbsoluta(eventoDiario.obtenerFechaInicio(), sonido);
        var alarmaMail = new AlarmaFechaAbsoluta(eventoDiario.obtenerFechaInicio(), mail);

        eventoDiario.agregarAlarmaEvento(alarmaNotificacion);
        eventoDiario.agregarAlarmaEvento(alarmaSonido);
        eventoDiario.agregarAlarmaEvento(alarmaMail);

        eventoDiario.desactivarAlarmaEvento(alarmaNotificacion);
        eventoDiario.desactivarAlarmaEvento(alarmaSonido);

        ArrayList<Alarma> listaAlarmas = eventoDiario.obtenerAlarmasEvento();

        assertFalse(listaAlarmas.contains(alarmaNotificacion));
        assertFalse(listaAlarmas.contains(alarmaSonido));
        assertTrue(listaAlarmas.contains(alarmaMail));

        assertEquals(1, listaAlarmas.size());
    }
}












