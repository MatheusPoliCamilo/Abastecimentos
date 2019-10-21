package com.example.abastecimento.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abastecimento.ListActivity;
import com.example.abastecimento.R;
import com.example.abastecimento.model.Abastecimento;

public class AbastecimentoViewHolder extends RecyclerView.ViewHolder {

    private TextView tvQuilometragem;
    private TextView tvLitros;
    private ImageView ivPosto;
    private String idDoAbastecimento;

    public AbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListActivity) v.getContext()).modificarAbastecimento(v, idDoAbastecimento);
            }
        });

        tvQuilometragem = itemView.findViewById(R.id.tvQuilometragem);
        tvLitros = itemView.findViewById(R.id.tvLitros);
        ivPosto = itemView.findViewById(R.id.imageView2);
    }

    public void atualizaGavetaComOObjetoQueChegou(Abastecimento c) {
        idDoAbastecimento = c.getId();
        tvQuilometragem.setText(c.getQuilometragem().toString() + " km");
        tvLitros.setText(c.getLitros().toString() + " litros");

        switch (c.getPosto()) {
            case "Texaco":
                ivPosto.setImageResource(R.drawable.texaco);
                break;
            case "Shell":
                ivPosto.setImageResource(R.drawable.shell);
                break;
            case "Ipiranga":
                ivPosto.setImageResource(R.drawable.ipiranga);
                break;
            case "Petrobras":
                ivPosto.setImageResource(R.drawable.petrobras);
                break;
            default:
                ivPosto.setImageResource(R.drawable.outros);
                break;
        }

    }
}
