package com.example.admin.gipp_ol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BarCodeActivity extends AppCompatActivity {

    ImageView img;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                abrirCamera();

            }
        });
    }

    public void abrirCamera(){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (resultCode == 0 && requestCode == RESULT_OK) {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                img.setImageBitmap(resizeImage(this, bitmap, 700, 600));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Bitmap resizeImage (Context context,Bitmap bmpOriginal,float newWidth, float newHeight) {
        Bitmap novoBmp;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novaW = newWidth * densityFactor;
        float novaH = newHeight * densityFactor;

        float scalaW = novaW / w;
        float scalaH = novaH / h;

        Matrix matrix = new Matrix();

        matrix.postScale(scalaW, scalaH);

        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
    }

    /*public void girarFoto(View v) {
        img.setRotation(90);
    }

    public void voltarFoto(View v){
        img.setRotation(0);
    }*/

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}