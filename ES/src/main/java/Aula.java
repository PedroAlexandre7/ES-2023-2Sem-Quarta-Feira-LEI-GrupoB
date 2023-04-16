import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Aula {
    private LocalDate data;
    private LocalTime horaFim;
    private LocalTime horaInicio;
    private Sala sala;
    private String turmas;
    private Turno turno;
    private String uc;
    private String cursos;
    private String diaSemana;

    public Aula(String cursos, String uc, Turno turno, String turmas, LocalTime horaInicio, LocalTime horaFim,
                Sala sala, LocalDate data){
        this.cursos=cursos;
        this.uc=uc;
        this.turno = turno;
        this.turmas = turmas;
        this.horaInicio=horaInicio;
        this.horaFim=horaFim;
        this.sala=sala;
        this.data=data;


    }
    @Override
    public String toString(){
        return "Cursos: " + cursos + "\nUC: " + uc + "\nTurno: " + turno + "\nTurmas: "+ turmas + "\nHoraInicio: " + horaInicio
                + "\nHoraFim: "+ horaFim + "\nSala: " + sala + "\nData: " + data;
    }

    public static String getDiaDaSemana(LocalDate data){
        DayOfWeek day = data.getDayOfWeek();
        return day.name();
    }

}
