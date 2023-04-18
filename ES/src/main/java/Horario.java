import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Horario {
    public List<Aula> getAulas() {
        return aulas;
    }

    private List<Aula> aulas;

    public Horario(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public Horario() {
        this.aulas = new ArrayList<>();
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

                List<String> ucs = Arrays.stream(campos[1].split(", ")).toList();

                Turno turno = new Turno(Integer.parseInt(campos[4]), campos[2]);
                LocalDate data = campos[8].isEmpty() ? null : LocalDate.parse(campos[8], DateTimeFormatter.ofPattern("dd/MM/yyyy")); // TODO Decidir se aula de horário sem data é para descartar ou não
                String nomeSala = campos[9].isEmpty() ? "" : campos[9];
                int lotacaoSala = campos[10].isEmpty() ? 0 : Integer.parseInt(campos[10]);
                Sala sala = new Sala(nomeSala, lotacaoSala);

                Aula aula = new Aula(cursos, ucs, turno, campos[3], LocalTime.parse(campos[6]), LocalTime.parse(campos[7]), sala, data);
                adicionarAula(aula);

            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Horario h = new Horario();
        h.lerCSV("input.csv");
        System.out.println(h.getAulas());
    }

}
