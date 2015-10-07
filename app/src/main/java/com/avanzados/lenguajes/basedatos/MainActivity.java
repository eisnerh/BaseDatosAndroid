package com.avanzados.lenguajes.basedatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnNewActivity;
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextEdad;
    private Button btnInsertar;
    private Button btnConsulta;
    private TextView textViewConsulta;
    private Button btnBorrarTodo, btnEditar, btnBorrarElemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        btnNewActivity = (Button)findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewActivity.class);
                startActivity(i);
            }
        });
    }

    private void inicializar(){

        editTextNombre = (EditText)findViewById(R.id.editTextNombre);
        editTextApellidos = (EditText)findViewById(R.id.editTextApellidos);
        editTextEdad = (EditText)findViewById(R.id.editTextEdad);

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnConsulta = (Button)findViewById(R.id.btnConsulta);
        btnBorrarTodo = (Button) findViewById(R.id.btnBorrarTodo);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnBorrarElemento = (Button)findViewById(R.id.btnBorrarElemento);

        textViewConsulta = (TextView)findViewById(R.id.textViewConsulta);

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
                Toast.makeText(MainActivity.this, "Insertando", Toast.LENGTH_SHORT).show();
            }
        });
        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta();
                Toast.makeText(MainActivity.this, "Consultando", Toast.LENGTH_LONG).show();
            }
        });
        btnBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarTodo();
                Toast.makeText(MainActivity.this, "Borrado", Toast.LENGTH_LONG).show();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editar();
            }
        });
        btnBorrarElemento.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                borrarelemento();
            }
        });
    }

    private void insertar(){
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString());

        BaseDatos baseDatos = new BaseDatos(this, "Usuario", null, 1);
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellidos", apellidos);
        registro.put("edad", edad);

        db.insert("persona", null, registro);
        db.close();

        editTextNombre.setText("");
        editTextApellidos.setText("");
        editTextEdad.setText("");
        textViewConsulta.setText("");
    }
    
    private void consulta(){
        BaseDatos dataBaseHelper = new BaseDatos(this, "Usuario", null, 1);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from persona", null); // El cursor mantiene todos los registros encontrados.
        String imprimir = "";
        if(cursor.moveToFirst()){ //cursor.moveToFirst() si el cursor est� vac�o devuelve false de lo contrario devuelve true y coloca el cursor al inicio de todos los registros encontrados.
            while(cursor.isAfterLast() == false){ //cursor.isAfterLast() pregunta si el cursor ya lleg� al final de los registros
                imprimir+= cursor.getString(0);
                imprimir+= ", ";
                imprimir+= cursor.getString(1);
                imprimir+= ", ";
                imprimir+= cursor.getInt(2);
                imprimir+= "\n";

                cursor.moveToNext(); //Mueve el cursor al siguiente registro
            }

            textViewConsulta.setText(imprimir);
        }else{
            textViewConsulta.setText("Sin datos");
        }
        db.close();
    }

    private void borrarTodo(){
        BaseDatos dataBaseHelper = new BaseDatos(this, "Usuario", null, 1);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        db.delete("persona",null,null);

        db.close();

        textViewConsulta.setText("");
    }

    private void editar(){

        BaseDatos dataBaseHelper = new BaseDatos(this, "Usuario", null, 1);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString());

        ContentValues registro = new ContentValues();

        registro.put("apellidos", apellidos);

        registro.put("edad", edad);

        int cantidad = db.update("persona",registro,"nombre = ?", new String[]{nombre});

        if (cantidad > 0){
            Toast.makeText(this,"Registro Actualizado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Registro No Encontrado",Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    private void borrarelemento(){
        BaseDatos dataBaseHelper = new BaseDatos(this, "Usuario", null, 1);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String nombre = editTextNombre.getText().toString();

        int cantidad = db.delete("persona","nombre = ?",new String[]{"nombre"});

        if (cantidad > 0){
            Toast.makeText(this,"Registro Eliminado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Registro No Encontrado",Toast.LENGTH_SHORT).show();
        }
        db.close();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_add){
            Toast.makeText(this,"Item Agregar",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}