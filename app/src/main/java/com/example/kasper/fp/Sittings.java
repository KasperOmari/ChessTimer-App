package com.example.kasper.fp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Sittings extends AppCompatActivity {
    private RadioButton btn5, btn10, cstBtn;
    private Button done;
    public EditText cstMins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sittings);

        cstMins = findViewById(R.id.cstMins);

        btn5 = findViewById(R.id.btn5);
        btn10 = findViewById(R.id.btn10);
        cstBtn = findViewById(R.id.cstBtn);

        done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        if (time < 1)
                            MainActivity.ChangeTimers(5);
                        else
                            MainActivity.ChangeTimers(time);
                        Intent mn = new Intent(Sittings.this,MainActivity.class);
                        startActivity(mn);
                    }else{
                        Toast.makeText(Sittings.this,"Enter Number Of Minutes",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
