package com.w3itexperts.ombe.APIservice;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.Map;


import com.w3itexperts.ombe.apimodals.OtpVerificationRequest;
import com.w3itexperts.ombe.apimodals.RegistrationResponse;
import com.w3itexperts.ombe.apimodals.eateries;
import com.w3itexperts.ombe.apimodals.locationDTO;
import com.w3itexperts.ombe.apimodals.userVoteDTO;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.usersDTO;
import com.w3itexperts.ombe.apimodals.usersgroupings;
import com.w3itexperts.ombe.apimodals.RegistrationRequest;

// retrofit how to request/ use api
// EXTERNAL RESOURCES AND TOOLS: https://square.github.io/retrofit/, youtube retrofit tutorial
public interface ApiService {

    // ALL USERS API STUFF HERE ================================================

    // Returns a list, data is:  userId,username,email,password,preferenes,createdAt,grouplist
    @GET("users/get-all-users")
    Call<List<users>> getAllUsers();

    @GET("users/get-user/{id}")
    Call<users> getUser(@Path("id") int id);

    @DELETE("users/delete-user/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @PUT("users/update-user/{id}")
    Call<users> updateUser(@Path("id") int id, @Body Map<String, String> body);

    @POST("users/add-user")
    Call<users> addUser(@Body Map<String, String> user);



    // ALL GROUP USERS STUFF HERE ======================================================

    //@POST("users-groupings/add-user-to-grouping")
    //Call<Void> addUserToGrouping(@Body usersgroupings userGrouping);

    //toni change
    @POST("users-groupings/add-user-to-grouping")
    Call<usersgroupings> addUserToGrouping(@Body Map<String, String> userGroupData);


    // Here we assume you pass two query parameters for removal.
    //@DELETE("users-groupings/remove-user-from-grouping")
    //Call<Void> removeUserFromGrouping(@Query("userId") int userId, @Query("groupingId") int groupingId);

    //toni change
    @POST("users-groupings/remove-user-from-grouping")
    Call<Boolean> removeUserFromGrouping(@Body Map<String, String> body);

    // ALL SESSIONS API HERE ================================================================

    @GET("sessions/get-all-sessions")
    Call<List<sessions>> getAllSessions();

    @GET("sessions/get-session/{id}")
    Call<sessions> getSession(@Path("id") int id);

    @DELETE("sessions/delete-session/{id}")
    Call<Void> deleteSession(@Path("id") int id);

//    @PUT("sessions/update-session")
//    Call<sessions> updateSession(@Body sessions session);

    //    @PUT("sessions/update-session")
//    Call<sessions> updateSession(@Body Map<String, String> session);
    @PUT("sessions/update-session/{id}")
    Call<sessions> updateSession(@Path("id") int id, @Body Map<String, String> session);


    //    @POST("sessions/add-session")
//    Call<sessions> addSession(@Body sessions session);
    @POST("sessions/add-session")
    Call<sessions> addSession(@Body Map<String, String> body);


    @PUT("sessions/session-completed/{id}")
    Call<Void> markSessionCompleted(@Path("id") int id);

    // ALL PURELY GROUPINGS API HERE ==============================================================

    @GET("groupings/get-all-groupings")
    Call<List<groupings>> getAllGroupings();

    @GET("groupings/get-grouping/{id}")
    Call<groupings> getGrouping(@Path("id") int id);

    @DELETE("groupings/delete-grouping/{id}")
    Call<Void> deleteGrouping(@Path("id") int id);

    // @PUT("groupings/update-grouping/{id}")
    //Call<groupings> updateGrouping(@Path("id") int id, @Body groupings grouping);

    @PUT("groupings/update-grouping/{id}")
    Call<groupings> updateGrouping(@Path("id") int id, @Body Map<String, String> body); // ✅

    //@POST("groupings/add-grouping")
    //Call<groupings> addGrouping(@Body groupings grouping);

    @POST("groupings/add-grouping")
    Call<groupings> addGrouping(@Body Map<String, String> groupData);

    //new by toni
    @GET("groupings/get-grouping-by-code/{code}")
    Call<groupings> getGroupingByCode(@Path("code") String groupCode);


    // ALL EMAIL API STUFF HERE ===================================================
    // Email (Registration) API
    @POST("email/register")
    Call<RegistrationResponse> registerEmail(@Body RegistrationRequest registrationRequest);

    // (Keep your other endpoints as before)
    @POST("email/verify-otp")
    Call<Boolean> verifyOtp(@Body com.w3itexperts.ombe.apimodals.OtpVerificationRequest otpRequest);


    @PUT("/eateries/find-eateries")
    Call<List<eateries>> findEateries(@Body locationDTO locationdto);

//    @GET("/eateries/get-images/{eateryId}")
//    Call<List<byte[]>> getEateryImages(@Path("eateryId") String eateryId);

    @GET("/eateries/get-images/{eateryId}")
    Call<List<String>> getEateryImages(@Path("eateryId") String eateryId);

    @POST("/users-sessions-eateries/user-vote")
    Call<Void> sendUserVote(@Body userVoteDTO vote);

    @GET("users-sessions-eateries/get-users-votes-by-eateryId/{id}")
    Call<List<userVoteDTO>> getUsersVotesByEateryId(@Path("id") String eateryId);

    // for leaderboard
    @GET("/users-sessions-eateries/get-users-votes-by-sessionId/{sessionId}")
    Call<List<userVoteDTO>> getUsersVotesBySessionId(@Path("sessionId") int sessionId);

    // for done swiping users
    @GET("users-sessions-eateries/get-finished-users/{sessionId}")
    Call<List<usersDTO>> getFinishedUsers(@Path("sessionId") int sessionId);
}
