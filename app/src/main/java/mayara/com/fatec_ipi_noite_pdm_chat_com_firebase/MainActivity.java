package mayara.com.fatec_ipi_noite_pdm_chat_com_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;
  private EditText loginEditText;
  private EditText senhaEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginEditText = findViewById(R.id.loginEditText);
    senhaEditText = findViewById(R.id.senhaEditText);
    mAuth = FirebaseAuth.getInstance();
  }

  public void fazerLogin (View v) {
    String login = loginEditText.getEditableText().toString();
    String senha = senhaEditText.getEditableText().toString();

    mAuth.signInWithEmailAndPassword(login, senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
      @Override
      public void onSuccess(AuthResult authResult) {
        startActivity (new Intent (MainActivity.this, ChatActivity.class));
        finish();
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        e.printStackTrace();
        Toast.makeText(MainActivity.this, e.getMessage(),
        Toast.LENGTH_SHORT).show();
        finish();
      }
    });
  }

  public void irParaCadastro (View v) {
    startActivity(new Intent(this, NovoUsuarioActivity.class));
  }
}
