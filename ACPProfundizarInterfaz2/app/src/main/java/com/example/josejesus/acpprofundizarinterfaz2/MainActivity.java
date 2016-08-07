package com.example.josejesus.acpprofundizarinterfaz2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class DetalleActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Si se esta en modo lasdscape
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                return;
            }

            if (savedInstanceState == null) {
                DetalleFragmento detalleFragmento = new DetalleFragmento();
                detalleFragmento.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, detalleFragmento).commit();
            }

        }
    }

    public static class ListaElementosFragmento extends ListFragment {
        boolean dualPanel;
        int checkPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, SO.SISTEMAS));

            View detalleFrame = getActivity().findViewById(R.id.fragDetalle);
            dualPanel = detalleFrame != null && detalleFrame.getVisibility() == View.VISIBLE; //DEtalle frame ya haya sido creado y sea visible

            if (savedInstanceState != null) {
                checkPosition = savedInstanceState.getInt("choice", 0);
            }

            if (dualPanel) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //Solo se puede seleccionar una celda a la vez
                showDetail(checkPosition);
            } else {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                getListView().setItemChecked(checkPosition, true);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putInt("choice", checkPosition); //Se guarda con en una pila valores que se podran acceder en los metodos create
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showDetail(position);
        }

        void showDetail(int index) { //Visualizacion de lo seleciconado

            checkPosition = index;

            if (dualPanel) {
                getListView().setItemChecked(index, true);
                DetalleFragmento detalleFragmento = (DetalleFragmento) getFragmentManager().findFragmentById(R.id.fragDetalle);

                if (detalleFragmento == null || detalleFragmento.getShownIndex() != index) { //Si detalle fue creado
                    detalleFragmento = DetalleFragmento.newInstance(index);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragDetalle, detalleFragmento);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }

            } else {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetalleActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }

    }

    public static class DetalleFragmento extends Fragment {

        public static DetalleFragmento newInstance (int index) {
            DetalleFragmento detalleFragmento = new DetalleFragmento();
            Bundle args = new Bundle();
            args.putInt("index", index);
            detalleFragmento.setArguments(args);

            return detalleFragmento;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ScrollView scrollView = new ScrollView(getActivity());
            TextView textView = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                    getActivity().getResources().getDisplayMetrics());

            textView.setPadding(padding, padding, padding, padding);
            scrollView.addView(textView);
            textView.setText(SO.DESCRIPCION[getShownIndex()]);
            return scrollView; //porque el scroll coniene al text
        }
    }

}
