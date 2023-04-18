import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.opencsv.CSVWriter;

public class GravarHorario {

    // Esta função recebe um horário como parâmetro e guarda em formato CSV
    public void gravaEmCSV(Horario h, String caminhoDeOutput) {
        try {
            File csvFile = new File(caminhoDeOutput);
            CsvMapper csvMapper = new CsvMapper();
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        } catch (IOException e) {
            System.err.println("gravaEmCSV(h,caminhoDeOutput): Erro ao escrever no ficheiro");
        }
    }

}
