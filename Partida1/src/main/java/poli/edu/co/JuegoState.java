package poli.edu.co;

import java.util.Random;

public final class JuegoState {

    private static final Random RANDOM = new Random();

    public static final int MAX_INTENTOS = 3;

    public static boolean validationMode = false;
    public static boolean finished = false;
    public static int attemptsLeft = MAX_INTENTOS;

    public static int ruleType = 0;
    public static String ruleExpression = "x*2";

    public static final int[] challengeInputs = new int[4];
    public static boolean challengeGenerated = false;

    public static String exploreMessage = "";
    public static String validationMessage = "";
    public static String lastOutput = "";

    static {
        resetFull();
    }

    public static void resetFull() {
        ruleType = RANDOM.nextInt(3);

        switch (ruleType) {
            case 0 -> ruleExpression = "x*2";
            case 1 -> ruleExpression = "x*3";
            default -> ruleExpression = "x+5";
        }

        validationMode = false;
        finished = false;
        attemptsLeft = MAX_INTENTOS;
        challengeGenerated = false;

        exploreMessage = "Ingresa números enteros y observa la salida del sistema.";
        validationMessage = "";
        lastOutput = "";
    }

    public static int applyRule(int x) {
        return switch (ruleType) {
            case 0 -> x * 2;
            case 1 -> x * 3;
            default -> x + 5;
        };
    }
public static void setNivel(String nivel) {
    switch (nivel) {
        case "FACIL" -> { ruleType = 0; ruleExpression = "x*2"; }
        case "INTERMEDIO" -> { ruleType = 2; ruleExpression = "x+5"; }
        case "DIFICIL" -> { ruleType = 1; ruleExpression = "x*3"; }
    }
}
    public static void startValidationMode() {
        validationMode = true;

        if (!challengeGenerated) {
            for (int i = 0; i < 4; i++) {
                challengeInputs[i] = RANDOM.nextInt(9) + 1;
            }
            challengeGenerated = true;
        }

        validationMessage = "Ahora debes dar las 4 salidas correctas. Intentos restantes: " + attemptsLeft;
    }

    public static void backToExploration() {
        validationMode = false;
        validationMessage = "";
        exploreMessage = "Ingresa números enteros y observa la salida del sistema.";
    }

    public static boolean checkRuleText(String text) {
        if (text == null) return false;
        String cleaned = text.replace(" ", "").toLowerCase();
        return cleaned.equals(ruleExpression);
    }

    public static boolean checkOutputs(int[] answers) {
        for (int i = 0; i < 4; i++) {
            if (answers[i] != applyRule(challengeInputs[i])) {
                return false;
            }
        }
        return true;
    }

    public static void registerFailedAttempt() {
        attemptsLeft--;

        if (attemptsLeft <= 0) {
            finished = true;
            validationMessage = "💀 Perdiste. La regla era: " + ruleExpression;
        } else {
            validationMessage = "❌ Incorrecto. Intentos restantes: " + attemptsLeft;
        }
    }

    public static void finishWon() {
        finished = true;
        validationMessage = "🎉 ¡Correcto! Descubriste la regla.";
    }
}