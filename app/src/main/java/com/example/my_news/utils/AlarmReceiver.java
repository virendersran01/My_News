package com.example.my_news.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.my_news.R;
import com.example.my_news.activities.WebViewActivity;

import static com.example.my_news.fragments.TopStoriesFragment.ITEM_POSITION;

//AlarmReceiver: this class loads shared preferences data and builds the notification feature when called
public class AlarmReceiver extends BroadcastReceiver {

    private Utils mUtils = new Utils();
    private String httpResult = "";
    private String subTextUrl = "";
    private String[] arrays;
    private SharedPreferencesData mStoredData = new SharedPreferencesData();

    @Override
    public void onReceive(Context context, Intent intent) {
        arrays = mStoredData.loadSharedPrefs(context).split(",");
//        executeNotificationHttpRequest(context);
    }

    //NotificationBuilder: this feature constructs the notification that'll be sent to the user whenever
    //they activate notification alerts in the SearchArticle activity
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(Context context) {

        final int NOTIFICATION_ID = 007;

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ITEM_POSITION, subTextUrl);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 0, intent, 0);

        //Instance of Notification Manager
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.text_notification))
                        .setSubText(subTextUrl)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);

        //Get instance of NotificationManager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message";
            String channelID = context.getString(R.string.default_notification_channel_id);
            int importanceLvl = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelID, channelName, importanceLvl);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }

        //Assert not null and build notification manager
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    //Http request method: takes a model object as a parameter and uses it to display the correct result
//    private void executeNotificationHttpRequest(final Context context) {
//
//        NewYorkTimesService.SearchArticleFl(
//    }
}
