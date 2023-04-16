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

    public void lerCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); //serve para descartar a primeira linha
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";", -1);
                System.out.println(linha);
                for (String part : campos) {
                    System.out.println(part);
                }
                String cursos = campos[0];

                String uc = campos[1];

                String turma = campos[3];

                Turno turno = new Turno(Integer.parseInt(campos[4]),campos[2]);

                LocalTime horaInicio = LocalTime.parse(campos[6]);

                LocalTime horaFim = LocalTime.parse(campos[7]);
                LocalDate data;
                 if(!campos[8].equals("")){
                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                     data = LocalDate.parse(campos[8], formatter);
                 }
                 else {
                     data = null;
                 }

                Sala sala;
                if(!campos[9].equals("") && !campos[10].equals(""))
                     sala = new Sala(campos[9],Integer.parseInt(campos[10]));
                else if(!campos[9].equals(""))
                     sala = new Sala(campos[9], 0);
                else if(!campos[10].equals(""))
                    sala = new Sala("", Integer.parseInt(campos[10]));
                else
                    sala = new Sala("",0);

                Aula aula = new Aula(cursos, uc, turno, turma, horaInicio, horaFim, sala, data);
                adicionarAula(aula);
                System.out.println(aula);
                i++;
                System.out.println(i);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Horario h = new Horario();
        h.lerCSV("C:/Users/tiago/IdeaProjects/LEI4-GrupoB/input.csv");
    }

}
