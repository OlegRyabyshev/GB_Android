package xyz.fcr.gb_android;

import android.util.Log;
import java.text.DecimalFormat;

public class Calculator {

    public String performEquals(String number1, String number2) {

        //Getting 1% from a number (99% = 0.99)
        if (number1.isEmpty() && number2.endsWith("%")) {
            String input = number2.substring(0, number2.length() - 1);
            double result = Double.parseDouble(input) / 100;
            return tryToRoundDouble(result);
        }

        //Operations with percent (Например 100+100% = 200)
        else if (number2.endsWith("%")) {
            char sign = number1.charAt(number1.length() - 1);
            String input1 = number1.substring(0, number1.length() - 1);
            String input2 = number2.substring(0, number2.length() - 1);

            return doBasicOperation(input1, input2, sign, true);
        }

        //Basic operations
        else {
            if (number1.isEmpty()) return number2;

            char sign = number1.charAt(number1.length() - 1);
            String input1 = number1.substring(0, number1.length() - 1);

            return doBasicOperation(input1, number2, sign, false);
        }
    }

    //Operations (+, -, *, /)
    public String doBasicOperation(String number1, String number2, char sign, boolean percentEnabled) {

        if (number2.equals("0") || number2.equals("0.0")) return "Infinity Error";

        final double num1 = Double.parseDouble(number1);
        final double num2 = Double.parseDouble(number2);

        double result = 0;

        if (percentEnabled) {
            switch (sign) {
                case ('+'):
                    result = num1 + (num2 / 100.0 * num1);
                    break;
                case ('-'):
                    result = num1 - (num2 / 100.0 * num1);
                    break;
                case ('×'):
                    result = num1 * (num2 / 100.0) * num1;
                    break;
                case ('/'):
                    result = num1 / ((num2 / 100.0) * num1);
                    break;
                default:
                    Log.d("Error", "Error in doBasicOperation");
            }
        } else {
            switch (sign) {
                case ('+'):
                    result = num1 + num2;
                    break;
                case ('-'):
                    result = num1 - num2;
                    break;
                case ('×'):
                    result = num1 * num2;
                    break;
                case ('/'):
                    result = num1 / num2;
                    break;
                default:
                    Log.d("Error", "Error in doBasicOperation");
            }
        }

        return tryToRoundDouble(result);
    }


    public String switchPlusAndMinus(String number) {
        if (number.isEmpty()) return "0";

        if (number.endsWith("Error")) return "0";

        if (number.startsWith("-")) {
            return number.substring(1);
        } else if (Character.isDigit(number.charAt(0))) {
            return ("-" + number);
        } else {
            Log.d("Error", "Error in switchPlusAndMinus");
            return "Error";
        }
    }

    //Rounds a doable if it ends with .0 (5.0 -> 5)
    public String tryToRoundDouble(double number) {
        String sNumber = String.valueOf(number);

        if (sNumber.endsWith(".0")) {
            DecimalFormat newNumber = new DecimalFormat("0.#");
            return newNumber.format(number);
        } else {
            return String.valueOf(number);
        }
    }
}