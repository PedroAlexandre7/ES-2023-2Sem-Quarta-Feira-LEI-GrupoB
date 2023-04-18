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

    public static void main(String[] args) {
        Horario horario = new Horario();
//        horario.lerCSV("ES/input.csv");
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss a");
        System.out.println(horario.getAulas().get(1).horaInicio().format(FORMATTER));
//        System.out.println(horario.getAulas().get(1).data().format(FORMATTER));
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


    static public void saveInJSON(Horario h, String outputFilePath) {
        try {

//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put();
//            String json = jsonObject.toString(2);
//            Files.write(Paths.get("output.json"), json.getBytes());
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
                aulaData.put("Data da aula", a.data().format(DATE_FORMATTER));
//                aulaData.put()

                data.add(aulaData);
            }

            File jsonFile = new File(outputFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonString = objectMapper.writeValueAsString("");
            objectMapper.writeValue(jsonFile, data);
        } catch (IOException e) {
            System.err.println("saveInJSON(h, outputFilePath): Erro ao escrever no ficheiro");
        }
    }

    private static String listToString(List<String> list) {
        if (list.size() == 0)
            return "";
        String str = list.toString();
        return str.substring(1, str.length() - 1);
    }

}
