package com.haulmont.testtask;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.*;
import com.haulmont.testtask.forms.NewDoctorWindow;
import com.haulmont.testtask.forms.NewPatientWindow;
import com.haulmont.testtask.forms.NewPrescriptionWindow;
import com.haulmont.testtask.models.DoctorModel;
import com.haulmont.testtask.models.Model;
import com.haulmont.testtask.models.PatientModel;
import com.haulmont.testtask.models.PrescriptionModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.print.Doc;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private Controller<Model, Patient> controller;
    private Grid grid;
    private TextField filterByDescription;
    private NativeSelect filterByPriority;
    private TextField filterByPatient;
    private Button clearFiltersButton;
    private Button addEntityButton;
    HorizontalLayout headLayout;
    Button patientButton;
    Button doctorButton;
    Button prescriptionButton;
    List<Patient> patients;
    List<Doctor> doctors;
    List<Prescription> prescription;
    BeanItemContainer<Entity> container;
    Button addPatientButton;
    Button addDoctorButton;
    Button addPrescriptionButton;
    HorizontalLayout filterToolbar;
    HorizontalLayout addEntityLayout;

    @Override
    protected void init(VaadinRequest request) {
        initHeadLayout();
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        grid = new Grid();
        preInit();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        filterByDescription = new TextField("Filter by description...");
        filterByPriority = new NativeSelect("Select priority filter...");
        filterByPriority.addItems(Priority.values());
        filterByPatient = new TextField("Filter by patient");
        clearFiltersButton = new Button("Clear filters");
        clearFiltersButton.addClickListener(e -> {
            filterByDescription.clear();
            filterByPriority.clear();
            filterByPatient.clear();
        });
        grid.addItemClickListener(e -> {
//            if (e.getItem() != null) {
//                NewPatientWindow entityWindow =
//                        new NewPatientWindow(this, "Patient");
//                entityWindow.setPatient((Patient)((BeanItem)e.getItem()).getBean());
//                entityWindow.setDeleteDisable(false);
//                entityWindow.setCreateNewForm(false);
//                addWindow(entityWindow);
//            }
            if (e.getItem() != null) {
                Entity entity = (Entity)((BeanItem)e.getItem()).getBean();
                if (entity instanceof Patient) {
                    NewPatientWindow entityWindow =
                            new NewPatientWindow(this, "Patient");
                    entityWindow.setPatient((Patient)entity);
                    entityWindow.setDeleteDisable(false);
                    entityWindow.setCreateNewForm(false);
                    addWindow(entityWindow);
                } else if (entity instanceof Doctor) {
                    NewDoctorWindow entityWindow =
                            new NewDoctorWindow(this, "Doctor");
                    entityWindow.setDoctor((Doctor)entity);
                    entityWindow.setDeleteDisable(false);
                    entityWindow.setCreateNewForm(false);
                    addWindow(entityWindow);
                } else if (entity instanceof Prescription) {
                    NewPrescriptionWindow entityWindow =
                            new NewPrescriptionWindow(this, "Prescription");
                    entityWindow.setPrescription((Prescription) entity);
                    entityWindow.setDeleteDisable(false);
                    entityWindow.setCreateNewForm(false);
                    addWindow(entityWindow);
                }
            }
        });
        filterToolbar = new HorizontalLayout(filterByDescription, filterByPriority,
                filterByPatient, clearFiltersButton);
        addEntityLayout = new HorizontalLayout(addPatientButton);
        filterToolbar.setSpacing(true);
        initPatientPage();

        HorizontalLayout gridLayout = new HorizontalLayout(grid);
        gridLayout.setSizeFull();
        grid.setSizeFull();
        gridLayout.setExpandRatio(grid, 1);
        layout.addComponents(headLayout, addEntityLayout, filterToolbar, gridLayout);
        layout.setSpacing(true);
        setContent(layout);
    }

    public void updatePatientList() {
        Controller<PatientModel, Patient> patController = new Controller<>(new PatientModel());
        List<Patient> patients = patController.getAll();
        BeanItemContainer<Patient> container =
                new BeanItemContainer<Patient>(Patient.class, patients);
        grid.removeAllColumns();
        grid.setContainerDataSource(container);
    }

    public void updateDoctorList() {
        Controller<DoctorModel, Doctor> docController = new Controller<>(new DoctorModel());
        List<Doctor> doctors = docController.getAll();
        BeanItemContainer<Doctor> container =
                new BeanItemContainer<Doctor>(Doctor.class, doctors);
        grid.removeAllColumns();
        grid.setContainerDataSource(container);
    }

    public void updatePrescriptionList() {
        Controller<PrescriptionModel, Prescription> prescController =
                new Controller<>(new PrescriptionModel());
        List<Prescription> prescriptions = prescController.getAll();
        BeanItemContainer<Prescription> container =
                new BeanItemContainer<Prescription>(Prescription.class, prescriptions);
        grid.removeAllColumns();
        grid.setContainerDataSource(container);
    }

    private void initHeadLayout() {
        headLayout = new HorizontalLayout();
        patientButton = new Button("Patients");
        doctorButton = new Button("Doctors");
        prescriptionButton = new Button("Prescriptions");
        patientButton.setSizeFull();
        doctorButton.setSizeFull();
        prescriptionButton.setSizeFull();
        patientButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        doctorButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        prescriptionButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        headLayout.addComponents(patientButton, doctorButton, prescriptionButton);
        headLayout.setSizeFull();
        headLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }

    private void initPatientPage() {
        changeDisableButton(patientButton);
//        addEntityButton = addPatientButton;
        addEntityLayout.removeAllComponents();
        addEntityLayout.addComponent(addPatientButton);
        filterToolbar.setVisible(false);
        updatePatientList();
    }

    private void initDoctorPage() {
        changeDisableButton(doctorButton);
        addEntityLayout.removeAllComponents();
        addEntityLayout.addComponent(addDoctorButton);
        filterToolbar.setVisible(false);
        updateDoctorList();
    }

    private void initPrescriptionPage() {
        changeDisableButton(prescriptionButton);
        addEntityLayout.removeAllComponents();
        addEntityLayout.addComponent(addPrescriptionButton);
        filterToolbar.setVisible(true);
        updatePrescriptionList();
    }

    private void preInit() {
        addPatientButton = new Button("Add patient button");
        addPatientButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addPatientButton.addClickListener(e -> {
            grid.deselectAll();
            NewPatientWindow entityWindow =
                    new NewPatientWindow(this, "New patient");
            entityWindow.setDeleteDisable(true);
            entityWindow.setCreateNewForm(true);
            addWindow(entityWindow);
        });
        addDoctorButton = new Button("Add doctor button");
        addDoctorButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addDoctorButton.addClickListener(e -> {
            grid.deselectAll();
            NewDoctorWindow entityWindow =
                    new NewDoctorWindow(this, "New doctor");
            entityWindow.setDeleteDisable(true);
            entityWindow.setCreateNewForm(true);
            addWindow(entityWindow);
        });
        addPrescriptionButton = new Button("Add prescription button");
        addPrescriptionButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addPrescriptionButton.addClickListener(e -> {
            grid.deselectAll();
            NewPrescriptionWindow entityWindow =
                    new NewPrescriptionWindow(this, "New prescription");
            entityWindow.setDeleteDisable(true);
            entityWindow.setCreateNewForm(true);
            addWindow(entityWindow);
        });
        patientButton.addClickListener(e -> {
            changeDisableButton(patientButton);
            initPatientPage();
//            grid.addItemClickListener(f -> {
//                if (f.getItem() != null) {
//                    NewPatientWindow entityWindow =
//                            new NewPatientWindow(this, "Patient");
//                    entityWindow.setPatient((Patient)((BeanItem)f.getItem()).getBean());
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                }
//            });
        });
        doctorButton.addClickListener(e -> {
            changeDisableButton(doctorButton);
            initDoctorPage();
//            grid.addItemClickListener(f -> {
//                if (f.getItem() != null) {
//                    NewDoctorWindow entityWindow =
//                            new NewDoctorWindow(this, "Doctor");
//                    entityWindow.setDoctor((Doctor)((BeanItem)f.getItem()).getBean());
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                }
//            });
        });
        prescriptionButton.addClickListener(e -> {
            changeDisableButton(prescriptionButton);
            initPrescriptionPage();
//            grid.addItemClickListener(f -> {
//                if (f.getItem() != null) {
//                    NewPrescriptionWindow entityWindow =
//                            new NewPrescriptionWindow(this, "Prescription");
//                    entityWindow.setPrescription((Prescription) ((BeanItem)f.getItem()).getBean());
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                }
//            });
        });
    }

    private void changeDisableButton(Button button) {
        patientButton.setEnabled(true);
        doctorButton.setEnabled(true);
        prescriptionButton.setEnabled(true);
        if (button == patientButton)
            patientButton.setEnabled(false);
        else if (button == doctorButton)
            doctorButton.setEnabled(false);
        else if (button == prescriptionButton)
            prescriptionButton.setEnabled(false);
    }
}