package darwingapp.com;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import petrov.kristiyan.colorpicker.ColorPicker;

public class Drawing extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuopt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle item selection
        switch (item.getItemId()) {
            case R.id.menuitems:
// Code for a new game goes here...
                return true;
            case R.id.View_menuitem:
// Code for help goes here...
                Intent v = new Intent(Drawing.this, viewart.class);
                startActivity(v);
                return true;
            case R.id.Erase_menuitem:
                paint.setColor(Color.parseColor("#FFFFFF"));
                return true;
                case R.id.search_menuitem:
                    Intent s = new Intent(Drawing.this, Search.class);
                    startActivity(s);
                    return true;
                    case R.id.Help_menuitem:
                        Intent h = new Intent(Drawing.this, InformationOnApp.class);
                        startActivity(h);
                        return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // creating the object of type DrawView
    // in order to get the reference of the View
    private DrawView paint;

    // creating objects of type button
    private ImageButton save, color, stroke, undo;

    // creating a RangeSlider object, which will
    // help in selecting the width of the Stroke
    private RangeSlider rangeSlider;
    private TextInputEditText TextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        // getting the reference of the views from their ids
        paint = (DrawView) findViewById(R.id.draw_view);
        rangeSlider = (RangeSlider) findViewById(R.id.rangebar);
        undo = (ImageButton) findViewById(R.id.btn_undo);
        save = (ImageButton) findViewById(R.id.btn_save);
        color = (ImageButton) findViewById(R.id.btn_color);
        stroke = (ImageButton) findViewById(R.id.btn_stroke);
        TextInputEditText = (TextInputEditText) findViewById(R.id.fileName);






        // creating a OnClickListener for each button,
        // to perform certain actions

        // the undo button will remove the most
        // recent stroke from the canvas
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });

        // the save button will save the current
        // canvas which is actually a bitmap
        // in form of PNG, in the storage
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextInputEditText.getVisibility() == View.VISIBLE)
                    TextInputEditText.setVisibility(View.GONE);
                else
                    TextInputEditText.setVisibility(View.VISIBLE);

                Editable Name;

                Name = TextInputEditText.getText();

                if(TextUtils.isEmpty(Name)){

                    Toast.makeText(getApplicationContext(),R.string.Namefilepopup, Toast.LENGTH_SHORT).show();

                }else{

                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    // Create a storage reference from our app
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://hwfirebase-6bc83.appspot.com");

// Create a reference to "mountains.jpg"
                    StorageReference mountainsRef = storageRef.child("Images/"+Name.toString()+".jpg");

// Create a reference to 'images/mountains.jpg'
                    StorageReference mountainImagesRef = storageRef.child("images/"+Name);


                    // While the file names are the same, the references point to different files
                    mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                    mountainsRef.getPath().equals(mountainImagesRef.getPath());


                    // getting the bitmap from DrawView class
                    Bitmap bmp = paint.save();

                    // opening a OutputStream to write into the file
                    OutputStream imageOutStream = null;

                    ContentValues cv = new ContentValues();

                    // name of the file
                    cv.put(MediaStore.Images.Media.DISPLAY_NAME, Name.toString()+ ".png");

                    // type of the file
                    cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

                    // location of the file to be saved
                    cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);


// Get the data from an ImageView as bytes
                    paint.setDrawingCacheEnabled(true);
                    paint.buildDrawingCache();
                    Bitmap bitmap = paint.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);


                    // get the Uri of the file which is to be created in the storage
                    Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
                    try {
                        // open the output stream with the above uri
                        imageOutStream = getContentResolver().openOutputStream(uri);

                        // this method writes the files in storage
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);

                        // close the output stream after use
                        imageOutStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }







            }
        });
        // the color button will allow the user
        // to select the color of his brush
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(Drawing.this);

                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // get the integer value of color
                        // selected from the dialog box and
                        // set it as the stroke color

                        paint.setColor(color);
                    }
                    @Override
                    public void onCancel() {
                        colorPicker.dismissDialog();
                    }
                })
                        .setColors(0,Color.BLACK,Color.CYAN,Color.rgb(178,34,34)
                                ,Color.rgb(255,140,0)
                                ,Color.rgb(0,128,0)
                                ,Color.rgb(102,205,170)
                                ,Color.GRAY,Color.rgb(106,90,205)
                                ,Color.rgb(216,191,216)
                                ,Color.LTGRAY,
                                Color.rgb(0,128,128)
                                ,Color.rgb(205,133,63)
                                ,Color.rgb(244,164,96)
                                ,Color.rgb(222,184,135)
                                ,Color.rgb(255,228,181)
                                ,Color.rgb(255,228,225)
                                ,Color.rgb(255,218,185)
                                ,Color.rgb(176,196,222)
                                ,Color.rgb(46,139,87)
                                ,Color.rgb(218,165,32)
                                ,Color.rgb(255,215,0)
                                ,Color.rgb(250,128,114)
                                ,Color.rgb(128,0,0)
                                ,Color.rgb(176,224,230)
                                ,Color.rgb(176,224,230)
                                ,Color.rgb(199,21,133)
                                ,Color.rgb(0,255,127)
                                ,Color.rgb(218,112,214))
                        // set the number of color columns
                        // you want  to show in dialog.
                        .setColumns(6)
                        // set a default color selected
                        // in the dialog
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });
        // the button will toggle the visibility of the RangeBar/RangeSlider
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });

        // set the range of the RangeSlider
        rangeSlider.setValueFrom(0.0f);
        rangeSlider.setValueTo(100.0f);

        // adding a OnChangeListener which will
        // change the stroke width
        // as soon as the user slides the slider
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
            }
        });

        // pass the height and width of the custom view
        // to the init method of the DrawView object
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });


    }

}