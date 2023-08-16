import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TareaDiaCompletoTest {
    @Test
    public void testCrearTareaDefault(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        assertEquals("Tarea sin titulo", tarea.obtenerTitulo());
        assertEquals("",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,5,10,0,0),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,5,10,0,0).plusDays(1),tarea.obtenerFechaVencimiento());


    }


    @Test
    public void testCrearTareaConParametros(){
        var tarea = new TareaDiaCompleto("Tarea","Descripcion",LocalDate.of(2023,4,18));
        assertEquals("Tarea",tarea.obtenerTitulo());
        assertEquals("Descripcion",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,4,18,0,0,0),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,4,18,23,59,59),tarea.obtenerFechaVencimiento());
        assertFalse(tarea.estaCompleta());
    }

    @Test
    public void testEstablecerAtributosManual(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        tarea.establecerTitulo("Trabajo practico");
        tarea.establecerDescripcion("Descripcion");
        tarea.establecerFechaInicio(LocalDateTime.of(2023, 4, 17, 10, 0));
        tarea.establecerFechaVencimiento(LocalDateTime.of(2023, 4, 17, 18, 0));
        assertEquals("Trabajo practico",tarea.obtenerTitulo());
        assertEquals("Descripcion",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023, 4, 17, 10, 0),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023, 4, 17, 18, 0),tarea.obtenerFechaVencimiento());

    }

    @Test
    public void testTareasTienenDistintoId(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        var tarea_uno = new TareaDiaCompleto("Tarea", "Descripcion", LocalDate.of(2023,4,18));
        var tarea_dos = new TareaDiaCompleto("Hacer tarea", "Descripcion", LocalDate.of(2023,5,18));

        assertNotEquals(tarea_uno.obtenerId(),tarea_dos.obtenerId());
        assertNotEquals(tarea.obtenerId(),tarea_uno.obtenerId());
        assertNotEquals(tarea.obtenerId(),tarea_dos.obtenerId());

    }

    @Test
    public void testMarcarTareaCompletada() {
        var tarea = new TareaDiaCompleto("Tarea","Descripcion",LocalDate.of(2023,4,18));
        tarea.marcarComoCompleta();
        assertTrue(tarea.estaCompleta());
    }
    @Test
    public void testActivarAlarma(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        var Notificacion = new Notificacion();
        var alarma = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);
        tarea.agregarAlarma(alarma);
        ArrayList<Alarma> listaAlarmas = tarea.obtenerAlarmas();
        assertTrue(listaAlarmas.contains(alarma));

    }
    @Test
    public void testBorrarAlarma(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var alarma = new AlarmaFechaAbsoluta(tarea.obtenerFechaInicio(),notificacion);
        var alarma_uno = new AlarmaFechaAbsoluta(tarea.obtenerFechaInicio(), sonido);
        tarea.agregarAlarma(alarma);
        tarea.agregarAlarma(alarma_uno);
        tarea.desactivarAlarma(alarma);
        ArrayList<Alarma> listaAlarmas = tarea.obtenerAlarmas();
        assertFalse(listaAlarmas.contains(alarma));
        assertEquals(1,listaAlarmas.size());


    }
    @Test
    public void testAgregarMuchasAlarmas(){
        var tarea = new TareaDiaCompleto(LocalDate.of(2023,5,10));
        var notificacion = new Notificacion();
        var sonido = new Sonido();
        var mail = new Email();
        var alarma = new AlarmaFechaAbsoluta(tarea.obtenerFechaInicio(),notificacion);
        var alarma_uno = new AlarmaFechaAbsoluta(tarea.obtenerFechaInicio(),sonido);
        var alarma_dos = new AlarmaFechaAbsoluta(tarea.obtenerFechaInicio(),mail);

        tarea.agregarAlarma(alarma);
        tarea.agregarAlarma(alarma_uno);
        tarea.agregarAlarma((alarma_dos));
        ArrayList<Alarma> listaAlarmas = tarea.obtenerAlarmas();
        assertEquals(3,listaAlarmas.size());

    }



}