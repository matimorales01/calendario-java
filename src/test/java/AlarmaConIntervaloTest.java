import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
public class AlarmaConIntervaloTest {
    @Test
    public void testAlarmaConIntervaloEstableceFechaYHoraCorrectas(){
        var notificacion = new Notificacion();
        var alarma = new AlarmaIntervalo(LocalDateTime.of(2023, 4, 17, 18, 0),30,notificacion);
        assertEquals(LocalDateTime.of(2023,4,17,17,30),alarma.obtenerFechaYHora());
        assertEquals(notificacion,alarma.obtenerEfecto());
    }
    @Test
    public void testAlarmaConIntervaloSeActivaDiaAnterior(){
        var notificacion = new Notificacion();
        var alarma = new AlarmaIntervalo(LocalDateTime.of(2023, 4, 17, 0, 0),30,notificacion);
        assertEquals(LocalDateTime.of(2023,4,16,23,30),alarma.obtenerFechaYHora());

    }
    @Test
    public void testAlarmaConIntervaloSeActivaMesAnterior(){
        var notificacion = new Notificacion();
        var alarma = new AlarmaIntervalo( LocalDateTime.of(2023, 4, 1, 0, 0),60,notificacion);
        assertEquals(LocalDateTime.of(2023,3,31,23,0),alarma.obtenerFechaYHora());

    }
    @Test
    public void testAlarmaConIntervaloSeActivaAnioAnterior(){
        var notificacion = new Notificacion();
        var alarma = new AlarmaIntervalo(LocalDateTime.of(2023, 1, 1, 0, 0),60,notificacion);
        assertEquals(LocalDateTime.of(2022,12,31,23,0),alarma.obtenerFechaYHora());

    }


}
