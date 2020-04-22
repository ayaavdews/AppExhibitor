package com.example.AppExhibitor;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AppExhibitor.Api.ApiService;
import com.example.AppExhibitor.Modul.ItemSpinner1;
import com.example.AppExhibitor.Modul.ItemSpinner2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.R.layout.simple_spinner_item;

public class ListActivity extends AppCompatActivity {

    private ArrayList<ItemSpinner2> goodModelArrayList;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private Spinner spinner;

    //Menerima Data yag dikirim Dari Login Page
    TextView tvResultExhibitor, tvResultStand;
    String resultExhibitor, resultStand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        spinner = findViewById(R.id.spCompany);
        fetchJSON();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();

                //Mengirim Data ke Fragment
                //Memasukkan data dari Login Page ke Variable untuk dikirim lagi ke Dashboard
                final String exhibitor  = extras.getString("result_exhibitor");
                final String stand      = extras.getString("result_stand");

                //Mengambil Data dari Spinner yang Dipilih :
                final String events = spinner.getSelectedItem().toString();

                //Melakukan Post terhadap Spinner yang dipilih untuk mendapat Deskripsi Event :
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiService.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Call<ItemSpinner1> call = service.GetDesc(events);

                call.enqueue(new Callback<ItemSpinner1>() {
                    @Override
                    public void onResponse(Call<ItemSpinner1> call, Response<ItemSpinner1> response) {
                        if(response.isSuccessful()){
                            Boolean status   = response.body().getError();
                            String deskripsi = response.body().getDescription();

                            if(status){
                                String pesan = "Events not found";
                                Toast.makeText(ListActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                            else{

                                Intent intent = new Intent(ListActivity.this, DashboardActivity.class);
                                intent.putExtra("result_event", events);
                                intent.putExtra("result_exhibitor", exhibitor);
                                intent.putExtra("result_stand", stand);
                                intent.putExtra("result_desc", deskripsi);
                                startActivity(intent);
                            }
                        }
                        else {
                            String pesan = "Error retrive data from server !";
                            Toast.makeText(ListActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemSpinner1> call, Throwable t) {
                        Toast.makeText(ListActivity.this, "Error try"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //Intent untuk pindah ke Tabbed View Dashboard :

            }
        });
    }

    private void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<String> call = api.getJSONString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        spinJSON(jsonresponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void spinJSON(String response) {
        try{
            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                goodModelArrayList = new ArrayList<>();
                JSONArray dataArray = obj.getJSONArray("data");

                for(int i=0; i<dataArray.length(); i++){
                    ItemSpinner2 itemSpinner2 = new ItemSpinner2();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    itemSpinner2 .setId(dataobj.getString("id"));
                    itemSpinner2 .setEvents(dataobj.getString("events"));
                    itemSpinner2 .setDescription(dataobj.getString("description"));

                    goodModelArrayList.add(itemSpinner2);
                }

                for (int i=0; i<goodModelArrayList.size(); i++){
                    playerNames.add(goodModelArrayList.get(i).getEvents().toString());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ListActivity.this, simple_spinner_item, playerNames);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
