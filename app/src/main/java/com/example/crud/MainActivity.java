package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crud.Adapter.Adapter;
import com.example.crud.model.Comida;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText nombre, precio, id,acomp;
    private CheckBox disp ;
    private ListView lista;
    private Comida comidaselect;
    private Spinner Tipo;
    private String[] Opciones = {"Seleccione","Desayuno","Sopa","Segundo","Asado","Otros"};
    private List<Comida> listComida = new ArrayList<Comida>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.lsProductos);
        View header = getLayoutInflater().inflate(R.layout.listheader, null);
        lista.addHeaderView(header);
        nombre = findViewById(R.id.txtNombre);
        precio = findViewById(R.id.txtPrecio);
        acomp = findViewById(R.id.txtAcompaniado);
        disp = findViewById(R.id.swDisponible);
        Tipo = (Spinner) findViewById(R.id.spinnerTp);
        Tipo.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,Opciones));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comidaselect = (Comida) parent.getItemAtPosition(position);
                nombre.setText(comidaselect.getNombre());
                precio.setText(comidaselect.getPrecio());
                acomp.setText(comidaselect.getAcompaniado());
                disp.setChecked(comidaselect.getDisponible());
                Tipo.setSelection(ValorSeleccionad(comidaselect.getTipo()));
            }
        });
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        litarDatos();
    }
    public int ValorSeleccionad (String texto)
    {
        for(int i =0;i<Opciones.length;i++ )
        {
            if(Opciones[i].equals(texto))
                return i;

        }
        return 0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.getText().toString().equals("") || precio.getText().toString().equals("") )
                {
                    validacion();
                }
                else {
                    Comida c = new Comida();
                    c.setNombre(nombre.getText().toString());
                    c.setAcompaniado(acomp.getText().toString());
                    c.setNombre(precio.getText().toString());
                    c.setUdi(UUID.randomUUID().toString());
                    c.setDisponible(disp.isChecked());
                    c.setTipo(Tipo.getSelectedItem().toString());
                    databaseReference.child("Comida").child(c.getUdi()).setValue(c);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();
                    Limpiar();
                    break;
                }
            }
            case R.id.icon_save:{
                if(nombre.getText().toString().equals("") || precio.getText().toString().equals("") )
                {
                    validacion();
                }
                else {
                    Comida c = new Comida();
                    c.setNombre(nombre.getText().toString());
                    c.setAcompaniado(acomp.getText().toString());
                    c.setNombre(precio.getText().toString());
                    c.setUdi(comidaselect.getUdi());
                    c.setDisponible(disp.isChecked());
                    c.setTipo(Tipo.getSelectedItem().toString());
                    databaseReference.child("Comida").child(c.getUdi()).setValue(c);

                    Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                    Limpiar();
                    break;
                }
            }
            case R.id.icon_delete:{
                Comida c = new Comida();
                c.setUdi(comidaselect.getUdi());
                databaseReference.child("Comida").child(c.getUdi()).removeValue();
                Toast.makeText(this,"Eliminar", Toast.LENGTH_LONG).show();
                Limpiar();
                break;
            }


            default:break;
        }
        return true;
    }
    public void litarDatos(){
        databaseReference.child("Comida").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listComida.clear();
                for (DataSnapshot objSnapshop : snapshot.getChildren())
                {
                    Comida c = objSnapshop.getValue(Comida.class);
                    listComida.add(c);
                    Adapter adapter = new Adapter(MainActivity.this,(ArrayList<Comida>) listComida);
                    lista.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void Limpiar()
    {
        nombre.setText("");
        precio.setText("");
        acomp.setText("");
        disp.setText("");


    }
    private  void validacion()
    {
        if(nombre.getText().toString().equals(""))
            nombre.setError("Required");
        else if(precio.getText().toString().equals(""))
            precio.setError("Required");

    }
}