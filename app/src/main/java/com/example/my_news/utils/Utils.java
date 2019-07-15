package com.example.my_news.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public String getNewDesk(String [] strings) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                if (i > 0 && (!strings[i].isEmpty()))
                    builder.append(" ");
                    builder.append(strings[i]);
            }
        }
        return builder.toString().isEmpty() ?
                "" : builder.toString();
    }

    public String getBeginDate(String beginDate) {
        if (beginDate.isEmpty())
            return setCalendarFormatPriorYear();
            String [] fDate = beginDate.split("/");
            return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }

    public String getEndDate(String endDate) {
        if (endDate.isEmpty())
            return setCalendarFormat();
            String [] fDate = endDate.split("/");
            return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }

    public String setCalendarFormat() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", simpleDateFormat.format(cal.getTime()));
    }

    public String setCalendarFormatPriorYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", simpleDateFormat.format(cal.getTime()));
    }

    public boolean queryInputIsEmpty (EditText searchQuery,
                                     TextInputLayout textInputLayout,
                                     CharSequence message) {
        if (searchQuery.getText().toString().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message);
            return true;
        } else {
            textInputLayout.setErrorEnabled(true);
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void snackbarMessage(View view, int message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView  = snackbar.getView();
        TextView textView = snackbarView.findViewById(
                android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

    public void changeCheckboxColor(int color, CheckBox[] boxes) {
        for (CheckBox box : boxes) {
            box.setTextColor(color);
        }
    }

    public boolean onUncheckedBoxes(CheckBox[] boxes) {
        if (!(boxes[0].isChecked() || boxes[1].isChecked() || boxes[2].isChecked()
                || boxes[3].isChecked() || boxes[4].isChecked()
                || boxes[5].isChecked())) {
            changeCheckboxColor(Color.RED, boxes);
            return true;
        } else {
            changeCheckboxColor(Color.BLACK, boxes);
            return false;
        }
    }
}
