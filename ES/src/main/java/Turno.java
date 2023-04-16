public class Turno {
    private int numInscritos;
    private String nome;

    public Turno(int numInscritos, String nome) {
        this.nome = nome;
        this.numInscritos = numInscritos;
    }
    @Override
    public String toString(){
        return nome + " | "+numInscritos + " inscritos";
    }
}
