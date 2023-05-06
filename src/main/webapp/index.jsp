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
       <input type="submit" value="Upload" id="uploadButton">
   </form>
   <%
       if (request.getMethod().equalsIgnoreCase("post")) {
           Part filePart = request.getPart("fileInput");
           File file = new File(filePart.getSubmittedFileName());
           Horario horario = new Horario();
           horario.lerCSV(file);
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
           out.println(new Gson().toJson(events));
       }
   %>

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

           $('form').submit(function(event) {
               event.preventDefault();
               var formData = new FormData(this);
               $.ajax({
                   url: this.action,
                   type: this.method,
                   data: formData,
                   processData: false,
                   contentType: false,
                   success: function(response) {
                       var events = JSON.parse(response);
                       calendar.removeAllEvents();
                       calendar.addEventSource(events);
                   },
                   error: function(xhr, status, error) {
                       console.log('Error:', error);
                   }
               });
           });

       });
   </script>
</body>
</html>
