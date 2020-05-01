package com.kashyapmedia.moviesdemo.repository;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.kashyapmedia.moviesdemo.AppExecutors;
import com.kashyapmedia.moviesdemo.api.responses.ApiEmptyResponse;
import com.kashyapmedia.moviesdemo.api.responses.ApiErrorResponse;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;
import com.kashyapmedia.moviesdemo.api.responses.ApiSuccessResponse;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NetworkBoundResource";
    AppExecutors appExecutors;

    private MediatorLiveData<Resource<ResultType>> result =new MediatorLiveData<>();


    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors=appExecutors;
        result.setValue(Resource.loading(null)) ;

        //@Suppress("LeakingThis")
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType resultType) {
                            setValue(Resource.success(resultType));
                        }
                    });
                }
            }
        });

    }


    @MainThread
    private void setValue( Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                setValue(Resource.loading(newData));
            }
        });

        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                if(response instanceof ApiSuccessResponse){
                    appExecutors.getDiskIO().execute(() -> {
                        saveCallResult(processResponse((ApiSuccessResponse<RequestType>)response));
                        appExecutors.getMainThread().execute(() -> {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(), new Observer<ResultType>() {
                                @Override
                                public void onChanged(ResultType newData) {
                                    setValue(Resource.success(newData));
                                }
                            });
                        });
                    });
                }else if(response instanceof ApiEmptyResponse){
                    appExecutors.getMainThread().execute(()-> {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb(), new Observer<ResultType>() {
                            @Override
                            public void onChanged(ResultType newData) {
                                setValue(Resource.success(newData));
                            }
                        });
                    });
                }else if(response instanceof ApiErrorResponse){
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            setValue(Resource.error(((ApiErrorResponse)response).getErrorMessage(), newData));
                        }
                    }) ;
                }
            }
        });
    }


    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @WorkerThread
    protected RequestType processResponse(ApiSuccessResponse<RequestType> response ) {
        return response.getBody();
    }

    protected void onFetchFailed() {}

    @WorkerThread
    protected abstract void saveCallResult( RequestType item);

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
