import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.Set;

public class EventoSemanal extends Evento {
    @JsonProperty("diasSemana")
    private Set<DayOfWeek> diasSemana;
    @JsonProperty("id")
    private int id;
    private static int idSiguiente = 0;

    public EventoSemanal(){

    }
    public EventoSemanal(LocalDateTime fechaInicio) {
        super(fechaInicio);
        this.diasSemana = null;
        this.id = idSiguiente++;
    }

    public EventoSemanal(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin,int maxOcurrencias,Repeticion tipoRepeticion ,Set<DayOfWeek> diasSemana) {
        super(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias,tipoRepeticion);
        this.diasSemana = diasSemana;
        this.id = idSiguiente++;
    }

    public Set<DayOfWeek> obtenerDiasSemana() {
        return diasSemana;
    }

    public void establecerDiasSemana(Set<DayOfWeek> diasSemana) {
        this.diasSemana = diasSemana;
    }
    public void establecerId(int id){
        this.id = id;
    }
    public int obtenerId(){ return id;}
    @Override
    protected LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha) {
        if (diasSemana == null || diasSemana.isEmpty()) {

            this.establecerFechaFin(fecha.plusWeeks(1));
            return fecha.plusWeeks(1);
        }
        for (int i = 1; i <= 7; i++) {

            LocalDateTime siguienteFecha = fecha.plusDays(i);

            DayOfWeek siguienteDia = siguienteFecha.getDayOfWeek();

            if (diasSemana.contains(siguienteDia)) {
                return siguienteFecha;
            }
        }
        return fecha;
    }
    //Si el set esta vacio, simplemente el evento se vuelve a repetir la semana siguiene
}
