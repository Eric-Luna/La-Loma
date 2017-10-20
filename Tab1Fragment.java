package com.tabian.tabfragments;

import com.google.android.gms.actions.ItemListIntents;
import com.google.firebase.FirebaseApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.inputType;
import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.tabian.tabfragments.CrearEvento.JF;
import static com.tabian.tabfragments.CrearEvento.JM;
import static com.tabian.tabfragments.R.id.contraseña;
import static com.tabian.tabfragments.R.id.date;
import static com.tabian.tabfragments.R.id.fab;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    public static ArrayList<String> keysID=new ArrayList<>();
    RecyclerView rv;
   public static DatabaseReference dataRef;
    List<Evento> events;
    EventAdapter Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        rv= (RecyclerView) view.findViewById(R.id.recycler);

        itemTouchHelper.attachToRecyclerView(rv);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        events=new ArrayList<>();
        Adapter= new EventAdapter(events);
        rv.setAdapter(Adapter);

        
        dataRef= FirebaseDatabase.getInstance().getReference("Eventos");




        FloatingActionButton fab = (FloatingActionButton)view.findViewById (R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), CrearEvento.class);
                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });



        ValueEventListener Listener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.removeAll(events);
                if(!keysID.isEmpty())
                keysID.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Evento e= snapshot.getValue(Evento.class);
                    events.add(e);
                   keysID.add(snapshot.getKey());

                }

                OrdenarXFecha(keysID,events);
            Adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        dataRef.addValueEventListener(Listener);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference contraRef = database.getReference("Contraseña");
        EditText etContra= (EditText)view.findViewById(contraseña);

        contraRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    if (Snapshot.getKey().equals("JM"))
                        JM = Snapshot.getValue().toString();
                    else if(Snapshot.getKey().equals("JF"))
                        JF = Snapshot.getValue().toString();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }


    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {


            //Show Dialog DESEA BORRAR?


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Desea borrar el evento?\n Ingrese contraseña");

            final EditText input = new EditText(getContext());
            final int position = viewHolder.getAdapterPosition();

            input.setText("");
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setLines(1);
            input.setMaxLines(1);
            input.setTextColor(Color.parseColor("#FFFFFF"));
            input.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            builder.setView(input);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if(input.getText().toString().equals(JM)||input.getText().toString().equals(JF)){

                        dataRef.child(keysID.get(position)).removeValue();
                       Adapter.notifyDataSetChanged();
                    }
                    else
                        Toast.makeText(getContext(),"Contraseña Incorrecta",Toast.LENGTH_LONG).show();

                }
            });

            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
            alert.show();


            Adapter.notifyDataSetChanged();


            //Remove swiped item from list and notify the RecyclerView

        }
    };



    public void OrdenarXFecha(ArrayList<String> IDs,List<Evento> events)
    {
        Evento aux;
        Evento aux2;
        String id1;
        String id2;

        int dia1=5000;
        int mes1=5000;
        int año1=5000;

        int dia2=5000;
        int mes2=5000;
        int año2=5000;

        String[] dateParts;
        String[] dateParts2;
        // Integer.parseInt(et.getText().toString());

        for(int i=0;i<events.size()-1;i++)
        {
        for(int j=i+1;j<events.size();j++)
        {

                dateParts = events.get(i).getFecha().split("/");
                if(dateParts[0]!="") {
                dia1=Integer.parseInt(dateParts[0]);
                mes1=Integer.parseInt(dateParts[1]);
                año1=Integer.parseInt(dateParts[2].replace(" ",""));
            }else
                {
                    dia1=0;
                    mes1=0;
                    año1=0;
                }

            Log.i(TAG, "i=" + i+"fecha "+events.get(i).getFecha());
            Log.i(TAG, "dia:" + dia1+" mes: "+mes1+" año: "+año1);

                dateParts2 = events.get(j).getFecha().split("/");
            if(dateParts2[0]!="") {
                dia2=Integer.parseInt(dateParts2[0]);
                mes2=Integer.parseInt(dateParts2[1]);
                año2=Integer.parseInt(dateParts2[2].replace(" ",""));
            }else
            {
                dia2=0;
                mes2=0;
                año2=0;
            }

            aux=events.get(i);
            aux2=events.get(j);
            id1=IDs.get(i);
            id2=IDs.get(j);


    if(año2<año1)
    {
        events.set(i,aux2);
        events.set(j,aux);

        IDs.set(i, id2);
        IDs.set(j, id1);
    }
    else  if(año2==año1)
    {
        if(mes2<mes1)
        {
            events.set(i,aux2);
            events.set(j,aux);

            IDs.set(i, id2);
            IDs.set(j, id1);
        }
        else if(mes2==mes1)
        {

              if(dia2<dia1)
        {
            events.set(i,aux2);
            events.set(j,aux);

            IDs.set(i, id2);
            IDs.set(j, id1);
        }

        }
    }//else if año==año


        }//for j


        }//for i

    }


}
