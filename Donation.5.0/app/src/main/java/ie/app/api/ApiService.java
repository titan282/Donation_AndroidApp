package ie.app.api;

import java.util.List;

import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    ApiService apiService =RetrofitClient.getClient().create(ApiService.class);
    @GET("donations")
    Call<List<Donation>> getAnswers();

    @DELETE("donations/{id}")
    Call<Donation> deleteItem(@Path("id") int itemId);

    @POST("donations")
    Call<Donation> addDonation(@Body Donation donation);
}
