import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Notificacion implements Efecto{
   public void reproducirEfecto(){
       Platform.runLater(() -> {
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("Notificacion");
           alerta.setHeaderText("Tarea o evento por comenzar");
           alerta.setContentText("Notificacion enviada");
           alerta.showAndWait();


    });
   }
}
