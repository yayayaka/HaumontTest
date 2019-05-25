package com.haulmont.testtask;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.*;
import com.haulmont.testtask.forms.NewDoctorWindow;
import com.haulmont.testtask.forms.NewEntityWindow;
import com.haulmont.testtask.forms.NewPatientWindow;
import com.haulmont.testtask.forms.NewPrescriptionWindow;
import com.haulmont.testtask.models.*;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

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
    Button editPatientButton;
    Button editDoctorButton;
    Button editPrescriptionButton;
    Button delPatientButton;
    Button delDoctorButton;
    Button delPrescriptionButton;
    Button showDoctorStatisticsButton;
    Button hideDoctorStatisticsButton;
    Button applyFilter;
    HorizontalLayout filterToolbar;
    HorizontalLayout addEntityLayout;

    @Override
    protected void init(VaadinRequest request) {
        initHeadLayout();
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        grid = new Grid();
        preInitAddEditDelButtons();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        filterByDescription = new TextField("Filter by description...");
        filterByPriority = new NativeSelect("Select priority filter...");
        filterByPriority.addItems(PrioritySelect.values());
//        filterByDescription.addTextChangeListener(e -> {
////            updatePrescriptionList();
//            updateEntityList(Entity.PRESCRIPTION);
//        });
        filterByPatient = new TextField("Filter by patient");
//        filterByPatient.addTextChangeListener(e -> {
////            updatePrescriptionList();
//            updateEntityList(Entity.PRESCRIPTION);
//        });
//        filterByPatient.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
//        filterByDescription.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
//        filterByPriority.addValueChangeListener(e -> {
////            updatePrescriptionList();
//            updateEntityList(Entity.PRESCRIPTION);
//        });
        clearFiltersButton = new Button("Clear filters");
        clearFiltersButton.setStyleName(ValoTheme.BUTTON_DANGER);
        clearFiltersButton.addClickListener(e -> {
            filterByDescription.clear();
            filterByPriority.clear();
            filterByPatient.clear();
            updateEntityList(Entity.PRESCRIPTION);
        });
        applyFilter = new Button("Apply filters");
        applyFilter.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        applyFilter.addClickListener(e -> {
            updateEntityList(Entity.PRESCRIPTION);
        });
//        grid.addItemClickListener(e -> {
//            if (e.getItem() != null) {
//                Entity entity = (Entity)((BeanItem)e.getItem()).getBean();
//                if (entity instanceof Patient) {
//                    NewPatientWindow entityWindow =
//                            new NewPatientWindow(this, "Patient");
//                    entityWindow.setPatient((Patient)entity);
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                } else if (entity instanceof Doctor) {
//                    NewDoctorWindow entityWindow =
//                            new NewDoctorWindow(this, "Doctor");
//                    entityWindow.setEntity((Doctor)entity);
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                } else if (entity instanceof Prescription) {
//                    NewPrescriptionWindow entityWindow =
//                            new NewPrescriptionWindow(this, "Prescription");
//                    entityWindow.setPrescription((Prescription) entity);
//                    entityWindow.setDeleteDisable(false);
//                    entityWindow.setCreateNewForm(false);
//                    addWindow(entityWindow);
//                }
//            }
//        });
        filterToolbar = new HorizontalLayout(filterByDescription, filterByPriority,
                filterByPatient, applyFilter, clearFiltersButton);
        addEntityLayout = new HorizontalLayout(addPatientButton);
        filterToolbar.setSpacing(true);
        initEntityPage(Entity.PATIENT);

        HorizontalLayout gridLayout = new HorizontalLayout(grid);
        gridLayout.setSizeFull();
        grid.setSizeFull();
        gridLayout.setExpandRatio(grid, 1);
        layout.addComponents(headLayout, addEntityLayout, filterToolbar, gridLayout);
        layout.setSpacing(true);
        setContent(layout);
    }

    public void updateEntityList(String entity) {
        grid.removeAllColumns();
        switch (entity) {
            case Entity.PATIENT :
                Controller<PatientModel, Patient> patController = new Controller<>(new PatientModel());
                List<Patient> patients = patController.getAll();
                BeanItemContainer<Patient> patContainer =
                        new BeanItemContainer<Patient>(Patient.class, patients);
//                grid.removeAllColumns();
                grid.setContainerDataSource(patContainer);
                break;
            case Entity.DOCTOR :
                Controller<DoctorModel, Doctor> docController = new Controller<>(new DoctorModel());
                List<Doctor> doctors = docController.getAll();
                BeanItemContainer<Doctor> docContainer =
                        new BeanItemContainer<Doctor>(Doctor.class, doctors);
//                grid.removeAllColumns();
                grid.setContainerDataSource(docContainer);
                break;
            case Entity.PRESCRIPTION :
                Controller<PrescriptionModel, Prescription> prescController =
                        new Controller<>(new PrescriptionModel());
//                Controller<PriorityModel, Priority> priorController = new Controller<>(new PriorityModel());
//                Controller<PatientModel, Patient> patientController = new Controller<>(new PatientModel());
                List<Prescription> prescriptions = prescController.getFiltered(filterByDescription.getValue(),
                        filterByPriority.getValue() == null ? null : filterByPriority.getValue().toString(),
                        filterByPatient.getValue());
                BeanItemContainer<Prescription> prescContainer =
                        new BeanItemContainer<Prescription>(Prescription.class, prescriptions);
//                grid.removeAllColumns();
                grid.setContainerDataSource(prescContainer);
                break;
            case Entity.DOCTOR_PRESCRIPTION_INFO :
                Controller<DoctorModel, Doctor> doctorController =
                        new Controller<>(new DoctorModel());
                List<DoctorPrescrInfo> docInfo = doctorController.getDocPrescInfo();
                BeanItemContainer<DoctorPrescrInfo> docInfoContainer =
                        new BeanItemContainer<DoctorPrescrInfo>(DoctorPrescrInfo.class, docInfo);
                grid.setContainerDataSource(docInfoContainer);
                break;
        }
    }

