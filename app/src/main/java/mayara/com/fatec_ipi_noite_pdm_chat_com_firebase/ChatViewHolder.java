package mayara.com.fatec_ipi_noite_pdm_chat_com_firebase;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ChatViewHolder extends RecyclerView.ViewHolder{
  TextView dataNomeTextView;
  TextView mensagemTextView;
  EditText mensagemEditText;
  ChatViewHolder (View v){
    super (v);
    this.dataNomeTextView =
      v.findViewById(R.id.dataNomeTextView);
    this.mensagemTextView =
      v.findViewById(R.id.mensagemTextView);
  }
}
