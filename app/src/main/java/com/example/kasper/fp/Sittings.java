package com.example.kasper.fp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Sittings extends AppCompatActivity implements View.OnClickListener{
    private RadioButton btn5, btn10, cstBtn;
    private Button done;
    public EditText cstMins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sittings);

        done = findViewById(R.id.done);
        btn5 = findViewById(R.id.btn5);
        btn10 = findViewById(R.id.btn10);
        cstBtn = findViewById(R.id.cstBtn);

        cstMins = findViewById(R.id.cstMins);

        done.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn10.setOnClickListener(this);
        cstBtn.setOnClickListener(this);

        cstMins.setEnabled(false);
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void onClick(View view) {
        if(view.getId() == R.id.done){
            if(btn5.isChecked()) {
                MainActivity.ChangeTimers(5);
                Intent mn = new Intent(Sittings.this,MainActivity.class);
                startActivity(mn);
            }else if(btn10.isChecked()){
                MainActivity.ChangeTimers(10);
                Intent mn = new Intent(Sittings.this,MainActivity.class);
                startActivity(mn);
            }else if(cstBtn.isChecked()){
                if(!isEmpty(cstMins)) {
                    long time = Long.parseLong(String.valueOf(cstMins.getText()));
                    if (time < 1) {
                        MainActivity.ChangeTimers(5);
                        Toast.makeText(Sittings.this,"Invalid Time , Default Time Done",Toast.LENGTH_SHORT).show();
                    }
                    else
                        MainActivity.ChangeTimers(time);
                    Intent mn = new Intent(Sittings.this,MainActivity.class);
                    startActivity(mn);
                }else{
                    Toast.makeText(Sittings.this,"Enter Number Of Minutes",Toast.LENGTH_SHORT).show();
                }
            }else {
                Intent mn = new Intent(Sittings.this,MainActivity.class);
                startActivity(mn);
            }
        }else if(view.getId() == R.id.cstBtn){
            if(cstBtn.isChecked())
                cstMins.setEnabled(true);
            else
                cstMins.setEnabled(false);
        }else if(view.getId() == R.id.btn5 || view.getId() == R.id.btn10){
            cstMins.setEnabled(false);
        }

    }
}
