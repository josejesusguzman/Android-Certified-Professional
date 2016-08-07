package com.example.josejesus.acpavanzadoscontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by josejesus on 6/30/2016.
 */
public class MiProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.josejesus.acpavanzadoscontentprovider.MiProvider"; //Tambien sera utilizado por la app de cliente del content
    static final String URL = "content://" + PROVIDER_NAME + "/cte";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int URI_CODE = 1; //Identificador derl URI
    static final UriMatcher uriMatcher; //Interpretar patrones en una Uri  QUE VA A HACER REFERENCIA Y OBTENER ELEMENTOS URI
    static HashMap<String, String> value;
    //Bloque estatico
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cte", URI_CODE); //CTE iniciales de la ruta del contenido QUE SE OBTENGA ALGUN ELEMENTO ESPECIFICO DE LA TABLA
        uriMatcher.addURI(PROVIDER_NAME, "cte/*", URI_CODE); //QUE SE OBTENGAN TODOS LOS ELEMENTOS
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "mybd";
    static final String TABLE_NAME = "names";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                queryBuilder.setProjectionMap(value); //Se necesita que se devuelvan todos los elementos de la tabla
                break;
            default:
                throw new IllegalArgumentException("Uri desconocida" + uri);
        }
        //Comprobar el tipo de ordenación
        if (sortOrder == null || sortOrder == "") {
            sortOrder = "name";
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri desconocida" + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri desconocida" + uri);
        }
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) { //La URI devielta va a hacer referencia a un nuevo elemento en la BD
        long rowId = db.insert(TABLE_NAME, "", values);
        if (rowId > 0 ) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Error al agregar el elemento " + uri);
    }

    //Identificar el tipo de datos que devuelve el content provider
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                return "vnd.android.cursor.dir/cte";
            default:
                throw  new IllegalArgumentException("URI no soportada " + uri);
        }
    }

}
