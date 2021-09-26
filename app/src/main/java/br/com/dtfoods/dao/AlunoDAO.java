package br.com.dtfoods.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.dtfoods.model.Aluno;

public class AlunoDAO {

   private static final List<Aluno> alunos = new ArrayList<>();
   private static int contadorDeIds = 1;

   public List<Aluno> todos() {
      //com essa sintaxe estamos retornando uma nova referência a quem chamar esse método, em outras palavras um cópia.
      return new ArrayList<>(alunos);
   }

   public void salvar(Aluno aluno) {
      aluno.setId(contadorDeIds);
      alunos.add(aluno);
      atualizaIds();
   }

   private void atualizaIds() {
      contadorDeIds++;
   }

   public void editar(Aluno aluno){
      Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
      if (alunoEncontrado != null){
         int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
         alunos.set(posicaoDoAluno, aluno);
      }
   }

   @Nullable
   private Aluno buscaAlunoPeloId(Aluno aluno) {
      for (Aluno a : alunos) {
         if (a.getId() == aluno.getId()) {
            return a;
         }
      }
      return null;
   }

   public void remover(Aluno aluno) {
      Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
      if (alunoDevolvido != null) {
         alunos.remove(alunoDevolvido);
      }
   }
}
