package hk.hku.cs.comp3330project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET()
    Call<MsgModel> getMessage(@Url String url);
}
