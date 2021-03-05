package xyz.fcr.gb_android;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {

    public void performEquals(String number1) {
        //
    }

    public void performEquals(String number1, String number2) {
        String rawInput = mTopText.getText().toString() + mBottomText.getText().toString();

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
        setView(mTopText, null);
    }

    //Базовые операции + - * /
    public static String basicOperations(final String input, final boolean percentEnabled) {
        final String[] args = input.split("\\+");
        final String[] args = input.split("-");
        final String[] args = input.split("\u00d7");
        final String[] args = input.split("/");

        final double num1 = Double.parseDouble(args[0]);
        final double num2 = Double.parseDouble(args[1]);
        double result;
        //+
        if (percentEnabled) {
            result = num1 + num2 / 100.0 * num1;
        } else {
            result = num1 + num2;
        }

        //-
        if (percentEnabled) {
            result = num1 - num2 / 100.0 * num1;
        } else {
            result = num1 - num2;
        }

        //*
        if (percentEnabled) {
            result = num1 * (num2 / 100.0) * num1;
        } else {
            result = num1 * num2;
        }

        // /
        if (percentEnabled) {
            result = num1 / ((num2 / 100.0) * num1);
        } else {
            result = num1 / num2;
        }
        return tryToRoundDouble(result);
    }

    //Rounds a number if it ends with .0 (5.0 -> 5)
    public static String tryToRoundDouble(double number) {
        String sNumber = String.valueOf(number);

        if (sNumber.endsWith(".0")) {
            DecimalFormat newNumber = new DecimalFormat("0.#");
            return newNumber.format(number);
        } else {
            return String.valueOf(number);
        }
    }
}