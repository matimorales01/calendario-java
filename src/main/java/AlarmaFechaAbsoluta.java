import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class AlarmaFechaAbsoluta extends Alarma{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaYHora")
    private  LocalDateTime fechaYHora;

    public AlarmaFechaAbsoluta(){

    }

   public AlarmaFechaAbsoluta(LocalDateTime fechaYHora, Efecto efecto) {
       super(efecto);

       this.fechaYHora = fechaYHora;
   }
   public void establecerFechaYHora(LocalDateTime fechaYHora){
       this.fechaYHora = fechaYHora;
   }
   public LocalDateTime obtenerFechaYHora(){
       return fechaYHora;
   }


}
