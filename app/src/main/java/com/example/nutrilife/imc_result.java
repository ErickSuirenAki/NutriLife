package com.example.nutrilife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class imc_result extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private Button button_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imc_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        textView = findViewById(R.id.text_result);
        button = findViewById(R.id.button_voltar);
        button_share = findViewById(R.id.button_share);

        String nome = intent.getStringExtra("Nome");
        double altura = intent.getDoubleExtra("Altura",0.0);
        double peso = intent.getDoubleExtra("Peso", 0.0);
        double quadril = intent.getDoubleExtra("Quadril", 0.0);
        double cintura = intent.getDoubleExtra("Cintura", 0.0);
        String sexo = intent.getStringExtra("Sexo");

        double imc = peso/(altura*altura);

        double rcq = cintura/quadril;

        String risco;
        String classificacao;

        if (imc < 17.0) {
            classificacao = "Muito abaixo do peso";
            risco = "Maior risco de problemas de saúde, deficiências nutricionais, redução do desempenho físico e fraqueza/letargia";
        }
        else if (imc < 18.5) {
            classificacao = "Abaixo do peso";
            risco = "Maior risco de problemas relacionados ao baixo peso e deficiências nutricionais";
        }
        else if (imc < 25.0) {
            classificacao = "Peso normal";
            risco = "Faixa de peso considerada adequada para a maioria dos adultos";
        }
        else if (imc < 30.0) {
            classificacao = "Sobrepeso";
            risco = "Maior risco de alterações metabólicas, diabetes tipo 2 e doenças cardiovasculares";
        }
        else if (imc < 35.0) {
            classificacao = "Obesidade Grau I";
            risco = "Risco elevado de diabetes tipo 2, hipertensão e doenças cardiovasculares";
        }
        else if (imc < 40.0) {
            classificacao = "Obesidade Grau II";
            risco = "Risco muito elevado de complicações metabólicas, cardiovasculares e respiratórias";
        }
        else {
            classificacao = "Obesidade Grau III";
            risco = "Risco muitíssimo elevado de comorbidades e comprometimento da saúde e qualidade de vida";
        }

        String classificacaoRCQ;

        if (sexo.equalsIgnoreCase("Masculino")) {

            if (rcq < 0.90) {
                classificacaoRCQ = "Normal";
            } else {
                classificacaoRCQ = "Risco aumentado";
            }

        } else {

            if (rcq < 0.85) {
                classificacaoRCQ = "Normal";
            } else {
                classificacaoRCQ = "Risco aumentado";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Olá, ").append(nome).append("!").append("\n");
        sb.append("Seu IMC é: ").append(String.format("%.2f",imc)).append("\n");
        sb.append("Classificação: ").append(classificacao).append("\n");
        sb.append("O que pode acontecer: ").append(risco).append("\n");
        sb.append("Sua RCQ é: ").append(String.format("%.2f",rcq)).append("\n");
        sb.append("Risco relacionado a RCQ: ").append(classificacaoRCQ).append("\n");

        textView.setText(sb.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compartilhar = new Intent(Intent.ACTION_SEND);

                compartilhar.setType("text/plain");

                compartilhar.putExtra(Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(compartilhar, "Compartilhar resultado"));
            }
        });




    }
}