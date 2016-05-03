package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by space on 16/5/3.
 */
public class MenuController {
    private Runnable onCopyListener;
    private Runnable onPasteListener;

    public void setOnCopyListener(Runnable listener) {
        onCopyListener = listener;
    }

    public void setOnPasteListener(Runnable listener) {
        onPasteListener = listener;
    }

    @FXML
    private void handleCopyAction(ActionEvent event) {
        onCopyListener.run();
    }
    @FXML
    private void handlePasteAction(ActionEvent event) {
        onPasteListener.run();
    }

    @FXML
    private void handleAboutAction(ActionEvent event) throws Exception {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Parent root = new FXMLLoader(getClass().getResource("views/about.fxml")).load();
        Scene dialogScene = new Scene(root, 250, 150);
        dialog.setTitle("关于\"计算器\"");
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
