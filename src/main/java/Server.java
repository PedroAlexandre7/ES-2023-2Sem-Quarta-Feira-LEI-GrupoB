import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crie a agenda e adicione algumas aulas
        Horario horario = new Horario();
        //horario.adicionarAula(new Aula("Matemática", "João", "08:00", "10:00"));
        //horario.adicionarAula(new Aula("História", "Maria", "10:00", "12:00"));

        // Defina a agenda como um atributo da solicitação
        request.setAttribute("horario", horario);

        // Encaminhe a solicitação para a página JSP da agenda
        request.getRequestDispatcher("agenda.jsp").forward(request, response);
    }
}
