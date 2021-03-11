package xyz.fcr.gb_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTopText;
    private TextView mBottomText;

    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopText = findViewById(R.id.topText);
        mBottomText = findViewById(R.id.bottomText);

        Button mButtonAC = findViewById(R.id.buttonAC);
        Button mButtonDel = findViewById(R.id.buttonDel);
        Button mButtonPercent = findViewById(R.id.buttonPercent);
        Button mButtonEquals = findViewById(R.id.buttonEquals);
        Button mButtonDot = findViewById(R.id.buttonDot);

        Button mButtonPlus = findViewById(R.id.buttonPlus);
        Button mButtonMinus = findViewById(R.id.buttonMinus);
        Button mButtonMultiply = findViewById(R.id.buttonMultiply);
        Button mButtonDivide = findViewById(R.id.buttonDivide);

        Button mButton000 = findViewById(R.id.button000);
        mButton0 = findViewById(R.id.button0);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);
        mButton8 = findViewById(R.id.button8);
        mButton9 = findViewById(R.id.button9);

        mButtonAC.setOnClickListener(v -> {
            mTopText.setText("");
            mBottomText.setText("0");
        });

        mButtonDel.setOnClickListener(v -> {
            if (mBottomText.getText().length() > 1) {
                String newText = mBottomText.getText().toString();
                mBottomText.setText(newText.substring(0, newText.length() - 1));
            } else mBottomText.setText("0");
        });

        //Можно и зажимать кнопку DEL вместо AC
        mButtonDel.setOnLongClickListener(v -> {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(5);

            mTopText.setText("");
            mBottomText.setText("0");
            return true;
        });

        mButtonPercent.setOnClickListener(v -> {
            if (!mTopText.getText().toString().contains("%")) {
                int numberSize = mBottomText.getText().toString().length();
                if (mBottomText.getText().toString().charAt(numberSize - 1) != '%')
                    mBottomText.append("%");
            }
        });

        mButtonEquals.setOnClickListener(v -> performResult());

        mButtonDot.setOnClickListener(v -> {
            int counter = 0;
            String number = mBottomText.getText().toString();

            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == '.') counter++;
            }

            if (!(mBottomText.getText().toString().endsWith("%"))) {
                if (counter == 0) mBottomText.append(".");
            }
        });


        //Базовые операции
        mButtonPlus.setOnClickListener(v -> copyToHistory("+"));

        mButtonMinus.setOnClickListener(v -> copyToHistory("-"));

        mButtonMultiply.setOnClickListener(v -> copyToHistory("×"));

        mButtonDivide.setOnClickListener(v -> copyToHistory("/"));

        //Цифры
        mButton000.setOnClickListener(v -> {
            for (int i = 0; i < 3; i++) {
                enterNumber(mButton0.getText().toString());
            }
        });

        mButton0.setOnClickListener(v -> enterNumber(mButton0.getText().toString()));
        mButton1.setOnClickListener(v -> enterNumber(mButton1.getText().toString()));
        mButton2.setOnClickListener(v -> enterNumber(mButton2.getText().toString()));
        mButton3.setOnClickListener(v -> enterNumber(mButton3.getText().toString()));
        mButton4.setOnClickListener(v -> enterNumber(mButton4.getText().toString()));
        mButton5.setOnClickListener(v -> enterNumber(mButton5.getText().toString()));
        mButton6.setOnClickListener(v -> enterNumber(mButton6.getText().toString()));
        mButton7.setOnClickListener(v -> enterNumber(mButton7.getText().toString()));
        mButton8.setOnClickListener(v -> enterNumber(mButton8.getText().toString()));
        mButton9.setOnClickListener(v -> enterNumber(mButton9.getText().toString()));
    }

    public void enterNumber(String number) {
        if (!(mBottomText.getText().toString().endsWith(getString(R.string.percent)))) {
            if (mBottomText.getText().toString().equals("0")) mBottomText.setText(number);
            else mBottomText.append(number);
        }
    }

    public void copyToHistory(String operationSign) {
        String history = mBottomText.getText().toString();

        if (!(history.endsWith("%"))) {
            if (mTopText.getText().toString().isEmpty()) {
                history += operationSign;
                setView(mTopText, history);
                setView(mBottomText, "0");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void performResult() {
        String rawInput = mTopText.getText().toString() + mBottomText.getText().toString();

        //При проценте (Например 87% = 0,87)
        if (rawInput.endsWith("%") && mTopText.getText().toString().isEmpty()) {
            String input = rawInput.substring(0, rawInput.length() - 1);
            double result = Double.parseDouble(input) / 100;
            setView(mBottomText, result);
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

    //Методы вывода результата на поле
    public static void setView(TextView view, String input) {
        view.setText(input);
    }

    @SuppressLint("SetTextI18n")
    public static void setView(TextView view, double input) {
        view.setText(input + "");

    }
}

