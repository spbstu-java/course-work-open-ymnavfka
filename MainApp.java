import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainApp extends Application {
    private TextArea output;

    @Override
    public void start(Stage stage) {
        ComboBox<String> sel = new ComboBox<>();
        sel.getItems().addAll("Task 1 - Hero", "Task 2 - HeroMethods", "Task 3 - Translator", "Task 4 - Stream");
        sel.setValue("Task 1 - Hero");

        output = new TextArea();
        output.setEditable(false);
        output.setWrapText(true);

        BorderPane root = new BorderPane();
        root.setTop(sel);
        root.setCenter(createPaneFor("Task 1 - Hero"));
        root.setBottom(output);

        sel.setOnAction(e -> root.setCenter(createPaneFor(sel.getValue())));

        Scene sc = new Scene(root, 800, 450);
        stage.setScene(sc);
        stage.setTitle("Tasks 1–4 GUI");
        stage.show();
    }

    private Pane createPaneFor(String name) {
        switch (name) {
            case "Task 1 - Hero": return task1Pane();
            case "Task 2 - HeroMethods": return task2Pane();
            case "Task 3 - Translator": return task3Pane();
            default: return task4Pane();
        }
    }

    private Pane task1Pane() {
        ComboBox<String> strategies = new ComboBox<>();
        strategies.getItems().addAll("walk","horse","fly");
        strategies.setValue("walk");
        TextField from = new TextField();
        from.setPromptText("from");
        TextField to = new TextField();
        to.setPromptText("to");
        Button run = new Button("Run");
        run.setOnAction(e -> {
            output.clear();
            Task1Runner.run(output, strategies.getValue(), safeText(from), safeText(to));
        });
        HBox h = new HBox(8, new Label("Strategy:"), strategies, new Label("From:"), from, new Label("To:"), to, run);
        h.setPadding(new javafx.geometry.Insets(10));
        return h;
    }

    private Pane task2Pane() {
        Button run = new Button("Run");
        run.setOnAction(e -> {
            output.clear();
            Task2Runner.run(output);
        });
        HBox h = new HBox(8, run);
        h.setPadding(new javafx.geometry.Insets(10));
        return h;
    }

    private Pane task3Pane() {
        Button chooseDict = new Button("Choose dict file");
        Button chooseText = new Button("Choose text file");
        TextField manual = new TextField();
        manual.setPromptText("Or paste text here");
        Button run = new Button("Translate");

        final File[] dict = new File[1];
        final File[] txt = new File[1];

        chooseDict.setOnAction(e -> {
            FileChooser c = new FileChooser();
            dict[0] = c.showOpenDialog(null);
            output.appendText("Dict: " + (dict[0]==null ? "none" : dict[0].getAbsolutePath()) + "\n");
        });
        chooseText.setOnAction(e -> {
            FileChooser c = new FileChooser();
            txt[0] = c.showOpenDialog(null);
            output.appendText("Text file: " + (txt[0]==null ? "none" : txt[0].getAbsolutePath()) + "\n");
        });
        run.setOnAction(e -> {
            output.clear();
            Task3Runner.run(output,
                    dict[0] == null ? null : dict[0].getAbsolutePath(),
                    txt[0] == null ? null : txt[0].getAbsolutePath(),
                    safeText(manual));
        });

        HBox h = new HBox(8, chooseDict, chooseText, manual, run);
        h.setPadding(new javafx.geometry.Insets(10));
        return h;
    }

    private Pane task4Pane() {
        TextField input = new TextField();
        input.setPromptText("numbers or words (comma or space separated)");

        ComboBox<String> methodBox = new ComboBox<>();
        methodBox.getItems().addAll(
                "average",
                "uniqueSquares",
                "sumEven",
                "toMap",
                "toUpperWithPrefix",
                "lastElement"
        );
        methodBox.setPromptText("Select method");

        Button run = new Button("Run");
        run.setOnAction(e -> {
            output.clear();
            String method = methodBox.getValue();
            if (method == null) {
                output.appendText("Please select a method.\n");
                return;
            }
            Task4Runner.run(output, safeText(input), method);
        });

        HBox h = new HBox(8, methodBox, input, run);
        h.setPadding(new javafx.geometry.Insets(10));
        return h;
    }

    private String safeText(TextField t) { return t.getText() == null ? "" : t.getText(); }

    public static void main(String[] args) { launch(); }
}
