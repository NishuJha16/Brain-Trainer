package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity2 extends AppCompatActivity {
    Random r=new Random();
    int count=0;
    int locationOfCorrectAnswer;
    ArrayList<Integer> answers=new ArrayList<Integer>();
    MediaPlayer mediaplayer2;
    CountDownTimer cd;


    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView timer;
    TextView result;
    TextView score;
    TextView question;
    Button replay;
    int scores=0;
    int questions=0;
    boolean active=true;
    boolean running=false;
    Handler mhandler=new Handler();
    public void nextQuestion()
    {

        if(count<30) {
            if(running==true) {
                cd.cancel();
            }

            active=true;
            count++;

            int a = r.nextInt(21);
            int b = r.nextInt(21);
            char operator;
            int ans;
            switch (r.nextInt(3)) {
                case 0:
                    operator = '+';
                    ans = a + b;
                    break;
                case 1:
                    operator = '-';
                    if (a < b) {
                        int c = a;
                        a = b;
                        b = c;
                    }
                    ans = a - b;
                    break;
                case 2:
                    operator = '*';
                    ans = a * b;
                    break;

                default:
                    operator = '\0';
                    ans = 0;

            }

            question.setText(Integer.toString(a) + operator + Integer.toString(b));

            locationOfCorrectAnswer = r.nextInt(4);

            answers.clear();

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers.add(ans);
                } else {
                    int wrongAnswer = r.nextInt(140);

                    while (wrongAnswer == ans) {
                        wrongAnswer = r.nextInt(140);
                    }

                    answers.add(wrongAnswer);
                }

            }
            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));
            cd= new CountDownTimer(10100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText("0:" + String.valueOf(millisUntilFinished / 1000));
                    running=true;
                }

                @Override
                public void onFinish() {
                    questions++;
                    result.setVisibility(View.VISIBLE);
                    result.setText("OOPS!! Time over:(");
                    score.setText(scores+"/"+questions);
                    clear(findViewById(R.id.score));
                    running=false;
                }
            }.start();
        }
        else if(count==30)
        {
            cd.cancel();
            count=0;
            if(mediaplayer2.isPlaying()||mediaplayer2.isLooping())
            {
                mediaplayer2.stop();
            }
            SweetAlertDialog s=  new SweetAlertDialog(MainActivity2.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            s.setTitleText("Your Score");
            s.setContentText(scores+"/"+questions);
            s.setConfirmText("Replay");
            s.setCustomImage(R.drawable.brain);
            s.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    replay(findViewById(R.id.timer));
                }
            });
            s.setCancelButton("Back", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
            s.setCancelable(false);
            s.setCanceledOnTouchOutside(false);
            s.show();
            active=false;
        }
    }
    public void check(View view)
    {

        if(active) {
            if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
                scores++;
                result.setText("Correct!! :)");
                result.setVisibility(View.VISIBLE);
                view.setBackgroundColor(Color.GREEN);
            } else {
                result.setText("Wrong!! :(");
                result.setVisibility(View.VISIBLE);
                view.setBackgroundColor(Color.RED);
            }
            questions++;
            score.setText(scores + "/" + questions);
            mhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clear(findViewById(R.id.timer));
                }
            },500);

        }
    }
    public void clear(View view)
    {
        result.setText("");
        button0.setBackgroundColor(getResources().getColor(R.color.btn04));
        button1.setBackgroundColor(getResources().getColor(R.color.btn13));
        button2.setBackgroundColor(getResources().getColor(R.color.btn13));
        button3.setBackgroundColor(getResources().getColor(R.color.btn04));
        nextQuestion();
    }
    public void playgame(View view)
    {

        if(active) {
            mediaplayer2=MediaPlayer.create(this,R.raw.game);
            mediaplayer2.setLooping(true);
            mediaplayer2.start();
            replay.setVisibility(View.INVISIBLE);
            timer.setText("0:10");
            result.setText("");
            score.setText("0/0");
            scores = 0;
            questions = 0;
            nextQuestion();

        }

    }
    public void replay(View  view)
    {
        active=true;
        playgame(view);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button0=(Button)findViewById(R.id.but0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        replay=findViewById(R.id.replay);
        timer=findViewById(R.id.timer);
        result=findViewById(R.id.result);
        score=findViewById(R.id.score);
        question=findViewById(R.id.question);
        replay(findViewById(R.id.question));
    }
    public void onDestroy() {
        super.onDestroy();
        if ( mediaplayer2 != null) {

            mediaplayer2.stop();

            mediaplayer2.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
    }
}