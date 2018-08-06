package com.ltime.buspad.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.ltime.buspad.R;

public class MoviesActivity extends Activity implements OnClickListener {
    public static final String TAO = "tao";
    private View movies_action_relativeLayout, movies_premiere_relativeLayout,
            movies_comedy_relativeLayout, movies_lslamic_relativeLayout,
            movies_drama_relativeLayout, movies_children_relativeLayout,
            movies_songs_relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movies);
        movies_action_relativeLayout = findViewById(R.id.movies_action_relativeLayout);
        movies_action_relativeLayout.setOnClickListener(this);
        movies_premiere_relativeLayout = findViewById(R.id.movies_premiere_relativeLayout);
        movies_premiere_relativeLayout.setOnClickListener(this);
        movies_comedy_relativeLayout = findViewById(R.id.movies_comedy_relativeLayout);
        movies_comedy_relativeLayout.setOnClickListener(this);
        movies_lslamic_relativeLayout = findViewById(R.id.movies_lslamic_relativeLayout);
        movies_lslamic_relativeLayout.setOnClickListener(this);
        movies_drama_relativeLayout = findViewById(R.id.movies_drama_relativeLayout);
        movies_drama_relativeLayout.setOnClickListener(this);
        movies_children_relativeLayout = findViewById(R.id.movies_children_relativeLayout);
        movies_children_relativeLayout.setOnClickListener(this);
        movies_songs_relativeLayout = findViewById(R.id.movies_songs_relativeLayout);
        movies_songs_relativeLayout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle b = new Bundle();

//		if(!checkNetworkWifi()){
//			return;
//		}
        switch (view.getId()) {
            case R.id.movies_action_relativeLayout:
                b.putString("category", "1");
                break;
            case R.id.movies_premiere_relativeLayout:
                b.putString("category", "2");
                break;
            case R.id.movies_comedy_relativeLayout:
                b.putString("category", "3");
                break;
            case R.id.movies_lslamic_relativeLayout:
                b.putString("category", "4");
                break;
            case R.id.movies_drama_relativeLayout:
                b.putString("category", "5");
                break;
            case R.id.movies_children_relativeLayout:
                b.putString("category", "6");
                break;
            case R.id.movies_songs_relativeLayout:
                b.putString("category", "7");
                break;
            default:
                break;
        }

        try {
            intent.setClass(MoviesActivity.this, MovieActivityA.class);
            intent.putExtras(b);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
