package np.com.softwarica.mongoapiclass.API;

import java.util.ArrayList;
import java.util.HashMap;

import np.com.softwarica.mongoapiclass.models.Hero;
import np.com.softwarica.mongoapiclass.models.ImageResponse;
import np.com.softwarica.mongoapiclass.models.LoginSignupResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HeroAPI {

    @GET("heroes")
    Call<ArrayList<Hero>> getHeroes(@Header("Cookie")String cookie);

    @POST("heroes")
    Call<Void> addHero(@Header("Cookie")String cookie,@Body Hero hero);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Header("Cookie")String cookie,@Field("name") String name, @Field("desc") String desc, @Field("image") String imagePath);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Header("Cookie")String cookie,@FieldMap HashMap<String, String> map);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Header("Cookie")String cookie,@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginSignupResponse> login(@Field("username") String username, @Field("password")String password);

    @FormUrlEncoded
    @POST("users/signup")
    Call<LoginSignupResponse> register(@Field("username") String username, @Field("password")String password);
}
