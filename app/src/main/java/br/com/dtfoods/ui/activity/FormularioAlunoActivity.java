package br.com.dtfoods.ui.activity;

import static br.com.dtfoods.ui.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
      carregarAluno();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
         finalizaFormulario();
      }
      return super.onOptionsItemSelected(item);
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

   private void inicializacaoCampos() {
      campoNome = findViewById(R.id.activity_formulario_aluno_nome);
      campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
      campoEmail = findViewById(R.id.activity_formulario_aluno_email);
   }

   private void finalizaFormulario() {
      preencherAluno();
      if (aluno.temIdValido()) {
         alunoDAO.editar(aluno);
      } else {
         alunoDAO.salvar(aluno);
      }
      finish();
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