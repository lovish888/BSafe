package com.insane.lovish.informe;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewReportActivity extends AppCompatActivity {

    static int REQUEST_IMAGE_CAPTURE = 1;
    static int REQUEST_GALLERY_CAPTURE = 2;
    Uri imageUri;

    TextView mDate;
    TextView title, description;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat date;
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String month = new DateFormatSymbols().getMonths()[i1];
            mDate.setText(i2 + " " + month + " " + i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Report");

        mDate = (TextView) findViewById(R.id.report_date);

        date = new SimpleDateFormat("dd MMMM yyyy");
        mDate.setText(date.format(calendar.getTime()));

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(NewReportActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_camera) {
            //Take photo
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Take picture and pass result on to the activity
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        } else if (id == R.id.action_gallery) {
            //Choose photo
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, REQUEST_GALLERY_CAPTURE);

        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            if (photo != null) {
                //TODO: Handle the image
            }
        } else if (requestCode == REQUEST_GALLERY_CAPTURE && resultCode == RESULT_OK) {

            //The address of the image on phone.
            imageUri = data.getData();
            InputStream inputStream;
            try {
                //Getting inputStream based on the URI of the image.
                inputStream = getContentResolver().openInputStream(imageUri);
                //Get a Bitmap from the stream.
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                if (photo != null) {
                    //TODO: Handle the image
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
