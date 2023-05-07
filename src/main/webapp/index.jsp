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
   <link href="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.css" rel="stylesheet" />
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/jquery/3.6.4/jquery.min.js"></script>
   <script src="https://cdn.jsdelivr.net/webjars/org.webjars/fullcalendar/5.11.3/main.js"></script>
</head>
<body>
   <h1>Minha Agenda</h1>
   <form action="" method="post">
       <input type="text" name="file_path" id="file_path">
       <input type="submit" value="Upload">
   </form>
   <%
         String eventData = null;
         Horario horario = null;
         if (request != null && request.getMethod().equalsIgnoreCase("POST")) {
             String fileName = request.getParameter("file_path");
             System.out.println(fileName);
             try {
                 horario = new Horario();
                 horario.lerCSV(new File(fileName));
             } catch (IOException e) {
                 System.out.println("Erro ao ler o ficheiro: " + fileName);
             }
             if (horario != null) {
                 eventData = "[";
                 for (Aula aula : horario.getAulas()) {
                     System.out.println("A ler aula...");
                     eventData += "{";
                     eventData += "\"title\":\"" + aula.uc() + "\",";
                     eventData += "\"start\":\"" + aula.data() + "T" + aula.horaInicio().toString() + "\",";
                     eventData += "\"end\":\"" + aula.data() + "T" + aula.horaFim().toString() + "\",";
                     eventData += "\"location\":\"" + aula.sala().toString() + "\",";
                     eventData += "},";
                 }
                 eventData = eventData.substring(0, eventData.length() - 1); //remove a ultima virgula
                 eventData += "]";
                 System.out.println("Criada a String" + eventData);
             }
         }
   %>
   <% if (eventData != null) { %>
        <div id="calendar" style=" height:500px";></div>
        <% System.out.println("Dentro do calendario: " + eventData); %>
        <script>
          document.addEventListener("DOMContentLoaded", function() {
            var eventData = <%= eventData %>; // assuming eventData is a JSON object or array
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
              events: eventData
            });
            calendar.render();
          });
        </script>

   <% } %>
   <% if (request.getMethod().equalsIgnoreCase("POST") && horario == null) { %>
       <p>Error reading file. Please try again.</p>
   <% } %>
</body>
</html>
