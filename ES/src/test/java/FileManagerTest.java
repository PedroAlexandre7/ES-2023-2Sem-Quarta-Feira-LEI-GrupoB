import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    void convertCSVtoJSON() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.csv", StandardCharsets.UTF_8))) {
            FileManager.convertCSVtoJSON(new File("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.csv"),"output.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expectedLines = null;
        List<String> actualLines = null;
        try {
            expectedLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.json"), StandardCharsets.UTF_8);
            actualLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\output.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedLines, actualLines);
        //assertEquals(new File("ES/empty.json"),FileManager.convertCSVtoJSON(null,"empty.json"));
        //assertThrows(Exception.class, () -> FileManager.convertCSVtoJSON(new File("ES/invalid.csv"),"error"));
    }

    @Test
    void convertJSONtoCSV() {
        assertEquals(new File("input.csv"), FileManager.convertJSONtoCSV(new File("input.json"),"output.csv"));
        assertEquals(new File("ES/empty.csv"),FileManager.convertCSVtoJSON(null,"empty.csv"));
        assertThrows(Exception.class, () -> FileManager.convertJSONtoCSV(new File("ES/invalid.json"),"error"));
    }
}