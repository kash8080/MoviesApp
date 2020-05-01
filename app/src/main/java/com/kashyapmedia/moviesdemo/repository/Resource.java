package com.kashyapmedia.moviesdemo.repository;


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
public class Resource<T> {

    private Status status;
    private T data;
    private String message;

    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<T>(Status.SUCCESS, data, null);
    }
    public static <T> Resource<T> error(String msg,T data) {
        return new Resource<T>(Status.ERROR, data, msg);
    }
    public static <T> Resource<T> loading(T data) {
        return new Resource<T>(Status.LOADING, data, null);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Status of a resource that is provided to the UI.
     *
     *
     * These are usually created by the Repository classes where they return
     * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
     */
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}



