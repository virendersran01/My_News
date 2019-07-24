package com.example.my_news.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.my_news.R;
import com.example.my_news.utils.AlarmReceiver;
import com.example.my_news.utils.SharedPreferencesData;
import com.example.my_news.utils.Utils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity {

    private final boolean TEST_MODE = true;
    public String[] CHECKBOX_VALUES = {"Books", "Health", "Movies", "Science",
            "Technology", "Travel"};
    public String[] CHECKBOX_POSITION = new String[6];

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
    @BindView(R.id.button_test)
    Button mButton;

    public Utils mUtils = new Utils();
    private CheckBox[] mCheckboxes;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;
    private SharedPreferencesData mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ButterKnife.bind(this);
        this.mCheckboxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3,
                mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};

        configureToolbar();
        configureAlarmManager(this);
        this.switchButton();

        if (TEST_MODE) {
            mButton.setVisibility(View.VISIBLE);

            mButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    sendTestNotification();
                }
            });
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureAlarmManager(Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationsActivity.this,
                0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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

    private void switchButton() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mUtils.queryInputIsEmpty(mSearchQueryTerm, hintLabel,
                            getResources().getString(R.string.query_error))) {
                        mSwitch.setChecked(false);
                    } else {
                        mSwitch.setChecked(true);
                    }

                    if (mUtils.onUncheckedBoxes(mCheckboxes)) {
                        mSwitch.setChecked(false);
                        mUtils.snackbarMessage(findViewById(R.id.activity_notification),
                                R.string.box_unchecked);
                    } else {
                        mSwitch.setChecked(true);
                    }

                    if (!(mSearchQueryTerm.getText().toString().isEmpty()
                            && !(mUtils.onUncheckedBoxes(mCheckboxes)))) {
                        mSwitch.setChecked(true);

                        //Launch AlarmManager
                        if (TEST_MODE) {
                            passingData();
                            startTestAlarm();
                        } else {
                            passingData();
                            startAlarm();
                        }
                    }
                } else {
                    //Disable AlarmManager
                    stopAlarm();
                }
            }
        });
    }

    //Task scheduler for AlarmManager & Notifications
    private void startTestAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 90000;
        assert mAlarmManager != null;
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval,
                interval, mPendingIntent);
        Toast.makeText(this, "Alarm test Set!", Toast.LENGTH_SHORT).show();
    }

    private void startAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                mPendingIntent);
        Toast.makeText(this, "Alarm set!",
                Toast.LENGTH_SHORT).show();
    }

    private void stopAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert mAlarmManager != null;
        mAlarmManager.cancel(mPendingIntent);
        Toast.makeText(this, "Alarm stopped!",
                Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendTestNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        //Get instance of NotificationManager
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.text_notification))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        //Get instance of NotificationManager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message";
            String channelID = "Message";
            int importanceLvl = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelID, channelName, importanceLvl);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }

        assert mNotificationManager != null;
        mNotificationManager.notify(001, mBuilder.build());
    }

    public void passingData() {
        String[] value = {mSearchQueryTerm.getText().toString(),
                mUtils.getNewDesk(CHECKBOX_VALUES)};
        StringBuilder sb = new StringBuilder();
        for (String eachValue : value) {
            sb.append(eachValue).append(",");
        }

        mSharedPrefs.saveToSharedPrefs(this, sb.toString());
    }
}
