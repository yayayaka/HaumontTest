package com.haulmont.testtask.forms;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.models.DoctorModel;
import com.haulmont.testtask.models.PatientModel;
import com.haulmont.testtask.models.PrescriptionModel;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class NewPrescriptionWindow extends Window {
    private FormLayout layout = new FormLayout();
    private TextField description = new TextField("Description");
    private NativeSelect patient = new NativeSelect("Patient");
    private NativeSelect doctor = new NativeSelect("Doctor");
    private DateField creationDate = new DateField("Creation Date");
    private DateField expireDate = new DateField("Expire Date");
    private NativeSelect priority = new NativeSelect("Priority");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private boolean isNewForm = false;

    private Controller<PrescriptionModel, Prescription> prescrController =
            new Controller<>(new PrescriptionModel());
    private Controller<PatientModel, Patient> patientController =
            new Controller<>(new PatientModel());
    private Controller<DoctorModel, Doctor> doctorController =
            new Controller<>(new DoctorModel());
    private Prescription prescription;
    private MainUI mainUI;
//    private Binder<Patient> binder = new Binder<>(Patient.class);
//    BeanFieldGroup<Patient> binder = new BeanFieldGroup<>(Patient.class);

    public NewPrescriptionWindow(MainUI mainUI, String caption) {
        super(caption);
        this.mainUI = mainUI;
        setModal(true);
        setResizable(false);
        center();
        layout.setMargin(true);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        layout.addComponents(description, patient, doctor, creationDate, expireDate, priority, buttons);
        ArrayList<String> patientsFIOs = new ArrayList<>();
        for (Patient patientItem : patientController.getAll()) {
            patientsFIOs.add(patientItem.getSecname() + " " +
                    patientItem.getName() + " " +
                    patientItem.getOtch());
        }
        patient.addItems(patientsFIOs);
        ArrayList<String> docFIOs = new ArrayList<>();
        for (Doctor docItem : doctorController.getAll()) {
            docFIOs.add(docItem.getSecname() + " " +
                    docItem.getName() + " " +
                    docItem.getOtch());
        }
        doctor.addItems(docFIOs);
        priority.addItems(Priority.values());

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

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
//        binder.setBean(prescription);
        description.setValue(prescription.getDescription());

//        patient.setValue(prescription.getPatient().getSecname() + prescription.getPatient().getName() +
//                prescription.getPatient().getOtch());
//        doctor.setValue(prescription.getDoctor().getSecname() + prescription.getDoctor().getName() +
//                prescription.getDoctor().getOtch());
        doctor.addItems(doctorController.getAll());
        creationDate.setValue(Date.valueOf(prescription.getCreateDate()));
        expireDate.setValue(Date.valueOf(prescription.getExpireDate()));
        description.selectAll();
    }

    private void delete() {
        prescrController.deleteOne(prescription.getId());
        mainUI.updatePrescriptionList();
        this.close();
    }

    private void save() {
        if (isNewForm) {
            try {
                String[] patFIO = ((String)patient.getValue()).split(" ");
                Patient selectedPatient = patientController.searchByFields(
                        new Patient(-1, patFIO[1], patFIO[0], patFIO[2], -1));
                String[] docFIO = ((String)doctor.getValue()).split(" ");
                Doctor selectedDoctor = doctorController.searchByFields(
                        new Doctor(-1, docFIO[1], docFIO[0], docFIO[2], "")) ;
                prescription = new Prescription(-1, description.getValue(),
                        selectedPatient,
                        selectedDoctor,
                        creationDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        expireDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        (Priority) priority.getValue()
                        );
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
//            prescription = new Patient(prescription.getId(), description.getValue(), lastName.getValue(),
//                    middleName.getValue(), Integer.valueOf(phone.getValue()));
//            prescrController.updateOne(prescription);
        }
        mainUI.updatePrescriptionList();
        this.close();
    }
}
