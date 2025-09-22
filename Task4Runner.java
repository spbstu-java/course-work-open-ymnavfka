import javafx.scene.control.TextArea;

import java.util.*;
import java.util.stream.Collectors;

public class Task4Runner {
    public static void run(TextArea out, String input) {
        if (input == null) input = "";
        String[] parts = input.trim().isEmpty() ? new String[0] : input.split("[,\\s]+");

        // try parse ints
        List<Integer> ints = new ArrayList<>();
        for (String p : parts) {
            try { ints.add(Integer.parseInt(p)); } catch (Exception ignored) {}
        }

        try {
            Class<?> st = Class.forName("Stream.StreamTasks");
            if (!ints.isEmpty()) {
                double avg = (double) st.getMethod("average", List.class).invoke(null, ints);
                Object uniqueSquares = st.getMethod("uniqueSquares", List.class).invoke(null, ints);
                int[] arr = ints.stream().mapToInt(i->i).toArray();
                int sumEven = (int) st.getMethod("sumEven", int[].class).invoke(null, (Object)arr);
                out.appendText("Average: " + avg + "\n");
                out.appendText("Unique squares: " + uniqueSquares + "\n");
                out.appendText("Sum even: " + sumEven + "\n");
            } else {
                // try strings toMap
                List<String> strs = Arrays.stream(parts).collect(Collectors.toList());
                Object map = st.getMethod("toMap", List.class).invoke(null, strs);
                out.appendText("Map: " + map + "\n");
            }
        } catch (ClassNotFoundException e) {
            out.appendText("StreamTasks class not found. Put Stream submodule in project.\n");
        } catch (Exception e) {
            out.appendText("Error: " + e + "\n");
        }
    }
}
