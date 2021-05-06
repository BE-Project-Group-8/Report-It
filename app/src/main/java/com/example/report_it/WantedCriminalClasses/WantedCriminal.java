package com.example.report_it.WantedCriminalClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.report_it.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class WantedCriminal extends AppCompatActivity {
    RadioButton rbtnVandalism,rbtnTheft,rbtnAssault,rbtnMurder;
    RadioGroup radioGroup;
    Button btnSearch,btnClear;
    ListView lv;
    FirebaseListAdapter adapter;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted_criminal);
        rbtnVandalism=findViewById(R.id.rdbVandalism);
        rbtnTheft=findViewById(R.id.rdbTheft);
        rbtnAssault=findViewById(R.id.rdbAssault);
        rbtnMurder=findViewById(R.id.rdbMurder);
        btnSearch=findViewById(R.id.applyFilter);
        btnClear=findViewById(R.id.clearFilter);
        radioGroup=findViewById(R.id.radioGroup);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Wanted Criminals");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Query query = FirebaseDatabase.getInstance().getReference().child("Wanted Criminals");
        lv=(ListView)findViewById(R.id.listViewWanted2);
        FirebaseListOptions<Wanted> options = new FirebaseListOptions.Builder<Wanted>()
                .setLayout(R.layout.wanted)
                .setQuery(query,Wanted.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView name=v.findViewById(R.id.tvWantedName);
                TextView gender=v.findViewById(R.id.tvWantedGender);
                TextView height=v.findViewById(R.id.tvWantedHeight);
                TextView skin_color=v.findViewById(R.id.tvWantedSkinColor);
                TextView alias=v.findViewById(R.id.tvWantedAlias);
                TextView place_dob=v.findViewById(R.id.tvWantedPlaceDob);
                TextView hair=v.findViewById(R.id.tvWantedHair);
                TextView last_seen=v.findViewById(R.id.tvWantedLastSeen);
                TextView eyes=v.findViewById(R.id.tvWantedEyes);
                TextView crime=v.findViewById(R.id.tvWantedCrime);

                ImageView image=v.findViewById(R.id.imgWantedImage);
                Wanted w=(Wanted)model;
                name.setText("Name: "+w.getName().toString());
                gender.setText("Gender: "+w.getGender().toString());
                height.setText("Height: "+w.getHeight().toString());
                skin_color.setText("Skin Color: "+w.getSkin_Color().toString());
                last_seen.setText("Last Seen At: "+w.getLast_Seen().toString());
                alias.setText("Alias:"+w.getAlias().toString());
                place_dob.setText("Place & Date Of Birth: "+w.getPlace_DOB().toString());
                eyes.setText("Eyes: "+w.getEyes().toString());
                hair.setText("Hair:"+w.getHair().toString());
                crime.setText("Crime: "+w.getCrime().toString());
                Picasso.get().load(w.getImage().toString()).into(image);

            }
        };
        lv.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    Query query = FirebaseDatabase.getInstance().getReference().child("Wanted Criminals");
                    lv=(ListView)findViewById(R.id.listViewWanted2);
                    FirebaseListOptions<Wanted> options = new FirebaseListOptions.Builder<Wanted>()
                            .setLayout(R.layout.wanted)
                            .setQuery(query,Wanted.class)
                            .build();
                    adapter = new FirebaseListAdapter(options) {
                        @Override
                        protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                            TextView name=v.findViewById(R.id.tvWantedName);
                            TextView gender=v.findViewById(R.id.tvWantedGender);
                            TextView height=v.findViewById(R.id.tvWantedHeight);
                            TextView skin_color=v.findViewById(R.id.tvWantedSkinColor);
                            TextView alias=v.findViewById(R.id.tvWantedAlias);
                            TextView place_dob=v.findViewById(R.id.tvWantedPlaceDob);
                            TextView hair=v.findViewById(R.id.tvWantedHair);
                            TextView last_seen=v.findViewById(R.id.tvWantedLastSeen);
                            TextView eyes=v.findViewById(R.id.tvWantedEyes);
                            TextView crime=v.findViewById(R.id.tvWantedCrime);

                            ImageView image=v.findViewById(R.id.imgWantedImage);
                            Wanted w=(Wanted)model;
                            name.setText("Name: "+w.getName().toString());
                            gender.setText("Gender: "+w.getGender().toString());
                            height.setText("Height: "+w.getHeight().toString());
                            skin_color.setText("Skin Color: "+w.getSkin_Color().toString());
                            last_seen.setText("Last Seen At: "+w.getLast_Seen().toString());
                            alias.setText("Alias:"+w.getAlias().toString());
                            place_dob.setText("Place & Date Of Birth: "+w.getPlace_DOB().toString());
                            eyes.setText("Eyes: "+w.getEyes().toString());
                            hair.setText("Hair:"+w.getHair().toString());
                            crime.setText("Crime: "+w.getCrime().toString());
                            Picasso.get().load(w.getImage().toString()).into(image);

                        }
                    };
                    lv.setAdapter(adapter);
                    adapter.startListening();
                }
                else
                {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectButton=(RadioButton)findViewById(selectedId);
                    String crimeCommitted=selectButton.getText().toString();
                    Query query = FirebaseDatabase.getInstance().getReference().child("Wanted Criminals").orderByChild("Crime").equalTo(crimeCommitted);
                    Toast.makeText(WantedCriminal.this,crimeCommitted,Toast.LENGTH_SHORT).show();
                    lv=(ListView)findViewById(R.id.listViewWanted2);
                    FirebaseListOptions<Wanted> options = new FirebaseListOptions.Builder<Wanted>()
                            .setLayout(R.layout.wanted)
                            .setQuery(query,Wanted.class)
                            .build();
                    adapter = new FirebaseListAdapter(options) {
                        @Override
                        protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                            TextView name=v.findViewById(R.id.tvWantedName);
                            TextView gender=v.findViewById(R.id.tvWantedGender);
                            TextView height=v.findViewById(R.id.tvWantedHeight);
                            TextView skin_color=v.findViewById(R.id.tvWantedSkinColor);
                            TextView alias=v.findViewById(R.id.tvWantedAlias);
                            TextView place_dob=v.findViewById(R.id.tvWantedPlaceDob);
                            TextView hair=v.findViewById(R.id.tvWantedHair);
                            TextView last_seen=v.findViewById(R.id.tvWantedLastSeen);
                            TextView eyes=v.findViewById(R.id.tvWantedEyes);
                            TextView crime=v.findViewById(R.id.tvWantedCrime);

                            ImageView image=v.findViewById(R.id.imgWantedImage);
                            Wanted w=(Wanted)model;
                            name.setText("Name: "+w.getName().toString());
                            gender.setText("Gender: "+w.getGender().toString());
                            height.setText("Height: "+w.getHeight().toString());
                            skin_color.setText("Skin Color: "+w.getSkin_Color().toString());
                            last_seen.setText("Last Seen At: "+w.getLast_Seen().toString());
                            alias.setText("Alias:"+w.getAlias().toString());
                            place_dob.setText("Place & Date Of Birth: "+w.getPlace_DOB().toString());
                            eyes.setText("Eyes: "+w.getEyes().toString());
                            hair.setText("Hair:"+w.getHair().toString());
                            crime.setText("Crime: "+w.getCrime().toString());
                            Picasso.get().load(w.getImage().toString()).into(image);

                        }
                    };
                    lv.setAdapter(adapter);
                    adapter.startListening();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                Query query = FirebaseDatabase.getInstance().getReference().child("Wanted Criminals");
                lv=(ListView)findViewById(R.id.listViewWanted2);
                FirebaseListOptions<Wanted> options = new FirebaseListOptions.Builder<Wanted>()
                        .setLayout(R.layout.wanted)
                        .setQuery(query,Wanted.class)
                        .build();
                adapter = new FirebaseListAdapter(options) {
                    @Override
                    protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                        TextView name=v.findViewById(R.id.tvWantedName);
                        TextView gender=v.findViewById(R.id.tvWantedGender);
                        TextView height=v.findViewById(R.id.tvWantedHeight);
                        TextView skin_color=v.findViewById(R.id.tvWantedSkinColor);
                        TextView alias=v.findViewById(R.id.tvWantedAlias);
                        TextView place_dob=v.findViewById(R.id.tvWantedPlaceDob);
                        TextView hair=v.findViewById(R.id.tvWantedHair);
                        TextView last_seen=v.findViewById(R.id.tvWantedLastSeen);
                        TextView eyes=v.findViewById(R.id.tvWantedEyes);
                        TextView crime=v.findViewById(R.id.tvWantedCrime);

                        ImageView image=v.findViewById(R.id.imgWantedImage);
                        Wanted w=(Wanted)model;
                        name.setText("Name: "+w.getName().toString());
                        gender.setText("Gender: "+w.getGender().toString());
                        height.setText("Height: "+w.getHeight().toString());
                        skin_color.setText("Skin Color: "+w.getSkin_Color().toString());
                        last_seen.setText("Last Seen At: "+w.getLast_Seen().toString());
                        alias.setText("Alias:"+w.getAlias().toString());
                        place_dob.setText("Place & Date Of Birth: "+w.getPlace_DOB().toString());
                        eyes.setText("Eyes: "+w.getEyes().toString());
                        hair.setText("Hair:"+w.getHair().toString());
                        crime.setText("Crime: "+w.getCrime().toString());
                        Picasso.get().load(w.getImage().toString()).into(image);

                    }
                };
                lv.setAdapter(adapter);
                adapter.startListening();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}