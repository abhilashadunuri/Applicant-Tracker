package com.example.ui.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmailProcessingServiceAsync {
    void uploadFile(String fileContent, AsyncCallback<Void> callback);
}
