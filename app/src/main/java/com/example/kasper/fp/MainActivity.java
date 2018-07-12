package com.example.kasper.fp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button timer1,timer2;
    private int whitePlayer;
    private ImageButton Reset,Pause,Setting;
    private TextView TxtMoves1,TxtMoves2;
    private int ch;
    private static final long StartTime=300000;

    private boolean Timer1Running,Timer2Running,isPause;

    private long TimeLeftInMills1=StartTime;
    private long TimeLeftInMills2=StartTime;

    private int moves1,moves2;
    private CountDownTimer cdt1,cdt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer1 = findViewById(R.id.timer1);
        timer2 =  findViewById(R.id.timer2);

        Setting = findViewById(R.id.Setting);
        Reset = findViewById(R.id.Reset);
        Pause = findViewById(R.id.Pause);

        TxtMoves1 = findViewById(R.id.TxtMoves1);
        TxtMoves2 = findViewById(R.id.TxtMoves2);

        timer1.setOnClickListener(this);
        timer2.setOnClickListener(this);
        Setting.setOnClickListener(this);

        Reset.setOnClickListener(this);
        Pause.setOnClickListener(this);

        Pause.setImageResource(R.drawable.pause);
        isPause=true;

        UpdateText();
        ResetTimers();
    }

    private void UpdateText(){
        int secs = (int) (TimeLeftInMills1 / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        timer1.setText(String.format("%d:%02d", mins,secs));
        TxtMoves1.setText(String.format("Moves : %d",moves1));

        secs = (int) (TimeLeftInMills2 / 1000);
        mins = secs / 60;
        secs = secs % 60;
        timer2.setText(String.format("%d:%02d", mins,secs));
        TxtMoves2.setText(String.format("Moves : %d",moves2));
    }

    private void StartTimer1(){
        cdt1 = new CountDownTimer(TimeLeftInMills1,100) {
            @Override
            public void onTick(long l) {
                TimeLeftInMills1 = l;
                UpdateText();
            }

            @Override
            public void onFinish() {
                Timer1Running = false;
                timer1.setText("done!");
                timer2.setText("done!");
            }
        }.start();
        Timer1Running = true;
    }
    private void StartTimer2(){
        cdt2 = new CountDownTimer(TimeLeftInMills2,100) {
            @Override
            public void onTick(long l) {
                TimeLeftInMills2 = l;
                UpdateText();
            }

            @Override
            public void onFinish() {
                Timer2Running = false;
                timer1.setText("done!");
                timer2.setText("done!");
            }
        }.start();
        Timer2Running = true;
    }

    private void PauseTimer1(){
        cdt1.cancel();
        Timer1Running = false;
    }

    private void PauseTimer2(){
        cdt2.cancel();
        Timer2Running = false;
    }

    private void ResetTimers(){
        if(Timer1Running)
            PauseTimer1();
        if(Timer2Running)
            PauseTimer2();

        timer1.setEnabled(true);
        timer2.setEnabled(true);

        timer1.setBackgroundColor(Color.WHITE);
        timer1.setTextColor(Color.BLACK);

        timer2.setBackgroundColor(Color.WHITE);
        timer2.setTextColor(Color.BLACK);

        Pause.setImageResource(R.drawable.pause);
        isPause = true;

        moves2=moves1=0;

        TimeLeftInMills1 = TimeLeftInMills2 = StartTime;

        UpdateText();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.timer1){
                if(Timer2Running){
                    PauseTimer2();
                    StartTimer1();
                    ++moves1;
                }else if(Timer1Running){
                    PauseTimer1();
                    StartTimer2();
                    ++moves1;
                }else{
                    timer1.setBackgroundColor(Color.BLACK);
                    timer1.setTextColor(Color.WHITE);
                    whitePlayer=2;
                    StartTimer2();
                }
                timer1.setEnabled(false);
                timer2.setEnabled(true);
        }else if(view.getId()==R.id.timer2){
            if(Timer1Running){
                PauseTimer1();
                StartTimer2();
                ++moves2;
            }else if(Timer2Running){
                PauseTimer2();
                StartTimer1();
                ++moves2;
            }else{
                timer2.setBackgroundColor(Color.BLACK);
                timer2.setTextColor(Color.WHITE);
                whitePlayer=1;
                StartTimer1();
            }
            timer2.setEnabled(false);
            timer1.setEnabled(true);
        }else if(view.getId()==R.id.Reset){
            if(TimeLeftInMills1!=StartTime || TimeLeftInMills2!=StartTime) {
                AlertDialog.Builder alt = new AlertDialog.Builder(MainActivity.this);
                alt.setMessage("Do You Want To Reset Timers ?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ResetTimers();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alt.create();
                alert.setTitle("Reset Conformation");
                alert.show();
            }
        }else if(view.getId()==R.id.Pause){
            if(TimeLeftInMills1 != StartTime || TimeLeftInMills2 != StartTime) {
                if (isPause) {
                    ch = Timer1Running ? 1 : 0;
                    ch += Timer2Running ? 2 : 0;
                    if (ch == 1) {
                        timer1.setEnabled(false);
                        PauseTimer1();
                    }
                    if (ch == 2) {
                        timer2.setEnabled(false);
                        PauseTimer2();
                    }
                    Pause.setImageResource(R.drawable.play);
                    isPause=false;
                } else {
                    if (ch == 1) {
                        timer1.setEnabled(true);
                        StartTimer1();
                    }
                    if (ch == 2) {
                        timer2.setEnabled(true);
                        StartTimer2();
                    }
                    Pause.setImageResource(R.drawable.pause);
                    isPause=true;
                }
            }
        }else if(view.getId() == R.id.Setting){
            if(isPause)
                Pause.performClick();

            //go to settings page
        }
    }
}//
/* TODO {Hovering buttons,Add multiple modes (plus 5 secs for each move , custom timer)}
*/
