package poli.edu.co.modelo.dao;

import poli.edu.co.modelo.Jugador;
import java.util.List;

/**
 * Contrato de acceso a datos para la entidad Jugador.
 * Permite cambiar la implementación (SQLite, H2, archivo…) sin tocar el resto.
 */
public interface JugadorDAO {

    /** Persiste un jugador y rellena su id generado. */
    void guardar(Jugador jugador);

    /** Devuelve los 10 mejores puntajes en orden descendente. */
    List<Jugador> obtenerTop10();
}
