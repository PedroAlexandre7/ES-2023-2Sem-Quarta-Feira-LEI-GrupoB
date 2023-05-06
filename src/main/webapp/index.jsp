<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.*"%>
<!DOCTYPE html>
<html>
<head>
   <title>Minha Agenda</title>
   <meta charset="UTF-8">
   <link href="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.css" rel="stylesheet" />
</head>
<body>
   <h1>Minha Agenda</h1>
   <input type="file" id="fileInput">
   <button onclick="processFile()">Upload and Process File</button>
   <div id="calendar" style=" height:500px";></div>
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.js"></script>
   <script>
        function processFile() {
          const fileInput = document.getElementById("fileInput");
          const selectedFile = fileInput.files[0];
          const fileReader = new FileReader();
          fileReader.onload = function(event) {
            const fileContents = event.target.result;
            const horario = new Horario();
            horario.lerCSV(fileContents);
            document.addEventListener('DOMContentLoaded', function() {
                       var calendarEl = document.getElementById('calendar');
                       var calendar = new FullCalendar.Calendar(calendarEl, {
                           initialView: 'dayGridMonth',
                           headerToolbar: {
                                left: 'prev,next today',
                                center: 'title',
                                right: 'dayGridMonth,timeGridWeek,timeGridDay'
                           },
                           events: [
                                <% for (Aula aula : horario.getAulas()) { %>
                                    {
                                        title: '<%= aula.uc%>',
                                        startTime: '<%= aula.horaInicio%>',
                                        endTime: '<%= aula.horaFim%>',
                                        daysOfWeek: [<%= aula.diaDaSemana%>],
                                        location: '<%= aula.sala%>'
                                    },
                                <% } %>
                           ]
                       });
                       calendar.render();
                   });
          };
        }
      </script>
</body>
</html>
