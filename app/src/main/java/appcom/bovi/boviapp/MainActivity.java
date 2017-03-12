package appcom.bovi.boviapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import appcom.bovi.boviapp.fragmentos.FragmentoInicio;
import appcom.bovi.boviapp.fragmentos.FragmentoRastreo;
import appcom.bovi.boviapp.fragmentos.FragmentoRegistro;
import appcom.bovi.boviapp.login.LoginActivity;
import appcom.bovi.boviapp.fragmentos.FragmentoNotificacion;
import appcom.bovi.boviapp.notifications.PushNotificationsPresenter;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentoNotificacion mNotificationsFragment;
    private PushNotificationsPresenter mNotificationsPresenter;


    private DrawerLayout drawerLayout;
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(); // Setear Toolbar como action bar
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_notificaciones);
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
                        .findFragmentById(R.id.notifications_container);

    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
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
                fragmentoGenerico = new FragmentoInicio();
                break;

            case R.id.nav_registro:
                fragmentoGenerico = new FragmentoRegistro();
                break;

            case R.id.nav_notificaciones:
                if (mNotificationsFragment == null) {
                    mNotificationsFragment = FragmentoNotificacion.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.notifications_container, mNotificationsFragment)
                            .commit();
                }
                mNotificationsPresenter = new PushNotificationsPresenter(
                        mNotificationsFragment, FirebaseMessaging.getInstance());
                break;

            case R.id.nav_rastreo:
                fragmentoGenerico = new FragmentoRastreo();
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
                    .replace(R.id.notifications_container, fragmentoGenerico)
                    .commit();
        }
        // Setear título actual
        //setTitle(itemDrawer.getTitle());
    }


    public void aceptar() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast t=Toast.makeText(this,"Se cerro sesion.", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {
        finish();
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


}
