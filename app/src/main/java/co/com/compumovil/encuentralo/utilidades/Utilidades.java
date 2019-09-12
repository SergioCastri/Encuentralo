package co.com.compumovil.encuentralo.utilidades;

public class Utilidades {

    public static final String TABLA_ELEMENTO = "elemento";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String CAMPO_IMAGEN = "imagen";

    public static final String CREAR_TABLA_ELEMENTO = "CREATE TABLE " +
            ""+TABLA_ELEMENTO+" ("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CAMPO_NOMBRE+" TEXT, "+CAMPO_DESCRIPCION+" TEXT,"+CAMPO_CATEGORIA+" TEXT,"+CAMPO_IMAGEN+" BLOB)";

}
