package com.example.registros;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseApplication extends Application {

    public static AtomicInteger alumnoID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
       //Iniciamos realm y le mandamos la configuración
        Realm.init(getApplicationContext());
        setUpConfig();
        Realm realm = Realm.getDefaultInstance();
        realm.close();
    }

    private void setUpConfig(){
      //Definimos la configuración de realm
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}