package com.example.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class GenUI implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(new HrLogin());
	}
}
