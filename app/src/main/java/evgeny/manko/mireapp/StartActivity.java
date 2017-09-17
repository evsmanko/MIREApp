package evgeny.manko.mireapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.start_groupname_text) MaterialEditText groupNameText;
    
    @BindView(R.id.helloTetView) TextView helloTv;
    @BindView(R.id.start_input_layout) LinearLayout inputLayout;
    
//    Lottie views
    @BindView(R.id.checked_inst_anim) LottieAnimationView instChecked;
    @BindView(R.id.checked_type_anim) LottieAnimationView typeChecked;
    @BindView(R.id.checked_course_anim) LottieAnimationView courseChecked;
    @BindView(R.id.animation_view) LottieAnimationView animationView;

    @BindView(R.id.start_inst_tv) TextView instituteNameTv;
    @BindView(R.id.start_type_tv) TextView typeTv;
    @BindView(R.id.start_course_tv) TextView courseTv;

    @BindView(R.id.start_fab_next) FloatingActionButton fabNext;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

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
                if(charSequence.length() == 1) {
                    int instId = UniversityInfo.getInstituteIDbyCharGroup(charSequence.charAt(0));
                    if (instId!=-1) {
                        instChecked.playAnimation();
                        instChecked.setVisibility(View.VISIBLE);
                        instituteNameTV.setText(UniversityInfo.getInstituteNamebyID(instId));

                    } else {
                        groupNameText.setError("Институт не сущевствует");
                    }
                } else {
                    if (charSequence.length() == 3) {
                        char c = charSequence.charAt(2);
                        if (c == 'Б') {
                            typeChecked.playAnimation();
                            typeChecked.setVisibility(View.VISIBLE);
                            typeTV.setText("Бакалавриат");
                        } else if (c == 'С') {
                            typeChecked.playAnimation();
                            typeChecked.setVisibility(View.VISIBLE);
                            typeTV.setText("Специалитет");
                        }
                    } else if ((charSequence.length() == 4 || charSequence.length() == 7) & charSequence.length() - 1 == last)
                        groupNameText.append("-");
                    else if (charSequence.length() == 10) {
                        char c = charSequence.charAt(9);
                        if (c >= '0' && c <= '9') {
                            int year = Character.digit(c, 10);
                            int course = 8 - year;
                            courseTV.setText(course + " курс");
                            courseChecked.playAnimation();
                            courseChecked.setVisibility(View.VISIBLE);
                        }
                    }
                }
                last = charSequence.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==10) fabNext.show();
            }
        });

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                helloTV.startAnimation(anim);
                helloTV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
