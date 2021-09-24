package br.com.dtfoods.activity;

import static br.com.dtfoods.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.dtfoods.R;
import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

   private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
   private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
   private EditText campoNome;
   private EditText campoTelefone;
   private EditText campoEmail;
   private final AlunoDAO alunoDAO = new AlunoDAO();
   private Aluno aluno;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_formulario_aluno);
      inicializacaoCampos();
      configuraBotaoSalvar();
      carregarAluno();
   }

   private void carregarAluno() {
      Intent dados = getIntent();
      if (dados.hasExtra(CHAVE_ALUNO)){
         setTitle(TITULO_APPBAR_EDITA_ALUNO);
         aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
         if (aluno != null) {
            preencherCampos();
         }
      } else {
         setTitle(TITULO_APPBAR_NOVO_ALUNO);
         aluno = new Aluno();
      }
   }

   private void preencherCampos() {
      campoNome.setText(aluno.getNome());
      campoTelefone.setText(aluno.getTelefone());
      campoEmail.setText(aluno.getEmail());
   }

   private void configuraBotaoSalvar() {
      Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
      botaoSalvar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            preencherAluno();
            salva(aluno);
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
      if (aluno.temIdValido()) {
         alunoDAO.editar(aluno);
      } else {
         alunoDAO.salvar(aluno);
      }
   }

   private void preencherAluno() {
      String nome = campoNome.getText().toString();
      String telefone = campoTelefone.getText().toString();
      String email = campoEmail.getText().toString();

      aluno.setNome(nome);
      aluno.setTelefone(telefone);
      aluno.setEmail(email);
   }
}