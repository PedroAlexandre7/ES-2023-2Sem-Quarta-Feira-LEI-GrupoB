<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <title>Minha Agenda</title>
   <meta charset="UTF-8">
   <link href="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.css" rel="stylesheet" />
</head>
<body>
   <h1>Minha Agenda</h1>
   <div id="calendar" style=" height:500px";></div>
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.js"></script>
   <script>
       document.addEventListener('DOMContentLoaded', function() {
           var calendarEl = document.getElementById('calendar');
           var calendar = new FullCalendar.Calendar(calendarEl, {
               initialView: 'dayGridMonth'
           });
           calendar.render();
       });
   </script>
</body>
</html>
