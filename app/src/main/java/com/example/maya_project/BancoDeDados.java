package com.example.maya_project;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Banco de dados simulado em memória.
 * Os métodos são todos estáticos pra facilitar o uso a partir das Activities.
 */
public class BancoDeDados {

    // listas que guardam os dados do app
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Consulta> consultas = new ArrayList<>();

    // referência para o usuário que está logado no momento
    private static Usuario usuarioLogado = null;

    // bloco estático: roda uma única vez ao usar a classe.
    static {
        // ====== USUÁRIO ADMIN PADRÃO ======
        usuarios.add(new Usuario(
                "Maya Yoshiko Yamamoto",
                "admin@gmail.com",
                "admin123",
                "10/03/1975",
                "São Paulo/SP",
                "(11) 99999-9999",
                "Rua Rio Grande, 141 — sala 3 — Vila Mariana, São Paulo/SP",
                "000.000.000-00",
                "",
                true
        ));

        // ====== USUÁRIO PACIENTE PADRÃO ======
        usuarios.add(new Usuario(
                "Maria Silva",
                "usuario@gmail.com",
                "usuario123",
                "15/05/1990",
                "Curitiba/PR",
                "(11) 98765-4321",
                "Rua das Flores, 100 — Pinheiros, São Paulo/SP",
                "123.456.789-00",
                "",
                false
        ));

        // ====== OUTROS PACIENTES ======
        usuarios.add(new Usuario(
                "João Pereira",
                "joao@gmail.com",
                "joao123",
                "22/05/1985",
                "Rio de Janeiro/RJ",
                "(11) 97777-7777",
                "Av. Brasil, 200 — Copacabana, Rio de Janeiro/RJ",
                "234.567.890-11",
                "",
                false
        ));
        usuarios.add(new Usuario(
                "Ana Costa",
                "ana@gmail.com",
                "ana123",
                "03/12/1988",
                "São Paulo/SP",
                "(11) 96666-6666",
                "Rua A, 300 — Moema, São Paulo/SP",
                "345.678.901-22",
                "",
                false
        ));
        usuarios.add(new Usuario(
                "Carlos Oliveira",
                "carlos@gmail.com",
                "carlos123",
                "28/02/1992",
                "Porto Alegre/RS",
                "(11) 95555-5555",
                "Rua B, 400 — Centro, São Paulo/SP",
                "456.789.012-33",
                "",
                false
        ));

        // ====== CONSULTAS PRÉ-AGENDADAS ======
        consultas.add(new Consulta("123.456.789-00", "12/05/2026", "14:00"));
        consultas.add(new Consulta("123.456.789-00", "19/05/2026", "10:30"));
    }

    // ====================================================================
    // AUTENTICAÇÃO
    // ====================================================================

    public static Usuario fazerLogin(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                usuarioLogado = u;
                return u;
            }
        }
        return null;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void deslogar() {
        usuarioLogado = null;
    }

    // ====================================================================
    // USUÁRIOS
    // ====================================================================

    public static void cadastrarUsuario(Usuario novo) {
        usuarios.add(novo);
    }

    public static Usuario buscarPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf() != null && u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Retorna apenas os pacientes (não inclui admin).
     */
    public static ArrayList<Usuario> listarPacientes() {
        ArrayList<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEhAdmin()) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    // ====================================================================
    // CONSULTAS
    // ====================================================================

    public static void agendarConsulta(Consulta nova) {
        consultas.add(nova);
    }

    public static ArrayList<Consulta> consultasDoCpf(String cpf) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultas) {
            if (c.getCpfPaciente().equals(cpf)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public static ArrayList<Consulta> listarTodasConsultas() {
        return consultas;
    }

    // ====================================================================
    // ANIVERSARIANTES
    // ====================================================================

    public static ArrayList<Usuario> aniversariantesDoMes(int mes) {
        ArrayList<Usuario> resultado = new ArrayList<>();
        for (Usuario u : listarPacientes()) {
            if (u.getMesAniversario() == mes) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    public static ArrayList<Usuario> aniversariantesDaSemana() {
        ArrayList<Usuario> resultado = new ArrayList<>();
        Calendar hoje = Calendar.getInstance();

        for (Usuario u : listarPacientes()) {
            int diaAniv = u.getDiaAniversario();
            int mesAniv = u.getMesAniversario();

            for (int i = 0; i < 7; i++) {
                Calendar dia = (Calendar) hoje.clone();
                dia.add(Calendar.DAY_OF_MONTH, i);
                int d = dia.get(Calendar.DAY_OF_MONTH);
                int m = dia.get(Calendar.MONTH) + 1;

                if (d == diaAniv && m == mesAniv) {
                    resultado.add(u);
                    break;
                }
            }
        }

        return resultado;
    }

    public static ArrayList<Usuario> aniversariantesDoAno() {
        ArrayList<Usuario> lista = listarPacientes();

        // bubble sort por mês e dia
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - 1 - i; j++) {
                Usuario a = lista.get(j);
                Usuario b = lista.get(j + 1);
                boolean trocar = false;
                if (a.getMesAniversario() > b.getMesAniversario()) {
                    trocar = true;
                } else if (a.getMesAniversario() == b.getMesAniversario()
                        && a.getDiaAniversario() > b.getDiaAniversario()) {
                    trocar = true;
                }
                if (trocar) {
                    lista.set(j, b);
                    lista.set(j + 1, a);
                }
            }
        }

        return lista;
    }
}
