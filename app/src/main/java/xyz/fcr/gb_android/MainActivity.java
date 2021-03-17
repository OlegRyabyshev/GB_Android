package xyz.fcr.gb_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTopText;
    private TextView mBottomText;
    private final Calculator calculator = new Calculator();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_theme) {
            showOptionsDialog();
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void showOptionsDialog() {
        final String[] themes = {
                getResources().getString(R.string.action_theme_light),
                getResources().getString(R.string.action_theme_dark)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.action_theme));

        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int themeInt = getCurrentTheme();

        builder.setSingleChoiceItems(themes, themeInt, (dialog, which) -> {
            if (which == 0) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putString("calculator_theme", "light");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putString("calculator_theme", "dark");
            }
            editor.apply();
        });

        builder.show();
    }

    private int getCurrentTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                return 0;
            case Configuration.UI_MODE_NIGHT_YES:
                return 1;
            default:
                return -1;
        }
    }

    private void setThemeFromSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyUserPref", Context.MODE_PRIVATE);
        String savedTheme = sharedPreferences.getString("calculator_theme", "");

        if (savedTheme.equals("light")) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if (savedTheme.equals("dark")) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("topViewState", textFromTopView());
        savedInstanceState.putString("bottomViewState", textFromBottomView());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setThemeFromSharedPreferences();

        mTopText = findViewById(R.id.topText);
        mBottomText = findViewById(R.id.bottomText);

        if (savedInstanceState != null) {
            mTopText.setText(savedInstanceState.getString("topViewState"));
            mBottomText.setText(savedInstanceState.getString("bottomViewState"));
        }

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

        //Buttons with functions
        mButtonAC.setOnClickListener(v -> {
            setView(mTopText, "");
            setView(mBottomText, "0");
        });

        mButtonDel.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            if (textFromBottomView().length() > 1) {
                String input = textFromBottomView();
                setView(mBottomText, input.substring(0, input.length() - 1));
            } else {
                setView(mBottomText, "0");
            }
        });

        mButtonDel.setOnLongClickListener(v -> {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibe.hasVibrator()) {
                long[] pattern = {50, 500};
                vibe.vibrate(pattern, -1);
            }

            setView(mBottomText, "0");
            return true;
        });

        mButtonPercent.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            if (!mTopText.getText().toString().contains("%")) {
                int numberSize = mBottomText.getText().toString().length();
                if (mBottomText.getText().toString().charAt(numberSize - 1) != '%')
                    mBottomText.append("%");
            }
        });

        mButtonEquals.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            String result = calculator.performEquals(textFromTopView(), textFromBottomView());
            setView(mBottomText, result);
            setView(mTopText, "");
        });

        mButtonDot.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            int counter = 0;
            String number = mBottomText.getText().toString();

            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == '.') counter++;
            }

            if (!(mBottomText.getText().toString().endsWith("%"))) {
                if (counter == 0) mBottomText.append(".");
            }
        });

        mButtonPlusMinus.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            setView(mBottomText, calculator.switchPlusAndMinus(textFromBottomView()));
        });


        //Buttons with basic operations (+, -, x, /)
        mButtonPlus.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            moveToTopView("+");
        });

        mButtonMinus.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            if (textFromBottomView().equals("0")) {
                setView(mBottomText, "-0");
            } else moveToTopView("-");
        });

        mButtonMultiply.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            moveToTopView("×");
        });

        mButtonDivide.setOnClickListener(v -> {
            if (bottomViewHasAnError()) return;

            moveToTopView("/");
        });

        //Buttons with numbers
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
        if (bottomViewHasAnError()) setView(mBottomText, number);

        if (!(textFromBottomView().endsWith(getString(R.string.percent)))) {
            if (textFromBottomView().equals("0")) setView(mBottomText, number);
            else if (textFromBottomView().equals("-0")) setView(mBottomText, "-" + number);
            else mBottomText.append(number);
        }
    }

    public void moveToTopView(String operationSign) {
        String history = textFromBottomView();

        if (!(history.endsWith("%")) && textFromTopView().isEmpty()) {
            history += operationSign;
            setView(mTopText, history);
            setView(mBottomText, "0");
        }
    }

    //Error checking
    public boolean bottomViewHasAnError() {
        if (textFromBottomView().endsWith("Error")) {
            setView(mBottomText, "0");
            return true;
        }
        return false;
    }

    //Extract text from views
    public String textFromTopView() {
        return mTopText.getText().toString();
    }

    public String textFromBottomView() {
        return mBottomText.getText().toString();
    }

    //Setter for view
    public static void setView(TextView view, String input) {
        view.setText(input);
    }
}

