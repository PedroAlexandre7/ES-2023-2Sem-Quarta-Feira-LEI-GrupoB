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
         testValidSchedule();
        Horario horario2 = new Horario();
        assertDoesNotThrow(() -> horario2.lerCSV(new File("").getAbsolutePath() + File.separator + "empty.csv"));
        assertEquals(horario2.getAulas().size(), 0);
        Horario horario3 = new Horario();
        assertThrowsExactly(FileNotFoundException.class, () -> horario3.lerCSV(new File("").getAbsolutePath() + File.separator + "invalid.csv"));
    }

    private static void testValidSchedule() {
        Horario horario1 = new Horario();
        assertDoesNotThrow(() -> horario1.lerCSV(new File("").getAbsolutePath() + File.separator + "validtest.csv"));
        Aula aulalinha8 = horario1.getAulas().get(6);
        Aula aulalinha9 = horario1.getAulas().get(7);
        System.out.println(aulalinha8.horaFim().format(FileManager.FORMATTER));
        assertAll(() -> assertEquals("ME",aulalinha8.cursos().get(0)), () -> assertEquals("LEI",aulalinha8.cursos().get(1)),
                () -> assertEquals("LIGE",aulalinha8.cursos().get(2)), () -> assertEquals("Teoria dos Jogos e dos Contratos",aulalinha8.uc()),
                () -> assertEquals("01789TP01",aulalinha8.turno().nome()), () -> assertEquals("MEA1",aulalinha8.turmas().get(0)),
                () -> assertEquals("MEA2",aulalinha8.turmas().get(1)), () -> assertEquals(30, aulalinha8.turno().numInscritos()),
                () -> assertEquals("Seg",aulalinha8.diaDaSemana()), () -> assertEquals("13:00:00", aulalinha8.horaInicio().format(FileManager.FORMATTER)),
                () -> assertEquals("14:30:00",aulalinha8.horaFim().format(FileManager.FORMATTER)), () -> assertEquals("28/11/2022", aulalinha8.data().format(FileManager.FORMATTER)),
                () -> assertEquals("AA2.25",aulalinha8.sala().nome()), () -> assertEquals(34,aulalinha8.sala().lotacao()));

    }

    @Test
    void lerJSON() {
    }
}