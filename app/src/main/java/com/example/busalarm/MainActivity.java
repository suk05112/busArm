package com.example.busalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    busFragment busFrag;
    favoriteFragment favFrag;
    busStopFragment stopFrag;
    FragmentTransaction transaction;

    Button findbtn;
    EditText findedt;
    //String str= findedt.getText().toString();//EditText에 작성된 Text얻어오기
    TextView text;

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findedt = (EditText)findViewById(R.id.findedt);
        findbtn = (Button)findViewById(R.id.findbtn);
        //text = (TextView)findViewById(R.id.text);

        fragmentManager = getSupportFragmentManager();

        busFrag = new busFragment();
        favFrag = new favoriteFragment();
        stopFrag = new busStopFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, favFrag).commitAllowingStateLoss();
        findbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("tag", "press button");
                switch(view.getId() ){
                    case R.id.findbtn:
                        String str= findedt.getText().toString();//EditText에 작성된 Text얻어오기
                        bundle.putString("key", str);

                        break;
                }
            }
        });


    }

    public void clickHandler(View view){
        transaction = fragmentManager.beginTransaction();

        switch(view.getId()){
            case R.id.busStop:
                transaction.replace(R.id.frameLayout, stopFrag).commitAllowingStateLoss();
                break;
            case R.id.Bus:
                transaction.replace(R.id.frameLayout, busFrag).commitAllowingStateLoss();
                break;
            case R.id.favorite:
                transaction.replace(R.id.frameLayout, favFrag).commitAllowingStateLoss();
                break;
        }
    }
}