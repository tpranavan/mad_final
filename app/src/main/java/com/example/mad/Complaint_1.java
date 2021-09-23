package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Complaint_1 extends AppCompatActivity {
    EditText name , Complaint;
    ImageView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint1);

        name = findViewById(R.id.name);
        Complaint = findViewById(R.id.Complaint);
        click = findViewById(R.id.btnshare);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString();
                String uComplaint = Complaint.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_TEXT,"Name: "+uname+"\n\nComplaint:"+uComplaint );
                startActivity(intent);


            }

        });
    }

}
