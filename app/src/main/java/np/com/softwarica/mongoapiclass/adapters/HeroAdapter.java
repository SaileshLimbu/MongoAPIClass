package np.com.softwarica.mongoapiclass.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import np.com.softwarica.mongoapiclass.R;
import np.com.softwarica.mongoapiclass.models.Hero;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.MyHolder> {
    private Context context;
    private ArrayList<Hero> listHeroes;

    public HeroAdapter(Context context, ArrayList<Hero> listHeroes) {
        this.context = context;
        this.listHeroes = listHeroes;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hero, viewGroup, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Hero hero = listHeroes.get(i);
        myHolder.tvName.setText(hero.getName());
        myHolder.tvDesc.setText(hero.getDesc());
        strictMode();
        URL url = null;
        try {
            url = new URL("http://10.0.2.2:3000/uploads/" + hero.getImage());
            myHolder.imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyDataChange(ArrayList<Hero> listHeroes) {
        this.listHeroes.clear();
        this.listHeroes.addAll(listHeroes);
        this.notifyDataSetChanged();
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getItemCount() {
        return listHeroes.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgProfile;
        private TextView tvName, tvDesc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            imgProfile = itemView.findViewById(R.id.imgProfile);
        }
    }
}
