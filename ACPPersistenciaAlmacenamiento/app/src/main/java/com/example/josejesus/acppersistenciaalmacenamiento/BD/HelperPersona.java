package com.example.josejesus.acppersistenciaalmacenamiento.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josejesus on 6/21/2016.
 */
public class HelperPersona extends SQLiteOpenHelper {

    private static HelperPersona helperPersona = null;

    private static final String NOMBRE_BD = "BD";
    private static final int VERSION_BD = 1;

    public static class TablaPersona {
        public static String TABLA = "tablaPersona";
        public static String COLUMNA_ID = "id";
        public static String COLUMNA_NOMBRE = "nombre";
        public static String COLUMNA_APELLIDO = "apellido";
        public static String COLUMNA_EDAD = "edad";
    }

    private static final String CONSULTA_CREAR_TABLA = "CREATE TABLE " +
            TablaPersona.TABLA + "(" +
            TablaPersona.COLUMNA_ID  + " integer primary key autoincrement, " +
            TablaPersona.COLUMNA_NOMBRE + " VARCHAR, " +
            TablaPersona.COLUMNA_APELLIDO + " VARCHAR, " +
            TablaPersona.COLUMNA_EDAD + " integer );";

    private static final String CONSULTA_ELIMINAR_TABLA = "DELETE TABLE if exists " + TablaPersona.TABLA;

    public static HelperPersona getInstance(Context context) {
        if (helperPersona == null) {
            helperPersona = new HelperPersona(context.getApplicationContext());
        }
        return helperPersona;
    }

    public HelperPersona(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//Cada vez que un objeto helperPersona es creado
        db.execSQL(CONSULTA_CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Eliminar la tabla y actualizar cada vez que se requiera
        db.execSQL(CONSULTA_ELIMINAR_TABLA);
        onCreate(db);
    }
}
