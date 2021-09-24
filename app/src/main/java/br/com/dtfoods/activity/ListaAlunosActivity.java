package br.com.dtfoods.activity;

import static br.com.dtfoods.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.dtfoods.R;
import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

   public static final String TITULO_APPBAR = "Lista de alunos";
   public static final String TAG = "ListaAlunosActivity";
   private final AlunoDAO alunoDAO = new AlunoDAO();
   private ArrayAdapter<Aluno> adapter;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_alunos);
      setTitle(TITULO_APPBAR);
      configuraFabNovoAluno();
      configuraLista();

//      StringBuilder nomeAluno = new StringBuilder("Aluno ");
//      for (int i = 0; i < 20; i++) {
//         alunoDAO.salvar(new Aluno(nomeAluno.toString(), "(11)99938-7391", "thiago.garcia@dtfoods.com.br"));
//         nomeAluno.append(i);
//      }

      alunoDAO.limpar();
      alunoDAO.salvar(new Aluno("Thiago Garcia", "(11)99938-7391", "thiago.garcia@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Denyse Garcia", "(11)99235-2210", "denyse.jardim@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Outro Aluno", "(99)98888-5511", "outro.aluno@gmail.com"));
   }

   @Override
   protected void onResume() {
      super.onResume();
      adapter.clear();
      adapter.addAll(alunoDAO.todos());
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
//      menu.add("Remover");
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
//      configuraListenerDeCliqueLongoPorItem(listaAlunos);
      registerForContextMenu(listaAlunos);
   }

//   private void configuraListenerDeCliqueLongoPorItem(ListView listaAlunos) {
//      listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//         @Override
//         public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
//            Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
//            if (alunoEscolhido != null) {
//               remover(alunoEscolhido);
//            } else {
//               Toast.makeText(ListaAlunosActivity.this, "Aluno inválido.", Toast.LENGTH_SHORT).show();
//            }
//            return false;
//         }
//      });
//   }

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
      adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
      listaAlunos.setAdapter(adapter);
   }
}
