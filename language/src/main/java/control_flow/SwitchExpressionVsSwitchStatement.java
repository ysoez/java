package control_flow;

import java.util.stream.Stream;

class SwitchExpressionVsSwitchStatement {

    public static void main(String[] args) {
        System.out.println("Switch Expression");
        Stream.of(90, 80, 70, 60)
                .map(SwitchExpressionVsSwitchStatement::expression)
                .forEach(System.out::print);
        System.out.println("\nSwitch Statement");
        Stream.of(90, 80, 70, 60)
                .map(SwitchExpressionVsSwitchStatement::statement)
                .forEach(System.out::print);
    }

    private static String statement(int score) {
        String letter = "";
        switch (Math.min(score / 10, 10)) {
            case 9, 10:
                letter = "A";
            case 8:
                letter = "B";
            case 7:
                letter = "C";
            case 6:
                letter = "D";
            default:
                letter = "F";
        }
        return letter;
    }

    private static String expression(int score) {
        return switch (Math.min(score / 10, 10)) {
            case 9, 10 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
    }

}
