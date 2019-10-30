package com.example.abastecimento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abastecimento.R;
import com.example.abastecimento.model.Abastecimento;
import com.example.abastecimento.model.AbastecimentoDao;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FormularioActivity extends AppCompatActivity {

    private Abastecimento objetoAbastecimento;
    private String idDoAbastecimento;
    private TextInputEditText etQuilometragem;
    private TextInputEditText etLitros;
    private MaterialSpinner spPosto;
    String caminhoDaFoto = null;

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

    public void salvar() {
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

    public void excluir() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.formulario_menu, menu);

        if(idDoAbastecimento == null) menu.removeItem(R.id.menuExcluir);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSalvar:
                salvar();
                break;
            case R.id.menuExcluir:
                excluir();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private File criarArquivoParaSalvarFoto() throws IOException {
        String nomeDaFoto = UUID.randomUUID().toString();

        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fotografia = File.createTempFile(nomeDaFoto, ".jpg", diretorio);
        caminhoDaFoto = fotografia.getAbsolutePath();

        return fotografia;
    }

    public void abrirCamera(View v){
        Intent intecaoAbrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File arquivoDaFoto = null;

        try {
            arquivoDaFoto = criarArquivoParaSalvarFoto();
        } catch (IOException ex) {
            Toast.makeText(this, "Não foi possível criar o arquivo para foto", Toast.LENGTH_LONG).show();
        }

        if (arquivoDaFoto != null) {
            Uri fotoURI = FileProvider.getUriForFile(this,
                    "com.example.abastecimento.fileprovider",
                    arquivoDaFoto);
            intecaoAbrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
            startActivityForResult(intecaoAbrirCamera, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                objetoAbastecimento.setCaminhoFotografia(caminhoDaFoto);
                atualizaFotografiaNaTela();
            }
        }
    }

    private void atualizaFotografiaNaTela(){
        if(objetoAbastecimento.getCaminhoFotografia() != null) {
            ImageView ivFotografia = findViewById(R.id.ivFotografia);
            ivFotografia.setImageURI(Uri.parse(objetoAbastecimento.getCaminhoFotografia()));
        }
    }
}
