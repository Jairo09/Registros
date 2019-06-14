package com.example.registros;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorAlumnos extends ArrayAdapter<Alumno> {

    Activity context;
    List<Alumno> personas;

    public AdaptadorAlumnos(Activity context, List<Alumno> listaAlumnos) {
        super(context, R.layout.alumno_list, listaAlumnos);
        this.personas = listaAlumnos;
    }

    public View getView(final int posicion, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.alumno_list, null);

        TextView tvNombre = item.findViewById(R.id.lblnombrelst);
        TextView tvEdad = item.findViewById(R.id.lbledadlst);
        TextView tvCarnet = item.findViewById(R.id.lblcarnetlst);
        TextView tvCarrera = item.findViewById(R.id.lblcarreralst);
        //Button btnelimnar = item.findViewById(R.id.btnEliminar);
        //Button btneditar = item.findViewById(R.id.btnEditar);

        tvNombre.setText(personas.get(posicion).getNombre());
        tvEdad.setText(personas.get(posicion).getEdad());
        tvCarnet.setText(personas.get(posicion).getCarnet());
        tvCarrera.setText(personas.get(posicion).getCarrera());


            /*btnelimnar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"Eliminar " + posicion ,Toast.LENGTH_LONG).show();
                }
            });
             */
        return (item);

    }
}
