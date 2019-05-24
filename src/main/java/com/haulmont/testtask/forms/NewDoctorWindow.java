package com.haulmont.testtask.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.models.DoctorModel;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class NewDoctorWindow extends Window {
    private FormLayout layout = new FormLayout();
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField middleName = new TextField("Middle name");
    private TextField specialization = new TextField("Specialization");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private boolean isNewForm = false;

    private Controller<DoctorModel, Doctor> controller = new Controller<>(new DoctorModel());
    private Doctor doctor;
    private MainUI mainUI;

    public NewDoctorWindow(MainUI mainUI, String caption) {
        super(caption);
        this.mainUI = mainUI;
        setModal(true);
        setResizable(false);
        center();
        layout.setMargin(true);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        layout.addComponents(firstName, lastName, middleName, specialization, buttons);
        setContent(layout);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setDeleteDisable(boolean boo) {
        delete.setVisible(!boo);
    }

    public void setCreateNewForm(boolean boo) {
        isNewForm = boo;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        firstName.setValue(doctor.getName());
        lastName.setValue(doctor.getSecname());
        middleName.setValue(doctor.getOtch());
        specialization.setValue(doctor.getSpec());
        firstName.selectAll();
    }

    private void delete() {
        controller.deleteOne(doctor.getId());
        mainUI.updateDoctorList();
        this.close();
    }

    private void save() {
        if (isNewForm) {
            doctor = new Doctor(-1, firstName.getValue(), lastName.getValue(),
                    middleName.getValue(), specialization.getValue());
            controller.addOne(doctor);
        } else {
            doctor = new Doctor(doctor.getId(), firstName.getValue(), lastName.getValue(),
                    middleName.getValue(), specialization.getValue());
            controller.updateOne(doctor);
        }
        mainUI.updateDoctorList();
        this.close();
    }
}
