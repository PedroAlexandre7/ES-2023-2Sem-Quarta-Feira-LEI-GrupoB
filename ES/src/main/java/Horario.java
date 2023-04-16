import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class Horario {
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

   /* private Curso[] criarCursos(String campo) {
        String cursos[] = campo.split(String.valueOf(','));
        Curso[] cursos1 = new Curso[cursos.length];
        int i = 0;
        for (String nome : cursos) {
            if(!this.cursos.contains(cursos[i])) {
                cursos1[i] = new Curso(nome);
                this.cursos.add(cursos1[i]);
            }
            i++;
        }
        return cursos1;
    }*/

   /* private Turma[] criarTurmas(String campo) {
        String[] turmas = campo.split(", ");
        Turma[] turmas1 = new Turma[turmas.length];
        int i = 0;
        for (String nome : turmas) {
            if (!this.turmas.contains(turmas[i])) {
                turmas1[i] = new Turma(nome);
                this.turmas.add(turmas1[i]);
            }
            i++;
        }
        return turmas1;
    }*/

    public void lerCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); //serve para descartar a primeira linha
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(String.valueOf(';'));
                String cursos = campos[0];

                String uc = campos[1];

                String turma = campos[3];

                Turno turno = new Turno(Integer.parseInt(campos[4]),campos[2]);

                LocalTime horaInicio = LocalTime.parse(campos[6]);

                LocalTime horaFim = LocalTime.parse(campos[7]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(campos[8], formatter);

                Sala sala = new Sala(campos[9],Integer.parseInt(campos[10]));

                Aula aula = new Aula(cursos, uc, turno, turma, horaInicio, horaFim, sala, data);
                adicionarAula(aula);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Horario h = new Horario();
        h.lerCSV("C:/Users/tiago/IdeaProjects/LEI4-GrupoB/horario_exemplo.csv");
    }

}

