import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class CalendarFromURITest {

    @Test
    void getCalendar() throws Exception {
        String uri_https = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_webcal = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_wrongprotocol = "www://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rbtss@iscte.pt&password=4k1kG5WcwTswvp4dIyvrmhqbx09X9ZedVSIAH3jHhBxVZSPZwK9w3RaJ4DwTMPkZte5iYAfY19bVr85lUROyobLvFIsDo479RPM2rp7sPjhnQBKQL9LaDPJKJ4Os9OgA";
        String uri_invalid = "abcdef";
        assertNotNull(CalendarFromURI.getCalendar(uri_https));
        assertNotNull(CalendarFromURI.getCalendar(uri_webcal));
        assertThrowsExactly(MalformedURLException.class, () ->CalendarFromURI.getCalendar(uri_wrongprotocol));
        assertThrowsExactly(IllegalArgumentException.class, () ->CalendarFromURI.getCalendar(uri_invalid), "URI is not absolute");
    }

}