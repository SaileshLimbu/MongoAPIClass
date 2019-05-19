package np.com.softwarica.mongoapiclass.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import np.com.softwarica.mongoapiclass.API.MyRetrofit;
import np.com.softwarica.mongoapiclass.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHeroUsingFieldMapActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etDesc;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hero);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);

        MyRetrofit.getAPI().addHero(map).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddHeroUsingFieldMapActivity.this, "Hero Added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddHeroUsingFieldMapActivity.this, "Failed to add hero", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddHeroUsingFieldMapActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
