package darwingapp.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    // Declare a member variable to Firestore instance

    private EditText UsernameInput;
    private EditText PasswordInput;
    //private Button LoginButton;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button LoginButton = findViewById(R.id.ButtonLogin);


        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsernameInput = findViewById(R.id.UsernameInput);
                PasswordInput = findViewById(R.id.PasswordInput);
                String E = UsernameInput.getText().toString();
                String P = PasswordInput.getText().toString();

                mAuth.signInWithEmailAndPassword(E, P)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Was the sign in successful?
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, Drawing.class));
                                } else {
                                    startActivity(new Intent(MainActivity.this, Drawing.class));
                                    Toast.makeText(getApplicationContext(),getString(R.string.access), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });


    }
}