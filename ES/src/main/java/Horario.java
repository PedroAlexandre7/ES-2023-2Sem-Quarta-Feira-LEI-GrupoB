import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;


public class Horario {
    private List<Aula> aulas;

    public Horario(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
    }


    /*public void lerCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(String.valueOf(';'));
                Curso curso = campos[0];
                UnidadeCurricular uc = campos[1];
                Turno turno = campos[2];
                Turma turma = campos[3];
                LocalTime horaInicio = campos[4];
                LocalTime horaFim = campos[5];
                Sala sala = campos[6];
                String data = campos[7];
                Aula aula = new Aula(curso, uc, turno, turma, horaInicio, horaFim, sala, data);
                adicionarAula(aula);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo CSV: " + e.getMessage());
        }
    }

     */


}

