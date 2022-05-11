package ie.app.api;

import java.util.List;

import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface ApiService {
    ApiService apiService =RetrofitClient.getClient().create(ApiService.class);
    @GET("donations")
    Call<List<Donation>> getAnswers();
}
