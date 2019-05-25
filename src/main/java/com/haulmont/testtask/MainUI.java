package com.haulmont.testtask;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.*;
import com.haulmont.testtask.forms.*;
import com.haulmont.testtask.models.*;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private Grid grid;
    private TextField filterByDescription;
    private NativeSelect filterByPriority;
    private TextField filterByPatient;
    private Button clearFiltersButton;
    HorizontalLayout headLayout;
    Button patientButton;
    Button doctorButton;
    Button prescriptionButton;
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
        filterByPatient = new TextField("Filter by patient");
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
                grid.setContainerDataSource(patContainer);
                break;
            case Entity.DOCTOR :
                Controller<DoctorModel, Doctor> docController = new Controller<>(new DoctorModel());
                List<Doctor> doctors = docController.getAll();
                BeanItemContainer<Doctor> docContainer =
                        new BeanItemContainer<Doctor>(Doctor.class, doctors);
                grid.setContainerDataSource(docContainer);
                break;
            case Entity.PRESCRIPTION :
                Controller<PrescriptionModel, Prescription> prescController =
                        new Controller<>(new PrescriptionModel());
                List<Prescription> prescriptions = prescController.getFiltered(filterByDescription.getValue(),
                        filterByPriority.getValue() == null ? null : filterByPriority.getValue().toString(),
                        filterByPatient.getValue());
                BeanItemContainer<Prescription> prescContainer =
                        new BeanItemContainer<Prescription>(Prescription.class, prescriptions);
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
                updateEntityList(Entity.PATIENT);
                break;
            case Entity.DOCTOR :
                changeDisableButton(doctorButton);
                addEntityLayout.removeAllComponents();
                addEntityLayout.addComponents(addDoctorButton, editDoctorButton, delDoctorButton, showDoctorStatisticsButton);
                filterToolbar.setVisible(false);
                updateEntityList(Entity.DOCTOR);
                break;
            case Entity.PRESCRIPTION :
                changeDisableButton(prescriptionButton);
                addEntityLayout.removeAllComponents();
                addEntityLayout.addComponents
                        (addPrescriptionButton, editPrescriptionButton, delPrescriptionButton);
                filterToolbar.setVisible(true);
                updateEntityList(Entity.PRESCRIPTION);
                break;
        }
    }

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
            initEntityPage(Entity.PATIENT);
        });
        doctorButton.addClickListener(e -> {
            changeDisableButton(doctorButton);
            initEntityPage(Entity.DOCTOR);
        });
        prescriptionButton.addClickListener(e -> {
            changeDisableButton(prescriptionButton);
            initEntityPage(Entity.PRESCRIPTION);
        });
        delPatientButton = new Button("Delete");
        delPatientButton.setStyleName(ValoTheme.BUTTON_DANGER);
        delPatientButton.addClickListener(e -> {
            Controller<PatientModel, Patient> controller =
                    new Controller<>(new PatientModel());
            if (grid.getSelectedRow() != null) {
                boolean isSuccessDel = controller.deleteOne(((Patient) grid.getSelectedRow()).getId());
                if (!isSuccessDel) {
                    AlertWindow alertWindow = new AlertWindow(
                            "Удаление не удалось, воэможно пациент имеет зависящие от него рецепты.");
                    addWindow(alertWindow);
                }
                initEntityPage(Entity.PATIENT);
            } else {
                AlertWindow alertWindow = new AlertWindow(
                        "Ни одна строка не выделена!");
                addWindow(alertWindow);
            }
        });
        delDoctorButton = new Button("Delete");
        delDoctorButton.setStyleName(ValoTheme.BUTTON_DANGER);
        delDoctorButton.addClickListener(e -> {
            Controller<DoctorModel, Doctor> controller =
                    new Controller<>(new DoctorModel());
            if (grid.getSelectedRow() != null) {
                boolean isSuccessDel = controller.deleteOne(((Doctor) grid.getSelectedRow()).getId());
                if (!isSuccessDel) {
                    AlertWindow alertWindow = new AlertWindow(
                            "Удаление не удалось, воэможно доктор имеет зависящие от него рецепты.");
                    addWindow(alertWindow);
                }
                initEntityPage(Entity.DOCTOR);
            } else {
                AlertWindow alertWindow = new AlertWindow(
                        "Ни одна строка не выделена!");
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
                AlertWindow alertWindow = new AlertWindow(
                        "Ни одна строка не выделена!");
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
            window.setCreateNewForm(isNewEntity);
            addWindow(window);
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