import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JSONtoCSV {

    public static void main(String[] args) {
        /*
        ObjectMapper objectMapper = new ObjectMapper();
        MappingIterator<Horario> dataIterator = objectMapper.readerFor(Horario.class)
                .readValues(new File("data.json"));
        List<Horario> dataList = dataIterator.readAll();

        // convert objects to CSV
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Horario.class).withHeader();
        String csv = csvMapper.writer(csvSchema)
                .writeValueAsString(dataList);

        // write CSV to file
        File csvFile = new File("data.csv");
        FileUtils.writeStringToFile(csvFile, csv, "UTF-8");

         */
    }
}
