package com.learn.mysound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSound;

    private SoundPool sp;
    private int soundId;
    private boolean spLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSound = findViewById(R.id.btnSoundpool);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spLoaded) {
                    sp.play(soundId, 1,1,0,0,1);
                }
            }
        });

        sp = new SoundPool.Builder()
                .setMaxStreams(10)
                .build();

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    spLoaded = true;
                } else {
                    Toast.makeText(MainActivity.this, "Gagal load", Toast.LENGTH_SHORT).show();
                }
            }
        });

        soundId = sp.load(this, R.raw.clinking_glasses, 1);
    }
}