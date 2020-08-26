package org.terna.qrr;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator extends AppCompatActivity {

    Button gen_Qr_btn;
    Button gen_btn;
    ImageView image;
    String text2Qr;
    private EditText text;
    private DatabaseReference geneRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);

        text = (EditText) findViewById(R.id.text);
        gen_btn = (Button) findViewById(R.id.gen_btn);
        gen_Qr_btn = (Button) findViewById(R.id.gen_Qr_btn);

        image = (ImageView) findViewById(R.id.image);

        mAuth = FirebaseAuth.getInstance();


        String name = getIntent().getStringExtra("Name");
        String snumber = getIntent().getStringExtra("SerialNumber");
        String batch = getIntent().getStringExtra("Batch");
        String date = getIntent().getStringExtra("Date");
        String mrp = getIntent().getStringExtra("Mrp");


        text2Qr = name + " " + snumber + " " + batch + " " + date + " " + mrp;

        if (name != null) {
            //value display throw
            Toast.makeText(Generator.this, " " + "" + name + "" + snumber + "" + batch + "" + date + "" + mrp, Toast.LENGTH_SHORT).show();
            text.setText(text2Qr);
        }

        gen_Qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gIntent = new Intent(Generator.this, Qr_information.class);
                startActivity(gIntent);

            }
        });

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                text2Qr = text.getText().toString().trim();

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {

                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);//qr image

                    //img saver
                    String mTimeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
                    String mImageName = "snap_" + mTimeStamp + ".jpg";

                    SaveImage(bitmap, mImageName);
                    gen_btn.setVisibility(view.INVISIBLE);
                    gen_Qr_btn.setVisibility(view.INVISIBLE);



                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
            // }
        });

    }

    private void SaveImage(Bitmap finalBitmap, String filename) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/QR_CODE_APP");
         myDir.mkdirs();

        String fname = filename;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        // file.createNewFile();
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(getApplicationContext(), "image save", Toast.LENGTH_SHORT).show();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


//    private void SaveImage(Bitmap finalBitmap, String filename) {
//
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/QR_CODE_APP");
//         myDir.mkdirs();
//         String fname = filename;
//        File file = new File(myDir, fname);
////try {
////            if (file.getParentFile().exists()|| file.getParentFile().delete()){
////                file.getParentFile().createNewFile();
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
//        if (file.exists())
//        {file.delete();}
//        try {
//            if (myDir.createNewFile()) {
//                FileOutputStream out = new FileOutputStream(file);
//                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                Toast.makeText(getApplicationContext(), "image save", Toast.LENGTH_SHORT).show();
//                out.flush();
//                out.close();
//            }
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
}

