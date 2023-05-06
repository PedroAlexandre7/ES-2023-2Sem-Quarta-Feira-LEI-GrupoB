public record Sala(String nome, int lotacao) {

    @Override
    public String toString() {
        return nome + " | lotação: " + lotacao;
    }
}
