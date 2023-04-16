import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Horario {
    private List<Aula> aulas;
    private List<Curso> cursos = new ArrayList<>();
    private List<Turma> turmas = new ArrayList<>();

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

    private Curso[] criarCursos(String campo) {
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
    }

    private Turma[] criarTurmas(String campo) {
        String[] turmas = campo.split(", "); //TODO VERIFICAR SE FUNCIONA
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
    }

    public void lerCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); //serve para descartar a primeira linha
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                String[] campos = linha.split(String.valueOf(';'));
                Curso[] cursos = criarCursos(campos[0]);
                UnidadeCurricular uc = new UnidadeCurricular(campos[1]);
                Turno turno = new Turno(Integer.parseInt(campos[4]),campos[2]);
                Turma[] turma = criarTurmas(campos[3]);
                LocalTime horaInicio = campos[4];
                LocalTime horaFim = campos[5];
                Sala sala = campos[6];
                String data = campos[7];
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

