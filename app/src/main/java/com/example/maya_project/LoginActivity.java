package com.example.maya_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Tela de login do app.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText campoEmail = findViewById(R.id.campoEmail);
        EditText campoSenha = findViewById(R.id.campoSenha);
        Button botaoEntrar = findViewById(R.id.botaoEntrar);
        TextView txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);

        botaoEntrar.setOnClickListener(v -> {
            String email = campoEmail.getText().toString().trim();
            String senha = campoSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha e-mail e senha.", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario logado = BancoDeDados.fazerLogin(email, senha);
            if (logado == null) {
                Toast.makeText(this, "E-mail ou senha incorretos.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Bem-vindo(a), " + logado.getNome() + "!", Toast.LENGTH_SHORT).show();

            Intent intent;
            if (logado.isEhAdmin()) {
                intent = new Intent(LoginActivity.this, AdminActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, UsuarioActivity.class);
            }
            startActivity(intent);
            finish();
        });

        txtEsqueciSenha.setOnClickListener(v -> {
            Toast.makeText(this, "Entre em contato com a clínica pra recuperar sua senha.",
                    Toast.LENGTH_LONG).show();
        });
    }
}
