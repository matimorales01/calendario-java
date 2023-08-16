import java.time.LocalDateTime;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Tipo de evento")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventoDiario.class, name = "Evento Diario"),
        @JsonSubTypes.Type(value = EventoSemanal.class, name = "Evento Semanal"),
        @JsonSubTypes.Type(value = EventoMensual.class, name = "Evento mensual"),
        @JsonSubTypes.Type(value = EventoAnual.class, name = "Evento anual"),
        @JsonSubTypes.Type(value = EventoDiaCompleto.class, name = "Evento dia completo")


})
public abstract class Evento {

    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaInicio")
    private LocalDateTime fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("fechaFin")
    private LocalDateTime fechaFin;
    @JsonProperty("maxOcurrencias")
    private int maxOcurrencias;
    @JsonProperty("ocurrenciasRealizadas")
    private int ocurrenciasRealizadas;
    @JsonProperty("tipoRepeticion")
    private Repeticion tipoRepeticion;
    @JsonProperty("alarmasEvento")
    private final ArrayList<Alarma> alarmasEvento;


    public Evento() {
        alarmasEvento = new ArrayList<>();

    }
    public Evento(LocalDateTime fechaInicio){

        this.titulo = "Evento sin titulo";
        this.descripcion = "-";
        this.fechaInicio = fechaInicio;
        this.fechaFin = this.fechaInicio.plusDays(1);
        this.maxOcurrencias = 1;
        this.ocurrenciasRealizadas = 0;
        this.tipoRepeticion = Repeticion.HASTA_FECHA_FIN;
        this.alarmasEvento = new ArrayList<>();

    }

    // PRE: Pido los datos necesarios para la creación de un evento
    // POS: Inicializo los valores correctos del evento con los datos disponibles
    public Evento(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxOcurrencias, Repeticion tipoRepeticion) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.maxOcurrencias = maxOcurrencias;
        this.ocurrenciasRealizadas = 0;
        this.tipoRepeticion = tipoRepeticion;
        this.alarmasEvento = new ArrayList<>();


    }

//GETTERS
    public String obtenerTitulo() {return titulo;}
    public String obtenerDescripcion() {return descripcion;}
    public LocalDateTime obtenerFechaInicio() {return fechaInicio;}
    public LocalDateTime obtenerFechaFin() {return fechaFin;}
    public int obtenerMaxOcurrencias() {return maxOcurrencias;}

    public int obtenerOcurrencias() {return ocurrenciasRealizadas;}
    public Repeticion obtenerTipoRepeticion() {return tipoRepeticion;}
    public ArrayList<Alarma> obtenerAlarmasEvento(){
        return alarmasEvento;
    }


//SETTERS
    public void establecerTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void establecerDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void establecerFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public void establecerFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void establecerMaxOcurrencias(int maxOcurrencias) {
        this.maxOcurrencias = maxOcurrencias;
    }
    public void establecerTipoRepeticion(Repeticion tipoRepeticion ) {
        this.tipoRepeticion = tipoRepeticion;
    }



    //Ests métodos lo heredan las subclases EventoDiario, EventoSemanal, EventoMensual y EventoAnual

    //Método para obtener la siguiente ocurrencia del Evento segun la frecuencia que tiene asignada.
    protected abstract LocalDateTime calcularSiguienteOcurrencia(LocalDateTime fecha);

    //Método para crear una nueva instancia del objeto evento segun las repeticiones que le corresponden al evento.
//    protected abstract ArrayList<Evento> switchCaseRepeticion(LocalDateTime proximaFecha,ArrayList<Evento>  proximosEventos);


    public void agregarAlarmaEvento(Alarma alarma){
        alarmasEvento.add(alarma);
    }

    public void desactivarAlarmaEvento(Alarma alarma){
        alarmasEvento.remove(alarma);
    }

    public void modificarAlarmaEvento(Alarma alarma, LocalDateTime fechayHora){
        alarma.establecerFechaYHora(fechayHora);
    }
    public boolean fechaFinNula() {
       return fechaFin == null;
   }

    public void sumarOcurrencias(){
        ocurrenciasRealizadas++;
    }
    public abstract void establecerId(int id);
    public abstract int obtenerId();

}


