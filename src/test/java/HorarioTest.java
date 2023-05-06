import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {


    @Test
    void getAulas() {
        Horario horario = new Horario();
        horario.adicionarAula(new Aula(null,null,null,null,null,null,null,null,null));
        assertNotNull(horario.getAulas());
    }

    @Test
    void adicionarAula() {
        Horario horario = new Horario();
        horario.adicionarAula(new Aula(null,null,null,null,null,null,null,null,null));
        assertEquals(1, horario.getAulas().size());
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
            assertDoesNotThrow(() -> horario.lerCSV(new File(new File("").getAbsolutePath() + File.separator + filename)));
        else
            assertDoesNotThrow(() -> horario.lerJSON(new File(new File("").getAbsolutePath() + File.separator + filename)));
        Aula aulanumero8 = horario.getAulas().get(6);
        Aula aulanumero9 = horario.getAulas().get(7);
        assertAll(() -> assertEquals("ME", aulanumero8.cursos().get(0)), () -> assertEquals("LEI", aulanumero8.cursos().get(1)),
                () -> assertEquals("LIGE", aulanumero8.cursos().get(2)), () -> assertEquals("Teoria dos Jogos e dos Contratos", aulanumero8.uc()),
                () -> assertEquals("01789TP01", aulanumero8.turno().nome()), () -> assertEquals("MEA1", aulanumero8.turmas().get(0)),
                () -> assertEquals("MEA2", aulanumero8.turmas().get(1)), () -> assertEquals(30, aulanumero8.turno().numInscritos()),
                () -> assertEquals("Seg", aulanumero8.diaDaSemana()), () -> assertEquals("13:00:00", aulanumero8.horaInicio().format(FileManager.TIME_FORMATTER)),
                () -> assertEquals("14:30:00", aulanumero8.horaFim().format(FileManager.TIME_FORMATTER)), () -> assertEquals("28/11/2022", aulanumero8.data().format(FileManager.DATE_FORMATTER)),
                () -> assertEquals("AA2.25", aulanumero8.sala().nome()), () -> assertEquals(34, aulanumero8.sala().lotacao()));
        assertAll(() -> assertEquals("", aulanumero9.sala().nome()), () -> assertEquals(0, aulanumero9.sala().lotacao()));

    }

    private static void testInvalidShedule(String filename) {
        Horario horario = new Horario();
        if (filename.endsWith(".csv"))
            assertThrows(Exception.class, () -> horario.lerCSV(new File(new File("").getAbsolutePath() + File.separator + filename)));
         else
            assertThrows(Exception.class, () -> horario.lerJSON(new File(new File("").getAbsolutePath() + File.separator + filename)));

    }

    private static void testEmptySchedule(String filename) {
        Horario horario = new Horario();
        if (filename.endsWith(".csv"))
            assertDoesNotThrow(() -> horario.lerCSV(new File( new File("").getAbsolutePath() + File.separator + filename)));
        else
            assertDoesNotThrow(() -> horario.lerCSV(new File( new File("").getAbsolutePath() + File.separator + filename)));
        assertEquals(0, horario.getAulas().size());
    }
}