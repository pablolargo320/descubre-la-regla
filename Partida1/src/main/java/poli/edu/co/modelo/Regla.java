package poli.edu.co.modelo;

public class Regla {

    private final Nivel nivel;

    public Regla(Nivel nivel) {
        this.nivel = nivel;
    }

    public int aplicar(int x) {
        return nivel.aplicar(x);
    }

    public String getExpresion() {
        return nivel.getExpresion();
    }

    /** Acepta la expresión escrita por el usuario con o sin espacios, en minúsculas. */
    public boolean verificarExpresion(String texto) {
        if (texto == null || texto.isBlank()) return false;
        return texto.replace(" ", "").toLowerCase().equals(nivel.getExpresion());
    }

    public Nivel getNivel() {
        return nivel;
    }
}
