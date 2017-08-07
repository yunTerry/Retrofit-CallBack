package com.terry.retrofit_callback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.terry.retrofit_callback.http.BaseBack;
import com.terry.retrofit_callback.http.Rest;
import com.terry.retrofit_callback.http.RxSubscribe;
import com.terry.retrofit_callback.http.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private ProgressBar progressBar;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rxbutton:
                Rest.getRestApi().getRxUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscribe<User>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                progressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            protected void onSuccess(User user) {
                                textView.setText("使用了Rxjava的回调封装，返回结果：\n" +
                                        user.toString());
                            }

                            @Override
                            public void onComplete() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                break;
            case R.id.button2:
                Rest.getRestApi().getUser().enqueue(new BaseBack<User>() {
                    @Override
                    protected void onSuccess(User user) {
                        textView.setText("普通回调封装，返回结果：\n" +
                                user.toString());
                    }
                });
                break;
        }
    }
}
