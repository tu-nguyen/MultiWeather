package edu.umb.multiweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.ButterKnife;

public class CompareCurrentActivity extends AppCompatActivity {
    final String DEGREE  = "\u00b0";
    final int MEAN = 0;
    final int MAX = 1;
    final int MIN = 2;

    private int mode = 0;

    DecimalFormat f = new DecimalFormat("#0.00");

    private TextView modeText;
    private TextView tempText0;
    private TextView tempText1;
    private TextView tempText2;
    private TextView tempText3;
    private TextView popText0;
    private TextView popText1;
    private TextView popText2;
    private TextView popText3;
    private TextView windText0;
    private TextView windText1;
    private TextView windText2;
    private TextView windText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_current);

        modeText = findViewById(R.id.modeText);
        tempText0 = findViewById(R.id.temp0);
        tempText1 = findViewById(R.id.temp1);
        tempText2 = findViewById(R.id.temp2);
        tempText3 = findViewById(R.id.temp3);
        popText0 = findViewById(R.id.pop0);
        popText1 = findViewById(R.id.pop1);
        popText2 = findViewById(R.id.pop2);
        popText3 = findViewById(R.id.pop3);
        windText0 = findViewById(R.id.wind0);
        windText1 = findViewById(R.id.wind1);
        windText2 = findViewById(R.id.wind2);
        windText3 = findViewById(R.id.wind3);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        double accuTemp = intent.getDoubleExtra("0temp", 0);
        double accuPop = intent.getDoubleExtra("0pop", 0);
        double accuWind = intent.getDoubleExtra("0wind", 0);

        double darkTemp = intent.getDoubleExtra("1temp", 0);
        double darkPop = intent.getDoubleExtra("1pop", 0);
        double darkWind = intent.getDoubleExtra("1wind", 0);

        double wuTemp = intent.getDoubleExtra("2temp", 0);
        double wuPop = intent.getDoubleExtra("2pop", 0);
        double wuWind = intent.getDoubleExtra("2wind", 0);

        double noaaTemp = intent.getDoubleExtra("3temp", 0);
        double noaaPop = intent.getDoubleExtra("3pop", 0);
        double noaaWind = intent.getDoubleExtra("3wind", 0);

        tempText0.setText(accuTemp + DEGREE + "F");
        popText0.setText(accuPop + "%");
        windText0.setText(accuWind + "mph");
        tempText1.setText(darkTemp + DEGREE + "F");
        popText1.setText(darkPop + "%");
        windText1.setText(darkWind + "mph");
        tempText2.setText(wuTemp + DEGREE + "F");
        popText2.setText(wuPop + "%");
        windText2.setText(wuWind + "mph");
        tempText3.setText(noaaTemp + DEGREE + "F");
        popText3.setText(noaaPop + "%");
        windText3.setText(noaaWind + "mph");
    }
}
