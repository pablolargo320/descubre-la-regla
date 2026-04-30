package poli.edu.co.modelo;

/**
 * Entidad que encapsula la regla matemática de una partida.
 * Delega el cálculo al enum {@link Nivel} y provee la verificación
 * de la expresión escrita por el jugador.
 */
public class Regla {

    private final Nivel nivel;

    public Regla(Nivel nivel) {
        this.nivel = nivel;
    }

    /** Aplica la función matemática al valor {@code x}. */
    public int aplicar(int x) {
        return nivel.aplicar(x);
    }

    /** Devuelve la expresión textual de la regla (ej.: "x * 2", "x^2 + 10"). */
    public String getExpresion() {
        return nivel.getExpresion();
    }

    /**
     * Verifica si el texto ingresado por el jugador corresponde a la expresión
     * de la regla. La comparación normaliza espacios y mayúsculas en AMBOS lados,
     * necesario porque las expresiones de Nivel incluyen espacios (ej. "x * 2").
     *
     * @param texto texto escrito por el jugador
     * @return {@code true} si coincide con la expresión de la regla
     */
    public boolean verificarExpresion(String texto) {
        if (texto == null || texto.isBlank()) return false;
        String normalTexto = texto.replace(" ", "").toLowerCase();
        String normalRegla = nivel.getExpresion().replace(" ", "").toLowerCase();
        return normalTexto.equals(normalRegla);
    }

    public Nivel getNivel() {
        return nivel;
    }
}
