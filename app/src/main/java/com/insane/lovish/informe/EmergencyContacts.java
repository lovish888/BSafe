package com.insane.lovish.informe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmergencyContacts extends AppCompatActivity {

    private final int PICK_CONTACT = 1;
    RecyclerView contactsRecyclerView;
    RecyclerViewAdapter adapter;
    View policeCard, ambulanceCard, fireBrigadeCard;
    Button Add;
    InformeDatabaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        dbAdapter = new InformeDatabaseAdapter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recyclerView);

        policeCard = findViewById(R.id.police);
        ((TextView) policeCard.findViewById(R.id.name)).setText(R.string.police_text);
        ((TextView) policeCard.findViewById(R.id.number)).setText("100");

        ambulanceCard = findViewById(R.id.ambulance);
        ((TextView) ambulanceCard.findViewById(R.id.name)).setText(R.string.ambulance_text);
        ((TextView) ambulanceCard.findViewById(R.id.number)).setText("101");

        fireBrigadeCard = findViewById(R.id.firebrigade);
        ((TextView) fireBrigadeCard.findViewById(R.id.name)).setText(R.string.firebrigade_text);
        ((TextView) fireBrigadeCard.findViewById(R.id.number)).setText("102");

        adapter = new RecyclerViewAdapter(this, dbAdapter.retrieveContacts(), 0);
        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Add = (Button) findViewById(R.id.add_button);

        onClickListeners();
    }

    private void onClickListeners() {
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callContacts(view);
            }
        });

        policeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:100"));
                startActivity(callIntent);
            }
        });

        ambulanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:101"));
                startActivity(callIntent);
            }
        });

        fireBrigadeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:102"));
                startActivity(callIntent);
            }
        });
    }

    public void callContacts(View c) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            if (data != null) {
                Uri contactData = data.getData();
                if (contactData != null) {
                    Cursor cursor = getContentResolver().query(contactData, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER},
                            null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        String name = cursor.getString(0);
                        String number = cursor.getString(1);
                        dbAdapter.insertContact(name, number);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new RecyclerViewAdapter(this, dbAdapter.retrieveContacts(), 0);
        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
