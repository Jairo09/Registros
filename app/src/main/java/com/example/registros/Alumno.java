package com.example.registros;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Alumno implements Parcelable {
    private String Nombre;
    private String Edad;
    private String Carnet;
    private String Carrera;



    public Alumno(String nombre, String edad, String carnet, String carrera) {

        Nombre = nombre;
        Edad = edad;
        Carnet = carnet;
        Carrera = carrera;

    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getCarnet() {
        return Carnet;
    }

    public void setCarnet(String carnet) {
        Carnet = carnet;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Nombre);
        dest.writeString(this.Edad);
        dest.writeString(this.Carnet);
        dest.writeString(this.Carrera);
    }

    protected Alumno(Parcel in) {
        this.Nombre = in.readString();
        this.Edad = in.readString();
        this.Carnet = in.readString();
        this.Carrera = in.readString();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
