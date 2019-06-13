package com.example.registros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class Actualizar extends AppCompatActivity {

    private EditText etNombre, etEdad, etCarnet;
    private Alumno alum;
    private Button btAceptar;
    private  Button btCancelar;

    Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        realm = Realm.getDefaultInstance();

        etNombre = findViewById(R.id.actNombre);
        etEdad = findViewById(R.id.actEdad);
        etCarnet = findViewById(R.id.actCarnet);
        //Spinner
        String [] opciones = { "Ingeniería de sistemas informáticos", "Licenciatura en idiomas", "Licenciatura en mercadeo internacional", "Doctorado en medicina", "Licenciatura en memes"};
        final Spinner spinner = findViewById(R.id.idspinnerdos);
        // Creando un arrayAdapter y asignandolo al spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Actualizar.this, R.layout.spinner_item_configuracion, opciones);

        spinner.setAdapter(adapter);


        alum = getIntent().getParcelableExtra("alumno");

        etNombre.setText(alum.getNombre());
        etEdad.setText(alum.getEdad());
        etCarnet.setText(alum.getCarnet());
        //Selleccionar spinner automaticamente
        String valSpinner = alum.getCarrera();
        for (int i =0; i<opciones.length; i++){
            if (opciones[i].equals(valSpinner)){
                spinner.setSelection(i);
                break;
            }
        }


        // Enlazamos las variables con los componentes que tenemos en el XML
        btAceptar = findViewById(R.id.btnGuardar);
        btCancelar = findViewById(R.id.btnCancelar);

        // Definimos el listener que ejecutará el método onClick del botón aceptar.
        btAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Guardamos el texto del EditText en un String.
                String nombre = etNombre.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();
                String carnet = etCarnet.getText().toString().trim();
                String carrera = spinner.getSelectedItem().toString();


                // Si los EditText no están vacíos recogemos el resultado.
                if (nombre.length()>0 || edad.length()>0 || carnet.length()>0 ) {

                    //Actualizamos los valores del objeto Alumno con los valores de los EditText
                    Alumno alumnEditar = realm.where(Alumno.class).equalTo("Carnet", carnet).findFirst();
                    realm.beginTransaction();
                    alumnEditar.setNombre(nombre);
                    alumnEditar.setEdad(edad);
                    alumnEditar.setCarrera(carrera);
                    realm.commitTransaction();
                    /*alum.setNombre(nombre);
                    alum.setEdad(edad);
                    alum.setCarnet(carnet);
                    alum.setCarrera(carrera);*/


                    // Recogemos el intent que ha llamado a esta actividad.
                    //Intent in = getIntent();
                    // Le metemos el resultado que queremos mandar a la actividad principal.
                    //in.putExtra("Estudiante", alumnEditar);
                    /* Establecemos el resultado, y volvemos a la actividad
                       principal. La variable que introducimos en primer lugar
                       "RESULT_OK" es de la propia actividad, no tenemos que
                       declararla nosotros.
                    */
                    //setResult(RESULT_OK, in);
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
                    alertRegresar();
                }
         });

    }




    //Metodo para Regreso a la actividad anterior
    @Override
    public void onBackPressed() {
       alertRegresar();
    }

    //Metodo para abrir Alerta de confirmacion si desea salir de la actividad
    public void alertRegresar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Actualizar.this);

        builder.setMessage("Los datos no se han guardado ¿Deseas cancelar la edición?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Actualizar.this, "Edicion cancelada", Toast.LENGTH_LONG).show();
                 finish();

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
