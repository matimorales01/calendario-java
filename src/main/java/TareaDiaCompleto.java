import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonTypeName("TareaDiaCompleto")
public class TareaDiaCompleto extends Tarea {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("fechaInicio")
        private LocalDateTime fechaInicio;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("fechaVencimiento")
        private LocalDateTime fechaVencimiento;


    public  TareaDiaCompleto(){
        super("Tarea dia Completo","-");

    }

    public TareaDiaCompleto(LocalDate fechaInicio) {
        super("Tarea sin titulo", "");

        this.fechaInicio = fechaInicio.atTime(0,0,0);
        this.fechaVencimiento = this.fechaInicio.plusDays(1);
    }


    public TareaDiaCompleto(String titulo, String descripcion,  LocalDate fechaInicio) {
        super(titulo,descripcion);
        this.fechaInicio = fechaInicio.atTime(0,0,0);
        this.fechaVencimiento = fechaInicio.atTime(23,59,59);
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
