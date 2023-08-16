import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EventoDiario extends Evento{
    private int intervalo;
    @JsonProperty("id")
    private int id;
    private static int idSiguiente = 0;

    public EventoDiario(){

    }
    public EventoDiario(LocalDateTime fechaInicio) {
        super(fechaInicio);
        this.intervalo = 1;
        this.id = idSiguiente++;
    }

    public EventoDiario(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias,Repeticion tipoRepeticion,int intervalo) {
        super(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion);
        this.intervalo = intervalo;
        this.id = idSiguiente++;
    }
    public void establecerId(int id){
        this.id = id;
    }
    public int obtenerId(){ return id;}
    public int obtenerIntervalo() {
        return intervalo;
    }

    public void establecerIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    @Override
    protected LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha) {
        if (intervalo > 1) {
            fecha = fecha.plusDays(intervalo - 1);
        }
        else if (intervalo == 0){
            return fecha;
        }

        else if (intervalo < 0){
            throw new RuntimeException("Intervalo de dias negativo");
        }
        return fecha.plusDays(1);
    }
}


