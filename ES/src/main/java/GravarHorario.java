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
            for (Aula a : h.getAulas()) {
                // TODO alterar os toStrings dos GETS
                // TODO Desculpa ter metido isto em comentário mas a estrutura do horário mudou
                // TODO btw acho q tem tá a ler isto é bue giro
//                String[] rowData = { a.getCursos().toString(), a.getUcs().toString(), a.getTurno().getNome(),
//                        a.getTurmas(),
//                        Integer.toString(a.getTurno().getNumInscritos()), a.getData().getDayOfWeek().toString(),
//                        a.getHoraInicio().toString(), a.getHoraFim().toString(), a.getData().toString(),
//                        a.getSala().getNome(), Integer.toString(a.getSala().getLotacao()) };
//                writer.writeNext(rowData);
            }
        } catch (IOException e) {
            System.err.println("gravaEmCSV(h,caminhoDeOutput): Erro ao escrever no ficheiro");
        }
    }

}
