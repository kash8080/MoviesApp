package com.kashyapmedia.moviesdemo.api.responses;

import java.io.IOException;

import retrofit2.Response;

public abstract class ApiResponse<T> {

    public static <T> ApiResponse<T> create(Throwable error){
        String s=error.getMessage();
        return new ApiErrorResponse<T>((s!=null && s.length()>0)?s:"unknown error");
    }

    public static <T> ApiResponse<T> create(Response<T> response){
        if(response.isSuccessful()){
            T body=response.body();
            if(body==null || response.code()==204){
                return new ApiEmptyResponse<T>();
            }else{
                return new ApiSuccessResponse<>(body);
            }
        }else{
            String msg=null;
            try {
                msg=response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(msg==null || msg.length()==0){
                msg=response.message();
            }
            if(msg==null || msg.length()==0){
                msg="unknown error";
            }
            return new ApiErrorResponse<>(msg);

        }
    }
}
