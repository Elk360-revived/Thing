import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class NoteApp extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Note Taking Application");

        BorderPane root = new BorderPane();
        textArea = new TextArea();
        root.setCenter(textArea);

        MenuBar menuBar = createMenuBar(primaryStage);
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(e -> textArea.clear());

        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(e -> openFile(primaryStage));

        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(e -> saveFile(primaryStage));

        MenuItem exitApp = new MenuItem("Exit");
        exitApp.setOnAction(e -> primaryStage.close());

        fileMenu.getItems().addAll(newFile, openFile, saveFile, new SeparatorMenuItem(), exitApp);

        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }

    private void openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                textArea.clear();
                String line;
                while ((line = br.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
