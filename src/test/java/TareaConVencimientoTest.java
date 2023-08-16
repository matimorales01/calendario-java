import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;
public class TareaConVencimientoTest {
    @Test
    public void testCrearTareaDefalt(){
        var tarea = new TareaConVencimiento(LocalDateTime.of(2023,5,10,22,30));        assertEquals("Tarea sin titulo",tarea.obtenerTitulo());
        assertEquals("",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,5,10,22,30),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,5,10,22,30).plusDays(1),tarea.obtenerFechaVencimiento());

    }
    @Test
    public void testCrearTareaConParametros(){
        var tarea = new TareaConVencimiento("Tarea","Descripcion",LocalDateTime.of(2023,4,18,15,0),LocalDateTime.of(2023,4,18,18,30));
        assertEquals("Tarea",tarea.obtenerTitulo());
        assertEquals("Descripcion",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023,4,18,15,0),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023,4,18,18,30),tarea.obtenerFechaVencimiento());
        assertFalse(tarea.estaCompleta());
    }

    @Test
    public void testEstablecerAtributosManual(){
        var tarea = new TareaConVencimiento(LocalDateTime.of(2023,5,10,22,30));        tarea.establecerTitulo("Tarea");
        tarea.establecerDescripcion("Descripcion");
        tarea.establecerFechaInicio(LocalDateTime.of(2023, 4, 17, 10, 0));
        tarea.establecerFechaVencimiento(LocalDateTime.of(2023, 4, 17, 18, 0));
        assertEquals("Tarea",tarea.obtenerTitulo());
        assertEquals("Descripcion",tarea.obtenerDescripcion());
        assertEquals(LocalDateTime.of(2023, 4, 17, 10, 0),tarea.obtenerFechaInicio());
        assertEquals(LocalDateTime.of(2023, 4, 17, 18, 0),tarea.obtenerFechaVencimiento());

    }
    @Test
    public void testMarcarTareaCompletada() {
        var tarea = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
        tarea.marcarComoCompleta();
        assertTrue(tarea.estaCompleta());
    }
    @Test
    public void testTareasTienenDistintoId(){
        var tarea_uno = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
        var tarea_dos = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
        var tarea_tres = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
        assertNotEquals(tarea_uno.obtenerId(),tarea_dos.obtenerId());
        assertNotEquals(tarea_uno.obtenerId(),tarea_tres.obtenerId());
        assertNotEquals(tarea_dos.obtenerId(),tarea_tres.obtenerId());

    }
    @Test
    public void testActivarAlarma(){
        var tarea = new TareaConVencimiento( "Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
        var Notificacion = new Notificacion();
        var alarma = new AlarmaFechaAbsoluta(LocalDateTime.of(2023, 4, 17, 10, 0),Notificacion);
        tarea.agregarAlarma(alarma);
        ArrayList<Alarma> listaAlarmas = tarea.obtenerAlarmas();
        assertTrue(listaAlarmas.contains(alarma));

    }
    @Test
    public void testBorrarAlarma(){
        var tarea = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
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
        var tarea = new TareaConVencimiento("Tarea", "Descripcion", LocalDateTime.of(2023, 4, 17, 10, 0), LocalDateTime.of(2023, 4, 17, 18, 0));
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
