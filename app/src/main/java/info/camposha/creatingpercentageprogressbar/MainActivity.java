package info.camposha.creatingpercentageprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PercentageProgressBar percentageProgressBar = findViewById(
            R.id.mPercentageProgressBar);
        percentageProgressBar.setPercent(30);
        percentageProgressBar.setHeightPercent(15);

    }
}
