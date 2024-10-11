package com.example.ui.service;

import com.example.ui.domain.files;
import com.google.gwt.user.client.rpc.RemoteService;

public interface FileUploadService extends RemoteService{
	files uploadFile(String fileName);
}
