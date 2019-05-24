package np.com.softwarica.mongoapiclass.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import np.com.softwarica.mongoapiclass.API.MyRetrofit;
import np.com.softwarica.mongoapiclass.R;
import np.com.softwarica.mongoapiclass.models.LoginSignupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onClick(View v) {
        if(!validate()) return;
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        MyRetrofit.getAPI().login(username, password).enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                if (response.isSuccessful()) {
                    MyRetrofit.cookie = response.headers().get("Set-Cookie");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Username and password does not match.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(){
        if(TextUtils.isEmpty(etUsername.getText().toString().trim())){
            etUsername.setError("Please enter username.");
            etUsername.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(etPassword.getText().toString().trim())){
            etPassword.setError("Please enter password.");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}
