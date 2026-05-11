package com.example.maya_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Fragment de exercícios.
 */
public class ExerciciosFragment extends Fragment {

    // Lista dos exercícios prescritos.
    private ArrayList<Exercicio> exercicios;

    // referências dos textos do card de resumo
    private TextView txtTotalExercicios;
    private TextView txtTempoTotal;
    private TextView txtConcluidos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);

        // monta a lista de exercícios
        exercicios = new ArrayList<>();
        exercicios.add(new Exercicio(
                "Alongamento de Coluna",
                "Deite de bruços com as pernas esticadas. Apoie as mãos no chão e eleve o tronco lentamente.",
                3, 10, 5, "5x por semana", R.drawable.exercicio_coluna));
        exercicios.add(new Exercicio(
                "Mobilização Cervical",
                "Sentado com a coluna ereta, gire a cabeça lentamente para os lados.",
                2, 12, 4, "4x por semana", R.drawable.exercicio_cervical));
        exercicios.add(new Exercicio(
                "Alongamento Lombar",
                "Deitado de costas, dobre os joelhos e leve-os para um dos lados.",
                3, 15, 6, "3x por semana", R.drawable.exercicio_lombar));

        txtTotalExercicios = view.findViewById(R.id.txtTotalExercicios);
        txtTempoTotal = view.findViewById(R.id.txtTempoTotal);
        txtConcluidos = view.findViewById(R.id.txtConcluidos);

        atualizarResumo();

        Button botaoFeito1 = view.findViewById(R.id.botaoFeito1);
        Button botaoFeito2 = view.findViewById(R.id.botaoFeito2);
        Button botaoFeito3 = view.findViewById(R.id.botaoFeito3);

        botaoFeito1.setOnClickListener(v -> marcarFeito(0, botaoFeito1));
        botaoFeito2.setOnClickListener(v -> marcarFeito(1, botaoFeito2));
        botaoFeito3.setOnClickListener(v -> marcarFeito(2, botaoFeito3));

        return view;
    }

    private void marcarFeito(int posicao, Button botao) {
        Exercicio ex = exercicios.get(posicao);
        if (ex.isFeitoHoje()) {
            Toast.makeText(getContext(), "Você já marcou este exercício hoje!", Toast.LENGTH_SHORT).show();
            return;
        }
        ex.marcarComoFeito();
        botao.setText("✓ Concluído");
        botao.setEnabled(false);
        atualizarResumo();
        Toast.makeText(getContext(), "Bom trabalho!", Toast.LENGTH_SHORT).show();
    }

    private void atualizarResumo() {
        int total = exercicios.size();
        int tempoTotal = 0;
        int concluidos = 0;

        for (Exercicio e : exercicios) {
            tempoTotal += e.getTempoEstimadoMin();
            if (e.isFeitoHoje()) {
                concluidos++;
            }
        }

        txtTotalExercicios.setText(String.valueOf(total));
        txtTempoTotal.setText(tempoTotal + " min");
        txtConcluidos.setText(String.valueOf(concluidos));
    }
}
