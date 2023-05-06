<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.java.Horario"%>
<%@ page import="java.io.*"%>
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
   <%
        String fileName = request.getParameter("fileInput");
        if (fileName != null && !fileName.isEmpty()) {
            Horario horario = new Horario();
            horario.lerCSV(new File(fileName));
        } else {
            out.println("Please select a file to upload.");
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
               }
           });
           calendar.render();
       });
   </script>
</body>
</html>
