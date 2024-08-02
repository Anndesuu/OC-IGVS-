package com.example.instructorsgradeviewsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GradeView extends AppCompatActivity {

    ImageView image;
    Button refresh, next;
    ProgressBar progress;
    List<Bitmap> bitmapList;
    String urlImage, Name, receivedString;
    Bitmap initial;
    TextRecognizer textRecognizer;
    TextView textView;
    int anything;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_view);


        image = findViewById(R.id.image);
        refresh = findViewById(R.id.refresh);
        next = findViewById(R.id.next);
        progress = findViewById(R.id.progress);

        Intent intent = getIntent();
        receivedString = intent.getStringExtra("subject");
        Name = intent.getStringExtra("name");

        bitmapList = new ArrayList<>();

        textView = findViewById(R.id.textview);
        anything = 0;
        getImage();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
                if (!bitmapList.isEmpty()) {
                    initial = bitmapList.get(0);
                    processs(initial);
                } else {
                    progress.setVisibility(View.INVISIBLE);
//                    Toast.makeText(GradeView.this, "No uploaded images available!", Toast.LENGTH_LONG).show();
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anything++;
                if (anything <= bitmapList.size() - 1) {
                    if (!bitmapList.isEmpty()) {
                        initial = bitmapList.get(anything);
                        processs(initial);}
                }
                else{
                    Toast.makeText(GradeView.this, "There's no next page", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

private void processs(Bitmap image_initial) {
    Bitmap mutableBitmap = image_initial.copy(Bitmap.Config.ARGB_8888, true);
    Bitmap inputBitmap = Bitmap.createScaledBitmap(initial, initial.getWidth(), initial.getHeight(), true);
    image.setImageBitmap(mutableBitmap);

    textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
    String messages = "OK";
    String message1 = "NOT";
    if (!textRecognizer.isOperational()) {
        Toast.makeText(this, "Text recognizer could not be set up on your device", Toast.LENGTH_SHORT).show();
        textView.setText(message1);
        return;
    } else {
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        textView.setText(messages);
    }

    Frame frame = new Frame.Builder().setBitmap(mutableBitmap).build();
    SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);

    Canvas canvas = new Canvas(mutableBitmap);
    canvas.drawBitmap(initial, 0, 0, null);

    for (int i = 0; i < textBlocks.size(); i++) {
        TextBlock textBlock = textBlocks.valueAt(i);
        String blockText = textBlock.getValue();

        for (Text line : textBlock.getComponents()) {
            String text = line.getValue();
            Rect boundingBox = line.getBoundingBox();
            int expandAmount = 2100;
            Rect expandedBoundingBox = new Rect(
                    boundingBox.left,
                    boundingBox.top,
                    boundingBox.right + expandAmount,
                    boundingBox.bottom
            );
            if (text.contains(Name)) {
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(expandedBoundingBox, paint);
                }
            else{
                searchitem(text, canvas, expandedBoundingBox);
            }
        }
    }
}

    private void searchitem(String text, Canvas canvas, Rect expandedBoundingBox) {
        String[] NotToBlur = {"Mrs Violeta B Monticalvo", "Descriptive Title", "Section", "Subject", "Code",
                "Time", "Student Name", "Examination", "Prelim", "Midterm", "Pre-Final", "Final", "Grade", "Grading System", "Remarks"};

        for (String num : NotToBlur) {
            if (text.contains(num)) {
                Log.d("GradeView", num + " Found");
                Paint paint = new Paint();
                paint.setColor(Color.TRANSPARENT);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                canvas.drawRect(expandedBoundingBox, paint);
                return;
            }
        }

        Log.d("GradeView", text + " Not Found");
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(15);
        canvas.drawRect(expandedBoundingBox, paint);
    }


    private void applyBlurToBoundingBox(Canvas canvas, Rect boundingBox) {
        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);
        tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        tempCanvas.drawRect(boundingBox, new Paint());
        float blurRadius = 10f;
        applyBlurToCanvas(bitmap, blurRadius);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private void applyBlurToCanvas(Bitmap bitmap, float blurRadius) {
        RenderScript rs = RenderScript.create(getApplicationContext());
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(blurRadius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bitmap);
        rs.destroy();
    }

    private void getImage() {
        String t1 = receivedString;
        String url1 = "https://geloyujin.000webhostapp.com/fetch_grades.php?";
        String url = url1 + "param=" + receivedString;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject =  new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for (int i = 0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String url2 = object.getString("image");

                            urlImage = "https://geloyujin.000webhostapp.com/grades/"+url2;

                            Glide.with(GradeView.this)
                                    .asBitmap()
                                    .load(urlImage)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            bitmapList.add(resource);
//                                            processImages();
//                                            image.setImageBitmap(resource);
                                        }
                                    });

                        }
                        progress.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}

