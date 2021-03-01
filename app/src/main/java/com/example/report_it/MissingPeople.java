package com.example.report_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MissingPeople extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_people);
        Query query = FirebaseDatabase.getInstance().getReference().child("Missing People");
        lv=(ListView)findViewById(R.id.listViewMissing);
        FirebaseListOptions<Missing> options = new FirebaseListOptions.Builder<Missing>()
                .setLayout(R.layout.missing)
                .setQuery(query,Missing.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView name=v.findViewById(R.id.tvMissingName);
                TextView gender=v.findViewById(R.id.tvMissingGender);
                TextView height=v.findViewById(R.id.tvMissingHeight);
                TextView skin_color=v.findViewById(R.id.tvMissingSkinColor);
                TextView last_seen=v.findViewById(R.id.tvMissingLastSeen);
                TextView clothes_details=v.findViewById(R.id.tvMissingClothes);
                TextView contact_details=v.findViewById(R.id.tvMissingContact);
                ImageView image=v.findViewById(R.id.imgMissingImage);
                Missing m=(Missing)model;
                name.setText("Name: "+m.getName().toString());
                gender.setText("Gender: "+m.getGender().toString());
                height.setText("Height: "+m.getHeight().toString());
                skin_color.setText("Skin Color: "+m.getSkin_Color().toString());
                last_seen.setText("Last Seen Location: "+m.getLast_Seen_Location().toString());
                clothes_details.setText("Clothes Last Seen In:\n"+m.getClothes_Details().toString());
                contact_details.setText("Contact: "+m.getContact_Details().toString());
                Picasso.get().load(m.getImage().toString()).into(image);

            }
        };
        lv.setAdapter(adapter);
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
}