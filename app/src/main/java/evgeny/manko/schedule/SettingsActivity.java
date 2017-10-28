package evgeny.manko.schedule;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ListView settingLV = (ListView) findViewById(R.id.setting_lv);
        settingLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new String[]{"Написать разработчику", "Оставить отзыв", "Выбрать другую группу"}));

        settingLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                switch (i){
                    case 0: intent.setData(Uri.parse("https://vk.com/evgenymanko"));
                        startActivity(intent);
                        break;
                    case 1: intent.setData(Uri.parse("market://details?id=evgeny.manko.schedule"));
                        startActivity(intent);
                        break;
                    case 2: getSharedPreferences(StartActivity.SHP_JSON_SCHEDULE_FILE, MODE_PRIVATE)
                            .edit().clear().apply();
                        Intent intent1 = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }
}
