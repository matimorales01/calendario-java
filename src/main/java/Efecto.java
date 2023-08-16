import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Tipo de efecto")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Notificacion.class, name = "notificacion"),
        @JsonSubTypes.Type(value = Sonido.class, name = "sonido"),
        @JsonSubTypes.Type(value = Email.class, name = "email")
})
public interface Efecto {
    void reproducirEfecto();
}
