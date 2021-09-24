package br.com.dtfoods.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dtfoods.R;
import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

   public static final String TITULO_APPBAR = "Novo aluno";
   private EditText campoNome;
   private EditText campoTelefone;
   private EditText campoEmail;
   private final AlunoDAO alunoDAO = new AlunoDAO();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_formulario_aluno);
      setTitle(TITULO_APPBAR);

      inicializacaoCampos();
      configuraBotaoSalvar();
   }

   private void configuraBotaoSalvar() {
      Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
      botaoSalvar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Aluno alunoCriado = criaAluno();
            salva(alunoCriado);
            finish();
         }
      });
   }

   private void inicializacaoCampos() {
      campoNome = findViewById(R.id.activity_formulario_aluno_nome);
      campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
      campoEmail = findViewById(R.id.activity_formulario_aluno_email);
   }

   private void salva(Aluno aluno) {
      alunoDAO.salvar(aluno);
   }

   @NonNull
   private Aluno criaAluno() {
      String nome = campoNome.getText().toString();
      String telefone = campoTelefone.getText().toString();
      String email = campoEmail.getText().toString();
      return new Aluno(nome, telefone, email);
   }
}