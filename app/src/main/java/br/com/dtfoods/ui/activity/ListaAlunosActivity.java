package br.com.dtfoods.ui.activity;

import static br.com.dtfoods.ui.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.dtfoods.R;
import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;
import br.com.dtfoods.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosActivity extends AppCompatActivity {

   public static final String TITULO_APPBAR = "Lista de alunos";
   public static final String TAG = "ListaAlunosActivity";
   private final AlunoDAO alunoDAO = new AlunoDAO();
   private ListaAlunosAdapter adapter;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_alunos);
      setTitle(TITULO_APPBAR);
      configuraFabNovoAluno();
      configuraLista();
   }

   @Override
   protected void onResume() {
      super.onResume();
      adapter.atualiza(alunoDAO.todos());
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
   }

   @Override
   public boolean onContextItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      if (itemId == R.id.activity_lista_alunos_menu_remover) {
         AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
         Aluno aluno = adapter.getItem(menuInfo.position);
         remover(aluno);
      }
      return super.onContextItemSelected(item);
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

   private void configuraLista() {
      ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
      configuraAdapter(listaAlunos);
      configuraListenerDeCliquePorItem(listaAlunos);
      registerForContextMenu(listaAlunos);
   }

   private void remover(Aluno aluno) {
      alunoDAO.remover(aluno);
      adapter.remove(aluno);
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

   private void configuraAdapter(@NonNull ListView listaAlunos) {
      adapter = new ListaAlunosAdapter(this);
      listaAlunos.setAdapter(adapter);
   }
}
