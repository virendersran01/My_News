package com.example.my_news.activities;

import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_news.R;
import static com.example.my_news.fragments.TopStoriesFragment.ITEM_POSITION;

//WebViewActivity: used for launching intents containing uris to link to other pages
//or specific articles selected by the user
public class WebViewActivity extends AppCompatActivity {

    Toolbar mToolbar;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //Initiate the necessary components
        // of the activity layout when created
        setupToolbar();
        WebViewReader();
    }

    //Instantiate instance of toolbar
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //Sets the respective WebView and provides it with a uri to load from the
    //activity passing the intent
    private void WebViewReader() {
        mWebView = findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        String uri = getIntent().getStringExtra(ITEM_POSITION);
        mWebView.loadUrl(uri);
    }
}
