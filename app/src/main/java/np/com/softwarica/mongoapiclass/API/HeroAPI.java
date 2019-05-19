package np.com.softwarica.mongoapiclass.API;

import java.util.HashMap;

import np.com.softwarica.mongoapiclass.models.Hero;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HeroAPI {
    @POST("heroes")
    Call<Void> addHero(@Body Hero hero);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Field("name") String name, @Field("desc") String desc);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@FieldMap HashMap<String, String> map);
}
