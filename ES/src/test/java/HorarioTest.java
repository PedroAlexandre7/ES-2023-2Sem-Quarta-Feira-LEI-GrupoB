import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.format.DateTimeParseException;

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
        Horario horario = new Horario();
        assertDoesNotThrow(() -> { horario.lerCSV(new File("").getAbsolutePath()+ File.separator + "invalid.csv");});
        System.out.println(horario.getAulas());
    }

    @Test
    void lerJSON() {
    }
}