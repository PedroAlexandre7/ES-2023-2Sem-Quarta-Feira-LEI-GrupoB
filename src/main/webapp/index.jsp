<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <title>Minha Agenda</title>
   <meta charset="UTF-8">
   <style>
      table, th, td {
         border: 1px solid black;
         border-collapse: collapse;
         padding: 10px;
      }
   </style>
</head>
<body>
   <h1>Minha Agenda</h1>
   <table>
      <tr>
         <th>Nome</th>
         <th>Sala</th>
         <th>Início</th>
         <th>Fim</th>
      </tr>
      <% for (Aula aula : horario.getAulas()) { %>
                  <tr>
                     <td><%= aula.uc() %></td>
                     <td><%= aula.sala() %></td>
                     <td><%= aula.horaInicio() %></td>
                     <td><%= aula.horaFim() %></td>
                  </tr>
      <% } %>
   </table>
</body>
</html>
