package br.com.dtfoods.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
   private int id = 0;
   private String nome;
   private String telefone;
   private String email;

   public Aluno(String nome, String telefone, String email) {
      this.nome = nome;
      this.telefone = telefone;
      this.email = email;
   }

   public Aluno() {
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getTelefone() {
      return telefone;
   }

   public void setTelefone(String telefone) {
      this.telefone = telefone;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @NonNull
   @Override
   public String toString() { return nome; }

   public void setId(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public boolean temIdValido() {
      return id > 0;
   }
}
