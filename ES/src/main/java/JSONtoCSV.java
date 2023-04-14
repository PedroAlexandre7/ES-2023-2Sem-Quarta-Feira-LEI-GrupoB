import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JSONtoCSV {

    public static void main(String[] args) {
        convertJSONtoCSV(new File("src/main/java/output.json"));
    }

    public static void convertJSONtoCSV(File jsonFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        MappingIterator<Horario> dataIterator;
        List<Horario> dataList;
        try {
            dataIterator = objectMapper.readerFor(Horario.class).readValues(jsonFile);
            dataList = dataIterator.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Horario.class).withHeader();
        String csv;
        try {
            csv = csvMapper.writer(csvSchema).writeValueAsString(dataList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        File csvFile = new File("output.csv");
        try (PrintWriter writer = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
            writer.print(csv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
