import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Temp {
    static public void gravaEmCSV(Horario h, String caminhoDeOutput) {
        try {
            File csvFile = new File(caminhoDeOutput);
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss");
            for (Aula a : h.getAulas()) {
                String[] rowData = { a.cursos().toString(), a.uc(), a.turno().nome(), a.turmas().toString(),
                        Integer.toString(a.turno().numInscritos()), a.data().getDayOfWeek().toString(),
                        a.horaInicio().format(FORMATTER), a.horaFim().format(FORMATTER), a.data().format(FORMATTER),
                        a.sala().nome(),
                        Integer.toString(a.sala().lotacao()) };
                writer.writeNext(rowData);
            }
        } catch (IOException e) {
            System.err.println("gravaEmCSV(h,caminhoDeOutput): Erro ao escrever no ficheiro");
        }
    }
}
