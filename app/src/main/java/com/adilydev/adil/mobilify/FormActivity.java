package com.adilydev.adil.mobilify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.android.volley.Request.Method;

public class FormActivity extends AppCompatActivity {

    EditText license;
    String violation;
    RequestQueue requestQueue;
    UUID uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        license = (EditText) findViewById(R.id.etLicense);

        requestQueue = Volley.newRequestQueue(FormActivity.this);

        // Generate unique id
        uid = UUID.randomUUID();

        // Notes
        final EditText etNotes = (EditText) findViewById(R.id.notes);

        // <Spinner >.............................

        //get the spinner from the xml.
        final Spinner dropdown = (Spinner) findViewById(R.id.spViolation);
        //create a list of items for the spinner.
        String[] items = new String[]{
                "Select a Violation...",
                "Red Light",
                "Dangerous driving",
                "Using phone while driving",
                "Others"
        };

        //create an adapter to describe how the items are displayed,
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items){
            // disable first item
            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
                // return super.isEnabled(position);
            }

            @Override
            public View getDropDownView(int position,
                                        @Nullable View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new MySpinnerSelectedListener());

        // </spinner >......................................

        // Button Confirm
        Button button_start  = (Button) findViewById(R.id.btnConfirm);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Send data to database..................................................

                StringRequest stringRequest =
                        new StringRequest(Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FormActivity.this,
                                "Success data registred in the database",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormActivity.this, "Error......",
                                Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parameters = new HashMap<String, String>();

                        parameters.put("license", license.getText().toString());
                        parameters.put("violation", violation);
                        parameters.put("uid", uid.toString());
                        parameters.put("notes", String.valueOf(etNotes));
                        return parameters;
                    }
                };

                requestQueue.add(stringRequest);

                Intent i = new Intent(getBaseContext(), Success.class);
                i.putExtra("MyText", license.getText().toString());
                i.putExtra("getData", violation);
                i.putExtra("uniqueID", uid.toString());
                i.putExtra("Notes", etNotes.toString());
                startActivity(i);
            }
        });

        // Button Help
        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getBaseContext(), Help.class);
                startActivity(i);
            }
        });


    }

    // Spinner Class

    class MySpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            violation = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}

