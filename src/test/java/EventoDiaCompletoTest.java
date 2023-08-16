import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class EventoDiaCompletoTest{
    @Test
    public void testEventoDiaCompletoDefault() {

        var eventoDiaCompleto = new EventoDiaCompleto(LocalDate.of(2023,5,10));

        String titulo =  "Evento sin titulo";
        String descripcion = "-";
        LocalDateTime fechaInicio = LocalDateTime.of(2023,5,10,0,0,0);
        LocalDateTime fechaFin = fechaInicio.plusDays(1);
        int ocurrenciasRealizadas = 0;
        int maxOcurrencias = 1;
        Repeticion tipoRepeticion = Repeticion.HASTA_FECHA_FIN;

        assertEquals(titulo, eventoDiaCompleto.obtenerTitulo());
        assertEquals(descripcion, eventoDiaCompleto.obtenerDescripcion());
        assertEquals(fechaInicio, eventoDiaCompleto.obtenerFechaInicio());
        assertEquals(fechaFin, eventoDiaCompleto.obtenerFechaFin());
        assertEquals(ocurrenciasRealizadas, eventoDiaCompleto.obtenerOcurrencias());
        assertEquals(maxOcurrencias, eventoDiaCompleto.obtenerMaxOcurrencias());
        assertEquals(tipoRepeticion, eventoDiaCompleto.obtenerTipoRepeticion());
        assertTrue(eventoDiaCompleto.obtenerAlarmasEvento().isEmpty());

        //Para los tests de constructor Default de la clase abstracta Evento se utilizó el metodo .toLocalDate para que el test no falle por milésima de segundos.
    }

    @Test
    public void testObtenerSiguienteOcurrenciaValoresDefault() {

        var eventoDiaCompleto = new EventoDiaCompleto(LocalDate.of(2023,5,10));

        LocalDateTime FechaEsperada = eventoDiaCompleto.obtenerFechaInicio().plusDays(1);
        LocalDateTime FechaActual = eventoDiaCompleto.calcularSiguienteOcurrencia(eventoDiaCompleto.obtenerFechaInicio());

        assertEquals(FechaEsperada, FechaActual);

    }
    @Test
    public void testObtenerSiguienteOcurrencia() {

        String      titulo = "Evento Dia Completo";
        String descripcion =  "Evento que se repite todos los dias";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    =    LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;

        var eventoDiaCompleto = new EventoDiaCompleto(LocalDate.of(2023,4,10));

        eventoDiaCompleto.establecerTitulo(titulo);
        eventoDiaCompleto.establecerDescripcion(descripcion);
        eventoDiaCompleto.establecerFechaInicio(fechaInicio);
        eventoDiaCompleto.establecerFechaFin(fechaFin);
        eventoDiaCompleto.establecerMaxOcurrencias(maxOcurrencias);
        eventoDiaCompleto.establecerTipoRepeticion(tipoRepeticion);

        LocalDateTime FechaEsperada = fechaInicio.plusDays(1);
        LocalDateTime FechaActual   = eventoDiaCompleto.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaEsperada, FechaActual);
    }

    @Test
    public void testBorrarAlarmas() {

        String        titulo      = "Evento Dia Completo";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 4;

        var eventoDiaCompleto = new EventoDiaCompleto(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion);

        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();

        var alarmaNotificacion = new AlarmaFechaAbsoluta(eventoDiaCompleto.obtenerFechaInicio(), notificacion);
        var alarmaSonido = new AlarmaFechaAbsoluta(eventoDiaCompleto.obtenerFechaInicio(), sonido);
        var alarmaMail = new AlarmaFechaAbsoluta(eventoDiaCompleto.obtenerFechaInicio(), mail);

        eventoDiaCompleto.agregarAlarmaEvento(alarmaNotificacion);
        eventoDiaCompleto.agregarAlarmaEvento(alarmaSonido);
        eventoDiaCompleto.agregarAlarmaEvento(alarmaMail);

        eventoDiaCompleto.desactivarAlarmaEvento(alarmaNotificacion);
        eventoDiaCompleto.desactivarAlarmaEvento(alarmaSonido);

        ArrayList<Alarma> listaAlarmas = eventoDiaCompleto.obtenerAlarmasEvento();

        assertFalse(listaAlarmas.contains(alarmaNotificacion));
        assertFalse(listaAlarmas.contains(alarmaSonido));
        assertTrue(listaAlarmas.contains(alarmaMail));

        assertEquals(1, listaAlarmas.size());
    }
}