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

public class FileConverter {

    public static void main(String[] args) {
        convertCSVtoJSON(new File("input.csv"), "output.json", ';');
        convertJSONtoCSV(new File("input.json"), "output.csv", ';');
        convertJSONtoCSV(new File("output.json"), "output2.csv", ';');
    }

    public static void convertCSVtoJSON(File inputFile, String outputFilePath, char separator) {

        File jsonFile = new File(outputFilePath);
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(separator).setUseHeader(true).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (MappingIterator<Object> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(inputFile)) {
            objectMapper.writeValue(jsonFile, mappingIterator.readAll());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void convertJSONtoCSV(File inputFile, String outputFilePath, char separator) {

        try {
            File csvFile = new File(outputFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> data = objectMapper.readValue(inputFile, new TypeReference<>(){});
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile), separator, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] headers = data.get(0).keySet().toArray(new String[0]);
            writer.writeNext(headers);
            for (Map<String, String> row : data)
                writer.writeNext(row.values().toArray(new String[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public static void convertJSONtoCSV(File jsonFile) { // FIXME Este é o do Afonso
//        ObjectMapper objectMapper = new ObjectMapper();
//        MappingIterator<Horario> dataIterator;
//        List<Horario> dataList;
//        try {
//            dataIterator = objectMapper.readerFor(Horario.class).readValues(jsonFile);
//            dataList = dataIterator.readAll();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(dataList.size());
//        CsvMapper csvMapper = new CsvMapper();
//        CsvSchema csvSchema = csvMapper.schemaFor(Horario.class).withHeader();
//        String csv;
//        try {
//            csv = csvMapper.writer(csvSchema).writeValueAsString(dataList);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        File csvFile = new File("output.csv");
//        try (PrintWriter writer = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
//            writer.print(csv);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
