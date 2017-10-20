package com.tabian.tabfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.format.DateFormat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.tabian.tabfragments.R.id.SpinRama;
import static com.tabian.tabfragments.R.id.date;
import static com.tabian.tabfragments.R.id.tpTime;


public class CrearEvento extends AppCompatActivity {

    static   String JM="";
    static  String JF="";


    TimePicker tpTime;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    java.util.Calendar C = java.util.Calendar.getInstance();
    EditText date;

    Spinner spinRama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);


         spinRama=(Spinner)findViewById(R.id.SpinRama);

        List<String> values = new ArrayList<String>();
        values.add("Familia de la Providencia");
        values.add("JM");
        values.add("JF");
        values.add("Juventud");
        values.add("Madres");
        values.add("Madrugadores");
        values.add("Campaña del Rosario");
        values.add("Obra de Familias");
        values.add("Fundadores");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, values);

        spinRama.setAdapter(dataAdapter);


        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);


         date= (EditText) findViewById(R.id.date) ;
date.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showDialog(DATE_ID);
    }
});


        tpTime =(TimePicker)findViewById(R.id.tpTime);

        // set the time picker mode to 24 hour view
        tpTime.setIs24HourView(true);
        tpTime.setCurrentHour(12);
        tpTime.setCurrentMinute(00);


        Button agregar = (Button) findViewById(R.id.addEvent);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             FirebaseDatabase database = FirebaseDatabase.getInstance();

                EditText etContra= (EditText)findViewById(R.id.contraseña);


if(etContra.getText().toString().equals(JM)||etContra.getText().toString().equals(JF)) {
    EditText textNombre = (EditText) findViewById(R.id.labNombre);
    EditText textDesc = (EditText) findViewById(R.id.labDescripcion);


    DatabaseReference myRef = database.getReference("Eventos").push();


    String rama;

    rama=spinRama.getSelectedItem().toString();


    Evento evento = new Evento(textNombre.getText().toString(), textDesc.getText().toString(), date.getText().toString(),tpTime.getCurrentHour(),tpTime.getCurrentMinute(),rama);

    myRef.setValue(evento);
    finish();
}
else
{ Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
    finish();
}

            }
        });

    }

    private void colocar_fecha() {
        date.setText(mDayIni + "/" + (mMonthIni+1) + "/" + mYearIni+" ");

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }


}
