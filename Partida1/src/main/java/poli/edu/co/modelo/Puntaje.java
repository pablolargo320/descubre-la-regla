package poli.edu.co.modelo;

/**
 * Objeto de valor inmutable que encapsula el puntaje de una partida.
 *
 * Fórmula: 1000 - (intentos_fallidos × 300)
 *   · 0 fallos  → 1000 pts
 *   · 1 fallo   →  700 pts
 *   · 2 fallos  →  400 pts
 *   · Derrota   →    0 pts
 */
public final class Puntaje {

    public static final int PUNTAJE_BASE            = 1000;
    public static final int DESCUENTO_POR_INTENTO   = 300;

    private final int valor;

    private Puntaje(int valor) {
        this.valor = valor;
    }

    /** Calcula el puntaje según intentos restantes y resultado final. */
    public static Puntaje calcular(int intentosRestantes, boolean gano) {
        if (!gano) return new Puntaje(0);
        int fallos = Partida.MAX_INTENTOS - intentosRestantes;
        int puntos = Math.max(0, PUNTAJE_BASE - fallos * DESCUENTO_POR_INTENTO);
        return new Puntaje(puntos);
    }

    /** Puntaje neutro (partida no terminada o perdida). */
    public static Puntaje cero() {
        return new Puntaje(0);
    }

    public int getValor() { return valor; }

    @Override
    public String toString() { return valor + " pts"; }
}
