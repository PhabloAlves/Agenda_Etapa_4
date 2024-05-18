package com.example.agende_comview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agende_comview.R;
import com.example.agende_comview.fragment.FragmentDatePicker;
import com.example.agende_comview.fragment.FragmentTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CompromissosDB compromissoDB;
    private Button buttonData;
    private Button buttonHora;
    private EditText editTextDescricao;
    private Button buttonHoje;
    private Button buttonOutraData;

    private String dataSelecionada;
    private String horaSelecionada;
    private TextView textViewCompromissos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compromissoDB = new CompromissosDB(this); // Inicializando o banco de dados
        buttonData = findViewById(R.id.data);
        buttonHora = findViewById(R.id.hora);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        textViewCompromissos = findViewById(R.id.textViewCompromissos);
        buttonHoje = findViewById(R.id.data_hoje);
        buttonOutraData = findViewById(R.id.data_outra);

        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraDialogoDatePicker(v);
            }
        });

        buttonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraDialogoTimePicker(v);
            }
        });

        Button botaoCriar = findViewById(R.id.buttonOk);
        botaoCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = editTextDescricao.getText().toString();
                Log.d("MainActivity", "Data do compromisso: " + dataSelecionada);
                Log.d("MainActivity", "Hora do compromisso: " + horaSelecionada);
                Log.d("MainActivity", "Descrição do compromisso: " + descricao);
                criaCompromisso(dataSelecionada, horaSelecionada, descricao);
            }
        });

        buttonHoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraCompromissos(getDataDeHoje());
            }
        });

        buttonOutraData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraDialogoDatePicker(v);
            }
        });

        mostraCompromissos(getDataDeHoje());
    }

    public void mostraDialogoDatePicker(View v) {
        DialogFragment newFragment = new FragmentDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void mostraDialogoTimePicker(View v) {
        DialogFragment newFragment = new FragmentTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void setDataSelecionada(String dataSelecionada) {
        this.dataSelecionada = dataSelecionada;
        Log.d("MainActivity", "Data selecionada: " + dataSelecionada);
        mostraCompromissos(dataSelecionada);
    }

    public void setHoraSelecionada(String horaSelecionada) {
        this.horaSelecionada = horaSelecionada;
        Log.d("MainActivity", "Hora selecionada: " + horaSelecionada);
    }

    private void criaCompromisso(String data, String hora, String descricao) {
        if (data != null && hora != null && !descricao.isEmpty()) {
            Compromisso compromisso = new Compromisso(data, hora, descricao);
            compromissoDB.addCompromisso(compromisso);
            mostraCompromissos(data);
        } else {
            Log.d("MainActivity", "Dados incompletos para criar compromisso");
        }
    }

    void mostraCompromissos(String data) {
        String clausulaWhere = CompromissosDBSchema.CompromissosTbl.Cols.DATA + " = ?";
        String[] argsWhere = new String[]{data};
        String compromissos = compromissoDB.listaCompromissos(clausulaWhere, argsWhere);
        textViewCompromissos.setText(compromissos);
    }

    private String getDataDeHoje() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        return formatter.format(calendar.getTime());
    }
}
