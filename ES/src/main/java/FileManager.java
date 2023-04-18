import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileManager {

    public static void main(String[] args) {
        convertCSVtoJSON(new File("input.csv"), "output.json");
        convertJSONtoCSV(new File("input.json"), "output.csv");
        convertJSONtoCSV(new File("output.json"), "output2.csv");
    }

    public static File convertCSVtoJSON(File inputFile, String outputFilePath) {

        File jsonFile = new File(outputFilePath);
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(';').setUseHeader(true).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (MappingIterator<Object> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(inputFile)) {
            objectMapper.writeValue(jsonFile, mappingIterator.readAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFile;
    }

    public static File convertJSONtoCSV(File inputFile, String outputFilePath) {
        File csvFile = new File(outputFilePath);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> data = objectMapper.readValue(inputFile, new TypeReference<>() {
            });
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] headers = data.get(0).keySet().toArray(new String[0]);
            writer.writeNext(headers);
            for (Map<String, String> row : data)
                writer.writeNext(row.values().toArray(new String[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFile;
    }

    static public void gravaEmCSV(Horario h, String caminhoDeOutput) {
        try {
            File csvFile = new File(caminhoDeOutput);
            //CsvMapper csvMapper = new CsvMapper();//comentei tal como tinhas feito Leo
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

    static public void saveInJSON(Horario h, String outputFilePath) {
        try {
            File jsonFile = new File(outputFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(jsonFile, h);
        } catch (IOException e) {
            System.err.println("saveInJSON(h, outputFilePath): Erro ao escrever no ficheiro");
        }
    }


}
