package com.example.registros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int REQUES_CODE = 10;
    private ListView lstAlumnos;
    private AdaptadorAlumnos adaptador;
    private ArrayList<Alumno> persona = new ArrayList<Alumno>();
    public int posic =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Adaptar();

        //pinchar el listview
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View arg1, int position, long arg3) {

                Enviar(position);

            }

        });

        //longClick del listview para eliminar Estudiantes
        lstAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                //Metodo para abrir alerta de borrar Alumnos
                alertBorrarAlumno(pos);

                return true;
            }
        });

    }


    //Metodo para abrir otra actividad pasandole un objeto Alumno
    public void Enviar(int posicion){
        Alumno alum = persona.get(posicion);
        posic = posicion;

        Intent abrir = new Intent(this, Actualizar.class);
        abrir.putExtra("alumno", alum);
        startActivityForResult(abrir, REQUES_CODE);
    }

    //Metodo quqe recibe la respuesta de otra actividad (ActivityforResult)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == REQUES_CODE && resultCode == RESULT_OK){
                if (data.hasExtra("Estudiante")){
                    Alumno copiaAlumno = data.getParcelableExtra("Estudiante");
                    persona.set(posic, copiaAlumno);
                    Adaptar();
                }
         }

    }

    //Metodo para adaptar modelo a ListView
    public void Adaptar(){
        adaptador = new AdaptadorAlumnos(this, persona);

        lstAlumnos = findViewById(R.id.lvLista);
        lstAlumnos.setAdapter(adaptador);
    }

    //Metodos para mostrar los botones de accion en menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
          getMenuInflater().inflate(R.menu.menu_main, menu);

          return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add){
            //Metodo para abrir alerta agreagar Estudiante
            alertNuevaPersona();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Metodo para abrir Alerta y agregar Estudiante
    public void alertNuevaPersona(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_agregar, null);
        builder.setView(view);

        final EditText edtNombre = view.findViewById(R.id.nomAlum);
        final EditText edtEdad = view.findViewById(R.id.edadAlum);
        final EditText edtCarn = view.findViewById(R.id.carnAlum);
        final EditText edtCarr = view.findViewById(R.id.carrAlum);

        builder.setMessage("Agregar Estudiante");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombre = edtNombre.getText().toString().trim();
                String edad = edtEdad.getText().toString();
                String carnet = edtCarn.getText().toString();
                String carrera = edtCarr.getText().toString();
                if (nombre.length()>0 || edad.length()>0 || carnet.length()>0 || carrera.length()>0 ){
                    agregarAlumno(nombre, edad, carnet, carrera);
                    Toast.makeText(getApplicationContext(), "Estudiante Agregado", Toast.LENGTH_SHORT).show();
                }

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

    //Metodo para abrir Alerta de Borrar Alumnos
    public void alertBorrarAlumno(final int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        Alumno al = persona.get(posicion);

        builder.setMessage("¿Deseas Borrar al alumno: "+al.getNombre()+"?");
        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Eliminado", Toast.LENGTH_LONG).show();
                persona.remove(posicion);
                Adaptar();

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

    //Metodo para agregar alumnos a la lista
    public void agregarAlumno(String nombre, String edad, String carnet, String carrera){
        persona.add(new Alumno(nombre,edad,carnet,carrera));
        //Toast.makeText(MainActivity.this, "ArrayList creado", Toast.LENGTH_LONG).show();
        Adaptar();
    }


}
