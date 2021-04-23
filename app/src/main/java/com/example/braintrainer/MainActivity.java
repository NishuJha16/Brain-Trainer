package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;



public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout1;
    Button go;
    MediaPlayer mediaplayer1;
    public void open(View view)
    {

        if(mediaplayer1.isPlaying()||mediaplayer1.isLooping()) {
            mediaplayer1.stop();
        }
        relativeLayout1=findViewById(R.id.r1);
        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        go=(Button)findViewById(R.id.go);
        mediaplayer1=MediaPlayer.create(this,R.raw.open);
        mediaplayer1.setLooping(true);
        mediaplayer1.start();

    }

    public void onDestroy() {
        super.onDestroy();
        if (mediaplayer1 != null ) {
            mediaplayer1.stop();

            mediaplayer1.release();

        }
    }
}
