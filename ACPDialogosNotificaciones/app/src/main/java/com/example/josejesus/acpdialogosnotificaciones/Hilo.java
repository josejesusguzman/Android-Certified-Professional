package com.example.josejesus.acpdialogosnotificaciones;

import android.app.ProgressDialog;

/**
 * Created by josejesus on 6/16/2016.
 */
public class Hilo  extends Thread {

    private ProgressDialog progressDialog;

    public Hilo (ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void run() {
        super.run();
        while (progressDialog.getProgress() < progressDialog.getMax()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            progressDialog.incrementProgressBy(10);
        }
        progressDialog.dismiss();
    }
}
