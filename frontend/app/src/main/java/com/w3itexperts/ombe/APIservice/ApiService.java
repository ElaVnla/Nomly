package com.w3itexperts.ombe.APIservice;

import java.util.List;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.w3itexperts.ombe.apimodals.OtpVerificationRequest;
import com.w3itexperts.ombe.apimodals.RegistrationResponse;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.usersgroupings;
import com.w3itexperts.ombe.apimodals.RegistrationRequest;



public interface ApiService {

    // Users API

    // Returns a list, data is:  userId,username,email,password,preferenes,createdAt,grouplist
    @GET("users/get-all-users")
    Call<List<users>> getAllUsers();

    @GET("users/get-user/{id}")
    Call<users> getUser(@Path("id") int id);

    @DELETE("users/delete-user/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @PUT("users/update-user/{id}")
    Call<users> updateUser(@Path("id") int id, @Body users user);

    @POST("users/add-user")
    Call<users> addUser(@Body users user);

// Users Groupings API

    @POST("users-groupings/add-user-to-grouping")
    Call<Void> addUserToGrouping(@Body usersgroupings userGrouping);

    // Here we assume you pass two query parameters for removal.
    @DELETE("users-groupings/remove-user-from-grouping")
    Call<Void> removeUserFromGrouping(@Query("userId") int userId, @Query("groupingId") int groupingId);

// Sessions API

    @GET("sessions/get-all-sessions")
    Call<List<sessions>> getAllSessions();

    @GET("sessions/get-session/{id}")
    Call<sessions> getSession(@Path("id") int id);

    @DELETE("sessions/delete-session/{id}")
    Call<Void> deleteSession(@Path("id") int id);

    @PUT("sessions/update-session")
    Call<sessions> updateSession(@Body sessions session);

    @POST("sessions/add-session")
    Call<sessions> addSession(@Body sessions session);

    @POST("sessions/session-completed/{id}")
    Call<Void> markSessionCompleted(@Path("id") int id);

// Groupings API

    @GET("groupings/get-all-groupings")
    Call<List<groupings>> getAllGroupings();

    @GET("groupings/get-grouping/{id}")
    Call<groupings> getGrouping(@Path("id") int id);

    @DELETE("groupings/delete-grouping/{id}")
    Call<Void> deleteGrouping(@Path("id") int id);

    @PUT("groupings/update-grouping/{id}")
    Call<groupings> updateGrouping(@Path("id") int id, @Body groupings grouping);

    @POST("groupings/add-grouping")
    Call<groupings> addGrouping(@Body groupings grouping);

    // Email (Registration) API
    @POST("email/register")
    Call<RegistrationResponse> registerEmail(@Body RegistrationRequest registrationRequest);

    // (Keep your other endpoints as before)
    @POST("email/verify-otp")
    Call<Boolean> verifyOtp(@Body com.w3itexperts.ombe.apimodals.OtpVerificationRequest otpRequest);


}
