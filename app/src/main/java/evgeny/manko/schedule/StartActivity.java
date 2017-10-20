package evgeny.manko.schedule;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import evgeny.manko.schedule.utils.Translit;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.start_groupname_text) MaterialEditText groupNameText;
    
    @BindView(R.id.helloTetView) TextView helloTv;
    @BindView(R.id.start_input_layout) LinearLayout inputLayout;
    
//    Lottie views
    @BindView(R.id.checked_inst_anim) LottieAnimationView instChecked;
    @BindView(R.id.checked_type_anim) LottieAnimationView typeChecked;
    @BindView(R.id.checked_course_anim) LottieAnimationView courseChecked;
    @BindView(R.id.animation_view) LottieAnimationView animationView;
    @BindView(R.id.dev_vk_tv) TextView deVkTV;

    @BindView(R.id.start_inst_tv) TextView instituteNameTv;
    @BindView(R.id.start_type_tv) TextView typeTv;
    @BindView(R.id.start_course_tv) TextView courseTv;

    @BindView(R.id.start_fab_next) FloatingActionButton fabNext;

    private SharedPreferences sPref;

    public static final int RC_SIGN_IN = 1;
    public static final String SHP_JSON_SCHEDULE_FILE = "schedule";
    public static final String SHP_JSON_SCHEDULE = "schedule";
    public static final String SHP_GROUP = "group";
    private String groupName = "";
    private static int instId;
    private static int course;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        deVkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri address = Uri.parse("https://vk.com/id179899596");
                startActivity(new Intent(Intent.ACTION_VIEW, address));
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                groupName = groupNameText.getText().toString();

                animationView.setAnimation("loading.json");
                animationView.loop(true);
                animationView.playAnimation();
                new ParseTask().execute();

                fabNext.hide();

            }
        });

        final Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inputLayout.startAnimation(anim2);
                inputLayout.setVisibility(View.VISIBLE);
                deVkTV.startAnimation(anim2);
                deVkTV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        
        fabNext.hide();

        groupNameText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        groupNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            
            int last = 0;
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 10) fabNext.hide();
                if(charSequence.length() > 0) {
                    instId = UniversityInfo.getInstituteIDbyGroup(charSequence.toString());
                    if(charSequence.length()==1){
                        instChecked.playAnimation();
                        instChecked.setVisibility(View.VISIBLE);
                    }

                    instituteNameTv.setText(UniversityInfo.getInstituteNamebyID(instId));
                }
                    if (charSequence.length() == 3) {
                        char c = charSequence.charAt(2);
                        typeChecked.playAnimation();
                        typeChecked.setVisibility(View.VISIBLE);
                        switch (c) {
                            case 'Б':
                                typeTv.setText("Бакалавриат");
                                break;
                            case 'С':
                                typeTv.setText("Специалитет");
                                break;
                        }
                    } else if (
                            (charSequence.length() == 4 || charSequence.length() == 7)
                                    && charSequence.length() - 1 == last
                            ) {
                        groupNameText.append("-");

                    } else if (charSequence.length() == 10) {
                        char c = charSequence.charAt(9);
                        if (c >= '0' && c <= '9') {
                            int year = Character.digit(c, 10);
                            course = 8 - year;
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(groupNameText.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            courseTv.setText(course + " курс");
                            courseChecked.playAnimation();
                            courseChecked.setVisibility(View.VISIBLE);
                        }
                    }
                last = charSequence.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==10) fabNext.show();
            }
        });
        animationView.setTag(R.id.ANIMATION_STATE, 0);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if((int)animationView.getTag(R.id.ANIMATION_STATE)==1){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    helloTv.startAnimation(anim);
                    helloTv.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void launchSomething(View view) {
        String groupTopic = groupNameText.getText().toString();
        FirebaseMessaging.getInstance().subscribeToTopic(Translit.toTranslit(groupTopic));
        Log.d("TOPIC ", Translit.toTranslit(groupTopic));
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params){
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL(UniversityInfo.getScheduleURLbyID(instId, course));

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
                error = false;
            } catch (Exception e) {
                error = true;
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            if(!error){
                Gson gson = new Gson();

                Type type = new TypeToken<HashMap<String, ArrayList<ArrayList<ArrayList<ArrayList<Lesson>>>>>>(){}.getType();
                HashMap<String, ArrayList<ArrayList<ArrayList<ArrayList<Lesson>>>>> map = gson.fromJson(strJson, type);

                sPref = getSharedPreferences(SHP_JSON_SCHEDULE_FILE, MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                String a = gson.toJson(map.get(groupName));
                ed.putString(SHP_JSON_SCHEDULE, a);
                ed.putString(SHP_GROUP, groupName);
                ed.commit();

                animationView.setAnimation("success.json");
                animationView.loop(false);
                animationView.setTag(R.id.ANIMATION_STATE, 1);
                animationView.setSpeed(0.5f);
                animationView.playAnimation();
            }else{
                animationView.setAnimation("error.json");
                animationView.loop(false);
                animationView.playAnimation();
            }





        }
    }


    @Override
    public void onBackPressed() {

        finishAffinity();

    }
}