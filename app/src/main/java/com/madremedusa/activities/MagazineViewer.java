package com.madremedusa.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.madremedusa.R;
import com.madremedusa.adapters.FullScreenAdapter;
import com.madremedusa.adapters.MagazineViewAdapter;
import com.madremedusa.items.ItemMagazine;

import java.util.ArrayList;

public class MagazineViewer extends AppCompatActivity {
    private Bundle extras;
    private Context context;
    static private ArrayList<String> magazineIssue;
    private RecyclerView magazineView;
    private FullScreenAdapter magazineAdapter;
    private LinearLayoutManager linearLayoutManagerHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_viewer);
        getSupportActionBar().hide();
        context = this;
        extras = getIntent().getExtras();
        magazineIssue = new ArrayList<>();
        if(extras != null){
            magazineIssue=extras.getStringArrayList("MagazineIssue");
        }
        magazineView = (RecyclerView)findViewById(R.id.magazineView);
        linearLayoutManagerHorizontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        magazineView.setLayoutManager(linearLayoutManagerHorizontal);
        magazineAdapter = new FullScreenAdapter(context, magazineIssue);
        magazineView.setAdapter(magazineAdapter);
    }
}
