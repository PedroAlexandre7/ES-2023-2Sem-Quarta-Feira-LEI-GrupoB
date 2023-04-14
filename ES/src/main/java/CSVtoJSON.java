import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVtoJSON {
    public static void main(String[] args) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(';').setUseHeader(true).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File inputFile = new File("src/main/java/input.csv");
        File outputFile = new File("output.json");
        try {
            List<Object> list = csvMapper.readerFor(Map.class)
                    .with(csvSchema)
                    .readValues(inputFile)
                    .readAll();
            objectMapper.writeValue(outputFile, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



