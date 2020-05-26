package com.example.sked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sked.api.InstituteApi;
import com.example.sked.domain.Institute;
import com.example.sked.service.NetworkService;
import com.example.sked.service.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onClick();
        onSwipe();
    }

    public void onClick() {
        ImageButton btnAddInstitute = findViewById(R.id.btn_add_institute);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_add_institute:
                        showLayoutInstitute();
                        break;

                }
            }
        };
        btnAddInstitute.setOnClickListener(onClickListener);
    }

    private void showLayoutInstitute() {
        LinearLayout footer = findViewById(R.id.layout_footer);
        RelativeLayout institute = findViewById(R.id.layout_institute);
        if (institute.getVisibility() == View.GONE) {
            institute.setVisibility(View.VISIBLE);
            final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_institute);
            footer.startAnimation(show);
        }
    }

    private void hideLayoutInstitute() {
        LinearLayout footer = findViewById(R.id.layout_footer);
        final RelativeLayout institute = findViewById(R.id.layout_institute);
        if (institute.getVisibility() == View.VISIBLE) {
            final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_institute);
            hide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    institute.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            footer.startAnimation(hide);
        }
    }

    public void onSwipe() {
        View footer = findViewById(R.id.layout_footer);
        footer.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                showLayoutInstitute();
            }
            @Override
            public void onSwipeDown() {
                super.onSwipeUp();
                hideLayoutInstitute();
            }
        });
    }


    private void getInstitutesFromServer() {
        NetworkService.getInstance()
                .getInstitutionApi()
                .getInstitutes()
                .enqueue(new Callback<List<Institute>>() {
                    @Override
                    public void onResponse(Call<List<Institute>> call, Response<List<Institute>> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.body().size());
                        } else {
                            System.out.println(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Institute>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closeApp() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Натисніть ще раз, щоб вийти", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}


//    public void onSwipe() {
//        View layout = findViewById(R.id.layout_wrapper_institute);
//        layout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
//
//            @Override
//            public void onClick() {
//                super.onClick();
//                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onDoubleClick() {
//                super.onDoubleClick();
//                Toast.makeText(MainActivity.this, "double click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onLongClick() {
//                super.onLongClick();
//                Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeUp() {
//                super.onSwipeUp();
//                Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeDown() {
//                super.onSwipeDown();
//                Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }