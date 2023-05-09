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
    <form method="POST" action="loadCalendar.jsp">
        <label for="filePath">Enter file path:</label>
        <input type="text" name="filePath" id="filePath">
        <button type="submit">Load Calendar</button>
    </form>
</body>
</html>
