package poli.edu.co.modelo;

public enum Nivel {

    FACIL("x*2") {
        @Override
        public int aplicar(int x) { return x * 2; }
    },
    INTERMEDIO("x+5") {
        @Override
        public int aplicar(int x) { return x + 5; }
    },
    DIFICIL("x*3") {
        @Override
        public int aplicar(int x) { return x * 3; }
    };

    private final String expresion;

    Nivel(String expresion) {
        this.expresion = expresion;
    }

    public abstract int aplicar(int x);

    public String getExpresion() {
        return expresion;
    }
}
