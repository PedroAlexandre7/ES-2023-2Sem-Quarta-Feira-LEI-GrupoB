import java.io.File;
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
        Horario horario = new Horario();
        try {
            horario.lerCSV(new File("input.csv"));
        } catch (Exception e) {
            System.out.println("Erro ao ler o ficheiro");
        }
        request.setAttribute("horario", horario);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
