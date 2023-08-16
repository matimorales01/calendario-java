import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class AlarmaIntervalo extends Alarma {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaYHora")
    private LocalDateTime fechaYHora;

    public AlarmaIntervalo(){

    }
    public AlarmaIntervalo(LocalDateTime fechaYHora, int intervalo, Efecto efecto) {
        super(efecto);
        this.fechaYHora = calcularIntervalo(fechaYHora, intervalo);
    }

    public void establecerFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public LocalDateTime obtenerFechaYHora() {
        return fechaYHora;
    }

    public LocalDateTime calcularIntervalo(LocalDateTime fechaYHora, int intervalo) {
        int anio = fechaYHora.getYear();
        int mes = fechaYHora.getMonthValue();
        int dia = fechaYHora.getDayOfMonth();
        int horas = fechaYHora.getHour();
        int minutos = fechaYHora.getMinute();
        int minutosTotales = calcularMinutosTotales(minutos, intervalo, horas);
        int horaNueva = minutosTotales / 60;
        int diaNuevo = dia;
        int[] nuevosValores = calcularNuevosValores(horaNueva,horas,diaNuevo,mes,anio);
        diaNuevo = nuevosValores[0];
        mes = nuevosValores[1];
        anio = nuevosValores[2];
        return (LocalDateTime.of(anio, mes, diaNuevo, horaNueva, minutosTotales % 60 ));
    }


    public int calcularMinutosTotales(int minutos, int intervalo, int horas) {
        int minutosTotales = horas * 60 + minutos;
        minutosTotales -= intervalo;
        if (minutosTotales < 0) {
            minutosTotales += 24 * 60;
        }
        return minutosTotales ;

    }


    public int calcularDiaNuevo(int horaNueva,int horas,int  diaNuevo){
        if (horaNueva > horas) {
            diaNuevo--;
        }
        return diaNuevo;
    }



    public int[] calcularNuevoAnio(int mes, int anio) {
        if (mes <= 0) {
            mes = 12;
            anio--;
        }
        return new int[]{mes, anio};
    }




    public int[] calcularNuevosValores(int horaNueva,int horas,int diaNuevo,int mes,int anio){
        diaNuevo = calcularDiaNuevo(horaNueva,horas,diaNuevo);
        if (diaNuevo <= 0) {
            int[] nuevosValores = calcularNuevoAnio(mes - 1, anio);
            mes = nuevosValores[0];
            anio = nuevosValores[1];
            int cantidadDeDias = LocalDate.of(anio, mes, 1).lengthOfMonth();
            diaNuevo = cantidadDeDias + diaNuevo;
         }
        return new int []{diaNuevo,mes,anio};
    }




}