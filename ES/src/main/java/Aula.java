import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Aula {

    private LocalDate data;
    private LocalTime horaFim;
    private LocalTime horaInicio;
    private Sala sala;
    private String turmas;
    private Turno turno;
    private List<String> ucs;
    private List<String> cursos;
    private String diaSemana;

    public Aula(List<String> cursos, List<String> ucs, Turno turno, String turmas, LocalTime horaInicio, LocalTime horaFim,
                Sala sala, LocalDate data){
        this.cursos=cursos;
        this.ucs=ucs;
        this.turno = turno;
        this.turmas = turmas;
        this.horaInicio=horaInicio;
        this.horaFim=horaFim;
        this.sala=sala;
        this.data=data;
    }

    @Override
    public String toString(){
        return "Cursos: " + cursos + "\nUC: " + ucs + "\nTurno: " + turno + "\nTurmas: "+ turmas + "\nHoraInicio: " + horaInicio
                + "\nHoraFim: "+ horaFim + "\nSala: " + sala + "\nData: " + data;
    }

    public static String getDiaDaSemana(LocalDate data){
        DayOfWeek day = data.getDayOfWeek();
        return day.name();
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public Sala getSala() {
        return sala;
    }

    public String getTurmas() {
        return turmas;
    }

    public Turno getTurno() {
        return turno;
    }

    public List<String> getUcs() {
        return ucs;
    }

    public List<String> getCursos() {
        return cursos;
    }

}
