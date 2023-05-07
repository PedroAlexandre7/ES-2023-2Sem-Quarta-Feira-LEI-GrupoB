import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class CalendarFromURITest {

    @Test
    void getCalendar() throws Exception {
        String uri_https = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_wrongprotocol = "www://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_incomplete = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4d";
        String uri_invalid1 = "https://www.youtube.com/";
        String uri_invalid2 = "abcdef";
        assertNotNull(CalendarFromURI.getCalendar(uri_https));
        assertNotNull(CalendarFromURI.getCalendar(uri_webcal));
        assertThrowsExactly(MalformedURLException.class, () ->CalendarFromURI.getCalendar(uri_wrongprotocol));
        assertThrowsExactly(IOException.class, () ->CalendarFromURI.getCalendar(uri_incomplete));
        assertThrowsExactly(ParserException.class, () ->CalendarFromURI.getCalendar(uri_invalid1));
        assertThrowsExactly(IllegalArgumentException.class, () ->CalendarFromURI.getCalendar(uri_invalid2), "URI is not absolute");
    }
    @Test
    void CalendarToHorario() throws Exception {
        Calendar calendar = CalendarFromURI.getCalendar("webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=tmpad@iscte.pt&password=AvIiaG2D8H507zDPQ5z2E0GtvRiqqR344nYcmySXrj8DipFXaOHMeA4xiRq5ssS79PQfWuioSeMAY3o4HDo0OBj7x7vUXBYGino1iaKucOhQy3InweQ41BVtztjuoMyx");
        Horario horario = CalendarFromURI.toHorario(calendar) ;
        assertFalse(horario.getAulas().isEmpty());
    }

}