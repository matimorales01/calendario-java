import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class EventoDiaCompleto extends Evento {
    @JsonProperty("id")
    private int id;
    private static int idSiguiente = 0;

    public  EventoDiaCompleto(){

    }
    public EventoDiaCompleto(LocalDate fechaInicio) {
        super(fechaInicio.atTime(0, 0, 0));
        this.id = idSiguiente++;
    }

    public EventoDiaCompleto(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion) {
        super(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion);
        this.id = idSiguiente++;
    }

    @Override
    protected LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha) {
        return fecha.plusDays(1);
    }

    public void establecerId(int id) {
        this.id = id;
    }

    public int obtenerId() {
        return id;
    }
}