package com.example.sked;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sked.domain.Institute;
import com.example.sked.service.NetworkService;
import com.example.sked.service.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private List<Institute> institutesFromServer;
    private List<Institute> myInstitutes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onClick();
        onSwipe();
        onTextChanged();
        getInstitutesFromServer();

    }

    private void getInstitutesFromServer() {
        showGifLoad();
        NetworkService.getInstance()
                .getInstitutionApi()
                .getInstitutes()
                .enqueue(new Callback<List<Institute>>() {
                    @Override
                    public void onResponse(Call<List<Institute>> call, Response<List<Institute>> response) {
                        if (response.isSuccessful()) {
                            institutesFromServer = response.body();
                            hideGifLoad();
                        } else {
                            hideGifLoad();
                            Toast.makeText(MainActivity.this, "Повторіть спробу ще раз.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Institute>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void showGifLoad() {
        ImageView image = findViewById(R.id.gif_load);
        image.setVisibility(View.VISIBLE);
    }

    private void hideGifLoad() {
        ImageView image = findViewById(R.id.gif_load);
        image.setVisibility(View.GONE);
    }

    //click and swipe ------------------------------------------------------------------------------
    public void onClick() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_add_institute:
                        showLayoutInstitute();
                        break;
                    case R.id.btn_clear_search_institute:
                        EditText searchInstitute = findViewById(R.id.searchInstitute);
                        searchInstitute.setText("");
                }
            }
        };
        ImageButton btnAddInstitute = findViewById(R.id.btn_add_institute);
        btnAddInstitute.setOnClickListener(onClickListener);

        ImageButton btnClearSearchInstitute = findViewById(R.id.btn_clear_search_institute);
        btnClearSearchInstitute.setOnClickListener(onClickListener);

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

    public void onTextChanged() {
        final EditText searchInstitute = findViewById(R.id.searchInstitute);
        searchInstitute.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String instituteName = searchInstitute.getText().toString().toUpperCase();
                fillInstitutes(instituteName);
            }
        });
    }

    private void fillInstitutes(String instituteNameEntered) {
        if (instituteNameEntered.isEmpty()) {
            outputInstitutes(new String[0]);
            return;
        }
        if (institutesFromServer == null) {
            Toast.makeText(MainActivity.this, "Обновите.", Toast.LENGTH_LONG).show();
            return;
        }

        List<Institute> institutes = new ArrayList<>();
        for (Institute institute : institutesFromServer) {
            String instituteName = institute.getName().toUpperCase();

            if (instituteName.contains(instituteNameEntered)) {
                institutes.add(institute);
            }
        }

        String[] instituteNames = new String[institutes.size()];

        int i = 0;
        for (Institute institute : institutes) {
            instituteNames[i] = institute.getName();
            i++;
        }

        outputInstitutes(instituteNames);
    }

    private void outputInstitutes(final String[] institutes) {
        ListView listInstitutes = findViewById(R.id.listInstitutes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, institutes);
        listInstitutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String instituteName = institutes[position];
                if(saveInstitute(instituteName)) {
                    outputMyInstitutes();
                    Toast.makeText(MainActivity.this, "Інститут збережено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ви вже маєте цей інститут", Toast.LENGTH_LONG).show();
                }
            }
        });
        listInstitutes.setAdapter(adapter);
    }

    private void outputMyInstitutes() {
        String[] institutes = new String[myInstitutes.size()];
        int i = 0;
        for (Institute institute : myInstitutes) {
            institutes[i] = institute.getName();
            i++;
        }

        ListView listInstitutes = findViewById(R.id.listMyInstitutes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, institutes);
        listInstitutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listInstitutes.setAdapter(adapter);
    }

    private boolean saveInstitute(String name) {
        for (Institute institute : myInstitutes) {
            if (institute.getName().equals(name)) {
                return false;
            }
        }
        for (Institute institute : institutesFromServer) {
            if (institute.getName().equals(name)) {
                myInstitutes.add(institute);
                return true;
            }
        }
        return false;
    }

    //anim -----------------------------------------------------------------------------------------
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

    //close app ------------------------------------------------------------------------------------
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


//searchInstitute.addTextChangedListener(new TextWatcher() {
//@Override
//public void afterTextChanged(Editable s) {
//        // TODO Auto-generated method stub
//        }
//
//@Override
//public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        // TODO Auto-generated method stub
//        }
//
//@Override
//public void onTextChanged(CharSequence s, int start, int before, int count) {
//        System.out.println("asdfasdf");
//        }
//        });


//
//
//    List<String> institutes = new ArrayList<>();
//
//    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
//            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
//
////                for (Institute institute : institutesFromServer) {
////                    String instituteName = institute.getName().toUpperCase();
////                    String instituteNameEntered = searchInstitute.getText().toString().toUpperCase();
////
////                    if (instituteName.contains(instituteNameEntered)) {
////                        institutes.add(institute);
////                    }
////                }
//                System.out.println("*****************************************");
//
//                        String instituteNameEntered = searchInstitute.getText().toString().toUpperCase();
//                        if (!instituteNameEntered.isEmpty()) {
//                        for (int i = 0; i < names.length; i++) {
//        if (names[i].toUpperCase().contains(instituteNameEntered)) {
//        institutes.add(names[i]);
//        System.out.println("========================== names[" + i + "]=" + names[i] + "==" + instituteNameEntered);
//        }
//        }
//
//        String[] instituteNames = new String[institutes.size()];
//
//        int i = 0;
//        for (String institute : institutes) {
//        instituteNames[i] = institute;
//        i++;
//        }
//
//        outputInstitutes(instituteNames);
//        }

