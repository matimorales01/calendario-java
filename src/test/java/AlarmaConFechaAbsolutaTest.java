import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class AlarmaConFechaAbsolutaTest {
    @Test
    public void testAlarmaConFechaAbsoluta(){
        var notificacion = new Notificacion();
        var alarma = new AlarmaFechaAbsoluta( LocalDateTime.of(2023, 4, 1, 0, 0),notificacion);
            assertEquals(LocalDateTime.of(2023,4,1,0,0),alarma.obtenerFechaYHora());
            assertEquals(notificacion,alarma.obtenerEfecto());
    }
}
