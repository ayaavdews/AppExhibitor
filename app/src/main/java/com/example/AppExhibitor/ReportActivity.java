package com.example.AppExhibitor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppExhibitor.Adapter.RetrofitAdapter;
import com.example.AppExhibitor.Api.ApiService;
import com.example.AppExhibitor.Modul.ModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ReportActivity extends AppCompatActivity {

    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        recyclerView = findViewById(R.id.recycler);

        fetchJSON();
    }

    private void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

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

    @SuppressLint("WrongConstant")
    private void writeRecycler(String response) {
        try{
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){
                ArrayList<ModelRecycler> modelRecyclerArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    ModelRecycler modelRecycler = new ModelRecycler();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    modelRecycler.setGuest_name(dataobj.getString("guest_name"));
                    modelRecycler.setPurpose(dataobj.getString("purpose"));
                    modelRecycler.setVisit_date(dataobj.getString("visit_date"));
                    modelRecycler.setVisit_time(dataobj.getString("visit_time"));

                    modelRecyclerArrayList.add(modelRecycler);
                }

                retrofitAdapter = new RetrofitAdapter(this,modelRecyclerArrayList);
                recyclerView.setAdapter(retrofitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }
            else {
                Toast.makeText(ReportActivity.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
