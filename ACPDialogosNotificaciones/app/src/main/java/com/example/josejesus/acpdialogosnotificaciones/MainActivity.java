package com.example.josejesus.acpdialogosnotificaciones;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        String[] opciones = getResources().getStringArray(R.array.opciones_lista);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        MiDialogoAlerta miDialogoAlerta = new MiDialogoAlerta();
                        miDialogoAlerta.show(getSupportFragmentManager(), "miDialogAlerta");
                        break;
                    case 1:
                        MiDialogoOpciones miDialogoOpciones = new MiDialogoOpciones();
                        miDialogoOpciones.show(getSupportFragmentManager(), "miDialogOpciones");
                        break;
                    case 2:
                        MiDialogoPersonalizado miDialogoPersonalizado = new MiDialogoPersonalizado();
                        miDialogoPersonalizado.show(getSupportFragmentManager(), "miDialogoPersonalizado");
                        break;
                    case 3:
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setMessage("Espere un momento por favor");
                        progressDialog.setTitle("Connie <3 <3 <3 <3 <3");
                        progressDialog.setProgress(0);
                        progressDialog.setMax(100);
                        progressDialog.show();
                        Hilo hilo = new Hilo(progressDialog);
                        hilo.start();
                        break;
                    case 4:
                        mostratNotificacion();
                        break;
                    case 5:
                        Toast toast = Toast.makeText(MainActivity.this, "Princesa Flama <3 <3 <3", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    case 6:
                        LayoutInflater inflater = getLayoutInflater();
                        View viewToast = inflater.inflate(R.layout.toast_layout, null);
                        Toast toast1 = new  Toast(getApplicationContext());
                        toast1.setDuration(Toast.LENGTH_LONG);
                        toast1.setView(viewToast);
                        toast1.show();
                        break;
                }
            }
        });
    }

    private void mostratNotificacion() {
        int notificationId = 1;

        Uri notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Sonido

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(notifySound) //Sonido
                .setLights(Color.GREEN, 3000, 3000) //Led
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setContentTitle("Star Butterfly")
                .setContentText("<3 <3 <3 <3");

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, notificationCompat.build());
    }

    public static class MiDialogoAlerta extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Mari <3 <3 <3 <3 <3 <3 <3");
            return builder.create();
        }
    }

    public static class MiDialogoOpciones extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Serena <3 <3 <3 <3 <3 <3");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setSingleChoiceItems(getResources().getStringArray(R.array.opciones_lista), 0,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("Opción seleccionada:" + which);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    public static class MiDialogoPersonalizado extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialogo_layout, null);
            builder.setView(view);
            return builder.create();
        }
    }

}
