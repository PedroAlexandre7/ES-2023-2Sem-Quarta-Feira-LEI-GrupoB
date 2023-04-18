import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileConverterTest {

    @Test
    void convertCSVtoJSON() {
        assertEquals(new File("input.csv"), FileConverter.convertCSVtoJSON(new File("input.csv"),"output.json"));
    }

    @Test
    void convertJSONtoCSV() {
        assertEquals(new File("input.csv"), FileConverter.convertJSONtoCSV(new File("input.json"),"output.csv"));
    }
}