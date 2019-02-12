package com.example.gighub;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddGigActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gig);

        findViewById(R.id.GigActivityLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
    }

    public void onClickAddNewGig(View view) {
        EditText artist = (EditText)findViewById(R.id.EditTextArtist);
        EditText genre = (EditText)findViewById(R.id.EditTextGenre);
        EditText venue = (EditText)findViewById(R.id.EditTextVenue);
        EditText price = (EditText)findViewById(R.id.EditTextPrice);
        EditText address = (EditText)findViewById(R.id.EditTextAddress);
        EditText shortDesc = (EditText)findViewById(R.id.EditTextShortDesc);
        EditText date = (EditText)findViewById(R.id.EditTextDate);

        Gig newGig = new Gig(artist.getText().toString(), genre.getText().toString(), venue.getText()
                .toString(), price.getText().toString(), address.getText().toString(),
                shortDesc.getText().toString(), date.getText().toString());

        db.addGig(newGig);
        Toast.makeText(getApplicationContext(), "Gig added successfully!", Toast.LENGTH_LONG).show();
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
