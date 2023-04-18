import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {

    @Test
    void getAulas() {
    }

    @Test
    void adicionarAula() {
    }

    @Test
    void removerAula() {
    }

    @Test
    void lerCSV() {
        Horario horario1 = new Horario();
        assertDoesNotThrow(() -> horario1.lerCSV(new File("").getAbsolutePath() + File.separator + "valid.csv"));
        assertNotEquals(horario1.getAulas().size(), 0 );
        Horario horario2 = new Horario();
        assertDoesNotThrow(() -> horario1.lerCSV(new File("").getAbsolutePath() + File.separator + "empty.csv"));
        assertEquals(horario2.getAulas().size(), 0);
        Horario horario3 = new Horario();
        assertThrowsExactly(FileNotFoundException.class, () -> horario3.lerCSV(new File("").getAbsolutePath() + File.separator + "invalid.csv"));
    }

    @Test
    void lerJSON() {
    }
}