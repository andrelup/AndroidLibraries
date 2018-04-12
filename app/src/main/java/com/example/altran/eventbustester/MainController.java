package com.example.altran.eventbustester;

import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@EBean(scope = EBean.Scope.Default)
class MainController {

    private TextView text;

    public MainController() {
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        text.setText(event.message);
    }

    public void register() {
        EventBus.getDefault().register(this);
    }

    public void unregister() {
        EventBus.getDefault().unregister(this);
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
