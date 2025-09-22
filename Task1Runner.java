import javafx.scene.control.TextArea;

public class Task1Runner {
    public static void run(TextArea out, String strategy, String from, String to) {
        if (from.isEmpty()) from = "A";
        if (to.isEmpty()) to = "B";
        MoveStrategy s = switch (strategy) {
            case "horse" -> (a,b) -> out.appendText("Hero rides a horse from "+a+" to "+b+"\n");
            case "fly" -> (a,b) -> out.appendText("Hero flies from "+a+" to "+b+"\n");
            default -> (a,b) -> out.appendText("Hero walks from "+a+" to "+b+"\n");
        };
        Hero h = new Hero(s);
        h.move(from, to);
    }
}
