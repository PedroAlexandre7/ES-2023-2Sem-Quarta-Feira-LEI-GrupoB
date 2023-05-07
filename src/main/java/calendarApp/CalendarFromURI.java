package calendarApp;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CalendarFromURI {

    private CalendarFromURI() {
    }

    /**
     *
     * @param uri o uri com a página para obter o calendário. Pode ter protocolo webcal ou https.
     * @return O calendário obtido do uri fornecido.
     * @throws URISyntaxException Se a hiperligação tiver o protocolo errado ou outro erro estrutural.
     * @throws IOException Se não foi possível aceder à hiperligação fornecida.
     * @throws ParserException Se a hiperligação corresponder a uma página não relacionada com calendários.
     */

    public static Calendar getCalendar(String uri) throws URISyntaxException, IOException, ParserException, IllegalArgumentException {
        if (uri.startsWith("webcal"))
            uri = uri.replaceFirst("webcal", "https");
        URL url = new URI(uri).toURL();
        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache");
        CalendarBuilder calendarBuilder = new CalendarBuilder();
        return calendarBuilder.build(url.openStream());
    }

    /**
     *
     * @param calendar o objeto do tipo Calendar que se pretende converter
     * Através da propriedade "DESCRIPTION" de cada registo do calendário do Fénix, obtém-se a informação para
     * obter um objeto do tipo Aula que será, posteriormente adicionada ao horário a ser devolvida pelo método.
     * Existem certos atributos correspondentes a esta aula que não estão presentes no calendário do Fénix,
     * portanto o objeto Aula terá "cursos" vazio, "turno" com "numInscritos" a 0, "turmas" vazio, e a "sala"
     * com "lotacao" a 0.
     * @return horário convertido
     */
    public static Horario toHorario(Calendar calendar) {
        Horario horario = new Horario();
        for (CalendarComponent calendarComponent : calendar.getComponents()) {
            Object[] properties = calendarComponent.getProperties().toArray();
            String[] descriptionProperties = properties[5].toString().split("\\\\n");
            String uc = Pattern.compile("Unidade de execução: ", Pattern.CANON_EQ).matcher(descriptionProperties[1]).replaceFirst("");
            Turno turno;
            if (descriptionProperties[3].contains("Turno: "))
                turno = new Turno(descriptionProperties[3].replaceFirst("Turno: ", ""), 0);
            else
                turno = new Turno(Pattern.compile("Descrição: ", Pattern.CANON_EQ).matcher(descriptionProperties[2]).replaceFirst(""), 0);
            String[] dataHoraInicio = Pattern.compile("Início: ", Pattern.CANON_EQ).matcher(descriptionProperties[4]).replaceFirst("").split(" ");
            String[] dataHoraFim = descriptionProperties[5].replaceFirst("Fim: ", "").split(" ");
            LocalDate data = LocalDate.parse(dataHoraInicio[0]);
            LocalTime horaInicio = LocalTime.parse(dataHoraInicio[1]);
            LocalTime horaFim = LocalTime.parse(dataHoraFim[1]);
            String salaNome = properties[6].toString().replaceFirst("LOCATION:", "").replaceAll("[\\\\\n\r]", "");
            String diaDaSemana = translateWeekDayEnum(data.getDayOfWeek());
            horario.adicionarAula(new Aula(new ArrayList<>(), uc, turno, new ArrayList<>(), diaDaSemana, horaInicio, horaFim, new Sala(salaNome, 0), data));
        }

        return horario;
    }

    /**
     *
     * @param weekDay o dia da semana do enumerado DayOfWeek
     * @return uma “String” traduzida correspondente ao weekDay
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
