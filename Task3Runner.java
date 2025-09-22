import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.InvocationTargetException;

public class Task3Runner {
    public static void run(TextArea out, String dictPath, String textFilePath, String manualText) {
        try {
            String text;
            if (manualText != null && !manualText.isEmpty()) text = manualText;
            else if (textFilePath != null) text = Files.readString(new File(textFilePath).toPath(), StandardCharsets.UTF_8);
            else { out.appendText("No text provided\n"); return; }

            if (dictPath == null || dictPath.isEmpty()) { out.appendText("No dictionary selected\n"); return; }

            Class<?> trClass = Class.forName("Translator.Translator");
            Object tr = trClass.getConstructor(String.class).newInstance(dictPath);
            String res = (String) trClass.getMethod("translate", String.class).invoke(tr, text);
            out.appendText(res + "\n");
        } catch (ClassNotFoundException e) {
            out.appendText("Translator class not found. Put Translator submodule in project.\n");
        } catch (InvocationTargetException ite) {
            out.appendText("Error: " + ite.getTargetException().getMessage() + "\n");
        } catch (Exception e) {
            out.appendText("Error: " + e + "\n");
        }
    }
}
