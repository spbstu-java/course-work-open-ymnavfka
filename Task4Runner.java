import javafx.scene.control.TextArea;

import java.util.*;
import java.util.stream.Collectors;

public class Task4Runner {
    public static void run(TextArea out, String input, String method) {
        if (input == null) input = "";

        String[] parts = input.trim().isEmpty() ? new String[0] : input.split("[,\\s]+");
        List<String> strs = Arrays.asList(parts);
        List<Integer> ints = new ArrayList<>();
        for (String p : parts) {
            try { ints.add(Integer.parseInt(p)); } catch (Exception ignored) {}
        }

        try {
            Class<?> st = Class.forName("Stream.StreamTasks");
            switch (method) {
                case "average" -> {
                    if (ints.isEmpty()) {
                        out.appendText("Error: need numbers for average\n");
                        return;
                    }
                    double avg = (double) st.getMethod("average", List.class).invoke(null, ints);
                    out.appendText("Average: " + avg + "\n");
                }
                case "toUpperWithPrefix" -> {
                    Object res = st.getMethod("toUpperWithPrefix", List.class).invoke(null, strs);
                    out.appendText("to upper with prefix: " + res + "\n");
                }
                case "uniqueSquares" -> {
                    Object res = st.getMethod("uniqueSquares", List.class).invoke(null, ints);
                    out.appendText("Unique squares: " + res + "\n");
                }
                case "lastElement" -> {
                    if (strs.isEmpty()) {
                        out.appendText("Error: collection is empty\n");
                        return;
                    }
                    Object res = st.getMethod("lastElement", Collection.class).invoke(null, strs);
                    out.appendText("Last element: " + res + "\n");
                }
                case "sumEven" -> {
                    int[] arr = ints.stream().mapToInt(i -> i).toArray();
                    int sumEven = (int) st.getMethod("sumEven", int[].class).invoke(null, (Object) arr);
                    out.appendText("Sum even: " + sumEven + "\n");
                }
                case "toMap" -> {
                    Object res = st.getMethod("toMap", List.class).invoke(null, strs);
                    out.appendText("Map: " + res + "\n");
                }
                default -> out.appendText("Unknown method: " + method + "\n");
            }
        } catch (Exception e) {
            out.appendText("Error: " + e + "\n");
        }
    }
}
