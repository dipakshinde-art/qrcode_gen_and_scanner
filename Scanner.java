package org.terna.qrr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Scanner extends AppCompatActivity {
    public boolean vin = true;
    TextView result;
    String scannedData;
   // TextView GenChildCount;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private Button scan_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        result = findViewById(R.id.result);
        final Activity activity = this;

        //countgenqr
        // GenChildCount = (TextView) findViewById(R.id.Count);

//        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("Scanner").child("codes");
//        countRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.e("QRINFO", "count is --- " + dataSnapshot.getChildrenCount());
//                GenChildCount.setText("Count - " + dataSnapshot.getChildrenCount());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            //scan save
            Intent intent = new Intent(Scanner.this, Scanner.class);
            DatabaseReference current_db = FirebaseDatabase.getInstance().getReference("scanner").child("qr-code-a43c2" + scannedData).child("Codes");

            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }
    //storing info to firebase

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String res = "";
            String data1[] = scanResult.getContents().split("\n");
            for (String k : data1) {
                res = res + (k + " ");
                Log.e("SCANNER", "RESULT ----------" + k);

            }
            result.setText(res);
            Toast.makeText(getApplicationContext(), "RESULT-" + res, Toast.LENGTH_SHORT).show();

            String name, snumber, batch, date, mrp;
            String[] arrOfStr = res.split(" ", 5);
            name = arrOfStr[0];
            snumber = arrOfStr[1];
            batch = arrOfStr[2];
            date = arrOfStr[3];
            mrp = arrOfStr[4];

            for (int i = 0; i < arrOfStr.length; i++) {
                Log.e("SCANNER", "QR ---------" + arrOfStr[i]);
            }
            Log.e("SCANNER", "RESULT --" + name + " " + snumber + " " + batch + " " + date + " " + mrp);

            HashMap<Object, String> hashMap = new HashMap<>();
            // put info in hashmap
            hashMap.put("Name", name);
            hashMap.put("SerialNumber", snumber);
            hashMap.put("Batch", batch);
            hashMap.put("Date", date);
            hashMap.put("Mrp", mrp);


            Log.e("SCAERRRR", "HASH MAP-------" + hashMap);
            DatabaseReference current_db = FirebaseDatabase.getInstance().getReference("Scanner").child("codes");
            current_db.child(current_db.push().getKey()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
            // detele from genrator only u have to call in child tht auto geneate qr -code - a43c22and pass it hee

            current_db = FirebaseDatabase.getInstance().getReference("generate").child("qr-code-a43c2" + snumber);
            current_db.removeValue();

        }

    }
}
