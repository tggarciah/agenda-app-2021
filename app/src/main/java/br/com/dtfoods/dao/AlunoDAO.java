package br.com.dtfoods.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.dtfoods.model.Aluno;

public class AlunoDAO {

   private final static List<Aluno> alunos = new ArrayList<>();

   public void salvar(Aluno aluno) {
      alunos.add(aluno);
   }

   public List<Aluno> todos() {
      //com essa sintaxe estamos retornando uma nova referência a quem chamar esse método, em outras palavras um cópia.
      return new ArrayList<>(alunos);
   }
}
