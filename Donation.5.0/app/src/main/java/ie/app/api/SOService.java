package ie.app.api;

import java.util.List;

import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    ApiService apiService = (ApiService) RetrofitClient.getClient().create(SOService.class);
    @GET("/donations")
    Call<Donation> getAnswers();

    @GET("/donations")
    Call<Donation> getAnswers(@Query("tagged") String tags);
}