//    public void updatePatientList() {
//        Controller<PatientModel, Patient> patController = new Controller<>(new PatientModel());
//        List<Patient> patients = patController.getAll();
//        BeanItemContainer<Patient> container =
//                new BeanItemContainer<Patient>(Patient.class, patients);
//        grid.removeAllColumns();
//        grid.setContainerDataSource(container);
//    }
//
//    public void updateDoctorList() {
//        Controller<DoctorModel, Doctor> docController = new Controller<>(new DoctorModel());
//        List<Doctor> doctors = docController.getAll();
//        BeanItemContainer<Doctor> container =
//                new BeanItemContainer<Doctor>(Doctor.class, doctors);
//        grid.removeAllColumns();
//        grid.setContainerDataSource(container);
//    }
//
//    synchronized public void updatePrescriptionList() {
//        Controller<PrescriptionModel, Prescription> prescController =
//                new Controller<>(new PrescriptionModel());
//        Controller<PriorityModel, Priority> priorController = new Controller<>(new PriorityModel());
//        Controller<PatientModel, Patient> patientController = new Controller<>(new PatientModel());
//        List<Prescription> prescriptions = prescController.getFiltered(filterByDescription.getValue(),
//                filterByPriority.getValue() == null ? null : filterByPriority.getValue().toString(),
//                filterByPatient.getValue());
//        BeanItemContainer<Prescription> container =
//                new BeanItemContainer<Prescription>(Prescription.class, prescriptions);
//        grid.removeAllColumns();
//        grid.setContainerDataSource(container);
//    }

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

    private void initEntityPage(String entityName) {
        switch (entityName) {
            case Entity.PATIENT :
                changeDisableButton(patientButton);
                addEntityLayout.removeAllComponents();
                addEntityLayout.addComponents(addPatientButton, editPatientButton, delPatientButton);
                filterToolbar.setVisible(false);
//                updatePatientList();
                updateEntityList(Entity.PATIENT);
                break;
            case Entity.DOCTOR :
                changeDisableButton(doctorButton);
                addEntityLayout.removeAllComponents();
                addEntityLayout.addComponents(addDoctorButton, editDoctorButton, delDoctorButton, showDoctorStatisticsButton);
                filterToolbar.setVisible(false);
//                updateDoctorList();
                updateEntityList(Entity.DOCTOR);
                break;
            case Entity.PRESCRIPTION :
                changeDisableButton(prescriptionButton);
                addEntityLayout.removeAllComponents();
                addEntityLayout.addComponents
                        (addPrescriptionButton, editPrescriptionButton, delPrescriptionButton);
                filterToolbar.setVisible(true);
//                updatePrescriptionList();
                updateEntityList(Entity.PRESCRIPTION);
                break;
        }
    }
