package poli.edu.co.modelo;

import poli.edu.co.modelo.dao.JugadorDAO;

import java.util.Random;

/**
 * Clase central del Modelo.
 * Resultado de unificar las antiguas clases JuegoModelo y Partida.
 *
 * Responsabilidades:
 *  - Mantener el estado de la partida activa (modo, intentos, reto).
 *  - Ejecutar la lógica del juego (aplicar regla, verificar respuestas).
 *  - Calcular el puntaje al finalizar.
 *  - Persistir el resultado del jugador vía DAO.
 *
 * Restricciones MVC (separación de capas):
 *  - NO almacena mensajes de UI, etiquetas ni textos de interfaz.
 *  - NO importa ni usa clases de JavaFX.
 *  - NO contiene anotaciones @FXML.
 *  - NO instancia dependencias externas (DAOs, entidades ajenas) — se reciben
 *    por inyección de dependencias desde los controladores o App.
 *
 * Uso de 'new' en esta clase:
 *  - new int[4]: array primitivo de estado interno; no es una dependencia.
 *  - Cualquier otro 'new' de dominio o infraestructura fue eliminado y
 *    trasladado al controlador correspondiente (véase NivelControlador,
 *    ResultadoControlador) o al Composition Root (App.java).
 */
public class Partida {

    public static final int MAX_INTENTOS = 3;
    private static final Random RANDOM = new Random();

    // ── Configuración ─────────────────────────────────────────────────────────
    private Nivel nivel;
    private Regla regla;

    // ── Estado ───────────────────────────────────────────────────────────────
    private boolean modoValidacion;
    private boolean terminada;
    private boolean ganada;
    private int     intentosRestantes;
    private final int[] entradasReto = new int[4]; // array de estado interno
    private boolean retoGenerado;

    // ── Resultado ─────────────────────────────────────────────────────────────
    private Puntaje puntaje;

    // ── Persistencia ─────────────────────────────────────────────────────────
    /** Recibido por inyección — el modelo solo conoce la abstracción. */
    private final JugadorDAO jugadorDAO;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Crea una partida en estado inicial de reposo.
     * El nivel y la regla se establecen posteriormente mediante
     * {@link #iniciarConNivel(Nivel, Regla)}, llamado desde el controlador.
     *
     * @param jugadorDAO implementación de persistencia inyectada desde el exterior
     *                   (Principio de Inversión de Dependencias: Partida depende
     *                    solo de la interfaz JugadorDAO, nunca de JugadorDAOImpl).
     */
    public Partida(JugadorDAO jugadorDAO) {
        this.jugadorDAO       = jugadorDAO;
        this.intentosRestantes = MAX_INTENTOS;
        this.puntaje           = Puntaje.cero();
    }

    // ── Configuración de nivel ────────────────────────────────────────────────

    /**
     * Inicia (o reinicia) la partida con el nivel y la regla especificados.
     *
     * Diseño: tanto {@code nivel} como {@code regla} son creados en el
     * controlador ({@code NivelControlador}) y recibidos aquí por parámetro,
     * evitando instanciaciones en el modelo y manteniendo separación de capas.
     *
     * @param nivel nivel de dificultad seleccionado
     * @param regla regla matemática asociada al nivel
     */
    public void iniciarConNivel(Nivel nivel, Regla regla) {
        this.nivel = nivel;
        this.regla = regla;
        reiniciarEstado();
    }

    private void reiniciarEstado() {
        modoValidacion    = false;
        terminada         = false;
        ganada            = false;
        intentosRestantes = MAX_INTENTOS;
        retoGenerado      = false;
        puntaje           = Puntaje.cero();
    }

    // ── Lógica del juego ─────────────────────────────────────────────────────

    /**
     * Aplica la regla matemática al valor {@code x}.
     *
     * @return resultado de f(x)
     */
    public int aplicarRegla(int x) {
        return regla.aplicar(x);
    }

    /**
     * Cambia al modo validación y genera los 4 números del reto
     * (solo la primera vez, para que sean consistentes).
     */
    public void iniciarModoValidacion() {
        modoValidacion = true;
        if (!retoGenerado) {
            for (int i = 0; i < 4; i++) {
                entradasReto[i] = RANDOM.nextInt(9) + 1;
            }
            retoGenerado = true;
        }
    }

    /** Regresa al modo exploración sin perder los números del reto generados. */
    public void volverExploracion() {
        modoValidacion = false;
    }

    /**
     * Verifica si el texto corresponde a la expresión de la regla.
     * La comparación normaliza espacios y mayúsculas en ambos lados.
     */
    public boolean verificarExpresion(String texto) {
        return regla.verificarExpresion(texto);
    }

    /**
     * Verifica si las 4 salidas numéricas del jugador son correctas.
     *
     * @param respuestas arreglo con las 4 respuestas del jugador
     * @return {@code true} si todas son correctas
     */
    public boolean verificarSalidas(int[] respuestas) {
        if (respuestas == null || respuestas.length < 4) return false;
        for (int i = 0; i < 4; i++) {
            if (respuestas[i] != regla.aplicar(entradasReto[i])) return false;
        }
        return true;
    }

    /**
     * Registra un intento fallido y actualiza el estado.
     *
     * @return {@code true} si el jugador agotó todos los intentos (derrota).
     */
    public boolean registrarIntentoFallido() {
        intentosRestantes--;
        if (intentosRestantes <= 0) {
            terminada = true;
            puntaje   = Puntaje.cero();
            return true;
        }
        return false;
    }

    /** Marca la partida como ganada y calcula el puntaje final. */
    public void ganar() {
        terminada = true;
        ganada    = true;
        puntaje   = Puntaje.calcular(intentosRestantes, true);
    }

    // ── Persistencia ─────────────────────────────────────────────────────────

    /**
     * Persiste el jugador recibido vía DAO.
     *
     * Diseño: {@link Jugador} es creado en el controlador
     * ({@code ResultadoControlador}) con los datos de nombre, puntaje y nivel,
     * y se pasa ya construido a este método. El modelo solo delega al DAO.
     *
     * @param jugador entidad a persistir (creada en el controlador)
     * @throws IllegalArgumentException si jugador es nulo
     * @throws RuntimeException         si falla la operación de BD
     */
    public void guardarJugador(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo.");
        }
        jugadorDAO.guardar(jugador);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public boolean isModoValidacion()     { return modoValidacion; }
    public boolean isTerminada()          { return terminada; }
    public boolean isGanada()             { return ganada; }
    public int     getIntentosRestantes() { return intentosRestantes; }
    public int[]   getEntradasReto()      { return entradasReto; }
    public Nivel   getNivel()             { return nivel; }
    public Puntaje getPuntaje()           { return puntaje; }
    public String  getExpresionRegla()    { return regla.getExpresion(); }
}
