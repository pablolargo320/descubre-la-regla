package poli.edu.co.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión con la base de datos SQLite embebida.
 * El archivo de BD se crea automáticamente en el directorio de ejecución.
 */
public final class ConexionBD {

    private static final String URL = "jdbc:sqlite:adivinaRegla.db";

    static {
        /*
         * En Java 9+ con el sistema de módulos, el driver SQLite queda en el
         * unnamed-module (classpath). Se carga explícitamente a través del
         * ClassLoader del sistema para que DriverManager lo registre.
         */
        try {
            Class.forName("org.sqlite.JDBC",
                          true,
                          ClassLoader.getSystemClassLoader());
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                "Driver SQLite no encontrado en el classpath: " + e.getMessage());
        }
    }

    private ConexionBD() { /* utilidad estática */ }

    /** Abre y devuelve una nueva conexión. El llamador debe cerrarla. */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Crea la tabla 'jugadores' si no existe.
     * Se llama una sola vez al arrancar la aplicación.
     */
    public static void inicializarTablas() {
        String sql = """
                CREATE TABLE IF NOT EXISTS jugadores (
                    id      INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre  TEXT    NOT NULL,
                    puntaje INTEGER NOT NULL,
                    nivel   TEXT    NOT NULL,
                    fecha   TEXT    NOT NULL
                )
                """;
        try (Connection conn = getConexion();
             Statement  stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar la base de datos: " + e.getMessage(), e);
        }
    }
}
