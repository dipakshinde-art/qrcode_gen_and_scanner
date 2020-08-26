package org.terna.qrr;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Qr_information extends AppCompatActivity  {
    private final String[] a = new String[]{
            "<b>Name:</b> ", "<b>Batch:</b> ", "<b>serial number:</b> ",
            "<b>date:</b>  ", "<b> Mrp:</b>"
    };
    Button gen_Qr_btn;
    EditText Name, SerialNumber, Batch, Date, Mrp;
    Button done;
   // TextView GenChildCount;


    public Qr_information() {
        //use cause help to runtime exception
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_information);

        gen_Qr_btn = (Button) findViewById(R.id.gen_Qr);

        Name = (EditText) findViewById(R.id.name);
        SerialNumber = (EditText) findViewById(R.id.snumber);
        Batch = (EditText) findViewById(R.id.batch);
        Date = (EditText) findViewById(R.id.dom);
        Mrp = (EditText) findViewById(R.id.MRP);
        done = (Button) findViewById(R.id.done);

        //countgenqr
      //  GenChildCount = (TextView) findViewById(R.id.Count);
//        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference().child("generate");
//        countRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.e("QRINFO", "count is ------ " + dataSnapshot.getChildrenCount());
//                GenChildCount.setText("Count - " + dataSnapshot.getChildrenCount());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Qr_information.this, Generator.class);
                String name = Name.getText().toString().trim();
                String snumber = SerialNumber.getText().toString().trim();
                String batch = Batch.getText().toString().trim();
                String date = Date.getText().toString().trim();
                String mrp = Mrp.getText().toString().trim();

                //    DatabaseReference current_db = FirebaseDatabase.getInstance().getReference("qr-code-a43c2").child("generate").child("code");
                DatabaseReference current_db = FirebaseDatabase.getInstance().getReference("generate").child("qr-code-a43c2"+snumber).child("Codes");

                intent.putExtra("Name", name);
                intent.putExtra("SerialNumber", snumber);
                intent.putExtra("Batch", batch);
                intent.putExtra("Date", date);
                intent.putExtra("Mrp", mrp); // ithun info generator chya string var jat nhi


                //value save to firebase
                Map newPost = new HashMap();
                newPost.put("Name", name);
                newPost.put("SerialNumber", snumber);
                newPost.put("Batch", batch);
                newPost.put("Date", date);
                newPost.put("Mrp", mrp);

                current_db.setValue(newPost);
                startActivity(intent);
                finish();
            }


//         private void button_click(){
//           boolean done=true;
//             GenChildCount=(TextView) findViewById(R.id.count);
//               String count=GenChildCount.getText().toString();
//
//               Intent intent=new Intent(this, Generator.class);
//             intent.putExtra("count:-",count);
//              startActivity(intent);
//            if(done==true)
//            {
//                count++;
//                GenChildCount.setText(Integer.toString(count));
//
//            }
//            else{
//                System.out.println(" ");
//            }
            //  }
        });


    }


}
