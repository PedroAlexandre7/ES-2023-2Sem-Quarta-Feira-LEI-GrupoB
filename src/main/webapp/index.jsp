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
       <input type="submit" value="Upload" id="Upload">
   </form>
   <%
         String eventData = null;
         Horario horario = null;
         List<String> ucs = null;
         Horario novo_horario = null;
         List<String> selectedCheckboxes = null;
         if (request != null && request.getMethod().equalsIgnoreCase("POST")) {
             String fileName = request.getParameter("file_path");
             try {
                 horario = new Horario();
                 horario.lerCSV(new File(fileName));
                 ucs = horario.getUcs();
             } catch (IOException e) {
                 System.out.println("Erro ao ler o ficheiro: " + fileName);
             }
             if (horario != null) {
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
                 %>
                 <form action="" method="post">
                 <% for (String s : ucs) { %>
                    <input type="checkbox" name="selectedStrings" value="<%= s %>"> <%= s %>
                 <%} %>
                    <input type="submit" value="Criar Horario" id="criarHorario">
                 </form>
                 <script>
                   document.getElementById("criarHorario").addEventListener("click", function() {
                        event.preventDefault();
                   <%
                       String[] selectedValues = request.getParameterValues("selectedCheckboxes");
                        if (selectedValues != null) {
                            for (int i = 0; i < selectedValues.length; i++) {
                                selectedCheckboxes.add(selectedValues[i]);
                            }
                        }
                       int lastSlashIndex = request.getParameter("file_path").lastIndexOf("\\");
                       String filePath_new = request.getParameter("file_path").substring(0, lastSlashIndex + 1) + "novoHorario.csv";
                       if ( selectedCheckboxes != null ) {
                           horario.criarHorario(selectedCheckboxes,filePath_new);
                           try {
                                novo_horario = new Horario();
                                novo_horario.lerCSV(new File(filePath_new));
                                ucs = novo_horario.getUcs();
                           } catch (IOException e) {
                                System.out.println("Erro ao ler o ficheiro: " + filePath_new);
                           }
                           if (novo_horario != null) {
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
                       }
                   %>
                       var eventData = <%= eventData %>;
                       var calendarEl = document.getElementById('calendar');
                       var calendar = new FullCalendar.Calendar(calendarEl, {
                       events: eventData
                       });
                       calendar.render();
                   });

                 </script>
             <%}
         }
   %>
   <% if (eventData != null) { %>
        <div id="calendar" style=" height:500px";></div>
        <script>
          document.getElementById("Upload").addEventListener("click", function() {
            event.preventDefault();
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
