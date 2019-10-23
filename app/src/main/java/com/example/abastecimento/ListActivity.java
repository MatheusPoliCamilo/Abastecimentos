package com.example.abastecimento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.abastecimento.adapter.AbastecimentoAdapter;
import com.example.abastecimento.model.AbastecimentoDao;

public class ListActivity extends AppCompatActivity {

    private AbastecimentoAdapter adaptador;
    private RecyclerView rvAbastecimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rvAbastecimentos = findViewById(R.id.rvAbastecimentos);

        adaptador = new AbastecimentoAdapter();
        rvAbastecimentos.setLayoutManager(new LinearLayoutManager(this));
        rvAbastecimentos.setAdapter(adaptador);
    }

    public void modificarAbastecimento(View v, String idDoAbastecimento) {
        Intent intencao = new Intent(this, FormularioActivity.class);
        intencao.putExtra("idAbastecimento", idDoAbastecimento);
        startActivityForResult(intencao, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                int posicao = data.getIntExtra("posicaoDoObjetoEditado", -1);
                adaptador.notifyItemChanged(posicao);
                rvAbastecimentos.smoothScrollToPosition(posicao);
            } else if (resultCode == 201) {
                Toast.makeText(this, "Abastecimento cadastrado com sucesso", Toast.LENGTH_LONG).show();
                int posicao = AbastecimentoDao.obterInstancia().obterLista().size() - 1;
                adaptador.notifyItemInserted(posicao);
                rvAbastecimentos.smoothScrollToPosition(posicao);
            } else if (resultCode == 202) {
                Toast.makeText(this, "Abastecimento removido com sucesso", Toast.LENGTH_LONG).show();
                int posicao = data.getIntExtra("posicaoDoObjetoExcluido", -1);
                adaptador.notifyItemRemoved(posicao);
            }
        }
    }

    public void adicionarAbastecimento(View v) {
        Intent intencao = new Intent(this, FormularioActivity.class);
        startActivityForResult(intencao, 1);
    }
}
