import java.util.ArrayList;
import java.util.List;

public class Horario {
    private List<Aula> aulas;

    public Horario( List<Aula> aulas){
        this.aulas = aulas;
    }

    public void adicionarAula(Aula aula){
        aulas.add(aula);
    }

    public void removerAula(Aula aula){
        aulas.remove(aula);
    }


}
