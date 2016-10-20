package com.sqisoft.remote.data;

public interface ResponseListener<U> {
    void response(boolean success, U data);
}
