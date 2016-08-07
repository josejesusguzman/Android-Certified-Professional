package com.example.josejesus.multitaskingloaders;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by josejesus on 6/25/2016.
 */
public class CursorLoaderListFragment extends ListFragment
        implements OnQueryTextListener, LoaderCallbacks<Cursor> {

    static final String[] COLUMNAS_TABLA_CONTACTO = new String[] {
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Contacts.CONTACT_STATUS
    };

    SimpleCursorAdapter cursorAdapter;
    String cadenaFiltro;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No existen contactos en el dispositivo");
        setHasOptionsMenu(true); //si habra un menu dentro de la app

        cursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null, new String[] {Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);

        setListAdapter(cursorAdapter);
        setListShown(false); //Definir si la lista tendra un indicador de animación cuando esta cargando datos dentro de la lista
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.add("Buscar");
        menuItem.setIcon(android.R.drawable.ic_menu_search);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = new SearchView(getActivity());
        searchView.setOnQueryTextListener(this);

        menuItem.setActionView(searchView);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        if (cadenaFiltro != null) {
            uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(cadenaFiltro)); //Indica que informacion se quiere obtener
        } else {
            uri = Contacts.CONTENT_URI;
        }

        String select = "((" + Contacts.DISPLAY_NAME + "NOTNULL) AND (" + Contacts.HAS_PHONE_NUMBER + "=1 ))"; //Consulta. El contacto debe tener un nombre y un telefono
        return new CursorLoader(getActivity(),
                uri,
                COLUMNAS_TABLA_CONTACTO,
                select, null,
                Contacts.DISPLAY_NAME + "COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);//No habra información para mostrar
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {//Cuando el search cambie entonces este metodo escuchara la informacion
        cadenaFiltro = !TextUtils.isEmpty(newText) ? newText : null;
        getLoaderManager().restartLoader(0, null, this);
        return false;
    }
}
