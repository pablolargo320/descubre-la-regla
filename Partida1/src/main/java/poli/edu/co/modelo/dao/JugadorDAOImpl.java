package poli.edu.co.modelo.dao;

import poli.edu.co.modelo.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación SQLite de JugadorDAO.
 * Toda la lógica de acceso a datos está aislada aquí.
 */
public class JugadorDAOImpl implements JugadorDAO {

    @Override
    public void guardar(Jugador jugador) {
        String sql = """
                INSERT INTO jugadores (nombre, puntaje, nivel, fecha)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection       conn = ConexionBD.getConexion();
             PreparedStatement ps   = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, jugador.getNombre());
            ps.setInt   (2, jugador.getPuntaje());
            ps.setString(3, jugador.getNivel());
            ps.setString(4, jugador.getFecha().toString());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) jugador.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el jugador: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Jugador> obtenerTop10() {
        String sql = "SELECT * FROM jugadores ORDER BY puntaje DESC LIMIT 10";
        List<Jugador> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConexion();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jugador j = new Jugador(
                    rs.getString("nombre"),
                    rs.getInt   ("puntaje"),
                    rs.getString("nivel")
                );
                j.setId(rs.getInt("id"));
                lista.add(j);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar jugadores: " + e.getMessage(), e);
        }
        return lista;
    }
}
