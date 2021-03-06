package com.car_market_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.car_market_android.application.CarMarketActivity;
import com.car_market_android.network.PostRequestResultEvent;
import com.car_market_android.util.EventsBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

public class UserUpdate extends CarMarketActivity implements OnClickListener {

    private Button Update;
    private Button Cacnel;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_update);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        EventsBus.getInstance().register(this);

        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        this.Update = (Button) this.findViewById(R.id.update_user_update);
        this.Cacnel = (Button) this.findViewById(R.id.cancel_user_update);

        this.Update.setOnClickListener(this);
        this.Cacnel.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        EventsBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_user_update:
                Toast.makeText(this, "Update button pressed...", Toast.LENGTH_SHORT).show();

                showProgressDialog(R.string.updating);

                //TODO: call apiClient

                dismissProgressDialog();
                break;
            case R.id.cancel_user_update:
                this.onBackPressed();
                break;
            default:
                Toast.makeText(this, "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
//			this.dialog = new ProgressDialog(this);
//			this.dialog.setMessage("Loading...");
//			this.dialog.show();
                break;
        }
    }

    @Subscribe
    public void onPostRequestTaskResult(PostRequestResultEvent event) {

        Gson gson = new GsonBuilder().create();

        switch (event.getCaller()) {
            default:
                dismissProgressDialog();
                break;
        }
    }
}
