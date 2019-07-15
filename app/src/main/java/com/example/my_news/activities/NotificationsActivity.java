package com.example.my_news.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.my_news.R;
import com.example.my_news.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity {

    public String [] CHECKBOX_VALUES = {"Books", "Health", "Movies", "Science",
    "Technology", "Travel"};
    public String [] CHECKBOX_POSITION = new String[6];

    @BindView(R.id.query_text_input_layout)
    public TextInputLayout hintLabel;
    @BindView(R.id.search_query_term)
    EditText mSearchQueryTerm;
    @BindView(R.id.checkbox_1)
    CheckBox mCheckBox1;
    @BindView(R.id.checkbox_2)
    CheckBox mCheckBox2;
    @BindView(R.id.checkbox_3)
    CheckBox mCheckBox3;
    @BindView(R.id.checkbox_4)
    CheckBox mCheckBox4;
    @BindView(R.id.checkbox_5)
    CheckBox mCheckBox5;
    @BindView(R.id.checkbox_6)
    CheckBox mCheckBox6;
    @BindView(R.id.switch_button)
    Switch mSwitch;

    public Utils mUtils = new Utils();
    private CheckBox[] mCheckboxes;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;
    //'DataStorage' Class?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ButterKnife.bind(this);
        this.mCheckboxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3,
        mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};

        configureToolbar();
        //configureAlarmManager(); ?
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onCheckboxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();
        CHECKBOX_POSITION[0] = CHECKBOX_VALUES[0];
        switch (view.getId()) {
            case R.id.checkbox_1:
                if (isChecked) {
                    CHECKBOX_POSITION[0] = CHECKBOX_VALUES[0];
                } else {
                    CHECKBOX_VALUES[0] = "";
                }
                break;
            case R.id.checkbox_2:
                if (isChecked) {
                    CHECKBOX_POSITION[1] = CHECKBOX_VALUES[1];
                } else {
                    CHECKBOX_VALUES[1] = "";
                }
                break;
            case R.id.checkbox_3:
                if (isChecked) {
                    CHECKBOX_POSITION[2] = CHECKBOX_VALUES[2];
                } else {
                    CHECKBOX_VALUES[2] = "";
                }
                break;
            case R.id.checkbox_4:
                if (isChecked) {
                    CHECKBOX_POSITION[3] = CHECKBOX_VALUES[3];
                } else {
                    CHECKBOX_VALUES[3] = "";
                }
                break;
            case R.id.checkbox_5:
                if (isChecked) {
                    CHECKBOX_POSITION[4] = CHECKBOX_VALUES[4];
                } else {
                    CHECKBOX_VALUES[4] = "";
                }
                break;
            case R.id.checkbox_6:
                if (isChecked) {
                    CHECKBOX_POSITION[5] = CHECKBOX_VALUES[5];
                } else {
                    CHECKBOX_VALUES[5] = "";
                }
                break;
        }
    }
}
