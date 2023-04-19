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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileManager {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        Horario horario = new Horario();
        horario.lerCSV(new File("ES/input.csv"));
        saveInJSON(horario, "output.json");
    }

    public static File convertCSVtoJSON(File inputFile, String outputFilePath) {

        File jsonFile = new File(outputFilePath);
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(';').setUseHeader(true).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (MappingIterator<Object> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
                .readValues(inputFile)) {
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
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ',', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] headers = data.get(0).keySet().toArray(new String[0]);
            writer.writeNext(headers);
            for (Map<String, String> row : data)
                writer.writeNext(row.values().toArray(new String[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFile;
    }

    static public void saveInJSON(Horario h, String outputFilePath) {
        List<Map<String, String>> data = new ArrayList<>();

        for (Aula a : h.getAulas()) {
            HashMap<String, String> aulaData = new HashMap<>();
            aulaData.put("Curso", listToString(a.cursos()));
            aulaData.put("Unidade Curricular", a.uc());
            aulaData.put("Turno", a.turno().nome());
            aulaData.put("Turma", listToString(a.turmas()));
            aulaData.put("Inscritos no turno", Integer.toString(a.turno().numInscritos()));
            aulaData.put("Dia da semana", a.diaDaSemana());
            aulaData.put("Hora início da aula", a.horaInicio().format(TIME_FORMATTER));
            aulaData.put("Hora fim da aula", a.horaFim().format(TIME_FORMATTER));
            if (a.data() != null)
                aulaData.put("Data da aula", a.data().format(DATE_FORMATTER));
            if (!a.sala().nome().isBlank())
                aulaData.put("Sala atribuída à aula", a.sala().nome());
            if (a.sala().lotacao() != 0)
                aulaData.put("Lotação da sala", Integer.toString(a.sala().lotacao()));
            data.add(aulaData);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File jsonFile = new File(outputFilePath);

        try {
            objectMapper.writeValue(jsonFile, data);
        } catch (IOException e) {
            System.err.println("saveInJSON(h, outputFilePath): Erro ao escrever no ficheiro");
        }
    }

    static public void saveInCSV(Horario h, String caminhoDeOutput) {
        try {
            File csvFile = new File(caminhoDeOutput); // Cria um ficheiro CSV
            // Cria um género de printwriter para escrever
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            for (Aula a : h.getAulas()) {
                String[] rowData = { listToString(a.cursos()), a.uc(), a.turno().nome(), listToString(a.turmas()),
                        Integer.toString(a.turno().numInscritos()), a.data().getDayOfWeek().toString(),
                        a.horaInicio().format(TIME_FORMATTER), a.horaFim().format(TIME_FORMATTER),
                        a.data().format(DATE_FORMATTER),
                        a.sala().nome(),
                        Integer.toString(a.sala().lotacao()) };
                writer.writeNext(rowData);
            }
        } catch (IOException e) {
            System.err.println("gravaEmCSV(h,caminhoDeOutput): Erro ao escrever no ficheiro");
        }
    }

    private static String listToString(List<String> list) {
        if (list.size() == 0)
            return "";
        String str = list.toString();
        return str.substring(1, str.length() - 1);
    }

}
