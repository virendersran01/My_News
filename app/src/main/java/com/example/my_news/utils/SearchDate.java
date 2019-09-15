package com.example.my_news.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//SearchDate: this class allows the user to select dates for parameters to their article search
public class SearchDate implements View.OnFocusChangeListener,
        DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar mCalendar;

    //Sets focus and creates a new instance of the calendar api
    public SearchDate(EditText editText, Context context) {
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        mCalendar = Calendar.getInstance();
    }

    //When a date is selected: format the date string correctly and set text
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Input string formatting
        String format = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format, Locale.US);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(simpleDateFormat.format(mCalendar.getTime()));
    }

    //Display newly selected calendar date
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            new DatePickerDialog(v.getContext(), this,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    }
}
