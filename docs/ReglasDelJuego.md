Documento – Reglas del Juego
Proyecto: Descubre la Regla
 Sprint: Sprint 1
 Responsable: Juan Pablo Largo Vargas

1. Objetivo del Juego
El objetivo del juego es que el jugador descubra una regla matemática secreta aplicada por el sistema.
El sistema recibe un número ingresado por el jugador y devuelve otro número transformado según una regla desconocida. El jugador debe deducir cuál es dicha regla.

2. Funcionamiento General
1.	El jugador inicia una partida.
2.	El sistema genera una regla matemática de manera aleatoria.
3.	El jugador ingresa un número entero.
4.	El sistema aplica la regla y devuelve un resultado.
5.	El jugador analiza los resultados e intenta descubrir la regla.
6.	El jugador puede proponer una regla como respuesta final.
7.	El sistema valida si la regla propuesta coincide con la regla secreta.

3. Reglas Matemáticas Iniciales (Sprint 1)
Durante el Sprint 1 se trabajará con un conjunto básico de reglas:
•	x * 2
•	x + 5
•	x - 3
•	x * x
•	Número par
•	Número impar
•	Múltiplo de 3
Estas reglas serán seleccionadas aleatoriamente al iniciar la partida.

4. Ejemplo de Funcionamiento
Si la regla secreta es:
x * 2
Y el jugador ingresa:
12
El sistema responderá:
24
El jugador deberá analizar los resultados obtenidos para deducir que la regla aplicada es multiplicar por 2.

5. Condiciones del Juego
•	Solo se aceptan números enteros.
•	El sistema no revela la regla hasta que el jugador la proponga.
•	Cada intento puede registrarse para cálculo de puntaje.
•	La partida finaliza cuando el jugador descubre correctamente la regla o alcanza el límite de intentos.

6. Cálculo de Puntaje (Propuesta Inicial)
El puntaje puede calcularse con base en:
•	Número de intentos realizados.
•	Tiempo empleado en descubrir la regla.
•	Exactitud en la propuesta final.
(Este apartado podrá ajustarse en futuros sprints).

7. Consideraciones Técnicas
•	La regla activa se almacena en memoria durante la partida.
•	La selección de la regla se realiza mediante generación aleatoria.
•	La lógica del juego se implementa separada de la interfaz gráfica (patrón MVC).

