package com.example.AppExhibitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.AppExhibitor.Api.ApiService;
import com.example.AppExhibitor.Modul.ItemGuest;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    Context mContext;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        loading = new ProgressDialog(this);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());

        String id = rawResult.getText();

        if(id != ""){
            sendTokenToServer(id);
        }
        else{
            String pesan = "Scann gagal harap scann lagi atau hidupkan flash";
            Toast.makeText(ScannerActivity.this, pesan, Toast.LENGTH_SHORT).show();
        }

        mScannerView.resumeCameraPreview(this);
    }

    private void sendTokenToServer(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemGuest> call = service.Scann(id);

        call.enqueue(new Callback<ItemGuest>() {
            @Override
            public void onResponse(Call<ItemGuest> call, Response<ItemGuest> response) {
                if(response.isSuccessful()){
                    Boolean status   = response.body().getError();
                    final String name  = response.body().getName();
                    final String email = response.body().getEmail();

                    if(status){
                        String pesan = "ID not found";
                        Toast.makeText(ScannerActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Bundle extras = getIntent().getExtras();
                        final String exhibitor  = extras.getString("result_exhibitor");
                        final String stand      = extras.getString("result_stand");
                        final String event      = extras.getString("result_event");
                        final String description= extras.getString("result_desc");

                        Intent intent = new Intent(ScannerActivity.this, PurposeActivity.class);
                        intent.putExtra("result_nama", name);
                        intent.putExtra("result_email", email);
                        intent.putExtra("result_exhibitor", exhibitor);
                        intent.putExtra("result_stand", stand);
                        intent.putExtra("result_event", event);
                        intent.putExtra("result_desc", description);
                        startActivity(intent);
                    }
                }
                else{
                    String pesan = "Error retrive data from server !";
                    Toast.makeText(ScannerActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemGuest> call, Throwable t) {
                Toast.makeText(ScannerActivity.this, "Error try"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
