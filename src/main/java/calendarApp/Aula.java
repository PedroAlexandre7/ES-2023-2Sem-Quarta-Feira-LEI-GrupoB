package calendarApp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Aula(List<String> cursos, String uc, Turno turno, List<String> turmas, String diaDaSemana, LocalTime horaInicio, LocalTime horaFim, Sala sala,
                   LocalDate data) implements Comparable<Aula>{

    @Override
    public int compareTo(Aula other) {
        if(this.data.isAfter(other.data))
            return 1;
        if(this.data.isBefore(other.data))
            return -1;
        if(this.horaInicio.isAfter(other.horaInicio))
            return 1;
        if(this.horaInicio.isBefore(other.horaInicio))
            return -1;
        if(this.horaFim.isAfter(other.horaFim))
            return 1;
        if(this.horaFim.isBefore(other.horaFim))
            return -1;
        return 0;
    }

    @Override
    public List<String> cursos() {
        return cursos;
    }

    @Override
    public String uc() {
        return uc;
    }

    @Override
    public Turno turno() {
        return turno;
    }

    @Override
    public List<String> turmas() {
        return turmas;
    }

    @Override
    public String diaDaSemana() {
        return diaDaSemana;
    }

    @Override
    public LocalTime horaInicio() {
        return horaInicio;
    }

    @Override
    public LocalTime horaFim() {
        return horaFim;
    }

    @Override
    public Sala sala() {
        return sala;
    }

    @Override
    public LocalDate data() {
        return data;
    }

    @Override
    public String toString() {
        return "Cursos: " + cursos + "\nUC: " + uc + "\ncalendarApp.Turno: " + turno + "\nTurmas: " + turmas + "\nDiaDaSemana: " + diaDaSemana + "\nHoraInicio: " + horaInicio
                + "\nHoraFim: " + horaFim + "\ncalendarApp.Sala: " + sala + "\nData: " + data;
    }

}
