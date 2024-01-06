package com.example.currency20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.util.NetworkKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity  {

    TextView tvtile,tvres;
    EditText et;
    Spinner sp;
    Button btn;

    double usd,value;
    String typeto;
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter adapter;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvtile = findViewById(R.id.textViewTitle);
        tvres = findViewById(R.id.textViewRes);
        et = findViewById(R.id.editText);
        sp = findViewById(R.id.spinner);
        btn = findViewById(R.id.button);
        pb = findViewById(R.id.progressBar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et.getText().toString().trim();
                if(s.equals(""))
                    Toast.makeText(MainActivity2.this, "Enter some value", Toast.LENGTH_SHORT).show();
                else
                {
                    usd=Integer.parseInt(et.getText().toString().trim());
                    typeto= list.get(sp.getSelectedItemPosition());
                    getAllCurrency(usd,typeto);
                }


            }
        });


        list.add("USDINR");
        list.add("USDEUR");
        list.add("USDJPY");
        list.add("USDGBP");
        list.add("USDCHF");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }

    private void getAllCurrency(double usd,String typeto) {
        pb.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, NetworkKeys.GET_ALL_CURRENCY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.setVisibility(View.GONE);
                try {
                    JSONObject j = new JSONObject(response);
                    value = j.getJSONObject("quotes").getDouble(typeto);
                    String finalValue = String.format("%.2f",usd*value);


                    tvres.setText(et.getText().toString().trim()+" USD = "+finalValue+" "+typeto.substring(3));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.GONE);
                error.printStackTrace();
                Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}