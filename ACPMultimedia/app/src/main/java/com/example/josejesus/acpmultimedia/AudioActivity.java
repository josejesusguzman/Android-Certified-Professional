package com.example.josejesus.acpmultimedia;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    private ImageButton btnPlay;
    private ImageButton btnPause;
    private ImageButton btnStop;
    private TextView txtEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        btnPlay = (ImageButton) findViewById(R.id.audio_img_play);
        //btnPause = (ImageButton) findViewById(R.id.audio_img_pause);
        btnStop = (ImageButton) findViewById(R.id.audio_img_stop);
        txtEstado = (TextView) findViewById(R.id.audio_txt_estado);

        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mediaPlayer.start();
        btnPlay.setImageResource(R.mipmap.ic_pause);
        txtEstado.setText("Reproduciendo...");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    txtEstado.setText("Musica en pausa.");
                    btnPlay.setImageResource(R.mipmap.ic_start);
                } else {
                    mediaPlayer.start();
                    txtEstado.setText("Reproduciendo...");
                    btnPlay.setImageResource(R.mipmap.ic_pause);
                }

            }
        });

        /*btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    btnPlay.setImageResource(R.mipmap.ic_start);
                    txtEstado.setText("Musica detenida");
                    try {//Regresa el media player a preparado
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
