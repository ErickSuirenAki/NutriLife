package com.example.nutrilife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextname;
    private  EditText editTextpeso;
    private  EditText editTextaltura;
    private  EditText editTextCintura;
    private  EditText editTextQuadril;

    private RadioGroup radioGroup;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextname = findViewById(R.id.edt_name);
        editTextpeso = findViewById(R.id.edt_peso);
        editTextaltura = findViewById(R.id.edt_altura);
        editTextCintura= findViewById(R.id.edt_cintura);
        editTextQuadril = findViewById(R.id.edt_quadril);
        radioGroup= findViewById(R.id.radioGroup);
        button = findViewById(R.id.button_calcular);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextname.getText().toString().isEmpty() || editTextaltura.getText().toString().isEmpty() || editTextpeso.getText().toString().isEmpty()
                        || editTextCintura.getText().toString().isEmpty() || editTextQuadril.getText().toString().isEmpty()){
                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                    janela.setTitle("NutriLife");
                    janela.setMessage("Preencha todos os campos");
                    janela.setIcon(R.drawable.nu);
                    janela.setPositiveButton("OK", null);
                    janela.show();
                }
                else{
                    double altura;
                    double peso;
                    double quadril;
                    double cintura;
                    try {
                        altura = Double.parseDouble(editTextaltura.getText().toString());
                        peso = Double.parseDouble(editTextpeso.getText().toString());
                        quadril = Double.parseDouble(editTextQuadril.getText().toString());
                        cintura = Double.parseDouble(editTextCintura.getText().toString());


                    } catch (NumberFormatException e) {
                        Toast.makeText(
                                MainActivity.this,
                                "Digite apenas valores numéricos.",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }
                    String nome = editTextname.getText().toString();
                    int selecionado = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(selecionado);
                    String sexo = rb.getText().toString();


                    AlertDialog.Builder janela = new AlertDialog.Builder(MainActivity.this);
                    janela.setTitle("NutriLife");
                    janela.setMessage("Todos os dados estão corretos?");
                    janela.setIcon(R.drawable.nu);
                    janela.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, imc_result.class);
                            intent.putExtra("Nome", nome);
                            intent.putExtra("Altura", altura);
                            intent.putExtra("Peso", peso);
                            intent.putExtra("Cintura", cintura);
                            intent.putExtra("Quadril", quadril);
                            intent.putExtra("Sexo", sexo);
                            startActivity(intent);

                        }
                    });

                    janela.setNegativeButton("Não", null);
                    janela.show();

                }

            }
        });

    }
}