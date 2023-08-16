import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class EventoAnual extends Evento {
    @JsonProperty("cantidadAnios")
    private int cantidadAnios;
    @JsonProperty("id")
    private int id;
    private static int idSiguiente = 0;

    public EventoAnual(){

    }
    public EventoAnual(LocalDateTime fechaInicio) {
        super(fechaInicio);
        this.cantidadAnios = 1;
        this.id = idSiguiente++;
    }

    public EventoAnual(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion, int cantidadAnios) {
        super(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias, tipoRepeticion);
        this.cantidadAnios = cantidadAnios;
        this.id = idSiguiente++;
    }


    public int obtenerCantidadAnios() {
        return cantidadAnios;
    }

    public void establecerCantidadAnios(int cantidadAnios) {
        this.cantidadAnios = cantidadAnios;
    }

    public void establecerId(int id){
        this.id = id;
    }
    public int obtenerId(){ return id;}
    protected LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha) {
        if (cantidadAnios > 1) {
            fecha = fecha.plusYears(cantidadAnios - 1);

        } else if (cantidadAnios == 0) {
            return fecha;

        } else if (cantidadAnios < 0) {
            throw new RuntimeException("Cantidad de años negativa");
        }
        return fecha.plusYears(1);
    }

}