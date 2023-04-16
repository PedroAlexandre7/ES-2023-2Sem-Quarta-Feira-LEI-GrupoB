public class Sala {
    private String nome;
    private int lotacao;


    public Sala(String nome, int lotacao){
        this.nome = nome;
        this.lotacao = lotacao;

    }
    @Override
    public String toString(){
        return nome + " | lotação: "+lotacao ;
    }
}
