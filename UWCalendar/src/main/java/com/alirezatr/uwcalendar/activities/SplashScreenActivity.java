package com.alirezatr.uwcalendar.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.SubjectsAdapter;
import com.alirezatr.uwcalendar.listeners.SubjectsListener;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.network.NetworkManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SplashScreenActivity extends Activity {
    NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen_page);

        networkManager = new NetworkManager(this);
        loadSubjects();
    }

    public void loadSubjects() {
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        mProgress.setVisibility(View.VISIBLE);
        networkManager.getSubjects(new SubjectsListener() {
            @Override
            public void onSuccess(ArrayList<Subject> subjects) {
                mProgress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception error) {
                mProgress.setVisibility(View.INVISIBLE);
                TextView loadError = (TextView) findViewById(R.id.splashScreenError);
                loadError.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });
    }

}
