package co.com.compumovil.encuentralo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.com.compumovil.encuentralo.R;
import co.com.compumovil.encuentralo.entidades.Elemento;
import co.com.compumovil.encuentralo.entidades.ElementoImagen;

public class DetalleElemento extends AppCompatActivity {

    TextView campoId, campoNombre, campoDescripcion, campoCategoria;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_elemento);

        campoId = (TextView) findViewById(R.id.campoId);
        campoNombre = (TextView) findViewById(R.id.campoNombre);
        campoDescripcion = (TextView) findViewById(R.id.campoDescripcion);
        campoCategoria = (TextView) findViewById(R.id.campoCategoria);
        imagen = (ImageView) findViewById(R.id.imagenElemento);

        Bundle objetoEnviado=getIntent().getExtras();
        ElementoImagen elem=null;
        byte[] ima = null;

        if(objetoEnviado!=null){
            elem= (ElementoImagen) objetoEnviado.getSerializable("elemento");
            ima = (byte[]) objetoEnviado.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(ima, 0, ima.length);

            campoId.setText(elem.getId().toString());
            campoNombre.setText(elem.getNombre().toString());
            campoDescripcion.setText(elem.getDescripcion().toString());
            campoCategoria.setText(elem.getCategoria().toString());
            imagen.setImageBitmap(bmp);

        }
    }

    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Se acaba de enviar un correo electronico al publicador del objeto, en cualquier momento se pondra en contacto contigo",Toast.LENGTH_SHORT).show();

    }
}
