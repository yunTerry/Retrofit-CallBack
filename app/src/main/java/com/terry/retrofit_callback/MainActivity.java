package com.terry.retrofit_callback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 使用RxJava的回调
     */
    private Button rxbutton;
    /**
     * 普通回调
     */
    private Button button2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rxbutton = (Button) findViewById(R.id.rxbutton);
        rxbutton.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rxbutton:
                break;
            case R.id.button2:
                break;
        }
    }
}
