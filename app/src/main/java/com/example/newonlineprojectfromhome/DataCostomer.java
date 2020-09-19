package com.example.newonlineprojectfromhome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataCostomer extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Adapter adapter;

    ArrayList<Model> datalist;

    CardView cvInbox;

    TextView tvNama, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_costomer);


        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);
        cvInbox = findViewById(R.id.cvInbox);
        recyclerView = findViewById(R.id.listCustomer);

        getDataCostomer();

    }

    private void getDataCostomer() {
        datalist = new ArrayList<>();

        AndroidNetworking.post("http://192.168.6.201/rentalLaptop/viewdata.php")
                .addBodyParameter("roleuser", "2")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("PAYLOAD");

                            for (int i = 0; i < data.length(); i++) {

                                Model model = new Model();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setEmail(object.getString("email"));
                                model.setNama(object.getString("name"));
                                model.setNohp(object.getString("nohp"));
                                model.setAlamat(object.getString("alamat"));
                                model.setNoktp(object.getString("noktp"));
                                datalist.add(model);

                            }

                            adapter = new Adapter(datalist);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DataCostomer.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("hmm", "onResponse: " + anError.toString());
                        Log.d("hmm", "onResponse: " + anError.getErrorBody());
                        Log.d("hmm", "onResponse: " + anError.getErrorCode());
                        Log.d("hmm", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataCostomer();
            Toast.makeText(this, "data's..", Toast.LENGTH_SHORT).show();

        }
    }
}
