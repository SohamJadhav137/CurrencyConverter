package com.example.currency20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.currency20.R;
import com.example.util.NetworkKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tvtitle,tvdesc,tvvalue;
    EditText et;
    Button btn;
    ProgressBar pb;
    Double var;
    Spinner sp;
    String a="0",b="USDINR",c="0",d="INR";
    String type;

    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvtitle = findViewById(R.id.textViewTitle);
        tvdesc = findViewById(R.id.textViewDesc);
        tvvalue = findViewById(R.id.textViewValue);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);
        pb = findViewById(R.id.progressBar);
        sp = findViewById(R.id.spinner);

        sp.setOnItemSelectedListener(this);
        list.add("India");
        list.add("Europe");
        list.add("Japan");
        list.add("UK");
        list.add("Swiss");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = et.getText().toString().trim();
                if(a.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter value", Toast.LENGTH_SHORT).show();
                else
                {
                    tvvalue.setText(c+" "+d);
                }

            }
        });
    }

    private void getAllCurrency(int a,String b) {
        pb.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, NetworkKeys.GET_ALL_CURRENCY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.setVisibility(View.GONE);
                try {
                    JSONObject j = new JSONObject(response);
                    var = j.getJSONObject("quotes").getDouble(b);
                    c = String.format("%.2f",a*var);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.GONE);
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0)
        {

            type="USDINR";
            d="INR";
            getAllCurrency(Integer.parseInt(a),b);
        }
        if(i==1)
        {
            b="USDEUR";
            d="EUR";
            getAllCurrency(Integer.parseInt(a),b);
        }
        if(i==2)
        {
            b="USDJPY";
            d="JPY";
            getAllCurrency(Integer.parseInt(a),b);
        }
        if(i==1)
        {
            b="USDGBP";
            d="GBP";
            getAllCurrency(Integer.parseInt(a),b);
        }
        if(i==1)
        {
            b="USDCHF";
            d="CHF";
            getAllCurrency(Integer.parseInt(a),b);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}