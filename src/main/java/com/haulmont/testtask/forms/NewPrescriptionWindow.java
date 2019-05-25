package com.haulmont.testtask.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.*;
import com.haulmont.testtask.models.DoctorModel;
import com.haulmont.testtask.models.PatientModel;
import com.haulmont.testtask.models.PrescriptionModel;
import com.haulmont.testtask.models.PriorityModel;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.print.Doc;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;

public class NewPrescriptionWindow extends NewEntityWindow {
    private FormLayout layout = new FormLayout();
    private TextField description = new TextField("Description");
    private NativeSelect patient = new NativeSelect("Patient");
    private NativeSelect doctor = new NativeSelect("Doctor");
    private DateField creationDate = new DateField("Creation Date");
    private DateField expireDate = new DateField("Expire Date");
    private NativeSelect priority = new NativeSelect("PrioritySelect");
    private Button save = new Button("OK");
    private Button cancel = new Button("Cancel");
    private boolean isNewForm = false;

    private Controller<PrescriptionModel, Prescription> prescrController =
            new Controller<>(new PrescriptionModel());
    private Controller<PatientModel, Patient> patientController =
            new Controller<>(new PatientModel());
    private Controller<DoctorModel, Doctor> doctorController =
            new Controller<>(new DoctorModel());
    private Controller<PriorityModel, Priority> priorityController =
            new Controller<>(new PriorityModel());
    private Prescription prescription;
    private MainUI mainUI;
//    private Binder<Patient> binder = new Binder<>(Patient.class);
//    BeanFieldGroup<Patient> binder = new BeanFieldGroup<>(Patient.class);

    public NewPrescriptionWindow(MainUI mainUI, String caption) {
        super(mainUI, caption);
        description.addValidator(new StringLengthValidator(
                "Длина поля не должна превышать 20 символов",
                1, 20, false));
        patient.addValidator(new NullValidator(
                "Значение не выбрано", false));
        doctor.addValidator(new NullValidator(
                "Значение не выбрано", false));
        creationDate.addValidator(new DateRangeValidator(
                "Неверное значение даты", new java.util.Date(0L),
                new java.util.Date(Long.MAX_VALUE), Resolution.DAY));
        expireDate.addValidator(new DateRangeValidator(
                "Неверное значение даты", new java.util.Date(0L),
                new java.util.Date(Long.MAX_VALUE), Resolution.DAY));
        priority.addValidator(new NullValidator(
                "Значение не выбрано", false));
        this.mainUI = mainUI;
        setModal(true);
        setResizable(false);
        center();
        layout.setMargin(true);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        layout.addComponents(description, patient, doctor, creationDate,
                expireDate, priority, buttons);
//        ArrayList<String> patientsFIOs = new ArrayList<>();
//        for (Patient patientItem : patientController.getAll()) {
//            patientsFIOs.add(patientItem.getSecname() + " " +
//                    patientItem.getName() + " " +
//                    patientItem.getOtch());
//        }
//        patient.addItems(patientsFIOs);
        patient.addItems(patientController.getAll());
//        ArrayList<String> docFIOs = new ArrayList<>();
//        for (Doctor docItem : doctorController.getAll()) {
//            docFIOs.add(docItem.getSecname() + " " +
//                    docItem.getName() + " " +
//                    docItem.getOtch());
//        }
//        doctor.addItems(docFIOs);
        doctor.addItems(doctorController.getAll());
        priority.addItems(priorityController.getAll());


        setContent(layout);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

//        binder.bindInstanceFields(this);
//        binder.bindMemberFields(this);

        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
    }

//    public void setDeleteDisable(boolean boo) {
//        delete.setVisible(!boo);
//    }

    public void setCreateNewForm(boolean boo) {
        isNewForm = boo;
    }

    public void setEntity(Entity entity) {
        Prescription prescription = (Prescription) entity;
        this.prescription = prescription;
//        binder.setBean(prescription);
        description.setValue(prescription.getDescription());
        if (!isNewForm) {
            patient.select(prescription.getPatient());
            doctor.select(prescription.getDoctor());
            priority.select(prescription.getPriority());
        }
//        patient.setValue(prescription.getPatient().getSecname() + prescription.getPatient().getName() +
//                prescription.getPatient().getOtch());
//        doctor.setValue(prescription.getDoctor().getSecname() + prescription.getDoctor().getName() +
//                prescription.getDoctor().getOtch());
//        doctor.addItems(doctorController.getAll());
        creationDate.setValue(Date.valueOf(prescription.getCreateDate()));
        expireDate.setValue(Date.valueOf(prescription.getExpireDate()));
        description.selectAll();
    }

    private void cancel() {
//        prescrController.deleteOne(prescription.getId());
//        mainUI.updatePrescriptionList();
//        mainUI.updateEntityList(Entity.PRESCRIPTION);
        this.close();
    }

    private void save() {
        if (isNewForm) {
            try {
//                String[] patFIO = ((String)patient.getValue()).split(" ");
//                Patient selectedPatient = patientController.searchByFields(
//                        new Patient(-1, patFIO[1], patFIO[0], patFIO[2], -1));
//                String[] docFIO = ((String)doctor.getValue()).split(" ");
//                Doctor selectedDoctor = doctorController.searchByFields(
//                        new Doctor(-1, docFIO[1], docFIO[0], docFIO[2], "")) ;
//                Priority selectedPriority = priorityController.searchByFields(new Priority(-1,
//                        (PrioritySelect) priority.getValue()));
                prescription = new Prescription(-1, description.getValue(),
                        (Patient) patient.getValue(),
                        (Doctor) doctor.getValue(),
                        ((java.util.Date)creationDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        ((java.util.Date)expireDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        (Priority) priority.getValue());
                prescrController.addOne(prescription);
//                prescription = new Prescription(-1, description.getValue(), patientController.,
//                        middleName.getValue(), Integer.valueOf(phone.getValue()));
//                prescrController.addOne(prescription);
            } catch (NumberFormatException e) {
                Window alertWindow = new Window("Warning");
                alertWindow.setResizable(false);
                alertWindow.setModal(true);
                alertWindow.setContent(new Label("Invalid phone value"));
                mainUI.addWindow(alertWindow);
            }
        } else {
            prescription = new Prescription(prescription.getId(), description.getValue(),
                    (Patient)patient.getValue(), (Doctor)doctor.getValue(),
                    ((Date)creationDate.getValue()).toLocalDate(),
                    ((Date)expireDate.getValue()).toLocalDate(),
                    (Priority)priority.getValue());
            prescrController.updateOne(prescription);
//            prescription = new Patient(prescription.getId(), description.getValue(), lastName.getValue(),
//                    middleName.getValue(), Integer.valueOf(phone.getValue()));
//            prescrController.updateOne(prescription);
        }
//        mainUI.updatePrescriptionList();
        mainUI.updateEntityList(Entity.PRESCRIPTION);
        this.close();
    }
}
