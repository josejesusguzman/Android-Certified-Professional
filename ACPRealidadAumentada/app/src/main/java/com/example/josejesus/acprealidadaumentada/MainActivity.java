package com.example.josejesus.acprealidadaumentada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private ArchitectView architectView;
    public static final String	WIKITUDE_SDK_KEY = "FK/Y9Ibc8LKjfyDyexhjiNwkvGyNDSiRqT1f71kKP22bdXe7xFHfI694Eg0l8eAzhXwmA1jwa+wmrBo0QEXqEjTwOYt+9EYewfVFtaq1afiWs+NbdR8xTB7t2aKG/rjSnlTFjvcogCLYhR5h3SAY7J/JlWSRNK4sAYtA5ebI/fBTYWx0ZWRfXzs3EuoF5hjzl12EcK/Z9vSu6FtHjBTcDFmQpyyi/CXgXTKVkZyavtJ6hHSYAB74gnYP/3oYz4jYQpo/zE04DJzErPUc9LbgY0Aa6NCrbC5iCunvSVjuhhxcE3RxgJxCKbbfdbSngLlaOec/N0duhSPz9D4eJugMn55ekBiGG/gWd1YDds/TJptT7IQsVSo6uQ+bQM6LHYsnj1EiV0l87GtYkfXxCfZJLW07TBh4blxBWGdSmnYcUPx8uA295Gj/B7KsQeT7sAgDc/OaRH1omfjLJxMCoeHQA7TZg13x360sVFgZVyoVF0Ta0WT1Df+rFj/FRnBAKPLzCdRdZRbRj1+lHAqE2mxtHJp8cic1JZCeYwOLiHL57C84I1SFBcZrFE8OTvIFLGQzluHgGi449jIzZ/zyAikXYV+RKgHYCUFJBT+bxd7RAnlMVg4w2iAit1x4lVRiTzpqdPOkl/cS7Bz2NbKvw/EctjhrUvdUOBjl5rWJM0wAGfE=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        architectView = (ArchitectView)findViewById(R.id.architectView);
        final StartupConfiguration configuration = new StartupConfiguration(WIKITUDE_SDK_KEY);

        try{
            architectView.onCreate(configuration);
        }catch (RuntimeException e){
            e.printStackTrace();
            Toast.makeText(this, "No se ha podido crear la vista",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if ( this.architectView != null ) {
            this.architectView.onPostCreate();
        }

        try {
            if (this.architectView!=null){
                this.architectView.load("videoAR/index.html");
            }
            else{
                Toast.makeText( MainActivity.this, "Architect View nulo", Toast.LENGTH_LONG ).show();
            }
        } catch (IOException e) {
            Log.e("AR", e.toString());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if ( this.architectView != null ) {
            this.architectView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( this.architectView != null ) {
            this.architectView.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( this.architectView != null ) {
            this.architectView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if ( this.architectView != null ) {
            this.architectView.onLowMemory();
        }
    }
}
