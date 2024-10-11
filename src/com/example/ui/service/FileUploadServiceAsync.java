package com.example.ui.service;

import com.example.ui.domain.files;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileUploadServiceAsync {
	void uploadFile(String fileName,AsyncCallback<files> callback); 
}
