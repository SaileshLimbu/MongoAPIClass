package np.com.softwarica.mongoapiclass.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import np.com.softwarica.mongoapiclass.API.MyRetrofit;
import np.com.softwarica.mongoapiclass.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFromBody, btnFromField, btnFromFieldMap, btnShowHeroes, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFromBody = findViewById(R.id.btnFromBody);
        btnFromField = findViewById(R.id.btnFromField);
        btnFromFieldMap = findViewById(R.id.btnFromFieldMap);
        btnShowHeroes = findViewById(R.id.btnShowHeroes);
        btnLogout = findViewById(R.id.btnLogin);

        btnFromBody.setOnClickListener(this);
        btnFromField.setOnClickListener(this);
        btnFromFieldMap.setOnClickListener(this);
        btnShowHeroes.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFromBody:
                startActivity(new Intent(this, AddHeroUsingBodyActivity.class));
                break;
            case R.id.btnFromField:
                startActivity(new Intent(this, AddHeroUsingFieldActivity.class));
                break;
            case R.id.btnFromFieldMap:
                startActivity(new Intent(this, AddHeroUsingFieldMapActivity.class));
                break;
            case R.id.btnShowHeroes:
                startActivity(new Intent(this, ShowHeroesActivity.class));
                break;
            case R.id.btnLogout:
                MyRetrofit.cookie = "";
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
