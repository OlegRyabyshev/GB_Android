package xyz.fcr.gb_android;

public class Calc
{
    public static String calcPlus(final String input, final boolean percentEnabled) {
        final String[] args = input.split("\\+");
        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);
        double result;
        if (percentEnabled) {
            result = num1 + num2 / 100.0 * num1;
        }
        else {
            result = num1 + num2;
        }
        return String.valueOf(result);
    }

    public static String calcMinus(final String input, final boolean percentEnabled) {
        final String[] args = input.split("-");
        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);
        double result;

        if (percentEnabled) {
            result = num1 - num2 / 100.0 * num1;
        }
        else {
            result = num1 - num2;
        }
        return String.valueOf(result);
    }

    public static String calcMultiply(final String input, final boolean percentEnabled) {
        final String[] args = input.split("\u00d7");
        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);
        double result;
        if (percentEnabled) {
            result = num1 * (num2 / 100.0) * num1;
        }
        else {
            result = num1 * num2;
        }
        return String.valueOf(result);
    }

    public static String calcDivide(final String input, final boolean percentEnabled) {
        final String[] args = input.split("/");
        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);
        double result;
        try {
            if (percentEnabled) {
                result = num1 / (num2 / 100.0 * num1);
            }
            else {
                result = num1 / num2;
            }
        }
        catch (ArithmeticException e) {
            return "Error";
        }
        return String.valueOf(result);
    }
}