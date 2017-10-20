package com.tabian.tabfragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;
import static com.tabian.tabfragments.CrearEvento.JF;
import static com.tabian.tabfragments.CrearEvento.JM;
import static com.tabian.tabfragments.R.id.fab;
import static com.tabian.tabfragments.Tab1Fragment.dataRef;
import static com.tabian.tabfragments.Tab1Fragment.keysID;

/**
 * Created by User on 2/28/2017.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    public static ArrayList<String> keysID2=new ArrayList<>();
    RecyclerView rv2;
    public static DatabaseReference dataRef2;
    List<Aviso> avisos;
    AvisoAdapter Adapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        rv2= (RecyclerView) view.findViewById(R.id.recycler2);

        itemTouchHelper.attachToRecyclerView(rv2);

        rv2.setLayoutManager(new LinearLayoutManager(getContext()));
        avisos=new ArrayList<>();
        Adapter2= new AvisoAdapter(avisos);
        rv2.setAdapter(Adapter2);


        dataRef2= FirebaseDatabase.getInstance().getReference("Avisos");

        FloatingActionButton fab2 = (FloatingActionButton)view.findViewById (R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), CrearAviso.class);
                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });


        ValueEventListener Listener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                avisos.removeAll(avisos);
                if(!keysID2.isEmpty())
                    keysID2.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Aviso a= snapshot.getValue(Aviso.class);
                    avisos.add(a);
                    keysID2.add(snapshot.getKey());

                }

                Adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };


        dataRef2.addValueEventListener(Listener);



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

                        dataRef2.child(keysID2.get(position)).removeValue();
                        Adapter2.notifyDataSetChanged();
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

            Adapter2.notifyDataSetChanged();


            //Remove swiped item from list and notify the RecyclerView

        }
    };

}
