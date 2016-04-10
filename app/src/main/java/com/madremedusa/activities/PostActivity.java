package com.madremedusa.activities;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.madremedusa.R;
import com.madremedusa.utils.DrawableManager;

public class PostActivity extends AppCompatActivity {
    private Bundle extras;
    static private DrawableManager DM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        final ImageView thumbnail = (ImageView)findViewById(R.id.thumbnail);
        final TextView title = (TextView)findViewById(R.id.title);
        final TextView author = (TextView)findViewById(R.id.author);
        final WebView content = (WebView)findViewById(R.id.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras();
        DM = new DrawableManager(this, ResourcesCompat.getDrawable(this.getResources(),R.mipmap.ic_launcher,null));
        if(extras != null){
            getSupportActionBar().setTitle(extras.getString("title"));
            DM.DisplayImage(extras.getString("thumbnail"), thumbnail);
            title.setText(extras.getString("title"));
            author.setText(getString(R.string.autor)+extras.getString("author"));
            content.loadDataWithBaseURL(null,extras.getString("content"), "text/html", "UTF-8",null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
