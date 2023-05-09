<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="calendarApp.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <title>Minha Agenda</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <link href="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/webjars/org.webjars/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.js"></script>
</head>
<body>
    <h1>Minha Agenda</h1>
    <%
        String eventData = null;
        Horario horario = null;
        List<Aula> aulas = null;
        if (request != null && request.getMethod().equalsIgnoreCase("POST")) {
            String filePath = request.getParameter("filePath");
            horario = new Horario();
            request.getSession().setAttribute("horario",horario);
            horario.lerCSV(new File(filePath));
            aulas = horario.getAulas();
            if (horario != null && !aulas.isEmpty()) {
                eventData = "[";
                for (Aula aula : horario.getAulas()) {
                    eventData += "{";
                    eventData += "\"title\":\"" + aula.uc() + "\",";
                    eventData += "\"start\":\"" + aula.data() + "T" + aula.horaInicio().toString() + "\",";
                    eventData += "\"end\":\"" + aula.data() + "T" + aula.horaFim().toString() + "\",";
                    eventData += "\"location\":\"" + aula.sala().toString() + "\",";
                    eventData += "},";
                }
                eventData = eventData.substring(0, eventData.length() - 1); //remove a ultima virgula
                eventData += "]";
            }
        }else{
            System.out.println("Erro na transmissão dos paramentros");
        }
    %>

    <div id="calendar"></div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var eventos = <%= eventData %>;
            console.log(eventos);
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                events: eventos,
                headerToolbar: {
                    start: 'prev,next today',
                    center: 'title',
                    end: 'dayGridMonth,timeGridWeek,timeGridDay'
                },
                initialView: 'dayGridMonth',
                slotMinTime: '08:00:00',
                slotMaxTime: '18:00:00',
                height: 'auto',
                eventContent: function(info) {
                    return {
                        html: '<b>' + info.timeText + '</b><br>' + info.event.title + '<br>' + info.event.extendedProps.location,
                        classNames: ['fc-event-main']
                    };
                }
            });
            calendar.render();

        });
    </script>

    <%  int count_sobreposicoes = 0;
        for (int i = 0; i < aulas.size() - 1; i++) {
            for (int j = i + 1; j < aulas.size(); j++) {
                if (horario.doTheyOverlap(aulas.get(i), aulas.get(j))) {
                    count_sobreposicoes++;
                }
            }
        }
    %>
    <button onclick="toggleDivSobreposicoes()">Sobreposições</button>
    <div id="sobreposicoes" style="display:none;"> <%= count_sobreposicoes %> Sobreposições </div>
    <br>
    <script>
        function toggleDivSobreposicoes() {
        var div = document.getElementById("sobreposicoes");
        if (div.style.display === "none") {
            div.style.display = "block";
        } else {
            div.style.display = "none";
        }
    }
    </script>

    <%  boolean sobrelotacoes = horario.checkForOverbooking(); %>
    <button onclick="toggleDivSobrelotacoes()">Existem Sobrelotações?</button>
    <div id="sobrelotacoes" style="display:none;"> <%= sobrelotacoes %> </div>
    <br>
    <script>
        function toggleDivSobrelotacoes() {
        var div = document.getElementById("sobrelotacoes");
        if (div.style.display === "none") {
            div.style.display = "block";
        } else {
            div.style.display = "none";
        }
    }
    </script>

    <% List<String> ucs = horario.getUcs(); %>

    <form method="POST" action="updateCalendar.jsp">
        <% for (int i = 0; i < ucs.size(); i++) { %>
            <input type="checkbox" name="uc" id="uc_<%=i%>" value="<%=ucs.get(i)%>">
        	<label for="uc_<%=i%>"><%=ucs.get(i)%></label>
        	<br />
        <% } %>
        <button type="submit">Update Calendar</button>
    </form>
</body>
</html>
