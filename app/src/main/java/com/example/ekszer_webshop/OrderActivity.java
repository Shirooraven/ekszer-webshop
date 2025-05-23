package com.example.ekszer_webshop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderActivity extends AppCompatActivity {

    private EditText editName, editAddress, editPhone;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    db.collection("cart")
      .document(uid)
      .collection("products")
      .get()
      .addOnSuccessListener(query -> {
          for (DocumentSnapshot doc : query.getDocuments()) {
              doc.getReference().delete(); // minden kosárelemet töröl
          }
          Toast.makeText(this, "Sikeres megrendelés!", Toast.LENGTH_LONG).show();
          finish(); // vissza a kosárhoz
      })
      .addOnFailureListener(e -> 
          Toast.makeText(this, "Hiba a kosár ürítésekor", Toast.LENGTH_SHORT).show());
});

    }
}
