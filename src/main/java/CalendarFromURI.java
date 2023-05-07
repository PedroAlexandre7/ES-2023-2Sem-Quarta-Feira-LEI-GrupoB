import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CalendarFromURI {

    private CalendarFromURI(){

    }

    /**
     *
     * @param uri
     * @return
     */
    public static Calendar getCalendar(String uri) {

        try {
            URL url = new URI(uri).toURL();
            System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache");
            CalendarBuilder calendarBuilder = new CalendarBuilder();
            return calendarBuilder.build(url.openStream());
        } catch (URISyntaxException | IOException | ParserException e) {
            System.err.println("There was a problem getting the calendar from the URI. " + e.getMessage());
            return null;
        }

    }

    /**
     *
     * @param calendar
     * @return
     */
    public static Horario CalendarToHorario(Calendar calendar) {
        Horario horario = new Horario();
        for (CalendarComponent calendarComponent : calendar.getComponents()) {
            Object[] properties = calendarComponent.getProperties().toArray();
            String[] descriptionProperties = properties[5].toString().split("\\\\n");
            String uc = descriptionProperties[1].replaceFirst("Unidade de execução: ", "");
            Turno turno = new Turno(descriptionProperties[3].replaceFirst("Turno: ", ""), 0);
            String[] dataHoraInicio = descriptionProperties[4].replaceFirst("Início: ", "").split(" ");
            String[] dataHoraFim = descriptionProperties[5].replaceFirst("Fim: ", "").split(" ");
            LocalDate data = LocalDate.parse(dataHoraInicio[0]);
            LocalTime horaInicio = LocalTime.parse(dataHoraInicio[1]);
            LocalTime horaFim = LocalTime.parse(dataHoraFim[1]);
            String salaNome = properties[6].toString().replaceFirst("LOCATION:", "").replaceAll("\\\\", "");
            String diaDaSemana = translateWeekDayEnum(data.getDayOfWeek());
            horario.adicionarAula(new Aula(new ArrayList<>(), uc, turno, new ArrayList<>(), diaDaSemana, horaInicio, horaFim, new Sala(salaNome, 0), data));

        }

        return horario;
    }

    /**
     *
     * @param weekDay
     * @return
     */
    private static String translateWeekDayEnum(DayOfWeek weekDay) {
        return switch (weekDay) {
            case MONDAY -> "Seg";
            case TUESDAY -> "Ter";
            case WEDNESDAY -> "Qua";
            case THURSDAY -> "Qui";
            case FRIDAY -> "Sex";
            case SATURDAY -> "Sab";
            case SUNDAY -> "Dom";
        };
    }

}
