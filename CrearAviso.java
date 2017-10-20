package com.tabian.tabfragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.VERSION_CODES.N;
import static com.tabian.tabfragments.CrearEvento.JF;
import static com.tabian.tabfragments.CrearEvento.JM;


public class CrearAviso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_aviso);

        Button add =(Button) findViewById(R.id.addAviso);
        final EditText Nombre=(EditText)findViewById(R.id.labNombre2);
        final EditText Descrip=(EditText)findViewById(R.id.labDescripcion2);
        final EditText contraseña=(EditText) findViewById(R.id.contraseña2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database2= FirebaseDatabase.getInstance();
                DatabaseReference AvisoRef= database2.getReference("Avisos").push();


                if(contraseña.getText().toString().equals(JM)||contraseña.getText().toString().equals(JF)) {
                    Aviso a = new Aviso(Nombre.getText().toString(), Descrip.getText().toString());

                    AvisoRef.setValue(a);



                }

                else
                    Toast.makeText(getApplicationContext(),"Contraseña Incorrecta", Toast.LENGTH_SHORT).show();


                finish();
            }
        });



    }
}
