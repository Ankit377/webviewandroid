package com.devparadigam.agrade.apiservices;

import com.devparadigam.agrade.model.response.ResultDetailsResponse;
import com.devparadigam.agrade.model.response.ResultModel;
import com.devparadigam.agrade.model.response.SaveResultModel;
import com.devparadigam.agrade.model.response.StudyMetarialModel;
import com.devparadigam.agrade.model.response.TestHistoryModel;
import com.devparadigam.agrade.model.response.ExaminationModel;
import com.devparadigam.agrade.model.response.ItemCategoryModel;
import com.devparadigam.agrade.model.response.PaperModel;
import com.devparadigam.agrade.model.response.TestModel;
import com.devparadigam.agrade.model.response.TransactionModel;
import com.devparadigam.agrade.model.response.UserData;
import com.devparadigam.agrade.model.response.UserDetails;
import com.devparadigam.agrade.model.response.UserProfileDetails;
import com.devparadigam.agrade.model.response.youtube.YoutubePlaylist;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {

    @FormUrlEncoded
    @POST("api/farmer_register")
    Single<JsonObjectResponse<UserDetails>> login(
            @Field("name") String username,
            @Field("email") String email,
            @Field("mobile_no") String mobile,
            @Field("address") String address,
            @Field("password") String password,
            @Field("c_password") String c_password);

    @FormUrlEncoded
    @POST("register")
    Single<JsonObjectResponse<UserProfileDetails>> userLogin(
            @Field("name") String username,
            @Field("account_type") String account_type,
            @Field("social_id") String social_id,
            @Field("device_id") String device_id,
            @Field("email") String email,
            @Field("mobile_no") String mobile_no);

    @FormUrlEncoded
    @POST("test")
    Single<JsonObjectResponse<InnerObjectResponse<TestModel>>> getTest(@Header("Authorization") String token,
                                                                       @Field("paper_id") String paper_id,
                                                                       @Field("language") String language
    );

    @FormUrlEncoded
    @POST("test/save_exam_result")
    Single<JsonObjectResponse<SaveResultModel>> saveTest(@Header("Authorization") String token,
                                                         @Field("paper_id") String paper_id,
                                                         @Field("test_marks") float test_marks,
                                                         @Field("total_time") String total_time,
                                                         @Field("total_attempt") int total_attempt,
                                                         @Field("not_attempt") int not_attempt,
                                                         @Field("bookmark") int bookmark,
                                                         @Field("not_visited") int not_visited,
                                                         @Field("total_marks") float total_marks,
                                                         @Field("correct_answer") int correct_answer,
                                                         @Field("incorrect_answer") int incorrect_answer,
                                                         @Field("response") String response
    );


    @GET("register/check_registration")
    Single<JsonObjectResponse<UserProfileDetails>> checkRegistration(@Query("mobile_no") String mobile_no);

    @GET("user/get_profile")
    Single<JsonObjectResponse<UserProfileDetails>> getProfile(@Header("Authorization") String token);

    @GET("home/examination")
    Single<JsonArrayResponse<ExaminationModel>> getExamType(
            @Header("Authorization") String token,
            @Query("exam_type") String exam_type);

    @GET("home/get_paper_by_exam_id")
    Single<JsonArrayResponse<PaperModel>> getPaper(
            @Header("Authorization") String token,
            @Query("exam_id") String exam_id);

    @FormUrlEncoded
    @POST("api/socialLogin")
    Single<JsonObjectResponse<UserData>> Sociallogin(
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("mobile_no") String mobile,
            @Field("login_type") String login_type,
            @Field("device_id") String device_id);


    @GET("api/categories-list")
    Single<JsonArrayResponse<ItemCategoryModel>> getCategory(
            @Header("Authorization") String token);

    @GET("playlistItems?part=snippet%2CcontentDetails&channelId=UCqwUrj10mAEsqezcItqvwEw")
    Single<YoutubePlaylist> getVideoList(@Query("key") String key,
                                         @Query("playlistId") String playlistId);

    @GET("test/test_history")
    Single<JsonArrayResponse<TestHistoryModel>> testHistory(
            @Header("Authorization") String token);

    @GET("study_material")
    Single<JsonArrayResponse<StudyMetarialModel>> getStudyMetarial(
            @Header("Authorization") String token);

        @GET("test/get_result")
        Single<JsonObjectResponse<ResultModel>> getResult(
                @Header("Authorization") String token,
                @Query("result_id") String result_id);

    @GET("test/get_result_detail")
    Single<JsonObjectResponse<ResultDetailsResponse>> get_result_detail(
            @Header("Authorization") String token,
            @Query("result_id") String result_id);

    @Multipart
    @POST("user/update_profile_pic")
    Single<JsonObjectResponse<String>> setProfilePhoto(
            @Header("Authorization") String token,
            @Part MultipartBody.Part profile_image);

    @FormUrlEncoded
    @POST("transaction")
    Single<JsonObjectResponse<TransactionModel>> getTransaction(
            @Header("Authorization") String token,
            @Field("amount") String amount,
            @Field("exam_id") String exam_id,
            @Field("trans_id") String trans_id,
            @Field("response") String response);


}


