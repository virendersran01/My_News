package com.example.my_news.utils;

import android.content.Context;
import android.content.Intent;

import static com.example.my_news.fragments.TopStoriesFragment.ITEM_POSITION;

public class Utils {

    public Utils () {
        //Empty constructor
    }

    //Start activity to display WebView content
    public void openActivityInWebView(String url, Context context, Class _class) {
        Intent intent = new Intent(context, _class);
        intent.putExtra(ITEM_POSITION, url);
        context.startActivity(intent);
    }
}
