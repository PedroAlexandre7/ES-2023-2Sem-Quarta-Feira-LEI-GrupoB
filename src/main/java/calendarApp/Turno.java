package calendarApp;

public record Turno(String nome, int numInscritos) {

    @Override
    public String toString() {
        return nome + " | " + numInscritos + " inscritos";
    }

}