//
//    private void initPatientPage() {
//        changeDisableButton(patientButton);
//        addEntityLayout.removeAllComponents();
//        addEntityLayout.addComponent(addPatientButton);
//        filterToolbar.setVisible(false);
//        updatePatientList();
//    }
//
//    private void initDoctorPage() {
//        changeDisableButton(doctorButton);
//        addEntityLayout.removeAllComponents();
//        addEntityLayout.addComponent(addDoctorButton);
//        filterToolbar.setVisible(false);
//        updateDoctorList();
//    }
//
//    private void initPrescriptionPage() {
//        changeDisableButton(prescriptionButton);
//        addEntityLayout.removeAllComponents();
//        addEntityLayout.addComponent(addPrescriptionButton);
//        filterToolbar.setVisible(true);
//        updatePrescriptionList();
//    }

    private void preInitAddEditDelButtons() {
        addPatientButton = new Button("Add");
        createAddOrEditButtonAndWindow(addPatientButton,
                new NewPatientWindow(this, "New Patient"), true);
        addDoctorButton = new Button("Add");
        createAddOrEditButtonAndWindow(addDoctorButton,
                new NewDoctorWindow(this, "New Doctor"), true);
        addPrescriptionButton = new Button("Add");
        createAddOrEditButtonAndWindow(addPrescriptionButton,
                new NewPrescriptionWindow(this, "New Prescription"), true);
        editPatientButton = new Button("Edit");
        createAddOrEditButtonAndWindow(editPatientButton,
                new NewPatientWindow(this, "Edit Patient"), false);
        editDoctorButton = new Button("Edit");
        createAddOrEditButtonAndWindow(editDoctorButton,
                new NewDoctorWindow(this, "Edit Doctor"), false);
        editPrescriptionButton = new Button("Edit");
        createAddOrEditButtonAndWindow(editPrescriptionButton,
                new NewPrescriptionWindow(this, "Edit Prescription"), false);
        patientButton.addClickListener(e -> {
            changeDisableButton(patientButton);
//            initPatientPage();
            initEntityPage(Entity.PATIENT);
        });
        doctorButton.addClickListener(e -> {
            changeDisableButton(doctorButton);
//            initDoctorPage();
            initEntityPage(Entity.DOCTOR);
        });
        prescriptionButton.addClickListener(e -> {
            changeDisableButton(prescriptionButton);
//            initPrescriptionPage();
            initEntityPage(Entity.PRESCRIPTION);
        });
        delPatientButton = new Button("Delete");
        delPatientButton.setStyleName(ValoTheme.BUTTON_DANGER);
        delPatientButton.addClickListener(e -> {
            Controller<PatientModel, Patient> controller =
                    new Controller<>(new PatientModel());
            if (grid.getSelectedRow() != null) {
                controller.deleteOne(((Patient) grid.getSelectedRow()).getId());
                initEntityPage(Entity.PATIENT);
            } else {
                Window alertWindow = new Window();
                alertWindow.setModal(true);
                alertWindow.center();
                Label label = new Label("Ни одна строка не выделена!");
                HorizontalLayout layout = new HorizontalLayout(label);
                layout.setWidth("300");
                layout.setHeight("100");
                layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
                alertWindow.setContent(layout);
                addWindow(alertWindow);
            }
        });
        delDoctorButton = new Button("Delete");
        delDoctorButton.setStyleName(ValoTheme.BUTTON_DANGER);
        delDoctorButton.addClickListener(e -> {
            Controller<DoctorModel, Doctor> controller =
                    new Controller<>(new DoctorModel());
            if (grid.getSelectedRow() != null) {
                controller.deleteOne(((Doctor) grid.getSelectedRow()).getId());
                initEntityPage(Entity.DOCTOR);
            } else {
                Window alertWindow = new Window();
                alertWindow.setModal(true);
                alertWindow.center();
                Label label = new Label("Ни одна строка не выделена!");
                HorizontalLayout layout = new HorizontalLayout(label);
                layout.setWidth("300");
                layout.setHeight("100");
                layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
                alertWindow.setContent(layout);
                addWindow(alertWindow);
            }
        });
        delPrescriptionButton = new Button("Delete");
        delPrescriptionButton.setStyleName(ValoTheme.BUTTON_DANGER);
        delPrescriptionButton.addClickListener(e -> {
            Controller<PrescriptionModel, Prescription> controller =
                    new Controller<>(new PrescriptionModel());
            if (grid.getSelectedRow() != null) {
                controller.deleteOne(((Prescription) grid.getSelectedRow()).getId());
                initEntityPage(Entity.PRESCRIPTION);
            } else {
                Window alertWindow = new Window();
                alertWindow.setModal(true);
                alertWindow.center();
                Label label = new Label("Ни одна строка не выделена!");
                HorizontalLayout layout = new HorizontalLayout(label);
                layout.setWidth("300");
                layout.setHeight("100");
                layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
                alertWindow.setContent(layout);
                addWindow(alertWindow);
            }
        });
        showDoctorStatisticsButton = new Button("Show statistic");
        showDoctorStatisticsButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        showDoctorStatisticsButton.addClickListener(e -> {
            addEntityLayout.removeComponent(showDoctorStatisticsButton);
            addEntityLayout.addComponent(hideDoctorStatisticsButton);
            updateEntityList(Entity.DOCTOR_PRESCRIPTION_INFO);
        });
        hideDoctorStatisticsButton = new Button("Hide statistic");
        hideDoctorStatisticsButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        hideDoctorStatisticsButton.addClickListener(e -> {
            addEntityLayout.removeComponent(hideDoctorStatisticsButton);
            addEntityLayout.addComponent(showDoctorStatisticsButton);
            updateEntityList(Entity.DOCTOR);
        });
    }

    private void createAddOrEditButtonAndWindow
            (Button button, NewEntityWindow window, boolean isNewEntity) {
//        button = new Button("Add patient button");
        button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        button.addClickListener(e -> {
            if (!isNewEntity) {
                Object rowObj = grid.getSelectedRow();
                if (rowObj == null) {
                    Window alertWindow = new Window();
                    alertWindow.setModal(true);
                    alertWindow.center();
                    Label label = new Label("Ни одна строка не выделена!");
                    HorizontalLayout layout = new HorizontalLayout(label);
                    layout.setWidth("300");
                    layout.setHeight("100");
                    layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
                    alertWindow.setContent(layout);
                    addWindow(alertWindow);
                    return;
                }
                if (rowObj instanceof Patient) {
                    window.setEntity((Patient)rowObj);
                } else if (rowObj instanceof Doctor) {
                    window.setEntity((Doctor)rowObj);
                } else if (rowObj instanceof Prescription) {
                    window.setEntity((Prescription)rowObj);
                }
            }
            grid.deselectAll();
//            NewPatientWindow window =
//                    new NewPatientWindow(this, "New patient");
            window.setCreateNewForm(isNewEntity);
            addWindow(window);
        });
    }
