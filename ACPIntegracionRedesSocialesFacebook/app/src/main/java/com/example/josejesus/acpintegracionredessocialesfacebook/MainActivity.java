package com.example.josejesus.acpintegracionredessocialesfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Vistas en el layout
    private LoginButton loginBoton;
    private ProfilePictureView imagenPerfil;
    private TextView nombreUsuarioFB;
    private Button publicarButton;


    //Clase ShareDialog almacena el díalogo de publicación.
    private ShareDialog dialogoPublicacion;

    //listener del evento que se lanzará cada vez que el perfil de usuario de FB cargado en la App sufra algún cambio de forma erronea o forma correcta.
    private ProfileTracker rastreadorEventosPerfil;
    //listener de eventos que estará pendiente de lo que pase cuando se ejecute el login de usuario de FB.erroneo,cancelado o exitoso.
    private CallbackManager eventosFacebookLogin;
    //listener de eventos que estará pendiente de lo que pase cuando se presente el dialog de publicación de FB
    private FacebookCallback<Sharer.Result> eventosFacebookPublicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Iniciar Facebook SDK, con el contexto actual, antes de usuarlo...

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //activación de listeners de eventos de Facebook SDK para Login, Carga de Perfil y Publicación.
        activaEventosFacebookLogin();
        activaEventosFacebookPerfilCargado();
        activaEventosFacebookPublicacion();


        //Reconoce las Views de la interfaz...
        loginBoton = (LoginButton) findViewById(R.id.main_face_loginButton);
        imagenPerfil = (ProfilePictureView) findViewById(R.id.main_face_profilePicture);
        nombreUsuarioFB = (TextView) findViewById(R.id.main_txt_saludo);
        publicarButton = (Button) findViewById(R.id.main_btn_postStatus);

        //Establece los permisos que se solicitarán al usuario cuando se autorize esta App
        //Articulo sobre permisos:
        //https://developers.facebook.com/docs/facebook-login/permissions
        loginBoton.setReadPermissions(Arrays.asList("public_profile", "email"));

        publicarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });
    }

    private void publicar() {
        Toast.makeText(MainActivity.this, "Presentando dialogo de publicación...", Toast.LENGTH_SHORT).show();

        //Presenta un tipo de publicación simple
        // otros ejemplos de tipos de publicaciones
        // https://developers.facebook.com/docs/sharing/android
        ShareLinkContent publicacionSimple = new ShareLinkContent.Builder().build();

        //Se instancia un dialogo de Publicación (ShareDialog) con el contexto actual...
        dialogoPublicacion = new ShareDialog(this);
        //Se registran en el díalogo de publicación los listeners de eventos login y de publicación con los que
        // Facebook SDK se comunicará cuando se necesite login o cuando ocurra algún evento en la publicación.
        dialogoPublicacion.registerCallback(eventosFacebookLogin, eventosFacebookPublicacion);
        //Se presenta el díalogo de publicación.
        dialogoPublicacion.show(publicacionSimple);

    }

    // Metodo lanzado cada vez que el se reconoce un evento de FB relacionado a la carga del perfil en la App.
    // (rastreadorEventosPerfil -> onCurrentProfileChanged )
    protected void actualizaElementosInterfaz() {
        // Sobre AccessTokens...autorización para que se pueda publicar como una cp fb da derechos momentaneo.
        // https://developers.facebook.com/docs/facebook-login/access-tokens
        boolean tokenDeAccesoValido = AccessToken.getCurrentAccessToken() != null;
        publicarButton.setEnabled(tokenDeAccesoValido);

        //Se obtiene el perfil actual del usuario logueado
        Profile perfilActual = Profile.getCurrentProfile();
        if (tokenDeAccesoValido && perfilActual != null) {
            //Se establecen el id del usuario y el nombre para mostrar del perfil que se cargo.
            imagenPerfil.setProfileId(perfilActual.getId());
            nombreUsuarioFB.setText(perfilActual.getFirstName());
        } else {
            //Se limpian los datos si es que no hay un token valido o no se cargo el perfil
            imagenPerfil.setProfileId(null);
            nombreUsuarioFB.setText(null);
        }
    }


    //==================================================================================================

    //Metodo lanzado cada vez que Facebook SDK termina de presentar alguna activity y devuelve algún resultado a esta App.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//autorizo a tu app. quien va hacer la app que responda cuando se terminó el login
        super.onActivityResult(requestCode, resultCode, data);
        eventosFacebookLogin.onActivityResult(requestCode, resultCode, data);//regresa acces token
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Cuando se destruye la Activity, se detiene el rastreo de eventos de carga de perfil en la App.
        rastreadorEventosPerfil.stopTracking();
    }


    //==================================================================================================

    //Se activa el listener del evento que se lanzara cada vez que el perfil de usuario
    // de FB cargado en la App ( ProfileTacker ) sufra algun cambio... ( onCurrentProfileChanged )
    private void activaEventosFacebookPerfilCargado() {
        rastreadorEventosPerfil = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Toast.makeText(getApplicationContext(), "Cambio en el perfil detectado... \nActualizando elementos de interfaz...", Toast.LENGTH_SHORT).show();
                actualizaElementosInterfaz();
            }
        };
    }

    //Se activa el listener de eventos que estara pendiente de lo que pase cuando se ejecute el login de usuario de FB
    private void activaEventosFacebookLogin() {
        eventosFacebookLogin = CallbackManager.Factory.create();

        //la clase LoginManager de Facebook SDK registra la variable eventosFacebookLogin para responder  a los eventos de Login de FB
        LoginManager.getInstance().registerCallback(eventosFacebookLogin, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Login con error = "+ exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Se activa el listener de eventos que estara pendiente de lo que pase cuando se presente el dialog de publicación de FB
    private void activaEventosFacebookPublicacion(){
        eventosFacebookPublicacion = new FacebookCallback<Sharer.Result>() {
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Dialogo de publicacion cancelado", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Dialogo de publicacion con Error = " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "Dialogo de publicacion exitoso", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
