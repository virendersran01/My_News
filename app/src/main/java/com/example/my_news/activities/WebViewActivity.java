package com.example.my_news.activities;

import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_news.R;

import static com.example.my_news.fragments.TopStoriesFragment.ITEM_POSITION;

public class WebViewActivity extends AppCompatActivity {

    Toolbar mToolbar;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        setupToolbar();
        WebViewReader();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void WebViewReader() {
        String uri = getIntent().getStringExtra(ITEM_POSITION);
        mWebView.loadUrl(uri);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
