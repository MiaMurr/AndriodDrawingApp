package darwingapp.com;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Search extends AppCompatActivity {

    private TextView exp;
    private EditText look;
    private Button findBtn;
    private ImageView Display;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        exp = findViewById(R.id.explmessage);
        look = findViewById(R.id.SearchGallery);
        findBtn = findViewById(R.id.btnFind);
        Display = findViewById(R.id.FoundImg);

        storageRef = FirebaseStorage.getInstance().getReference();


        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Ser = look.getText().toString();

                storageRef.child("Images/"+Ser+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri.toString()).into(Display);
                        exp.setText(R.string.OnSuccess);

                    }



                });

                storageRef.child("Images/"+Ser+".jpg").getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        exp.setText(R.string.Onfailure);
                    }
                });


            }
        });
    }
}