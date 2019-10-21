package com.example.abastecimento.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abastecimento.R;
import com.example.abastecimento.model.Abastecimento;
import com.example.abastecimento.model.AbastecimentoDao;

public class AbastecimentoAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout elementoPrincipalXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.abastecimento_item, parent, false
        );
        return new AbastecimentoViewHolder(elementoPrincipalXML) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Abastecimento c = AbastecimentoDao.obterInstancia().obterLista().get(position);
        AbastecimentoViewHolder gaveta = (AbastecimentoViewHolder) holder;
        gaveta.atualizaGavetaComOObjetoQueChegou(c);
    }

    @Override
    public int getItemCount() {
        return AbastecimentoDao.obterInstancia().obterLista().size();
    }
}
