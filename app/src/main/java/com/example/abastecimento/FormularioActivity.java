package com.example.abastecimento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.abastecimento.R;
import com.example.abastecimento.model.Abastecimento;
import com.example.abastecimento.model.AbastecimentoDao;
import com.google.android.material.textfield.TextInputEditText;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FormularioActivity extends AppCompatActivity {

    private Abastecimento objetoAbastecimento;
    private String idDoAbastecimento;
    private TextInputEditText etQuilometragem;
    private TextInputEditText etLitros;
    private MaterialSpinner spPosto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        spPosto = findViewById(R.id.spPosto);
        etQuilometragem = findViewById(R.id.etQuilometragem);
        etLitros = findViewById(R.id.etLitros);

        String[] opcoesPrioridade = getResources().getStringArray(R.array.opcoes_prioridade);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesPrioridade);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosto.setAdapter(adapter);

        idDoAbastecimento = getIntent().getStringExtra("idAbastecimento");

        if (idDoAbastecimento == null) {
            objetoAbastecimento = new Abastecimento();
            Button btExcluir = findViewById(R.id.btExcluir);
            btExcluir.setVisibility(View.INVISIBLE);
        } else {
            objetoAbastecimento = AbastecimentoDao.obterInstancia().obterObjetoPeloId(idDoAbastecimento);
            etQuilometragem.setText(objetoAbastecimento.getQuilometragem().toString());
            for (int i = 0; i < spPosto.getAdapter().getCount(); i++) {
                if (spPosto.getAdapter().getItem(i).toString().equals(objetoAbastecimento.getPosto())) {
                    spPosto.setSelection(i + 1);
                    break;
                }
            }
            etLitros.setText(objetoAbastecimento.getLitros().toString());
        }

    }

    public void salvar(View v) {
        objetoAbastecimento.setQuilometragem(Integer.parseInt(etQuilometragem.getText().toString()));
        objetoAbastecimento.setPosto(spPosto.getSelectedItem().toString());
        objetoAbastecimento.setLitros(Double.parseDouble(etLitros.getText().toString()));

        if (idDoAbastecimento == null) {
            AbastecimentoDao.obterInstancia().adicionarNaLista(objetoAbastecimento);
            setResult(201);
        } else {
            int posicaoDoObjeto = AbastecimentoDao.obterInstancia().atualizaNaLista(objetoAbastecimento);
            Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
            intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoEditado", posicaoDoObjeto);
            setResult(200, intencaoDeFechamentoDaActivityFormulario);
        }
        finish();

    }

    public void excluir(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Você tem certeza?")
                .setMessage("Você quer mesmo excluir?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posicaoDoObjeto = AbastecimentoDao.obterInstancia().excluiDaLista(objetoAbastecimento);
                        Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
                        intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoExcluido", posicaoDoObjeto);
                        setResult(202, intencaoDeFechamentoDaActivityFormulario);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
