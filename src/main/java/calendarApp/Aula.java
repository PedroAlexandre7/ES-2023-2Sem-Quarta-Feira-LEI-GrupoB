package calendarApp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Aula(List<String> cursos, String uc, Turno turno, List<String> turmas, String diaDaSemana, LocalTime horaInicio, LocalTime horaFim, Sala sala,
                   LocalDate data) {

    @Override
    public String toString() {
        return "Cursos: " + cursos + "\nUC: " + uc + "\ncalendarApp.Turno: " + turno + "\nTurmas: " + turmas + "\nDiaDaSemana: " + diaDaSemana + "\nHoraInicio: " + horaInicio
                + "\nHoraFim: " + horaFim + "\ncalendarApp.Sala: " + sala + "\nData: " + data;
    }

}
