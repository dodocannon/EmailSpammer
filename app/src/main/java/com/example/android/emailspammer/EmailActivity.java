package com.example.android.emailspammer;

import android.app.Activity;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class EmailActivity extends AppCompatActivity{
    @Override
    protected void onCreate( Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        final EditText to = findViewById(R.id.to);
        final EditText from = findViewById(R.id.from);
        final EditText text = findViewById(R.id.text);
        Button sendEmail = findViewById(R.id.emailSendButton);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = text.getText().toString();
                String f = from.getText().toString();
                String r = to.getText().toString();
                to.getText().clear();
                from.getText().clear();
                text.getText().clear();
                if (r.isEmpty())
                {
                    System.out.println("Empty");
                    Toast.makeText(EmailActivity.this.getApplicationContext(), "You did not specify an address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread est = new Thread( new EmailSenderThread(t, r, f, EmailActivity.this.getApplicationContext()));
                est.start();
            }
        });


    }

}
