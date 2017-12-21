package edu.umb.multiweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;

public class AggregateHourlyActivity extends AppCompatActivity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate_hourly);

        test = findViewById(R.id.test);

        for (int i = 0; i < 100; i++) {
            test.append("test" + "\n");
        }



        ButterKnife.bind(this);


    }
}
