package co.com.compumovil.encuentralo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

import co.com.compumovil.encuentralo.utilidades.Utilidades;

public class RegistrarElemento extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView imagen;
    private Uri mUri;
    private EditText nombreElemento, decripcion;
    RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_elemento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        imagen = (ImageView) findViewById(R.id.imagenElemento);
        nombreElemento = (EditText) findViewById(R.id.nombreElemento);
        decripcion = (EditText) findViewById(R.id.decripcion);
        rg = (RadioGroup) findViewById(R.id.radioGrupo);


    }
    public void onClick(View view) {

        registrarElemento();
        //registrarElementoSQL();
    }

    private void registrarElementoSQL() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_elementos",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String insert ="INSERT INTO "+ Utilidades.TABLA_ELEMENTO +
                " ( " + Utilidades.CAMPO_ID + "," + Utilidades.CAMPO_NOMBRE + "," + Utilidades.CAMPO_DESCRIPCION + "," + Utilidades.CAMPO_CATEGORIA + ") VALUES ( 1 ,'"+
                nombreElemento.getText().toString() + "','" + decripcion.getText().toString() + "','Otros')";

        db.execSQL(insert);

        db.close();


    }

    private void registrarElemento() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_elementos",null,1);
        BitmapDrawable drawable = (BitmapDrawable) imagen.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        byte[] blob = baos.toByteArray();

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(Utilidades.CAMPO_NOMBRE, nombreElemento.getText().toString());
        values.put(Utilidades.CAMPO_DESCRIPCION, decripcion.getText().toString());
        values.put(Utilidades.CAMPO_CATEGORIA, ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString());
        values.put(Utilidades.CAMPO_IMAGEN, blob);

        Long idResultante=db.insert(Utilidades.TABLA_ELEMENTO,Utilidades.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            mUri = path;
            imagen.setImageURI(path);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrar_elemento, menu);
        return true;
    }

    public void subirImagen(View view) {
        cargarImagen();
    }

    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, getString(R.string.seleccioneApp)), 10);
    }



}
