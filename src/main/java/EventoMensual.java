import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class EventoMensual extends Evento{
    @JsonProperty("cantidadMeses")
    private int cantidadMeses;
    @JsonProperty("id")
    private int id;
    private static int idSiguiente = 0;
    public EventoMensual(){

    }

    public EventoMensual(LocalDateTime fechaInicio){
        super(fechaInicio);
        this.cantidadMeses = 1;
        this.id = idSiguiente++;
    }

    public EventoMensual(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion, int cantidadMeses) {
        super(titulo, descripcion, fechaInicio, fechaFin, maxOcurrencias,tipoRepeticion);
        this.cantidadMeses = cantidadMeses;
        this.id = idSiguiente++;
    }



    public void establecerId(int id){
        this.id = id;
    }

    public int obtenerId(){ return id;}

    public int obtenerCantidadMeses() {
        return cantidadMeses;
    }

    public void establecerCantidadMeses(int cantidadMeses) {
        this.cantidadMeses = cantidadMeses;
    }

    @Override
    protected LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha) {
        if (cantidadMeses > 1) {
            fecha = fecha.plusMonths(cantidadMeses - 1);
        }
        else if (cantidadMeses == 0){
            return fecha;
        }

        else if (cantidadMeses < 0){
            throw new RuntimeException("Cantidad de meses negativo");
        }
        return fecha.plusMonths(1);
    }
}
