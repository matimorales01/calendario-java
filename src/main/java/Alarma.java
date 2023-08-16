import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

import java.util.Timer;
import java.util.TimerTask;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Tipo de alarma")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlarmaIntervalo.class, name = "alarmaIntervalo"),
        @JsonSubTypes.Type(value = AlarmaFechaAbsoluta.class, name = "alarmaFechaAbsoluta")
})
public abstract class Alarma {
    @JsonProperty("efecto")
    private Efecto efecto;
    private Timer timer;
    public abstract void establecerFechaYHora(LocalDateTime fechaYHora);

    public abstract LocalDateTime obtenerFechaYHora();

    public Alarma(){

    }
    public Alarma(Efecto efecto){
        this.efecto = efecto;
        this.timer = new Timer();


    }

    public void activarAlarma(LocalDateTime fechaYHora) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sonarAlarma(efecto);
            }
        };

        long delay = calcularTiempoAlarma(fechaYHora);
        timer.schedule(task, delay);
        }

    public long calcularTiempoAlarma(LocalDateTime fechaYHora) {
        LocalDateTime now = LocalDateTime.now();
        return now.until(fechaYHora, ChronoUnit.MILLIS);
    }
    public void sonarAlarma(Efecto efecto) {
        efecto.reproducirEfecto();
    }
    public Efecto obtenerEfecto(){
        return efecto;
    }



}