//
//    private void preInitAddEditDelButtons() {
//        addPatientButton = new Button("Add patient button");
//        addPatientButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//        addPatientButton.addClickListener(e -> {
//            grid.deselectAll();
//            NewPatientWindow entityWindow =
//                    new NewPatientWindow(this, "New patient");
//            entityWindow.setDeleteDisable(true);
//            entityWindow.setCreateNewForm(true);
//            addWindow(entityWindow);
//        });
//        editPatientButton = new Button("Edit patient button");
//        editPatientButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//        editPatientButton.addClickListener(e -> {
//            grid.deselectAll();
//            NewPatientWindow entityWindow =
//                    new NewPatientWindow(this, "Patient");
//            entityWindow.setDeleteDisable(true);
//            entityWindow.setCreateNewForm(false);
//            addWindow(entityWindow);
//        });
//        addDoctorButton = new Button("Add doctor button");
//        addDoctorButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//        addDoctorButton.addClickListener(e -> {
//            grid.deselectAll();
//            NewDoctorWindow entityWindow =
//                    new NewDoctorWindow(this, "New doctor");
//            entityWindow.setDeleteDisable(true);
//            entityWindow.setCreateNewForm(true);
//            addWindow(entityWindow);
//        });
//        addPrescriptionButton = new Button("Add prescription button");
//        addPrescriptionButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//        addPrescriptionButton.addClickListener(e -> {
//            grid.deselectAll();
//            NewPrescriptionWindow entityWindow =
//                    new NewPrescriptionWindow(this, "New prescription");
//            entityWindow.setDeleteDisable(true);
//            entityWindow.setCreateNewForm(true);
//            addWindow(entityWindow);
//        });
//        patientButton.addClickListener(e -> {
//            changeDisableButton(patientButton);
//            initPatientPage();
//        });
//        doctorButton.addClickListener(e -> {
//            changeDisableButton(doctorButton);
//            initDoctorPage();
//        });
//        prescriptionButton.addClickListener(e -> {
//            changeDisableButton(prescriptionButton);
//            initPrescriptionPage();
//        });
//    }

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