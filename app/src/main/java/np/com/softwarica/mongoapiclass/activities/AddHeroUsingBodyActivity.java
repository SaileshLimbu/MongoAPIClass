package np.com.softwarica.mongoapiclass.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import np.com.softwarica.mongoapiclass.API.MyRetrofit;
import np.com.softwarica.mongoapiclass.R;
import np.com.softwarica.mongoapiclass.models.Hero;
import np.com.softwarica.mongoapiclass.models.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHeroUsingBodyActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etDesc;
    private CircleImageView imgProfile;
    private Button btnRegister;
    private String imagePath;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hero);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        imgProfile = findViewById(R.id.imgProfile);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        context = this;
    }

    @Override
    public void onClick(View v) {
        File file = new File(imagePath);
        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imageFile", file.getName(), mFile);

        MyRetrofit.getAPI().uploadImage(fileToUpload).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if(response.isSuccessful()){
                    saveHero(response.body().getFileName());
                }else{
                    Toast.makeText(context, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveHero(String imageName) {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Hero hero = new Hero(name, desc, imageName);
        MyRetrofit.getAPI().addHero(hero).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Hero Added.", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etDesc.setText("");
                    imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                } else {
                    Toast.makeText(context, "Failed to add hero", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void browseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imagePath = getPath(uri);
            imgProfile.setImageURI(uri);
        }
    }

    public String getPath(Uri uri) {
        String[] projectile = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projectile, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
}