import java.time.LocalDateTime;
import java.util.ArrayList;

//Implementacion patron Builder por la sobrecargar del parametro para la creacion de eventos
//Se combina junto a Abstract Factory Method
public interface ConstructorEventos
{
    void setTitulo();

    void setDescripcion();

    void setFechaInicio();

    void setFechaFin();

    void setMaxOcurrencias();

    void setTipoRepeticion();

    void setDiasSemana();

    void setIntervalo();

    Evento obtenerEventoCreado();

    ArrayList<Evento> repeticionEvento(LocalDateTime proximaFecha, ArrayList<Evento> OcurrenciasEventos);

}
