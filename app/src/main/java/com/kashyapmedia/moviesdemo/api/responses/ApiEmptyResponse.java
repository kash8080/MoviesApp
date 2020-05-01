package com.kashyapmedia.moviesdemo.api.responses;


/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
public class ApiEmptyResponse<T> extends ApiResponse<T> {
}
