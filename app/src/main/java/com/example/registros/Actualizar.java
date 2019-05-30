package com.example.registros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Actualizar extends AppCompatActivity {

    private EditText etNombre, etEdad, etCarnet, etCarrera;
    private Alumno alum;
    private Button btAceptar;
    private  Button btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        etNombre = findViewById(R.id.actNombre);
        etEdad = findViewById(R.id.actEdad);
        etCarnet = findViewById(R.id.actCarnet);
        etCarrera = findViewById(R.id.actCarrera);

        alum = getIntent().getParcelableExtra("alumno");

        etNombre.setText(alum.getNombre());
        etEdad.setText(alum.getEdad());
        etCarnet.setText(alum.getCarnet());
        etCarrera.setText(alum.getCarrera());


        // Enlazamos las variables con los componentes que tenemos en el XML
        btAceptar = findViewById(R.id.btnGuardar);
        btCancelar = findViewById(R.id.btnCancelar);

        // Definimos el listener que ejecutará el método onClick del botón aceptar.
        btAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Guardamos el texto del EditText en un String.
                String nombre = etNombre.getText().toString().trim();
                String edad = etEdad.getText().toString();
                String carnet = etCarnet.getText().toString();
                String carrera = etCarrera.getText().toString();


                // Si los EditText no están vacíos recogemos el resultado.
                if (!nombre.equals("") && !edad.equals("")  && !carnet.equals("")  && !carrera.equals("") ) {

                    //Actualizamos los valores del objeto Alumno con los valores de los EditText
                    alum.setNombre(nombre);
                    alum.setEdad(edad);
                    alum.setCarnet(carnet);
                    alum.setCarrera(carrera);

                    // Recogemos el intent que ha llamado a esta actividad.
                    Intent in = getIntent();
                    // Le metemos el resultado que queremos mandar a la actividad principal.
                    in.putExtra("Estudiante", alum);
                    /* Establecemos el resultado, y volvemos a la actividad
                       principal. La variable que introducimos en primer lugar
                       "RESULT_OK" es de la propia actividad, no tenemos que
                       declararla nosotros.
                    */
                    setResult(RESULT_OK, in);
                    Toast.makeText(Actualizar.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                    // Finalizamos la Activity para volver a la anterior
                    finish();
                } else {
                    // Si no hay nada escrito en los EditText lo avisamos.
                    Toast.makeText(Actualizar.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });

            // Definimos el listener que ejecutará el método onClick del botón cancelar.
         btCancelar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View vi) {
                    // Si se pulsa el botón, establecemos el resultado como cancelado.
                    // Al igual que con "RESULT_OK", esta variable es de la activity.
                    setResult(RESULT_CANCELED);
                    Toast.makeText(Actualizar.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    // Finalizamos la Activity para volver a la anterior
                    finish();
                }
         });

    }

}
