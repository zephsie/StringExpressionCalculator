package com.example.stringexpressioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> inputItems = new ArrayList<>();

    public static final int SPACE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        onWindowFocusChanged(true);

        if (Build.VERSION.SDK_INT >= 28) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClickStart(View view) {
        int id = view.getId();

        TextView input = findViewById(R.id.input);
        TextView output = findViewById(R.id.output);

        output.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.baseText));

        switch (id) {
            case R.id.deleteAll: {
                inputItems.clear();
                output.setText("");
                break;
            }
            case R.id.delete: {
                if (!inputItems.isEmpty()) {
                    inputItems.remove(inputItems.size() - 1);
                }
                break;
            }
            case R.id.solve: {
                output.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.largeText));
                break;
            }
            default: {
                inputItems.add(((TextView) findViewById(id)).getText().toString());
            }
        }

        StringBuilder items = new StringBuilder();

        for (String inputItem : inputItems) {
            items.append(inputItem);
        }

        input.setText(items.toString());

            ICalculator calculator = new Calculator();

            AnswerFormat answerFormat = new AnswerFormat();

            String outputResult;

            if (items.length() > 0) {
                try {
                    double result = calculator.calculate(items.toString()
                            .replace("e", "" + Math.E)
                            .replace(Html.fromHtml("&#960;"), "" + Math.PI)
                            .replace(",", "."));

                    if (Double.isInfinite(result)) {
                        outputResult = "Infinity";
                    } else if (Double.isNaN(result)) {
                        outputResult = "NaN";
                    } else {
                        outputResult = answerFormat.getFormattedAnswer(result, SPACE);
                    }
                } catch (CalculatorException e) {
                    outputResult = e.getMessage();
                }
            } else {
                outputResult = "";
            }

            output.setText(outputResult);
    }
}
