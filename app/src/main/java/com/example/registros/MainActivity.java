package com.example.registros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Alumno>> {

    private static final int REQUES_CODE = 10;
    private ListView lstAlumnos;
    private RealmResults<Alumno> listaAlumnos;
    private AdaptadorAlumnos adaptador;
    private ArrayList<Alumno> persona = new ArrayList<Alumno>();
    public int posic =0;
    private Alumno alumno;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        Adaptar();
    }

    private void listenOnClick(){
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
            public boolean onItemLongClick(AdapterView<?> arg0,final View view, int pos, long id) {
                //Metodo para abrir alerta de borrar Alumnos
                //alertBorrarAlumno(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Seguro que deseas eliminar?")
                        .setTitle("Eliminar")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarAlumno(view);
                                Adaptar();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog Notificacion = builder.create();
                Notificacion.show();
                return true;
            }
        });

       /* lstAlumnos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Seguro que deseas eliminar?")
                        .setTitle("Eliminar")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarAlumno(view);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog Notificacion = builder.create();
                Notificacion.show();
                return false;
            }
        });*/

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

        listaAlumnos = realm.where(Alumno.class).findAll();
        lstAlumnos = findViewById(R.id.lvLista);
        adaptador = new AdaptadorAlumnos(this, listaAlumnos);
        lstAlumnos.setAdapter(adaptador);
        listenOnClick();
    }

    //Metodo para adaptar modelo a spiner
    public void AdaptarSpiner(){
        String [] opciones= {"Opcion Uno", "Opcion Dos", "Opcion Tres"};
        Spinner spinner = findViewById(R.id.idspinner);
       // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(adapter);

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
        builder.setCancelable(false);
        builder.setView(view);



        final EditText edtNombre = view.findViewById(R.id.nomAlum);
        final EditText edtEdad = view.findViewById(R.id.edadAlum);
        final EditText edtCarn = view.findViewById(R.id.carnAlum);
        //Spinner
        String [] opciones = {"Ingrese carrera", "Ingeniería de sistemas informáticos", "Licenciatura en idiomas", "Licenciatura en mercadeo internacional", "Doctorado en medicina", "Licenciatura en memes"};
        final Spinner spinner = view.findViewById(R.id.idspinner);
        // Creando un arrayAdapter y asignandolo al spinner
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item_configuracion, opciones){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (position == 0){
                    TextView textView = new TextView(MainActivity.this);
                    textView.setHeight(0);
                    textView.setVisibility(view.GONE);
                    convertView = textView;
                    return convertView;
                }else{
                    return super.getDropDownView(position, null, parent);
                }

            }
        };
        spinner.setAdapter(adapter);
        //spinner.setPrompt("Seleccione una carrera");

        builder.setMessage("Agregar Estudiante");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancelado, Los datos no se han guardado", Toast.LENGTH_LONG).show();
            }
        });

       final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        /*Esto captura el evento del boton positivo y dependiendo de las codiciones
          y las validaciones se cerrará el alert*/
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        String nombre = edtNombre.getText().toString().trim();
                        String edad = edtEdad.getText().toString().trim();
                        String carnet = edtCarn.getText().toString().trim().toUpperCase();
                        String carrera = spinner.getSelectedItem().toString();


                        if (nombre.length()>0 && edad.length()>0 && carnet.length()>0){
                            if (carrera.equals("Ingrese carrera")){
                                Toast.makeText(getApplicationContext(), "Seleccione una carrera", Toast.LENGTH_SHORT).show();
                            }else{
                                if (!validarCarnet(carnet)){
                                    agregarAlumno(nombre, edad, carnet, carrera);
                                    Toast.makeText(getApplicationContext(), "Estudiante Agregado", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }else{
                                    Toast.makeText(getApplicationContext(), "El alumno ya existe", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }else {
                            // Si no hay nada escrito en los EditText lo avisamos.
                            Toast.makeText(MainActivity.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

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
       // persona.add(new Alumno(nombre,edad,carnet,carrera));
        //Toast.makeText(MainActivity.this, "ArrayList creado", Toast.LENGTH_LONG).show();
        realm.beginTransaction();
        Alumno alumno = new Alumno(nombre, edad, carnet, carrera);
        realm.copyToRealm(alumno);
        realm.commitTransaction();
        Adaptar();
    }

    public void eliminarAlumno(View view){
        Alumno alum = listaAlumnos.get(lstAlumnos.getPositionForView(view));
        realm.beginTransaction();
        if (alum != null){
            alum.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public boolean validarCarnet(String carnet){
        Alumno alumExist = realm.where(Alumno.class).equalTo("Carnet", carnet).findFirst();
        if (alumExist != null){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void onChange(RealmResults<Alumno> alumnos) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
