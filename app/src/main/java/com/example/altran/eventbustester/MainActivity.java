package com.example.altran.eventbustester;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    MainController controller;

    @ViewById(R.id.welcome_message_id)
    TextView welcomeMessage;

    @ViewById(R.id.toolbar)
    Toolbar myToolbar;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @AfterViews
    public void init() {
        controller.setText(welcomeMessage);
        controller.register();
        setSupportActionBar(myToolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent("EventBus Was activated"));

            }
        });
    }

    @OptionsItem({R.id.action_settings, R.id.action_continue})
    void action_settings(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        } else if(id == R.id.action_continue){
            startActivity(new Intent(this, SecondActivity_.class));
        }
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        controller.unregister();
    }
}
