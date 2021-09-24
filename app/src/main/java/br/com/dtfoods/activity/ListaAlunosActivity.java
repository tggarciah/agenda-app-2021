package br.com.dtfoods.activity;

import static br.com.dtfoods.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.dtfoods.R;
import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

   public static final String TITULO_APPBAR = "Lista de alunos";
   public static final String TAG = "ListaAlunosActivity";
   private final AlunoDAO alunoDAO = new AlunoDAO();

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_alunos);
      setTitle(TITULO_APPBAR);
      configuraFabNovoAluno();

      alunoDAO.salvar(new Aluno("Thiago Garcia", "(11)99938-7391", "thiago.garcia@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Denyse Garcia", "(11)99235-2210", "denyse.jardim@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Outro Aluno", "(99)98888-5511", "outro.aluno@gmail.com"));
   }

   private void configuraFabNovoAluno() {
      FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
      botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            abreFormularioModoInsereAluno();
         }
      });
   }

   private void abreFormularioModoInsereAluno() {
      startActivity(new Intent(this, FormularioAlunoActivity.class));
   }

   @Override
   protected void onResume() {
      super.onResume();
      configuraLista();
   }

   private void configuraLista() {
      ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
      List<Aluno> alunos = alunoDAO.todos();
      configuraAdapter(listaAlunos, alunos);
      configuraListenerDeCliquePorItem(listaAlunos);
   }

   private void configuraListenerDeCliquePorItem(@NonNull ListView listaAlunos) {
      listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
            Aluno aluno = (Aluno) adapterView.getItemAtPosition(posicao);

            // atalho para o código abaixo logi
            Log.i(TAG, "aluno: " + aluno);

            abreFormularioModoEditaAluno(aluno);
         }
      });
   }

   private void abreFormularioModoEditaAluno(Aluno aluno) {
      Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
      vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
      startActivity(vaiParaFormularioActivity);
   }

   private void configuraAdapter(@NonNull ListView listaAlunos, List<Aluno> alunos) {
      listaAlunos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos));
   }
}
