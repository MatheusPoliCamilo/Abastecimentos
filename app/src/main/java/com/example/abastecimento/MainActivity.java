package com.example.abastecimento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    private View.OnClickListener btnAbastecimentosClick = new View.OnClickListener() {
        public void onClick(View v){
            intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        }
    };
}