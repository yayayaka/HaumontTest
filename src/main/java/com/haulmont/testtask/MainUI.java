package com.haulmont.testtask;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Priority;
import com.haulmont.testtask.forms.NewPatientForm;
import com.haulmont.testtask.models.Model;
import com.haulmont.testtask.models.PatientModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItem;
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
    private NewPatientForm window;
//    private Table table;


    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        controller = new Controller<>(new PatientModel());
        List<Patient> patients = controller.getAll();
        BeanItemContainer<Patient> container =
                new BeanItemContainer<Patient>(Patient.class, patients);
        grid = new Grid(container);
//        grid = new Grid();
        updateList();
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
        addEntityButton = new Button("Add new patient");
        addEntityButton.addClickListener(e -> {
            grid.deselectAll();
            window = new NewPatientForm(this, "New patient");
//            window.setPatient(new Patient());
            window.setDeleteDisable(true);
            window.setCreateNewForm(true);
            addWindow(window);
        });
        grid.addItemClickListener(e -> {
            if (e.getItem() != null) {
                window = new NewPatientForm(this, "Patient");
                window.setPatient((Patient)((BeanItem)e.getItem()).getBean());
//                window.setPatient(new Patient( (long)e.getItem().getItemProperty("ID").getValue(),
//                        (String)e.getItem().getItemProperty("First Name").getValue(),
//                        (String)e.getItem().getItemProperty("Second Name").getValue(),
//                        (String)e.getItem().getItemProperty("Middle Name").getValue(),
//                        (int)e.getItem().getItemProperty("Phone No").getValue()));
                window.setDeleteDisable(false);
                window.setCreateNewForm(false);
                addWindow(window);
            }
        });
        HorizontalLayout toolbar = new HorizontalLayout(filterByDescription, filterByPriority,
                filterByPatient, clearFiltersButton, addEntityButton);
        toolbar.setSpacing(true);

        HorizontalLayout main = new HorizontalLayout(grid);//, window);
//        HorizontalLayout main = new HorizontalLayout(table);//, window);
        main.setSizeFull();
//        main.setMargin(true);
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);
        layout.addComponents(toolbar, main);

        setContent(layout);
    }

    public void updateList() {
        List<Patient> patients = controller.getAll();
        BeanItemContainer<Patient> container =
                new BeanItemContainer<Patient>(Patient.class, patients);
//        grid.
        grid.setContainerDataSource(container);
//        grid = new Grid();
//        grid.addColumn("ID", Long.class);
//        grid.addColumn("First Name", String.class);
//        grid.addColumn("Second Name", String.class);
//        grid.addColumn("Middle Name", String.class);
//        grid.addColumn("Phone No", Integer.class);
//        for (Patient patient : patients) {
//            grid.addRow(patient.getId(), patient.getName(), patient.getSecname(),
//                    patient.getOtch(), patient.getPhoneNo());
//        }
    }
}