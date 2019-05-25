package com.haulmont.testtask.forms;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class AlertWindow extends Window {
    public AlertWindow(String message) {
        setModal(true);
        center();
        Label label = new Label(message);
        HorizontalLayout layout = new HorizontalLayout(label);
        layout.setWidth("300");
        layout.setHeight("100");
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }
}
