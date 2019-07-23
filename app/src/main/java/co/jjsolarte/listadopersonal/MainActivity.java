package co.jjsolarte.listadopersonal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.jjsolarte.listadopersonal.model.Persona;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText edtNombre;
    Button btnGuardar;
    ListView lisView;

    List<String> listaPersonal;
    ArrayAdapter adapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        inicializarVariables();
        lisView.setAdapter(adapter);

        cargarPersonas();

        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RealmResults<Persona> listaPersonaBD = realm.
                        where(Persona.class).findAll();
                if (!listaPersonaBD.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            ""+listaPersonaBD.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarPersonas() {
        RealmResults<Persona> listaLocal = realm.where(Persona.class).findAll();
        if (!listaLocal.isEmpty()){
            for (Persona persona: listaLocal){
                listaPersonal.add(persona.getNombre());
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void onGuardar(View view){
        listaPersonal.add(edtNombre.getText().toString());
        realm.beginTransaction();
        realm.commitTransaction();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Persona persona = realm.createObject(Persona.class);
                persona.setNombre(edtNombre.getText().toString());
                persona.setEdaad(26);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void inicializarVariables() {
        edtNombre = findViewById(R.id.mainEdtNombre);
        btnGuardar = findViewById(R.id.mainBtnGuardar);
        lisView = findViewById(R.id.mainLista);
        listaPersonal = new ArrayList<>();
        adapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,listaPersonal);
    }
}
