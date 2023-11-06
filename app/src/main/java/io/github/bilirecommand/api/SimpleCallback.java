package io.github.bilirecommand.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SimpleCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        resp(response.body(),call,response);
    }

    public abstract void resp(T body, Call<T> call, Response<T> response);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
    }
}
