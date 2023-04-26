import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    void convertCSVtoJSON() {
        FileManager.convertCSVtoJSON(new File(new File("").getAbsolutePath() + File.separator + "validtest.csv"),"output.json");
        List<String> expectedLines;
        List<String> actualLines;
        try {
            expectedLines = Files.readAllLines(Path.of(new File("").getAbsolutePath() + File.separator + "validtest.json"), StandardCharsets.UTF_8);
            actualLines = Files.readAllLines(Path.of(new File("").getAbsolutePath() + File.separator + "output.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedLines, actualLines);
        assertThrows(Exception.class, () -> FileManager.convertCSVtoJSON(null,"EmptyOutput.json"));
    }

    @Test
    void convertJSONtoCSV() {
        FileManager.convertJSONtoCSV(new File(new File("").getAbsolutePath() + File.separator + "validtest.json"),"output.csv");
        List<String> expectedLines;
        List<String> actualLines;
        try {
            expectedLines = Files.readAllLines(Path.of(new File("").getAbsolutePath() + File.separator + "validtest.csv"), StandardCharsets.UTF_8);
            actualLines = Files.readAllLines(Path.of(new File("").getAbsolutePath() + File.separator + "output.csv"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedLines, actualLines);
        assertThrows(Exception.class, () -> FileManager.convertJSONtoCSV(null,"error"));
    }
}