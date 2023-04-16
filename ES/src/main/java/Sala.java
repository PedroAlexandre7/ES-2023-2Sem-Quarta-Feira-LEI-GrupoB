public class Sala {
    private String nome;
    private int lotacao;


    public Sala(String nome, int lotacao){
        this.lotacao = lotacao;
        this.nome = nome;
    }
    @Override
    public String toString(){
        return nome + " | lotação: "+lotacao ;
    }
}
