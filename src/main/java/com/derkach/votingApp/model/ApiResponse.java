package com.derkach.votingApp.model;

import com.fasterxml.jackson.annotation.JsonView;

public class ApiResponse<T> {

    @JsonView
    private T success;

    @JsonView
    private ApiError failure;

    public ApiResponse(T success, ApiError failure) {
        this.success = success;
        this.failure = failure;
    }

    public T getSuccess() {
        return success;
    }

    public void setSuccess(T success) {
        this.success = success;
    }

    public static class ApiError {
        private String message;

        public ApiError() {
        }

        public ApiError(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
