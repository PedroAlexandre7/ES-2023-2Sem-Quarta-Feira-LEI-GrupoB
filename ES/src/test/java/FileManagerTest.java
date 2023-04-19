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
        List<String> expectedLines;
        List<String> actualLines;
        try {
            expectedLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.json"), StandardCharsets.UTF_8);
            actualLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\output.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedLines, actualLines);
        assertThrows(Exception.class, () -> FileManager.convertCSVtoJSON(null,"EmptyOutput.json"));
    }

    @Test
    void convertJSONtoCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.json", StandardCharsets.UTF_8))) {
            FileManager.convertJSONtoCSV(new File("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.json"),"output.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expectedLines;
        List<String> actualLines;
        try {
            expectedLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\validtest.csv"), StandardCharsets.UTF_8);
            actualLines = Files.readAllLines(Path.of("C:\\Users\\aguas\\Desktop\\ISCTE\\3ºAno\\2Semestre\\ES\\LEI4-GrupoB\\ES\\output.csv"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedLines, actualLines);
        assertThrows(Exception.class, () -> FileManager.convertJSONtoCSV(null,"error"));
    }
}