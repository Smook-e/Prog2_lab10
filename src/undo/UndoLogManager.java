package undo;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UndoLogManager {

    private static final String LOG_FILE = "undo.log";
    public static void logMove(String folder, UndoLogEntry entry) throws IOException {
        new File(folder).mkdirs();
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(folder + "/" + LOG_FILE, true))) {
            bw.write(entry.toLine());
            bw.newLine();
        }
    }

    public static UndoLogEntry popLast(String folder) throws IOException {
        File file = new File(folder + "/" + LOG_FILE);
        if (!file.exists()) return null;

        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) return null;

        String last = lines.remove(lines.size() - 1);
        Files.write(file.toPath(), lines);
        return UndoLogEntry.fromLine(last);
    }

    public static void clear(String folder) {
        File file = new File(folder + "/" + LOG_FILE);
        if (file.exists()) file.delete();
    }
}
