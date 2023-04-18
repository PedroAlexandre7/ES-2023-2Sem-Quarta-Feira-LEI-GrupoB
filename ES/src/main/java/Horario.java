import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Horario {

    private List<Aula> aulas;

    public Horario(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public Horario() {
        this.aulas = new ArrayList<>();
    }

    public static void main(String[] args) {
        Horario h = new Horario();
        //h.lerCSV("input.csv");

        h.lerJSON("input.json");
        System.out.println(h.getAulas());
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
    }

    public void lerCSV(String caminhoArquivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            br.readLine(); //serve para descartar a primeira linha
            String linha;
            while ((linha = br.readLine()) != null) {

                String[] campos = linha.split(";", -1);

                List<String> cursos = Arrays.stream(campos[0].split(", ")).toList();
                Turno turno = new Turno(campos[2], Integer.parseInt(campos[4]));
                List<String> turmas = Arrays.stream(campos[3].split(", ")).toList();
                LocalDate data = campos[8].isEmpty() ? null : LocalDate.parse(campos[8], DateTimeFormatter.ofPattern("dd/MM/yyyy")); // TODO Decidir se aula de horário sem data é para descartar ou não
                String nomeSala = campos[9].isEmpty() ? "" : campos[9];
                int lotacaoSala = campos[10].isEmpty() ? 0 : Integer.parseInt(campos[10]);
                Sala sala = new Sala(nomeSala, lotacaoSala);

                Aula aula = new Aula(cursos, campos[1], turno, turmas, LocalTime.parse(campos[6]), LocalTime.parse(campos[7]), sala, data);
                adicionarAula(aula);

            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo CSV: " + e.getMessage());
        }
    }

    public void lerJSON(String caminhoArquivo) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File inputFile = new File(caminhoArquivo);
            List<Map<String, String>> data = objectMapper.readValue(inputFile, new TypeReference<>() {});
            for (Map<String, String> row : data) {
                List<String> cursos = Arrays.asList(row.get("Curso").split(", "));
                Turno turno = new Turno(row.get("Turno"), Integer.parseInt(row.get("Inscritos no turno")));
                List<String> turmas = Arrays.asList(row.get("Turma").split(", "));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataAula = row.get("Data da aula").isEmpty() ? LocalDate.now() : LocalDate.parse(row.get("Data da aula"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String nomeSala = row.get("Sala atribuída à aula").isEmpty() ? "" : row.get("Sala atribuída à aula");
                int lotacaoSala = row.get("Lotação da sala").isEmpty() ? 0 : Integer.parseInt(row.get("Lotação da sala"));
                Sala sala = new Sala(nomeSala, lotacaoSala);
                Aula aula = new Aula(cursos, row.get("Unidade Curricular"), turno, turmas, LocalTime.parse(row.get("Hora início da aula")), LocalTime.parse(row.get("Hora fim da aula")), sala, dataAula);
                adicionarAula(aula);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON: " + e.getMessage());
        }
    }


}
