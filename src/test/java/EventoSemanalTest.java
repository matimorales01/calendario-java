import org.junit.Test;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class EventoSemanalTest {

    //Testeo constructor default de la clase Evento para eventoSemanal
    @Test
    public void testEventoSemanalDefault() {

        var eventoSemanal = new EventoSemanal(LocalDateTime.of(2023,5,10,20,30));

        String titulo =  "Evento sin titulo";
        String descripcion = "-";

        LocalDateTime fechaInicio = LocalDateTime.of(2023,5,10,20,30);
        LocalDateTime fechaFin = fechaInicio.plusDays(1);

        int ocurrenciasRealizadas = 0;
        int maxOcurrencias = 1;
        Repeticion tipoRepeticion = Repeticion.HASTA_FECHA_FIN;

        assertEquals(titulo, eventoSemanal.obtenerTitulo());
        assertEquals(descripcion, eventoSemanal.obtenerDescripcion());
        assertEquals(fechaInicio, eventoSemanal.obtenerFechaInicio());
        assertEquals(fechaFin, eventoSemanal.obtenerFechaFin());
        assertEquals(ocurrenciasRealizadas, eventoSemanal.obtenerOcurrencias());
        assertEquals(maxOcurrencias, eventoSemanal.obtenerMaxOcurrencias());
        assertEquals(tipoRepeticion, eventoSemanal.obtenerTipoRepeticion());
        assertNull(eventoSemanal.obtenerDiasSemana());

        //Para los tests de constructor Default de la clase abstracta Evento se utilizó el metodo .toLocalDate para que el test no falle por milésima de segundos.

    }

    @Test
    public void testObtenerSiguienteOcurrenciaValoresDefault() {

        var eventoSemanal = new EventoSemanal(LocalDateTime.of(2023,5,10,20,30));

        //Por default, el evento no se repite de forma semanal

        LocalDateTime FechaEsperada = eventoSemanal.obtenerFechaInicio().plusWeeks(1);
        LocalDateTime FechaActual = eventoSemanal.calcularSiguienteOcurrencia(eventoSemanal.obtenerFechaInicio());

        assertEquals(FechaEsperada, FechaActual);

    }

    //Caso 2 veces por semana
    @Test
    public void testEventoDosVecesPorSemana() {

        String titulo = "Evento Semanal";
        String descripcion =   "Evento que se repite todos los lunes y los viernes";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin =    LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;

        Set<DayOfWeek> diasSemana = Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

        var eventoSemanal = new EventoSemanal(LocalDateTime.of(2023,5,10,20,30));

        eventoSemanal.establecerTitulo(titulo);
        eventoSemanal.establecerDescripcion(descripcion);
        eventoSemanal.establecerFechaInicio(fechaInicio);
        eventoSemanal.establecerFechaFin(fechaFin);
        eventoSemanal.establecerMaxOcurrencias(maxOcurrencias);
        eventoSemanal.establecerTipoRepeticion(tipoRepeticion);
        eventoSemanal.establecerDiasSemana(diasSemana);

        //Creo Varias alarmas
        var Notificacion = new Notificacion();
        var mail = new Email();

        var alarmaNotif = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 15, 10, 0),Notificacion);
        var alarmaMail = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 12, 10, 0),mail);

        eventoSemanal.agregarAlarmaEvento(alarmaNotif);
        eventoSemanal.agregarAlarmaEvento(alarmaMail);

        ArrayList<Alarma> listaAlarmas = eventoSemanal.obtenerAlarmasEvento();

        LocalDateTime FechaEsperada = fechaInicio.plusDays(4); //Se espera que la proxima ocurrencia del evento sea el viernes 7 de abril
        LocalDateTime FechaActual = eventoSemanal.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaEsperada, FechaActual);
        assertEquals(diasSemana, eventoSemanal.obtenerDiasSemana());

        assertTrue(listaAlarmas.contains(alarmaMail));
        assertTrue(listaAlarmas.contains(alarmaNotif));
    }

    //Caso 3 veces por semana
    @Test
    public void testEventoTresVecesPorSemana() {

        String titulo = "Evento Semanal";
        String descripcion =   "Evento que se repite todos los lunes, miercoles y viernes";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 3, 9, 0); //lunes 3 de abril
        LocalDateTime fechaFin = LocalDateTime.of(2023, 4, 24, 9, 30); //lunes 24 de abril
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        Set<DayOfWeek> diasSemana = Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY ,DayOfWeek.FRIDAY);
        int maxOcurrencias = 1;

        var eventoSemanal = new EventoSemanal(titulo, descripcion, fechaInicio, fechaFin,maxOcurrencias, tipoRepeticion ,diasSemana);

        LocalDateTime FechaMiercolesEsperada = fechaInicio.plusDays(2); //Se espera que la proxima ocurrencia del evento sea el miercoles 5 de abril
        LocalDateTime FechaMiercolesActual = eventoSemanal.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(FechaMiercolesEsperada, FechaMiercolesActual);

        LocalDateTime FechaViernesEsperada = FechaMiercolesEsperada.plusDays(2); //Se espera que la proxima ocurrencia del evento sea el viernes 7 de abril
        LocalDateTime FechaViernesActual = eventoSemanal.calcularSiguienteOcurrencia(FechaMiercolesActual);

        assertEquals(FechaViernesEsperada, FechaViernesActual);
        assertEquals(diasSemana, eventoSemanal.obtenerDiasSemana());
    }

    @Test
    public void testBorrarAlarmas() {

        String        titulo      = "Evento Semanal";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 17, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 4;
        Set<DayOfWeek> diasSemana = Set.of(DayOfWeek.MONDAY);

        var eventoSemanal = new EventoSemanal(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,diasSemana);

        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();

        var alarmaNotificacion = new AlarmaFechaAbsoluta(eventoSemanal.obtenerFechaInicio(), notificacion);
        var alarmaSonido = new AlarmaFechaAbsoluta(eventoSemanal.obtenerFechaInicio(), sonido);
        var alarmaMail = new AlarmaFechaAbsoluta(eventoSemanal.obtenerFechaInicio(), mail);

        eventoSemanal.agregarAlarmaEvento(alarmaNotificacion);
        eventoSemanal.agregarAlarmaEvento(alarmaSonido);
        eventoSemanal.agregarAlarmaEvento(alarmaMail);

        eventoSemanal.desactivarAlarmaEvento(alarmaNotificacion);
        eventoSemanal.desactivarAlarmaEvento(alarmaSonido);

        ArrayList<Alarma> listaAlarmas = eventoSemanal.obtenerAlarmasEvento();

        assertFalse(listaAlarmas.contains(alarmaNotificacion));
        assertFalse(listaAlarmas.contains(alarmaSonido));
        assertTrue(listaAlarmas.contains(alarmaMail));

        assertEquals(1, listaAlarmas.size());
    }
    @Test
    public void SetDiasSemanaVacio(){
        String        titulo      = "Evento Semanal";
        String        descripcion = "Evento que ingresa un intervalo de dias nulo";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 10, 9, 0);
        LocalDateTime fechaFin    = LocalDateTime.of(2023, 4, 18, 9, 30);
        Repeticion tipoRepeticion = Repeticion.HASTA_OCURRENCIAS;
        int maxOcurrencias = 1;

        var eventoSemanal = new EventoSemanal(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion ,null);

        LocalDateTime fechaEsperada = fechaInicio.plusWeeks(1);
        LocalDateTime fechaActual = eventoSemanal.calcularSiguienteOcurrencia(fechaInicio);

        assertEquals(fechaEsperada, fechaActual);

    }


}





