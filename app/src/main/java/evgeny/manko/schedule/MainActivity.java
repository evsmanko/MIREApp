package evgeny.manko.schedule;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.TimeZone;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import hirondelle.date4j.DateTime;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<ArrayList<ArrayList<ArrayList<Lesson>>>> schedule;
    public static DateTime dateTime;
    private DatePickerTimeline timeLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences(StartActivity.SHP_JSON_SCHEDULE_FILE, MODE_PRIVATE);
        if(sPref.getString(StartActivity.SHP_GROUP, "").equals("")){
            startActivity(new Intent(getApplicationContext(), StartActivity.class));
        }
        else{



            dateTime = DateTime.now(TimeZone.getDefault());



            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<ArrayList<ArrayList<ArrayList<Lesson>>>>>(){}.getType();
            String a = sPref.getString(StartActivity.SHP_JSON_SCHEDULE, null);
            schedule = gson.fromJson(a, type);
            setContentView(R.layout.activity_main);
            final ViewPager pager = (ViewPager) findViewById(R.id.schedule_pager);
            timeLine = (DatePickerTimeline) findViewById(R.id.time_line);

            timeLine.setFirstVisibleDate(dateTime.getYear(), dateTime.getMonth()-1, dateTime.getDay());
            timeLine.setLastVisibleDate(2017, 11, 25);
            timeLine.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
                @Override
                public void onDateSelected(int year, int month, int day, int index) {
                    int item = new DateTime(year, month+1, day, 0, 0, 0, 0).getDayOfYear()-
                            dateTime.getDayOfYear();
                    pager.setCurrentItem(item);
                }
            });
            pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    timeLine.setSelectedDate(2017, dateTime.plusDays(position).getMonth()-1,
                            dateTime.plusDays(position).getDay());
                }

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }


    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScheduleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 50;
        }

    }
}
