public class Turno {
    private String nome;
    private int numInscritos;


    public Turno(String nome, int numInscritos) {
        this.nome = nome;
        this.numInscritos = numInscritos;
    }

    @Override
    public String toString() {
        return nome + " | " + numInscritos + " inscritos";
    }

    public int getNumInscritos() {
        return numInscritos;
    }

    public String getNome() {
        return nome;
    }

}
