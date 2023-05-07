import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Horario {

    private final List<Aula> aulas;

    public Horario() {
        this.aulas = new ArrayList<>();
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

//    public void removerAula(Aula aula) {
//        aulas.remove(aula);
//    }

    public void lerCSV(File ficheiro) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
            br.readLine(); // serve para descartar a primeira linha
            String linha;
            while ((linha = br.readLine()) != null)
                criarAulaCSV(linha);
        } catch (Exception e) {
            throw new Exception("Erro a ler ficheiro CSV " + ficheiro.getName() + ", " + e.getMessage());
        }
    }

    private void criarAulaCSV(String linha) {
        String[] campos = linha.split(";", -1);
        List<String> cursos = Arrays.stream(campos[0].split(", ")).toList();
        Turno turno = new Turno(campos[2], Integer.parseInt(campos[4]));
        List<String> turmas = Arrays.stream(campos[3].split(", ")).toList();
        LocalDate data = campos[8].isEmpty() ? null : LocalDate.parse(campos[8], DateTimeFormatter.ofPattern("dd/MM/yyyy")); // TODO Decidir se aula de horário sem data é para descartar ou não
        String nomeSala = campos[9].isEmpty() ? "" : campos[9];
        int lotacaoSala = campos[10].isEmpty() ? 0 : Integer.parseInt(campos[10]);
        Sala sala = new Sala(nomeSala, lotacaoSala);
        Aula aula = new Aula(cursos, campos[1], turno, turmas, campos[5], LocalTime.parse(campos[6]), LocalTime.parse(campos[7]), sala, data);
        adicionarAula(aula);
    }

    public void lerJSON(File ficheiro) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, String>> data = objectMapper.readValue(ficheiro, new TypeReference<>() {});
            for (Map<String, String> row : data)
                criarAulaJSON(row);
        } catch (Exception e) {
            throw new Exception("Erro a ler ficheiro JSON " + ficheiro.getName() + ", " + e.getMessage());
        }
    }

    private void criarAulaJSON(Map<String, String> row) {
        List<String> cursos = Arrays.asList(row.get("Curso").split(", "));
        Turno turno = new Turno(row.get("Turno"), Integer.parseInt(row.get("Inscritos no turno")));
        List<String> turmas = Arrays.asList(row.get("Turma").split(", "));
        LocalDate dataAula = row.get("Data da aula").isEmpty() ? null : LocalDate.parse(row.get("Data da aula"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String nomeSala = row.get("Sala atribuída à aula").isEmpty() ? "" : row.get("Sala atribuída à aula");
        int lotacaoSala = row.get("Lotação da sala").isEmpty() ? 0 : Integer.parseInt(row.get("Lotação da sala"));
        Sala sala = new Sala(nomeSala, lotacaoSala);
        Aula aula = new Aula(cursos, row.get("Unidade Curricular"), turno, turmas, row.get("Dia da semana"), LocalTime.parse(row.get("Hora início da aula")), LocalTime.parse(row.get("Hora fim da aula")), sala, dataAula);
        adicionarAula(aula);
    }


    //TODO falta validar se uma começa enquanto a outra está a decorrer
    private void checkForColisions(){
        for(Aula a : aulas){
            for(Aula b : aulas){
                if(!a.equals(b)&& a.diaDaSemana()==b.diaDaSemana() &&a.data()==b.data() && a.sala()==b.sala() && doTheyOverlap(a, b))
                    System.err.println("Foi encontrada uma colisão na aula: " +a+ " com a aula: " +b);
            }
        }
    }

    //retorna true se houver colisões
    private boolean doTheyOverlap(Aula a, Aula b){
        if(a.horaFim().isBefore(b.horaInicio()) || b.horaFim().isBefore(a.horaInicio()) )
            return false;
        return true;

    }

    private void checkForOverbooking(){
        for(Aula a : aulas){
            int totalInscritos = a.turno().numInscritos();
            if(a.sala().lotacao()<totalInscritos){
                System.err.println("Há sobrelotação na aula: " + a);
            }
        }
    }


}
