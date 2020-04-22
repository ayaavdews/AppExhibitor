package com.example.AppExhibitor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AppExhibitor.Api.ApiService;
import com.example.AppExhibitor.Modul.ItemLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edEmail, edPassword;
    Context mContext;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = (EditText) findViewById(R.id.email);
        edPassword = (EditText) findViewById(R.id.password);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        mContext = this;
        loading = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(edEmail.getText());
                String password = String.valueOf(edPassword.getText());

                if(email.equals("")){
                    edEmail.setError("email kosong");
                }
                else if(password.equals("")){
                    edPassword.setError("password kosong");
                }
                else
                {
                    sendTokenToServer(email, password);
                }
            }
        });
    }

    private void sendTokenToServer(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.URL_HEAD)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemLogin> call = service.Login(email, password);

        call.enqueue(new Callback<ItemLogin>() {
            @Override
            public void onResponse(Call<ItemLogin> call, Response<ItemLogin> response) {

                if(response.isSuccessful()){
                    Boolean status   = response.body().getError();
                    String exhibitor = response.body().getNama();
                    String stand     = response.body().getStand();
                    if(status){
                        String pesan = "Email/Password salah";
                        Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        loading = ProgressDialog.show(mContext, null, "Please Wait", true, false);
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.putExtra("result_exhibitor", exhibitor);
                        intent.putExtra("result_stand", stand);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    String pesan = "Error retrive data from server !";
                    Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemLogin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error try"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
