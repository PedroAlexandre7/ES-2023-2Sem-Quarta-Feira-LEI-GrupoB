<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="calendarApp.Horario"%>
<%@ page import="calendarApp.Aula"%>
<%@ page import="calendarApp.Turno"%>
<%@ page import="calendarApp.Sala"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
<head>
   <title>Minha Agenda</title>
   <meta charset="UTF-8">
   <link href="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.css" rel="stylesheet" />
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
   <h1>Minha Agenda</h1>
   <form action="" method="post" enctype="multipart/form-data">
       <input type="file" name="fileInput" id="fileInput">
       <input type="submit" value="Upload" onclick="submitForm()">
   </form>


   <div id="calendar" style=" height:500px";></div>
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.js"></script>
   <script>
       document.addEventListener('DOMContentLoaded', function() {
           var calendarEl = document.getElementById('calendar');
           var calendar = new FullCalendar.Calendar(calendarEl, {
               initialView: 'dayGridMonth',
               headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
               },
               events: []
           });
           calendar.render();

           function submitForm() {
                event.preventDefault();
                var form = document.getElementById("myForm");
                var xhr = new XMLHttpRequest();
                xhr.open(form.method, form.action, true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        var response = xhr.responseText;
                        var output = document.getElementById("output");
                        output.innerHTML = response;
                    } else {
                        console.log('Request failed.  Returned status of ' + xhr.status);
                    }
                };
                xhr.send(new FormData(form));
            }

       });
   </script>
   <%
          if (request.getMethod().equalsIgnoreCase("post")) {
              String filePath = request.getParameter("fileInput");
              Horario horario = new Horario();
              horario.lerCSV(new File(filePath));
              List<Map<String, Object>> events = new ArrayList<Map<String, Object>>();
              for (Aula aula : horario.getAulas()) {
                  Map<String, Object> event = new HashMap<String, Object>();
                  event.put("title", aula.uc());
                  event.put("start", aula.horaInicio().toString());
                  event.put("end", aula.horaFim().toString());
                  event.put("dow", Collections.singletonList(aula.diaDaSemana()));
                  event.put("location", aula.sala().toString());
                  events.add(event);
              }
              Gson gson = new Gson();
              String json = gson.toJson(events);
              response.setContentType("application/json");
              response.setCharacterEncoding("UTF-8");
              response.getWriter().write(json);
          }
      %>
</body>
</html>
