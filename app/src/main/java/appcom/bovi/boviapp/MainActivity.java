package appcom.bovi.boviapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://boviapp.appspot.com");

    Bitmap imagen;
    Uri fileUri;

    final List<Registro> registros = new ArrayList<>();  //Se utiliza para traer los archivos de la DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "Registro añadido:" + dataSnapshot.getValue().toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /*
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
        Bundle args = new Bundle();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_inicio:
                fragmentoGenerico = new FragmentoTab();
                break;

            case R.id.nav_registro:
                fragmentoGenerico = new FragmentoRegistro();
                break;

            case R.id.nav_listado:
                leerRegistro();
                if(registros.size()>1) {
                    fragmentoGenerico = new FragmentoListado();
                    args.putSerializable("DATA", (Serializable) registros);
                    fragmentoGenerico.setArguments(args);
                }
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
                    leerRegistro();
                    if(registros.size()>1) {
                        fragmentoGenerico = new FragmentoListado();
                        args.putSerializable("DATA", (Serializable) registros);
                        fragmentoGenerico.setArguments(args);
                    }

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
                    String Nombre ="", Raza ="", Ubicacion="";
                    int Edad = 0, Peso =0;
                    Nombre = spl[2];
                    Raza = spl[3];
                    Edad = Integer.parseInt(spl[4]);
                    Peso = Integer.parseInt(spl[5]);
                    Ubicacion = spl[6];
                    guardarRegistro(FirebaseAuth.getInstance().getCurrentUser().getUid()
                            ,Nombre,Edad,Peso,Raza,"diegoOchoa",Ubicacion);

                    if (fileUri != null){
                        gurdarImagen(fileUri);
                    }
                    fragmentoGenerico = new FragmentoInicio();
                    Toast.makeText(MainActivity.this, "Registro Exitoso",
                            Toast.LENGTH_SHORT).show();
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

    private void guardarRegistro(String userId, String nombre, int edad, int peso,
                                 String raza, String dueño, String ubicacion) {
        Registro registro = new Registro(nombre,edad,peso,raza,dueño,ubicacion);
        myRef.push().setValue(registro);
    }

    private void actualizarRegistro(String userId, String nombre, int edad, int peso,
                                 String raza, String dueño, String ubicacion) {
        Registro registro = new Registro(nombre,edad,peso,raza,dueño,ubicacion);
        myRef.child("Registro").setValue(registro);
    }


    private void gurdarImagen(Uri file){

        final StorageReference photoReference = storageReference.child("fotos").child(file.getLastPathSegment());

        photoReference.putFile(fileUri).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "uploadFromUri:onFailure", e);
                //downloadUrl = null;
                Toast.makeText(MainActivity.this, "Error: upload failed",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "uploadFromUri:onSuccess");
                // Aquí obtenemos la url de la foto que cargamos
                //downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                //savePictureUrlAsANote(downloadUrl);
            }
        });

    }

    private void leerRegistro() {

        myRef.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                registros.removeAll(registros);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Registro registro = snapshot.getValue(Registro.class);
                    registros.add(registro);
                    Log.i("LEER REGI","numero:"+registros.size()+"-"+registro.nombre);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("MAIN REGISTRO",""+registros.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TOMAR_FOTO && data != null) {
            imagen = (Bitmap) data.getExtras().get("data");
            fileUri = getImageUri(this,imagen);
            Log.i("FOTO",fileUri.getLastPathSegment());
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
