package com.haulmont.testtask.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.models.PatientModel;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class NewPatientWindow extends Window {
    private FormLayout layout = new FormLayout();
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField middleName = new TextField("Middle name");
    private TextField phone = new TextField("Phone No");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private boolean isNewForm = false;

    private Controller<PatientModel, Patient> controller =
            new Controller<>(new PatientModel());
    private Patient patient;
    private MainUI mainUI;
//    private Binder<Patient> binder = new Binder<>(Patient.class);
//    BeanFieldGroup<Patient> binder = new BeanFieldGroup<>(Patient.class);
//    FieldGroup group = new FieldGroup();

    public NewPatientWindow(MainUI mainUI, String caption) {
        super(caption);
//        binder.setItemDataSource(patient);
        this.mainUI = mainUI;
        setModal(true);
        setResizable(false);
        center();
        layout.setMargin(true);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        layout.addComponents(firstName, lastName, middleName, phone, buttons);
        setContent(layout);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

//        binder.bindInstanceFields(this);
//        binder.bindMemberFields(this);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setDeleteDisable(boolean boo) {
        delete.setVisible(!boo);
    }

    public void setCreateNewForm(boolean boo) {
        isNewForm = boo;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
//        binder.setBean(patient);
//        binder.setItemDataSource(patient);
        firstName.setValue(patient.getName());
        lastName.setValue(patient.getSecname());
        middleName.setValue(patient.getOtch());
        phone.setValue(String.valueOf(patient.getPhoneNo()));
        firstName.selectAll();
    }

    private void delete() {
        controller.deleteOne(patient.getId());
        mainUI.updatePatientList();
        this.close();
    }

    private void save() {
        if (isNewForm) {
            try {
                patient = new Patient(-1, firstName.getValue(), lastName.getValue(),
                        middleName.getValue(), Integer.valueOf(phone.getValue()));
                controller.addOne(patient);
            } catch (NumberFormatException e) {
                Window alertWindow = new Window("Warning");
                alertWindow.setResizable(false);
                alertWindow.setModal(true);
                alertWindow.setContent(new Label("Invalid phone value"));
                mainUI.addWindow(alertWindow);
            }
        } else {
            patient = new Patient(patient.getId(), firstName.getValue(), lastName.getValue(),
                    middleName.getValue(), Integer.valueOf(phone.getValue()));
            controller.updateOne(patient);
        }
        mainUI.updatePatientList();
        this.close();
    }
}
