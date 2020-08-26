package org.terna.qrr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class table extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView gencount, soldcoun;
    Button geneator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        gencount = (TextView) findViewById(R.id.genoroductCount);
        soldcoun = (TextView) findViewById(R.id.soldproductCount);

        geneator=(Button) findViewById(R.id.gen);


        geneator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gIntent = new Intent(table.this, Generator.class);
                startActivity(gIntent);
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("generate").child("qr-code-a43c2").child("Codes");
        //  databaseReference=firebaseDatabase.getReference("generate");

        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference().child("generate");
        countRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("QRINFO", "count is --- " + dataSnapshot.getChildrenCount());
                gencount.setText(" Manufacture count:- " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        DatabaseReference countRRef = FirebaseDatabase.getInstance().getReference("Scanner").child("codes");
        countRRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("QRINFO", "count is -- " + dataSnapshot.getChildrenCount());
                soldcoun.setText("Sold Count - " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}