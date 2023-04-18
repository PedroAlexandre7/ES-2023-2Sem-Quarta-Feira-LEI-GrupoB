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
    void lerCSV() {
        testValidSchedule("validtest.csv");
        testInvalidShedule("invalid.csv");
        testEmptySchedule("empty.csv");

    }

    @Test
    void lerJSON() {
        testValidSchedule("validtest.json");
        testInvalidShedule("invalid.json");
        testEmptySchedule("empty.json");

    }

    private static void testValidSchedule(String filename) {
        Horario horario = new Horario();
        if (filename.endsWith(".csv"))
            assertDoesNotThrow(() -> horario.lerCSV(new File("").getAbsolutePath() + File.separator + filename));
        else
            assertDoesNotThrow(() -> horario.lerJSON(new File("").getAbsolutePath() + File.separator + filename));
        Aula aulalinha8 = horario.getAulas().get(6);
        Aula aulalinha9 = horario.getAulas().get(7);
        assertAll(() -> assertEquals("ME", aulalinha8.cursos().get(0)), () -> assertEquals("LEI", aulalinha8.cursos().get(1)),
                () -> assertEquals("LIGE", aulalinha8.cursos().get(2)), () -> assertEquals("Teoria dos Jogos e dos Contratos", aulalinha8.uc()),
                () -> assertEquals("01789TP01", aulalinha8.turno().nome()), () -> assertEquals("MEA1", aulalinha8.turmas().get(0)),
                () -> assertEquals("MEA2", aulalinha8.turmas().get(1)), () -> assertEquals(30, aulalinha8.turno().numInscritos()),
                () -> assertEquals("Seg", aulalinha8.diaDaSemana()), () -> assertEquals("13:00:00", aulalinha8.horaInicio().format(FileManager.TIME_FORMATTER)),
                () -> assertEquals("14:30:00", aulalinha8.horaFim().format(FileManager.TIME_FORMATTER)), () -> assertEquals("28/11/2022", aulalinha8.data().format(FileManager.DATE_FORMATTER)),
                () -> assertEquals("AA2.25", aulalinha8.sala().nome()), () -> assertEquals(34, aulalinha8.sala().lotacao()));
        assertAll(() -> assertEquals("", aulalinha9.sala().nome()), () -> assertEquals(0, aulalinha9.sala().lotacao()));

    }

    private static void testInvalidShedule(String filename) {
        Horario horario = new Horario();
        if (filename.endsWith(".csv")) {
            assertThrowsExactly(FileNotFoundException.class, () -> horario.lerCSV(new File("").getAbsolutePath() + File.separator + ""));
            assertThrows(Exception.class, () -> horario.lerCSV(new File("").getAbsolutePath() + File.separator + filename));
        } else {
            assertThrowsExactly(FileNotFoundException.class, () -> horario.lerJSON(new File("").getAbsolutePath() + File.separator + ""));
            assertThrows(Exception.class, () -> horario.lerJSON(new File("").getAbsolutePath() + File.separator + filename));
        }
    }

    private static void testEmptySchedule(String filename) {
        Horario horario = new Horario();
        if (filename.endsWith(".csv"))
            assertDoesNotThrow(() -> horario.lerCSV(new File("").getAbsolutePath() + File.separator + filename));
        else
            assertDoesNotThrow(() -> horario.lerCSV(new File("").getAbsolutePath() + File.separator + filename));
        assertEquals(horario.getAulas().size(), 0);
    }
}