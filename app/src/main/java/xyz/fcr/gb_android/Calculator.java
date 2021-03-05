package xyz.fcr.gb_android;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {

    public void performEquals(String number1) {
        //
    }

    public String performEquals(String number1, String number2) {
/*        String rawInput = mTopText.getText().toString() + mBottomText.getText().toString();

        //При проценте (Например 87% = 0,87)
        if (rawInput.endsWith("%") && mTopText.getText().toString().isEmpty()) {
            String input = rawInput.substring(0, rawInput.length() - 1);
            double result = Double.parseDouble(input) / 100;
            setView(mBottomText, tryToRoundDouble(result));
        }

        //При операциях на проценте (Например 100+100% = 200)
        else if (rawInput.endsWith(getString(R.string.percent))) {
            String input = rawInput.substring(0, rawInput.length() - 1);

            if (input.contains(getString(R.string.plus)))
                setView(mBottomText, Calc.calcPlus(input, true));
            else if (input.contains(getString(R.string.minus)))
                setView(mBottomText, Calc.calcMinus(input, true));
            else if (input.contains(getString(R.string.multiply)))
                setView(mBottomText, Calc.calcPlus(input, true));
            else if (input.contains(getString(R.string.divide)))
                setView(mBottomText, Calc.calcPlus(input, true));
        }

        //При операциях без процента
        else {
            if (rawInput.contains(getString(R.string.plus)))
                setView(mBottomText, Calc.calcPlus(rawInput, false));
            else if (rawInput.contains(getString(R.string.minus)))
                setView(mBottomText, Calc.calcMinus(rawInput, false));
            else if (rawInput.contains(getString(R.string.multiply)))
                setView(mBottomText, Calc.calcMultiply(rawInput, false));
            else if (rawInput.contains(getString(R.string.divide)))
                setView(mBottomText, Calc.calcDivide(rawInput, false));
        }

        //В конце вычисления и вывода результата обнуляем верхнюю строку
        setView(mTopText, null);*/
        return "123";
    }

    //Базовые операции + - * /
/*
    public String doBasicOperation(String num1, String num2, char sign, boolean percentEnabled) {
*/
/*      final String[] args = input.split("\\+");
        final String[] args = input.split("-");
        final String[] args = input.split("\u00d7");
        final String[] args = input.split("/");

        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);*//*


        double result;

        if (percentEnabled) {
            switch (sign) {
                case ('+'):
                    result = num1 + (num2 / 100.0 * num1);
                    break;
                case ('-'):
                    result = num1 - (num2 / 100.0 * num1);
                    break;
                case ('*'):
                    result = num1 * (num2 / 100.0) * num1;
                    break;
                case ('/'):
                    result = num1 / ((num2 / 100.0) * num1);
                    break;
            }
        } else {
            switch (sign) {
                case ('+'):
                    result = num1 + num2;
                    break;
                case ('-'):
                    result = num1 - num2;
                    break;
                case ('*'):
                    result = num1 * num2;
                    break;
                case ('/'):
                    result = num1 / num2;
                    break;
            }
        }

        return tryToRoundDouble(result);
    }
*/

    public String switchPlusAndMinus(String number) {
        if (number.isEmpty()) return "0";

        if (number.startsWith("-")) {
            return number.substring(1);
        } else if (Character.isDigit(number.charAt(0))) {
            return ("-" + number);
        } else return "Error in switching %";
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