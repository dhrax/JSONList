package com.example.jsonlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Monumento> listaMonumentos = new ArrayList<>();
    public static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arrayAdapter = new ArrayAdapter(this, listaMonumentos);
        ListView lvMonumentos = findViewById(R.id.listView);
        lvMonumentos.setAdapter(arrayAdapter);
    }

    private void cargarListaMonumentos() {

        TareaDescargaDatos tarea = new TareaDescargaDatos(this);
        tarea.execute(Constantes.URL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarListaMonumentos();
    }
}
