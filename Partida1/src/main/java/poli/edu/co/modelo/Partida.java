package poli.edu.co.modelo;

import java.util.Random;

/**
 * Representa una sesión de juego activa.
 * Encapsula el estado mutable de la partida: modo, intentos y reto de validación.
 */
public class Partida {

    public static final int MAX_INTENTOS = 3;
    private static final Random RANDOM = new Random();

    private final Regla regla;
    private boolean modoValidacion;
    private boolean terminada;
    private boolean ganada;           // true solo si el jugador acertó
    private int intentosRestantes;
    private final int[] entradasReto;
    private boolean retoGenerado;

    public Partida(Regla regla) {
        this.regla = regla;
        this.modoValidacion = false;
        this.terminada = false;
        this.intentosRestantes = MAX_INTENTOS;
        this.entradasReto = new int[4];
        this.retoGenerado = false;
    }

    public int aplicarRegla(int x) {
        return regla.aplicar(x);
    }

    public void iniciarModoValidacion() {
        modoValidacion = true;
        if (!retoGenerado) {
            for (int i = 0; i < 4; i++) {
                entradasReto[i] = RANDOM.nextInt(9) + 1;
            }
            retoGenerado = true;
        }
    }

    public void volverExploracion() {
        modoValidacion = false;
    }

    public boolean verificarExpresion(String texto) {
        return regla.verificarExpresion(texto);
    }

    public boolean verificarSalidas(int[] respuestas) {
        for (int i = 0; i < 4; i++) {
            if (respuestas[i] != regla.aplicar(entradasReto[i])) return false;
        }
        return true;
    }

    /** @return true si el jugador agotó todos los intentos (perdió). */
    public boolean registrarIntentoFallido() {
        intentosRestantes--;
        if (intentosRestantes <= 0) {
            terminada = true;
            return true;
        }
        return false;
    }

    public void ganar() {
        terminada = true;
        ganada    = true;
    }

    public String getExpresionRegla() {
        return regla.getExpresion();
    }

    public boolean isModoValidacion()   { return modoValidacion; }
    public boolean isTerminada()        { return terminada; }
    public boolean isGanada()           { return ganada; }
    public int getIntentosRestantes()   { return intentosRestantes; }
    public int[] getEntradasReto()      { return entradasReto; }
}
