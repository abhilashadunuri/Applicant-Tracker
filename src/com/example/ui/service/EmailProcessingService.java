package com.example.ui.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("auth")
public interface EmailProcessingService extends RemoteService {
	void uploadFile(String filecontent);
}
