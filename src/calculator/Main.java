package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("views/standard.fxml"));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("views/menus.fxml"));
        Parent root = mainLoader.load();
        Parent menuBar = menuLoader.load();
        BorderPane borderPane = (BorderPane) root.lookup("#root");
        borderPane.setTop(menuBar); //reuse menu bar definition

        Controller mainController = mainLoader.getController();
        MenuController menuController = menuLoader.getController();

        menuController.setOnCopyListener(mainController::onCopy);
        menuController.setOnPasteListener(mainController::onPaste);

        primaryStage.setTitle("计算器");
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        // One line commented below is enough lol
        // try {Runtime.getRuntime().exec("calc.exe");return;} catch (Exception e) {e.printStackTrace();}
        launch(args);
    }
}
