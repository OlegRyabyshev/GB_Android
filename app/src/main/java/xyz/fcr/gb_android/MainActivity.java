package xyz.fcr.gb_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTopText;
    private TextView mBottomText;
    private Calculator calculator;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            return true;
        }

        if (id == R.id.action_theme) {
            showOptionsDialog();
            return true;
        }

        if (id == R.id.action_about) {
            setContentView(R.layout.about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.layout.About, menu);
        return true;
    }

    private void showOptionsDialog() {
        final String[] themes = {
                getResources().getString(R.string.action_theme_light),
                getResources().getString(R.string.action_theme_dark),
                getResources().getString(R.string.action_theme_default)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.action_theme));

        builder.setSingleChoiceItems(themes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                else if (which == 1)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
        });

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopText = findViewById(R.id.topText);
        mBottomText = findViewById(R.id.bottomText);

        final Button mButtonAC = findViewById(R.id.buttonAC);
        final Button mButtonDel = findViewById(R.id.buttonDel);
        final Button mButtonPercent = findViewById(R.id.buttonPercent);
        final Button mButtonEquals = findViewById(R.id.buttonEquals);
        final Button mButtonDot = findViewById(R.id.buttonDot);
        final Button mButtonPlusMinus = findViewById(R.id.buttonPlusMinus);

        final Button mButtonPlus = findViewById(R.id.buttonPlus);
        final Button mButtonMinus = findViewById(R.id.buttonMinus);
        final Button mButtonMultiply = findViewById(R.id.buttonMultiply);
        final Button mButtonDivide = findViewById(R.id.buttonDivide);

        final Button mButton0 = findViewById(R.id.button0);
        final Button mButton1 = findViewById(R.id.button1);
        final Button mButton2 = findViewById(R.id.button2);
        final Button mButton3 = findViewById(R.id.button3);
        final Button mButton4 = findViewById(R.id.button4);
        final Button mButton5 = findViewById(R.id.button5);
        final Button mButton6 = findViewById(R.id.button6);
        final Button mButton7 = findViewById(R.id.button7);
        final Button mButton8 = findViewById(R.id.button8);
        final Button mButton9 = findViewById(R.id.button9);


        mButtonPlusMinus.setOnClickListener(v -> {
            if (textFromBottomView().isEmpty()) return;

            String input = textFromBottomView();
            if (input.startsWith("-")) {
                mBottomText.setText(input.substring(1));
            } else if (Character.isDigit(input.charAt(0))) {
                input = "-" + input;
                mBottomText.setText(input);
            }
        });

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
            if (vibe.hasVibrator()) {
                long[] pattern = {50, 500};
                vibe.vibrate(pattern, -1);
            }

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

        mButtonEquals.setOnClickListener(v -> Calc.performResult());

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

        mButtonMinus.setOnClickListener(v -> {
            if (textFromBottomView().equals("0")) setView(mBottomText, "-0");
            else copyToHistory("-");
        });

        mButtonMultiply.setOnClickListener(v -> copyToHistory("×"));
        mButtonDivide.setOnClickListener(v -> copyToHistory("/"));

        //Цифры
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
        if (!(textFromBottomView().endsWith(getString(R.string.percent)))) {
            if (textFromBottomView().equals("0")) setView(mBottomText, number);
            else if (textFromBottomView().equals("-0")) setView(mBottomText, "-" + number);
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


    //Методы для вытаскивания текстов из вьюшек
    public String textFromTopView() {
        return mTopText.getText().toString();
    }

    public String textFromBottomView() {
        return mBottomText.getText().toString();
    }

    //Методы вывода результата на поле
    public static void setView(TextView view, String input) {
        view.setText(input);
    }

}

