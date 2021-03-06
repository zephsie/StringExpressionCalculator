package com.example.stringexpressioncalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private AdRequest adRequest;

    /**
     * Input items.
     */
    ArrayList<String> inputItems = new ArrayList<>();

    /**
     * Sets the length of the output string.
     */
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

        MobileAds.initialize(this, initializationStatus -> {
        });

        adRequest = new AdRequest.Builder().build();

        loadAd();
    }

    private void loadAd() {
        InterstitialAd.load(this,"\n" +
                        "ca-app-pub-6240815819353431/1683112214", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;

                                loadAd();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    /**
     * On click start.
     *
     * @param view pressed button.
     */
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClickStart(View view) {
        int id = view.getId();

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final VibrationEffect vibrationEffect;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK);
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect);
        }

        IExpressionChecker expressionChecker = new ExpressionChecker();

        TextView input = findViewById(R.id.input);
        TextView output = findViewById(R.id.output);
        HorizontalScrollView scroll = findViewById(R.id.scroll_inp);

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
                String currentItem = ((TextView) findViewById(id)).getText().toString();

                if (expressionChecker.check(inputItems, currentItem)) {
                    inputItems.add(currentItem);
                }
            }
        }

        StringBuilder items = new StringBuilder();

        for (String inputItem : inputItems) {
            items.append(inputItem);
        }

        input.setText(items.toString());

        scroll.post(() -> scroll.fullScroll(View.FOCUS_RIGHT));

        ICalculator calculator = new Calculator();

        AnswerFormat answerFormat = new AnswerFormat();

        String outputResult;

        if (items.length() > 0) {
            try {
                double result = calculator.calculate(items.toString()
                        .replace("e", "" + Math.E)
                        .replace("??", "" + Math.PI)
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

        if (id == R.id.solve && mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
        }
    }
}
