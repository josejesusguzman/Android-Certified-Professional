package com.example.josejesus.acppersistenciaalmacenamiento.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josejesus on 6/21/2016.
 */
public class PersonaDataSource { //Metodos de operaciones en la BD

    private SQLiteDatabase database;
    private HelperPersona helperPersona;

    private String[] columnasTablaPersona = {
            HelperPersona.TablaPersona.COLUMNA_ID,
            HelperPersona.TablaPersona.COLUMNA_NOMBRE,
            HelperPersona.TablaPersona.COLUMNA_APELLIDO,
            HelperPersona.TablaPersona.COLUMNA_EDAD
    };

    public PersonaDataSource(Context context) {
        helperPersona = HelperPersona.getInstance(context);
    }

    public void open() { //Abrir BD
        database = helperPersona.getWritableDatabase();
    }

    public void close() {
        helperPersona.close();
    }

    public void insertarRegistro(String nombre, String apellido, int edad) {
        ContentValues values = new ContentValues(); //Concatenar los valores que estan entrando
        values.put(HelperPersona.TablaPersona.COLUMNA_NOMBRE, nombre);
        values.put(HelperPersona.TablaPersona.COLUMNA_APELLIDO, apellido);
        values.put(HelperPersona.TablaPersona.COLUMNA_EDAD, Integer.toString(edad));
        database.insert(HelperPersona.TablaPersona.TABLA, null, values);
    }

    public List<Persona> obtenerReguistros(){
        List<Persona> personaList = new ArrayList<Persona>();
        Cursor cursor = database.query(HelperPersona.TablaPersona.TABLA, columnasTablaPersona, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Persona nuevaPosicion = parseCursorPersona(cursor);
            personaList.add(nuevaPosicion);
            cursor.moveToNext();
        }
        cursor.close();
        return personaList;
    }

    public void borrarRegistro(Persona posicion) {
        database.delete(HelperPersona.TablaPersona.TABLA,
                HelperPersona.TablaPersona.COLUMNA_EDAD + " = " + posicion.getEdad(),
                null);

    }

    private Persona parseCursorPersona(Cursor cursor) {
        try {
            Persona persona = new Persona();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setApellido(cursor.getString(2));
            persona.setEdad(cursor.getInt(3));
            return persona;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

}
