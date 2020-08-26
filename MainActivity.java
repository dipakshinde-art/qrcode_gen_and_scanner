package org.terna.qrr;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button gen, scan,table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gen = (Button) findViewById(R.id.gen);
        scan = (Button) findViewById(R.id.scan);
        table=(Button)findViewById(R.id.tbl);

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gIntent = new Intent(MainActivity.this, Generator.class);
                startActivity(gIntent);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rIntent = new Intent(MainActivity.this, Scanner.class);
                startActivity(rIntent);
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tIntent = new Intent(MainActivity.this, table.class);
                startActivity(tIntent);
            }
        });




    }

    @Override
    public void  onClick(View v)
    {

    }

}

