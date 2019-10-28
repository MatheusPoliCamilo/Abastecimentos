package com.example.abastecimento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.abastecimento.model.Abastecimento;
import com.example.abastecimento.model.AbastecimentoDao;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAbastecimentos;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbastecimentos = findViewById(R.id.button);
        btnAbastecimentos.setOnClickListener(btnAbastecimentosClick);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView autonomia = findViewById(R.id.tvAutonomia);
        autonomia.setText(getAutonomia() + " KM/L");
    }

    private View.OnClickListener btnAbastecimentosClick = new View.OnClickListener() {
        public void onClick(View v){
            intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        }
    };

    private String getAutonomia() {
        ArrayList<Abastecimento> abastecimentos = AbastecimentoDao.obterInstancia().obterLista();

        DecimalFormat format = new DecimalFormat("0.##");

        if (abastecimentos.size() <= 1) return "0.0";

        int quilometragem = abastecimentos.get(0).getQuilometragem() -
                abastecimentos.get(abastecimentos.size() - 1).getQuilometragem();
        double litros = 0.0;

        for (int i = 1; i < abastecimentos.size(); i++) {
            litros += abastecimentos.get(i).getLitros();
        }

        return format.format(quilometragem/litros);
    }
}