package com.learn.mycustomnotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.learn.mycustomnotif.NotificationService.CHANNEL_ID;
import static com.learn.mycustomnotif.NotificationService.CHANNEL_NAME;
import static com.learn.mycustomnotif.NotificationService.REPLY_ACTION;

public class ReplyActivity extends AppCompatActivity {

    private static final String KEY_MESSAGE_ID = "key_message_id";
    private static final String KEY_NOTIF_ID = "key_notif_id";

    private int mMessageid;
    private int mNotifId;

    private EditText edtReply;

    public static Intent getReplyMessageIntent(Context context, int notifId, int messageId) {
        Intent intent = new Intent(context, ReplyActivity.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_MESSAGE_ID, messageId);
        intent.putExtra(KEY_NOTIF_ID, notifId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();

        if (REPLY_ACTION.equals(intent.getAction())) {
            mMessageid = intent.getIntExtra(KEY_MESSAGE_ID, 0);
            mNotifId = intent.getIntExtra(KEY_NOTIF_ID, 0);
        }

        edtReply = findViewById(R.id.edtReply);
        ImageButton sendButton = findViewById(R.id.btnSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mNotifId, mMessageid);
            }
        });
    }

    private void sendMessage(int mNotifId, int mMessageid) {
        updateNotification(mNotifId);

        String message = edtReply.getText().toString().trim();
        Toast.makeText(this, "Message ID: " + mMessageid + "\nMessage: " + message,
                Toast.LENGTH_SHORT).show();
    }

    private void updateNotification(int mNotifId) {
        NotificationManager notificationManagerComapt = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(getString(R.string.notif_title_sent))
                .setContentText(getString(R.string.notif_content_sent));
        // Untuk android Oreo ke atas perlu menambahkan notification channel
        // materi ini akan dibahas lebih lanjut di modul extended
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create or Update
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerComapt != null) {
                notificationManagerComapt.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerComapt != null) {
            notificationManagerComapt.notify(mNotifId, notification);
        }
    }
}