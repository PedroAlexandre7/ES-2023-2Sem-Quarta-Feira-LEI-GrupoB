import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CSVtoJSON {
    public static void main(String[] args) {
        convert(';', new File("src/main/java/input.csv"));
    }

    public static void convert(char separator, File csvFile) {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(separator).setUseHeader(true).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File jsonFile = new File("output.json");

        try (MappingIterator<Object> map = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile)) {
            objectMapper.writeValue(jsonFile, map.readAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







