package com.w3itexperts.ombe.APIservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// We prep the API communication here ==============
// EXTERNAL RESOURCES AND TOOLS:
// https://stackoverflow.com/questions/49725171/cannot-resolve-symbol-apiclient-retrofit how to prep
// can use retrofit to communcicate/use API
// https://square.github.io/retrofit/
// https://www.youtube.com/watch?v=j7lRiTJ_-cI
public class ApiClient {
    // if we run locally: 10.0.2.2
    // if we run physically: ip address + same wifi network
    // GUYS CHANGE THIS ONE IF YOU USING THE PHONE
    // 1. open terminal cmd: type ipconfig
    // 2. copy the ipv4 address thingy
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // backend passing as json!!!
                    .build();
        }
        return retrofit.create(ApiService.class);
    }


}
