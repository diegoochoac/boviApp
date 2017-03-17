package appcom.bovi.boviapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import appcom.bovi.boviapp.dataBase.FirebaseReferences;
import appcom.bovi.boviapp.fragmentos.FragmentoInicio;
import appcom.bovi.boviapp.fragmentos.FragmentoListado;
import appcom.bovi.boviapp.fragmentos.FragmentoRastreo;
import appcom.bovi.boviapp.fragmentos.FragmentoRegistro;
import appcom.bovi.boviapp.fragmentos.FragmentoTab;
import appcom.bovi.boviapp.login.LoginActivity;
import appcom.bovi.boviapp.fragmentos.FragmentoNotificacion;
import appcom.bovi.boviapp.notifications.PushNotificationsPresenter;
import appcom.bovi.boviapp.objetos.Registro;
import appcom.bovi.boviapp.utils.OnFragmentInteractionListener;


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnMapReadyCallback {


    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentoNotificacion mNotificationsFragment;
    private PushNotificationsPresenter mNotificationsPresenter;

    private FragmentoRastreo mFragmentoRastreo;

    private static final int TOMAR_FOTO = 1;
    private static final int LOCATION_REQUEST_CODE = 2;

    private GoogleMap mMap;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String drawerTitle;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_notificaciones);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
            // Seleccionar el fragmento de INICIO
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        // ¿Existe un usuario logueado?
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        mNotificationsFragment = (FragmentoNotificacion) getSupportFragmentManager()
                .findFragmentById(R.id.main_container);

        /*guardarRegistro(FirebaseAuth.getInstance().getCurrentUser().getUid()
                ,"vacaprueba2",6,"diegoOchoa");
        leerRegistro(FirebaseAuth.getInstance().getCurrentUser().getUid());*/

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    //Funciona cuando se selecciona una opcion del menu desplegable de la izquierda
    //Sirve para abrir los fragment
    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_inicio:
                fragmentoGenerico = new FragmentoTab();
                break;

            case R.id.nav_registro:
                fragmentoGenerico = new FragmentoRegistro();
                break;

            case R.id.nav_notificaciones:
                if (mNotificationsFragment == null) {
                    mNotificationsFragment = FragmentoNotificacion.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, mNotificationsFragment)
                            .addToBackStack(null)
                            .commit();
                }
                mNotificationsPresenter = new PushNotificationsPresenter(
                        mNotificationsFragment, FirebaseMessaging.getInstance());
                break;

            case R.id.nav_rastreo:
                if (mFragmentoRastreo == null) {
                    mFragmentoRastreo = FragmentoRastreo.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_container, mFragmentoRastreo)
                            .addToBackStack(null)
                            .commit();
                    mFragmentoRastreo.getMapAsync(this);
                }
                break;

            case R.id.nav_cerrarsesion:
                AlertDialog.Builder dialogoCerrarSesion = new AlertDialog.Builder(this);
                dialogoCerrarSesion.setTitle("Advertencia");
                dialogoCerrarSesion.setMessage("Desea cerrar sesión");
                dialogoCerrarSesion.setCancelable(false);
                dialogoCerrarSesion.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogoCerrarSesion, int id) {
                        aceptar();
                    }
                });
                dialogoCerrarSesion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogoCerrarSesion, int id) {
                        cancelar();
                    }
                });
                dialogoCerrarSesion.show();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragmentoGenerico)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void aceptar() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        Toast t = Toast.makeText(this, "Se cerro sesión.", Toast.LENGTH_SHORT);
        t.show();
        finish();
    }

    public void cancelar() {
        finish();
    }

    @Override
    protected void onPause() {
        Log.i("MainActivity","OnPause");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.i("MainActivity","OnStart");
        super.onStart();
    }

    @Override
    public void onFragmentIteration(Uri uri) {

        Log.i("MAIN ", "URI: " + uri.toString());
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();

        String[] spl = uri.toString().split(":");
        switch (spl[0]) {

            //<editor-fold desc="Fragmento Inicio">
            case FragmentoInicio.SET_INICIO:
                if (spl[1].equals("0")) {
                    Log.i("main", "ENTRO REGISTRO" + spl[1]);
                    fragmentoGenerico = new FragmentoRegistro();
                    /*args.putString("Contratista",  sharedpreferences.getString(contratista,""));
                    args.putString("Trabajador", sharedpreferences.getString(usuario,""));
                    args.putString("Maquina",  sharedpreferences.getString(maquina,""));
                    args.putString("Hacienda",  sharedpreferences.getString(hacienda,""));
                    args.putString("Suerte",  sharedpreferences.getString(suerte,""));
                    fragmentoGenerico.setArguments(args);*/
                } else if (spl[1].equals("1")) {
                    Log.i("main", "ENTRO LISTADO" + spl[1]);
                    fragmentoGenerico = new FragmentoListado();
                } else if (spl[1].equals("2")) {
                    Log.i("main", "ENTRO NOTIFICACIONES" + spl[1]);
                    if (mNotificationsFragment == null) {
                        mNotificationsFragment = FragmentoNotificacion.newInstance();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_container, mNotificationsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    mNotificationsPresenter = new PushNotificationsPresenter(
                            mNotificationsFragment, FirebaseMessaging.getInstance());
                } else if (spl[1].equals("3")) {
                    Log.i("main", "ENTRO RASTREO" + spl[1]);
                    fragmentoGenerico = new FragmentoRastreo();
                }
                break;
            //</editor-fold>

            //<editor-fold desc="Fragmento Registro">
            case FragmentoRegistro.SET_REGISTRO:
                if (spl[1].equals("FOTO")) {
                    Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camaraIntent, TOMAR_FOTO);
                } else if (spl[1].equals("RAZA")) {
                    //TODO:AGREGAR UN ALERT CON LA LISTA DE ESPECIES
                } else if (spl[1].equals("AGREGAR")) {
                    //TODO:AGREGAR EN BASE DE DATOS
                }
                break;
            //</editor-fold>
        }

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.main_container, fragmentoGenerico)
                    .addToBackStack(null)
                    .commit();
        }
    }


    private void guardarRegistro(String userId, String nombre, int edad, String dueño) {
        Registro registro = new Registro(nombre, edad, dueño);
        myRef.child("Registro").child(userId).setValue(registro);
    }

    private void leerRegistro(final String userId) {
        myRef.child("Registro").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Registro registro = dataSnapshot.getValue(Registro.class);
                        Log.i("Registro:", userId + "-" + registro.nombre);
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TOMAR_FOTO && data != null) {
            Bitmap imagen = (Bitmap) data.getExtras().get("data");
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("PRESIONO:","ATRAS");
       if (getFragmentManager().getBackStackEntryCount() > 0) {
           getFragmentManager().popBackStack();
        } else{
           super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


}
