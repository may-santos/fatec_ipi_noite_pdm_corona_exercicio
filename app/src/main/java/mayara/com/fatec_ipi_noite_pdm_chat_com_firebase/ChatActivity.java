package mayara.com.fatec_ipi_noite_pdm_chat_com_firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.time.chrono.IsoEra.CE;

public class ChatActivity extends AppCompatActivity {

  private RecyclerView mensagensRecyclerView;
  private ChatAdapter adapter;
  private List<Mensagem> mensagens;
  private EditText mensagemEditText;
  private FirebaseUser fireUser;
  private CollectionReference mMsgsReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    mensagensRecyclerView =
      findViewById(R.id.mensagensRecyclerView);
    mensagens = new ArrayList<>();
    adapter = new ChatAdapter(mensagens, this);
    mensagensRecyclerView.setAdapter(adapter);
    LinearLayoutManager linearLayoutManager = new
      LinearLayoutManager(this);
    linearLayoutManager.setReverseLayout(true);
    mensagensRecyclerView.setLayoutManager(linearLayoutManager);
    mensagemEditText = findViewById(R.id.mensagemEditText);
  }

  private void setupFirebase (){
    fireUser = FirebaseAuth.getInstance().getCurrentUser();
    mMsgsReference = FirebaseFirestore.getInstance().collection("mensagens");
    getRemoteMsgs();
  }
  @Override
  protected void onStart() {
    super.onStart();
    setupFirebase();
  }

  private void getRemoteMsgs (){
    mMsgsReference.addSnapshotListener(new
     EventListener<QuerySnapshot>() {
       @Override
       public void onEvent(@Nullable QuerySnapshot
                             queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
       {
         mensagens.clear();
         for (DocumentSnapshot doc :
           queryDocumentSnapshots.getDocuments()){
           Mensagem incomingMsg = doc.toObject(Mensagem.class);
           mensagens.add(incomingMsg);
         }
         Collections.sort(mensagens);
         adapter.notifyDataSetChanged();
       }
     });
  }

  public void enviarMensagem (View view){
    String mensagem = mensagemEditText.getText().toString();
    Mensagem m = new Mensagem (fireUser.getEmail(), new Date(), mensagem);
    esconderTeclado(view);
    mMsgsReference.add(m);
  }

  private void esconderTeclado (View v){
    InputMethodManager ims =
      (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    ims.hideSoftInputFromWindow(v.getWindowToken(), 0);
  }
}
