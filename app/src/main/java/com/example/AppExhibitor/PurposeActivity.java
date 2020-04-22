package com.example.AppExhibitor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AppExhibitor.Api.ApiService;
import com.example.AppExhibitor.Modul.ItemPengunjung;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PurposeActivity extends AppCompatActivity {

    private TextView
            resultExhibitor,
            resultStand,
            resultEvent,
            resultDescription,
            resultGuest,
            resultEmail,
            resultTanggal,
            resultWaktu;

    ProgressDialog loading;
    Context mContext;
    private EditText edTujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);

        edTujuan = (EditText) findViewById(R.id.purpose);
        Button btnSave       = (Button) findViewById(R.id.btnSave);

        mContext = this;
        loading = new ProgressDialog(this);

        Bundle extras        = getIntent().getExtras();
        final String exhibitor  = extras.getString("result_exhibitor");
        final String stand      = extras.getString("result_stand");
        final String event      = extras.getString("result_event");
        final String description= extras.getString("result_desc");
        final String guest      = extras.getString("result_nama");
        final String email      = extras.getString("result_email");

        final String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        final String waktu   = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        resultGuest = findViewById(R.id.tvGuestName);
        resultGuest.setText(guest);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tujuan = String.valueOf(edTujuan.getText());

                if(tujuan.equals("")){
                    edTujuan.setError("Please fill the purpose!");
                }

                else{
                    String exhibitor_name = exhibitor;
                    String exhibitor_stand= stand;
                    String guest_name     = guest;
                    String guest_email    = email;
                    String visit_date     = tanggal;
                    String visit_time     = waktu;
                    String purpose        = tujuan;

                    sendTokenToServer(
                            exhibitor_name,
                            exhibitor_stand,
                            guest_name,
                            guest_email,
                            visit_date,
                            visit_time,
                            purpose);
                }
            }
        });
    }

    private void sendTokenToServer(String exhibitor_name, String exhibitor_stand, String guest_name, String guest_email, String visit_date, String visit_time, String purpose) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemPengunjung> call = service.Save(exhibitor_name, exhibitor_stand, guest_name, guest_email, visit_date, visit_time, purpose);

        call.enqueue(new Callback<ItemPengunjung>() {
            @Override
            public void onResponse(Call<ItemPengunjung> call, Response<ItemPengunjung> response) {

                if(response.isSuccessful()){
                    Boolean status   = response.body().getStatus();
                    if(status){
                        loading = ProgressDialog.show(mContext, null, "Please Wait", true, false);
                        Intent intent = new Intent(PurposeActivity.this, ReportActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        String pesan = "Missing parameter, cann't save Data";
                        Toast.makeText(PurposeActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String pesan = "Error retrive data from server !";
                    Toast.makeText(PurposeActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemPengunjung> call, Throwable t) {
                Toast.makeText(PurposeActivity.this, "Error try"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
