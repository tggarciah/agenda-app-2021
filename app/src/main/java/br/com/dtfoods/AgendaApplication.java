package br.com.dtfoods;

import android.app.Application;

import br.com.dtfoods.dao.AlunoDAO;
import br.com.dtfoods.model.Aluno;

public class AgendaApplication extends Application {

   @Override
   public void onCreate() {
      super.onCreate();

      criaAlunosDeTestes();
   }

   private void criaAlunosDeTestes() {
      AlunoDAO alunoDAO = new AlunoDAO();
      alunoDAO.salvar(new Aluno("Thiago Garcia", "(11)99938-7391", "thiago.garcia@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Denyse Garcia", "(11)99235-2210", "denyse.jardim@dtfoods.com.br"));
      alunoDAO.salvar(new Aluno("Outro Aluno", "(99)98888-5511", "outro.aluno@gmail.com"));
   }
}
