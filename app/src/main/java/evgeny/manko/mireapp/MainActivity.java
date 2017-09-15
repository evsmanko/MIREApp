package evgeny.manko.mireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, StartActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
