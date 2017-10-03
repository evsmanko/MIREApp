package evgeny.manko.mireapp;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Arrays;
import java.util.List;

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

    public static final int RC_SIGN_IN = 1;

//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;

//    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
//            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
//            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
//    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

//        mFirebaseAuth = FirebaseAuth.getInstance();


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
                    instChecked.playAnimation();
                    instChecked.setVisibility(View.VISIBLE);
                    instituteNameTv.setText(UniversityInfo.getInstituteNamebyID(instId));
                } else {
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
                            int course = 8 - year;
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(groupNameText.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            courseTv.setText(course + " курс");
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
                helloTv.startAnimation(anim);
                helloTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

//                mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                     User is signed in
//                    Toast.makeText(MainActivity.this, "You're now signed in. Welcome to FriendlyChat.", Toast.LENGTH_SHORT).show();
//                } else {
//                     User is signed out
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setProviders(
//                                            AuthUI.EMAIL_PROVIDER,
//                                            AuthUI.GOOGLE_PROVIDER)
//                                    .build(),
//                            RC_SIGN_IN);
//                }
//            }
//        };


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mAuthStateListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//        }
//    }

    public void launchSignInFlow(View view) {
            Intent intent = new Intent(StartActivity.this, AuthActivity.class);
            startActivity(intent);
    }





}
