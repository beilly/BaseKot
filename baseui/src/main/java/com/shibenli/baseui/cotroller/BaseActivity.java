package com.shibenli.baseui.cotroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shibenli on 2017/6/21.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Unbinder bind = null;
    protected FragmentActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        int layout = layout();
        if (layout != 0){
            setContentView(layout);
            if (bindTo()){
                bind = ButterKnife.bind(this);
            }
            Intent intent = getIntent();
            init(intent == null ? new Intent() : intent);
        }
    }

    public  @LayoutRes int layout(){
        return 0;
    }

    public boolean bindTo(){
        return true;
    }

    public abstract void init(@NonNull Intent intent);

    @Override
    protected void onDestroy() {
        if (null != bind){
            bind.unbind();
        }
        super.onDestroy();
    }
}
