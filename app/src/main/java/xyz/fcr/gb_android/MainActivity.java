package xyz.fcr.gb_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopText = findViewById(R.id.topText);
        mBottomText = findViewById(R.id.bottomText);

        mButtonAC = findViewById(R.id.buttonAC);
        mButtonDel = findViewById(R.id.buttonDel);
        mButtonPercent = findViewById(R.id.buttonPercent);
        mButtonEquals = findViewById(R.id.buttonEquals);
        mButtonDot = findViewById(R.id.buttonDot);

        mButtonPlus = findViewById(R.id.buttonPlus);
        mButtonMinus = findViewById(R.id.buttonMinus);
        mButtonMultiply = findViewById(R.id.buttonMultiply);
        mButtonDivide = findViewById(R.id.buttonDivide);

        mButton000 = findViewById(R.id.button000);
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


        mButtonAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopText.setText("");
                mBottomText.setText("0");
            }
        });

        mButtonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomText.getText().length() > 1) {
                    String newText = mBottomText.getText().toString();
                    mBottomText.setText(newText.substring(0, newText.length() - 1));
                } else mBottomText.setText("0");
            }
        });

        //Можно и зажимать кнопку DEL вместо AC
        mButtonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(5);

                mTopText.setText("");
                mBottomText.setText("0");
                return true;
            }
        });

        mButtonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTopText.getText().toString().contains("%")) {
                    int numberSize = mBottomText.getText().toString().length();
                    if (mBottomText.getText().toString().charAt(numberSize - 1) != '%')
                        mBottomText.append("%");
                }
            }
        });

        mButtonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performResult();
            }
        });

        mButtonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                String number = mBottomText.getText().toString();

                for (int i = 0; i < number.length(); i++) {
                    if (number.charAt(i) == '.') counter++;
                }

                if (!(mBottomText.getText().toString().endsWith("%"))) {
                    if (counter == 0) mBottomText.append(".");
                }
            }
        });


        //Базовые операции
        mButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToHistory("+");
            }
        });

        mButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToHistory("-");
            }
        });

        mButtonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToHistory("×");
            }
        });

        mButtonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToHistory("/");
            }
        });

        //Цифры
        mButton000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    enterNumber(mButton0.getText().toString());
                }
            }
        });

        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton0.getText().toString());
            }
        });
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton1.getText().toString());
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton2.getText().toString());
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton3.getText().toString());
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton4.getText().toString());
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton5.getText().toString());
            }
        });
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton6.getText().toString());
            }
        });
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton7.getText().toString());
            }
        });
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton8.getText().toString());
            }
        });
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber(mButton9.getText().toString());
            }
        });
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

    @SuppressLint("SetTextI18n")
    public static void setView(TextView view, int input) {
        view.setText(input + "");
    }
}

