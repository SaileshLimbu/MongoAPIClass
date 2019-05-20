package np.com.softwarica.mongoapiclass.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import np.com.softwarica.mongoapiclass.API.MyRetrofit;
import np.com.softwarica.mongoapiclass.R;
import np.com.softwarica.mongoapiclass.adapters.HeroAdapter;
import np.com.softwarica.mongoapiclass.models.Hero;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowHeroesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Hero> listHeroes = new ArrayList<>();
    private HeroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_heroes);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new HeroAdapter(ShowHeroesActivity.this, listHeroes);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowHeroesActivity.this));
        recyclerView.setAdapter(adapter);
        loadHeroes();
    }

    private void loadHeroes() {
        MyRetrofit.getAPI().getHeroes().enqueue(new Callback<ArrayList<Hero>>() {
            @Override
            public void onResponse(Call<ArrayList<Hero>> call, Response<ArrayList<Hero>> response) {
                if (response.isSuccessful()) {
                    adapter.notifyDataChange(response.body());
                } else {
                    Toast.makeText(ShowHeroesActivity.this, "Failed to get hero list.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Hero>> call, Throwable t) {
                Toast.makeText(ShowHeroesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
