package co.com.compumovil.encuentralo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import co.com.compumovil.encuentralo.entidades.Elemento;
import co.com.compumovil.encuentralo.entidades.ElementoImagen;
import co.com.compumovil.encuentralo.utilidades.Utilidades;

public class ListadoElementos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ConexionSQLiteHelper conn;
    ListView listViewElementos;
    ArrayList<String> listaInformacion;
    ArrayList<Elemento> listaElementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_elementos);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_elementos", null, 1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_elementos",null,1);

        listViewElementos = (ListView) findViewById(R.id.listViewElementos);
        consultarListaElementos();


        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewElementos.setAdapter(adaptador);

        listViewElementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion="id: "+listaElementos.get(pos).getId()+"\n";
                informacion+="Nombre: "+listaElementos.get(pos).getNombre()+"\n";
                informacion+="Descripcion: "+listaElementos.get(pos).getDescripcion()+"\n";
                informacion+="Categoria: "+listaElementos.get(pos).getCategoria()+"\n";

                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

                Elemento ele=listaElementos.get(pos);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ele.getImagen().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                ElementoImagen ei =new  ElementoImagen(ele.getId(), ele.getNombre(), ele.getDescripcion(), ele.getCategoria());

                Intent intent=new Intent(ListadoElementos.this, DetalleElemento.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("elemento", ei);
                bundle.putByteArray("image",byteArray);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



    }

    private void consultarListaElementos() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Elemento elemento=null;
        listaElementos=new ArrayList<Elemento>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_ELEMENTO,null);
        Bitmap bitmap = null;

        while (cursor.moveToNext()){
            elemento=new Elemento();
            elemento.setId(cursor.getInt(0));
            elemento.setNombre(cursor.getString(1));
            elemento.setDescripcion(cursor.getString(2));
            elemento.setCategoria(cursor.getString(3));
            byte[] blob = cursor.getBlob(4);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
            elemento.setImagen(bitmap);

            listaElementos.add(elemento);
        }
        obtenerLista();
    }
    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i=0; i<listaElementos.size();i++){
            listaInformacion.add(listaElementos.get(i).getId()+" - "
                    +listaElementos.get(i).getNombre());
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
        getMenuInflater().inflate(R.menu.listado_elementos, menu);
        return true;
    }



}
