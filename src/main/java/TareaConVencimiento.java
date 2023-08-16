import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

public class TareaConVencimiento extends Tarea{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaInicio")
    private LocalDateTime fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaVencimiento")
    private LocalDateTime fechaVencimiento;

    public TareaConVencimiento(){
        super("Tarea con Vencimiento","-");
    }
    public TareaConVencimiento(LocalDateTime fechaInicio){
        super("Tarea sin titulo","-");
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = this.fechaInicio.plusDays(1);

    }
    public TareaConVencimiento(String titulo, String descripcion,LocalDateTime fechaInicio, LocalDateTime fechaVencimiento){
        super(titulo,descripcion);
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;

    }
    public LocalDateTime obtenerFechaInicio() {
        return fechaInicio;
    }
    public LocalDateTime obtenerFechaVencimiento(){
        return fechaVencimiento;
    }
    public void establecerFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public void establecerFechaVencimiento(LocalDateTime fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }

}