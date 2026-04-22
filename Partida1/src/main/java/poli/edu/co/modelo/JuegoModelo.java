package poli.edu.co.modelo;

import java.util.Random;

/**
 * Servicio/fachada del juego. Coordina la Partida activa y genera los mensajes
 * de retroalimentación que la vista necesita mostrar.
 */
public class JuegoModelo {

    private static final Random RANDOM = new Random();

    private Partida partida;
    private String mensajeExploracion;
    private String mensajeValidacion;
    private String ultimaSalida;

    public JuegoModelo() {
        iniciarPartidaAleatoria();
    }

    // -------------------------------------------------------------------------
    // Ciclo de vida de la partida
    // -------------------------------------------------------------------------

    /** Crea una nueva partida con nivel aleatorio. */
    public void reiniciar() {
        iniciarPartidaAleatoria();
        ultimaSalida = "";
    }

    /** Crea una nueva partida con el nivel indicado. */
    public void setNivel(Nivel nivel) {
        partida = new Partida(new Regla(nivel));
        resetMensajes();
        ultimaSalida = "";
    }

    private void iniciarPartidaAleatoria() {
        Nivel[] niveles = Nivel.values();
        Nivel nivel = niveles[RANDOM.nextInt(niveles.length)];
        partida = new Partida(new Regla(nivel));
        resetMensajes();
    }

    private void resetMensajes() {
        mensajeExploracion = "Ingresa números enteros y observa la salida del sistema.";
        mensajeValidacion  = "";
    }

    // -------------------------------------------------------------------------
    // Lógica de juego (delega a Partida)
    // -------------------------------------------------------------------------

    public int aplicarRegla(int x) {
        return partida.aplicarRegla(x);
    }

    public void iniciarModoValidacion() {
        partida.iniciarModoValidacion();
        mensajeValidacion = "Da las 4 salidas correctas. Intentos restantes: "
                + partida.getIntentosRestantes();
    }

    public void volverExploracion() {
        partida.volverExploracion();
        mensajeValidacion  = "";
        mensajeExploracion = "Ingresa números enteros y observa la salida del sistema.";
    }

    public boolean verificarTextoRegla(String texto) {
        return partida.verificarExpresion(texto);
    }

    public boolean verificarSalidas(int[] respuestas) {
        return partida.verificarSalidas(respuestas);
    }

    public void registrarIntentoFallido() {
        boolean perdio = partida.registrarIntentoFallido();
        if (perdio) {
            mensajeValidacion = "Perdiste. La regla era: " + partida.getExpresionRegla();
        } else {
            mensajeValidacion = "Incorrecto. Intentos restantes: " + partida.getIntentosRestantes();
        }
    }

    public void ganar() {
        partida.ganar();
        mensajeValidacion = "¡Correcto! Descubriste la regla: " + partida.getExpresionRegla();
    }

    // -------------------------------------------------------------------------
    // Getters (delegan al estado de Partida o al propio servicio)
    // -------------------------------------------------------------------------

    public boolean isModoValidacion()     { return partida.isModoValidacion(); }
    public boolean isTerminado()          { return partida.isTerminada(); }
    public int getIntentosRestantes()     { return partida.getIntentosRestantes(); }
    public int[] getEntradasReto()        { return partida.getEntradasReto(); }
    public String getMensajeExploracion() { return mensajeExploracion; }
    public String getMensajeValidacion()  { return mensajeValidacion; }
    public String getUltimaSalida()       { return ultimaSalida; }

    public void setUltimaSalida(String salida)        { this.ultimaSalida = salida; }
    public void setMensajeExploracion(String mensaje) { this.mensajeExploracion = mensaje; }
}
