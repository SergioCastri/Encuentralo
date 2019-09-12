package co.com.compumovil.encuentralo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_elementos",null,1);
  }

  public void registrarElemento(View view){
    Intent i = new Intent(this, RegistrarElemento.class);
    startActivity(i);
  }

  public void listadoElementos(View view){
    Intent i = new Intent(this, ListadoElementos.class);
    startActivity(i);
  }
}
