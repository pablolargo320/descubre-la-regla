package poli.edu.co.modelo;

import java.time.LocalDateTime;

/**
 * Entidad que representa a un jugador y su resultado en una partida.
 */
public class Jugador {

    private int           id;
    private final String  nombre;
    private final int     puntaje;
    private final String  nivel;
    private final LocalDateTime fecha;

    public Jugador(String nombre, int puntaje, String nivel) {
        this.nombre  = nombre;
        this.puntaje = puntaje;
        this.nivel   = nivel;
        this.fecha   = LocalDateTime.now();
    }

    // ── getters ──────────────────────────────────────────────────────────────
    public int           getId()     { return id; }
    public String        getNombre() { return nombre; }
    public int           getPuntaje(){ return puntaje; }
    public String        getNivel()  { return nivel; }
    public LocalDateTime getFecha()  { return fecha; }

    public void setId(int id) { this.id = id; }
}
