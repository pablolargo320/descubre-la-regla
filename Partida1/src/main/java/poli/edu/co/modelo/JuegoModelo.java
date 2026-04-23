package poli.edu.co.modelo;

import poli.edu.co.modelo.dao.JugadorDAO;
import poli.edu.co.modelo.dao.JugadorDAOImpl;

import java.util.Random;

/**
 * Servicio / fachada principal del juego.
 * Coordina la Partida activa, calcula el Puntaje y delega el registro
 * del Jugador al repositorio correspondiente.
 */
public class JuegoModelo {

    private static final Random RANDOM = new Random();

    // ── Estado de la sesión ──────────────────────────────────────────────────
    private Partida    partida;
    private Nivel      nivelActual;
    private Puntaje    puntaje    = Puntaje.cero();

    // ── Mensajes para la vista ───────────────────────────────────────────────
    private String mensajeExploracion;
    private String mensajeValidacion;
    private String ultimaSalida;

    // ── Acceso a datos ───────────────────────────────────────────────────────
    private final JugadorDAO jugadorDAO = new JugadorDAOImpl();

    public JuegoModelo() {
        iniciarPartidaAleatoria();
    }

    // ── Ciclo de vida ────────────────────────────────────────────────────────

    /** Nueva partida con nivel aleatorio (para el botón "Nueva partida"). */
    public void reiniciar() {
        iniciarPartidaAleatoria();
        ultimaSalida = "";
        puntaje      = Puntaje.cero();
    }

    /**
     * Inicia una partida con el nivel elegido por el jugador.
     * Crea una nueva Partida y resetea el puntaje.
     */
    public void setNivel(Nivel nivel) {
        nivelActual  = nivel;
        partida      = new Partida(new Regla(nivel));
        puntaje      = Puntaje.cero();
        ultimaSalida = "";
        resetMensajes();
    }

    private void iniciarPartidaAleatoria() {
        Nivel[] niveles = Nivel.values();
        nivelActual = niveles[RANDOM.nextInt(niveles.length)];
        partida     = new Partida(new Regla(nivelActual));
        resetMensajes();
    }

    private void resetMensajes() {
        mensajeExploracion = "Ingresa números enteros y observa la salida del sistema.";
        mensajeValidacion  = "";
    }

    // ── Lógica de juego (delega a Partida) ───────────────────────────────────

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

    /** Registra un fallo y calcula el puntaje si la partida termina. */
    public void registrarIntentoFallido() {
        boolean perdio = partida.registrarIntentoFallido();
        if (perdio) {
            puntaje           = Puntaje.cero();
            mensajeValidacion = "Perdiste. La regla era: " + partida.getExpresionRegla();
        } else {
            mensajeValidacion = "Incorrecto. Intentos restantes: " + partida.getIntentosRestantes();
        }
    }

    /** Marca la victoria y calcula el puntaje final. */
    public void ganar() {
        partida.ganar();
        puntaje           = Puntaje.calcular(partida.getIntentosRestantes(), true);
        mensajeValidacion = "¡Correcto! Descubriste la regla: " + partida.getExpresionRegla();
    }

    // ── Registro del jugador ─────────────────────────────────────────────────

    /**
     * Crea un Jugador con el nombre dado y lo persiste vía DAO.
     *
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     * @throws RuntimeException         si ocurre un error de persistencia
     */
    public void guardarJugador(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío.");
        }
        Jugador jugador = new Jugador(nombre.trim(), puntaje.getValor(), nivelActual.name());
        jugadorDAO.guardar(jugador);
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public boolean isModoValidacion()      { return partida.isModoValidacion(); }
    public boolean isTerminado()           { return partida.isTerminada(); }
    public boolean isUltimaPartidaGanada() { return partida.isGanada(); }
    public int     getIntentosRestantes()  { return partida.getIntentosRestantes(); }
    public int[]   getEntradasReto()       { return partida.getEntradasReto(); }
    public Nivel   getNivelActual()        { return nivelActual; }
    public Puntaje getPuntaje()            { return puntaje; }

    public String getMensajeExploracion()  { return mensajeExploracion; }
    public String getMensajeValidacion()   { return mensajeValidacion; }
    public String getUltimaSalida()        { return ultimaSalida; }

    public void setUltimaSalida(String salida)        { this.ultimaSalida = salida; }
    public void setMensajeExploracion(String mensaje) { this.mensajeExploracion = mensaje; }
}
