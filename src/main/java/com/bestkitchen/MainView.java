package com.bestkitchen;

import com.bestkitchen.entity.*;
import com.bestkitchen.dao.InvoicePersistence;
import com.bestkitchen.dao.LoadDataManager;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainView extends UI {

    private MenuBar.Command command;
    private MenuBar.Command consultWaitersCommand;
    private MenuBar.Command consultClientsCommand;
    private MenuBar menuBar;
    private VerticalLayout layout;
    private Panel mainPanel = new Panel();
    private LoadDataManager loadDataManager;
    private ComboBox<Client> clientCombo;
    private ComboBox<Waiter> waitersCombo;
    private ComboBox<Table> tableCombo;
    private ComboBox<Chef> chefCombo;
    private TextField meal;
    private TextField cost;
    private static ResourceBundle systemMessages = ResourceBundle.getBundle("systemMessages", Locale.forLanguageTag("es"));

    @Override
    public void init(VaadinRequest request) {
        layout = new VerticalLayout();
        setContent(layout);
        menuBar = new MenuBar();
        layout.addComponent(menuBar);
        loadDataManager = new LoadDataManager();
        createCommand();
        consultWaitersCommand();
        consultClientsCommand();
        createMenu();
    }

    private void consultWaitersCommand(){
        consultWaitersCommand = (MenuBar.Command) selectedItem -> {
            List<WaiterResult> results = loadDataManager.loadWaiterResult();
            showWaiterResult(results);
        };
    }

    private void consultClientsCommand(){
        consultClientsCommand = (MenuBar.Command) selectedItem -> {
            List<ClientResult> results = loadDataManager.loadClientResult();
            showClientResult(results);
        };
    }

    private void showClientResult(List<ClientResult> results){
        mainPanel.setSizeUndefined();
        Grid<ClientResult> grid = new Grid<>();
        grid.setItems(results);
        grid.addColumn(ClientResult::getName).setCaption(systemMessages.getString("name"));
        grid.addColumn(ClientResult::getLastName).setCaption(systemMessages.getString("lastName"));
        grid.addColumn(ClientResult::getTotal).setCaption(systemMessages.getString("totalSpend"));
        mainPanel.setContent(grid);
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
    }

    private void showWaiterResult(List<WaiterResult> results){
        mainPanel.setSizeUndefined();
        Grid<WaiterResult> grid = new Grid<>();
        grid.setItems(results);
        grid.addColumn(WaiterResult::getName).setCaption(systemMessages.getString("name"));
        grid.addColumn(WaiterResult::getLastName).setCaption(systemMessages.getString("lastName"));
        grid.addColumn(WaiterResult::getInvoicedAmount).setCaption(systemMessages.getString("billed"));
        mainPanel.setContent(grid);
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
    }

    private void createCommand(){
        command = (MenuBar.Command) selectedItem -> {
            switch (selectedItem.getText()) {
                case "Facturas":
                    createInvoicePanel();
                    break;
                case "Clientes":
                    createClientPanel();
                    break;
                case "Camareros":
                    createWaiterPanel();
                    break;
                case "Mesas":
                    createTablePanel();
                    break;
                case "Cocineros":
                    createChefsPanel();
                    break;
            }
        };
    }
    @SuppressWarnings("unused")
    private void createMenu(){
        MenuBar.MenuItem register = menuBar.addItem(systemMessages.getString("register"), null, null);
        MenuBar.MenuItem invoices = register.addItem(systemMessages.getString("invoice"), null, command);
        MenuBar.MenuItem clients = register.addItem(systemMessages.getString("clients"), null, command);
        MenuBar.MenuItem waiter = register.addItem(systemMessages.getString("waiters"), null, command);
        MenuBar.MenuItem tables = register.addItem(systemMessages.getString("tables"), null, command);
        MenuBar.MenuItem chefs = register.addItem(systemMessages.getString("chefs"), null, command);
        MenuBar.MenuItem consults = menuBar.addItem(systemMessages.getString("consults"), null, null);
        consults.addItem(systemMessages.getString("waitersConsults"), null, consultWaitersCommand);
        consults.addItem(systemMessages.getString("clientsConsults"), null, consultClientsCommand);
    }

    private void createInvoicePanel(){
        mainPanel.setSizeUndefined();
        mainPanel.setCaption(systemMessages.getString("invoiceRegister"));
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        FormLayout content = new FormLayout();
        content.addComponent(createClientCombo());
        content.addComponent(createWaiterCombo());
        content.addComponent(createTableCombo());
        content.addComponent(createChefCombo());
        meal = new TextField(systemMessages.getString("plate"));
        cost = new TextField(systemMessages.getString("cost"));
        meal.setRequiredIndicatorVisible(true);
        cost.setRequiredIndicatorVisible(true);
        content.addComponent(meal);
        content.addComponent(cost);
        content.addComponent(createInvoiceSummitButton());
        content.setSizeUndefined();
        content.setMargin(true);
        mainPanel.setContent(content);
    }

    private void createClientPanel(){
        mainPanel.setSizeUndefined();
        mainPanel.setCaption(systemMessages.getString("clientsRegister"));
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        final TextField name = new TextField(systemMessages.getString("name"));
        name.setRequiredIndicatorVisible(true);
        final TextField lastName = new TextField(systemMessages.getString("lastName"));
        lastName.setRequiredIndicatorVisible(true);
        final TextField secondLastName = new TextField(systemMessages.getString("secondLastName"));
        secondLastName.setRequiredIndicatorVisible(true);
        final TextField observations = new TextField(systemMessages.getString("observations"));
        observations.setRequiredIndicatorVisible(true);
        Button saveButton = new Button("Guardar");
        saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            InvoicePersistence persistence = new InvoicePersistence();
            Client client = new Client();
            client.setClientName(name.getValue());
            client.setClientLastName(lastName.getValue());
            client.setClientSecondLastName(secondLastName.getValue());
            client.setClientObservations(observations.getValue());
            persistence.save(client);
        });
        FormLayout content = new FormLayout();
        content.addComponent(name);
        content.addComponent(lastName);
        content.addComponent(secondLastName);
        content.addComponent(observations);
        content.addComponent(saveButton);
        content.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);
        content.setSizeUndefined();
        content.setMargin(true);
        mainPanel.setContent(content);
    }

    private void createTablePanel(){
        mainPanel.setSizeUndefined();
        mainPanel.setCaption(systemMessages.getString("tablesRegister"));
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        final TextField maxCapacity = new TextField(systemMessages.getString("maxCapacity"));
        maxCapacity.setRequiredIndicatorVisible(true);
        final TextField location = new TextField(systemMessages.getString("location"));
        location.setRequiredIndicatorVisible(true);
        Button saveButton = new Button(systemMessages.getString("save"));
        saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            InvoicePersistence persistence = new InvoicePersistence();
            Table table = new Table();
            table.setMaxCapacity(Integer.valueOf(maxCapacity.getValue()));
            table.setLocation(location.getValue());
            persistence.save(table);
        });
        FormLayout content = new FormLayout();
        content.addComponent(maxCapacity);
        content.addComponent(location);
        content.addComponent(saveButton);
        content.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);
        content.setSizeUndefined();
        content.setMargin(true);
        mainPanel.setContent(content);
    }

    private void createChefsPanel(){
        mainPanel.setSizeUndefined();
        mainPanel.setCaption(systemMessages.getString("chefsRegister"));
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        final TextField name = new TextField(systemMessages.getString("name"));
        name.setRequiredIndicatorVisible(true);
        final TextField lastName = new TextField(systemMessages.getString("lastName"));
        lastName.setRequiredIndicatorVisible(true);
        final TextField secondLastName = new TextField(systemMessages.getString("secondLastName"));
        secondLastName.setRequiredIndicatorVisible(true);
        Button saveButton = new Button(systemMessages.getString("save"));
        saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            InvoicePersistence persistence = new InvoicePersistence();
            Chef chef = new Chef();
            chef.setName(name.getValue());
            chef.setLastName(lastName.getValue());
            chef.setSecondLastName(secondLastName.getValue());
            persistence.save(chef);
        });
        FormLayout content = new FormLayout();
        content.addComponent(name);
        content.addComponent(lastName);
        content.addComponent(secondLastName);
        content.addComponent(saveButton);
        content.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);
        content.setSizeUndefined();
        content.setMargin(true);
        mainPanel.setContent(content);
    }

    private void createWaiterPanel(){
        mainPanel.setSizeUndefined();
        mainPanel.setCaption(systemMessages.getString("waitersRegister"));
        layout.addComponent(mainPanel);
        layout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        final TextField name = new TextField(systemMessages.getString("name"));
        name.setRequiredIndicatorVisible(true);
        final TextField lastName = new TextField(systemMessages.getString("lastName"));
        lastName.setRequiredIndicatorVisible(true);
        final TextField secondLastName = new TextField(systemMessages.getString("secondLastName"));
        secondLastName.setRequiredIndicatorVisible(true);
        Button saveButton = new Button(systemMessages.getString("save"));
        saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            InvoicePersistence persistence = new InvoicePersistence();
            Waiter waiter = new Waiter();
            waiter.setName(name.getValue());
            waiter.setLastName(lastName.getValue());
            waiter.setSecondLastName(secondLastName.getValue());
            persistence.save(waiter);
        });
        FormLayout content = new FormLayout();
        content.addComponent(name);
        content.addComponent(lastName);
        content.addComponent(secondLastName);
        content.addComponent(saveButton);
        content.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);
        content.setSizeUndefined();
        content.setMargin(true);
        mainPanel.setContent(content);
    }

    private ComboBox<Client> createClientCombo(){
        List<Client> clients = loadDataManager.loadAllData(Client.class);
        clientCombo = new ComboBox<>(systemMessages.getString("client"));
        clientCombo.setRequiredIndicatorVisible(true);
        clientCombo.setEmptySelectionAllowed(false);
        clientCombo.setItems(clients);
        clientCombo.setItemCaptionGenerator((ItemCaptionGenerator<Client>) Client::toString);
        return clientCombo;
    }

    private ComboBox<Waiter> createWaiterCombo(){
        List<Waiter> waiters = loadDataManager.loadAllData(Waiter.class);
        waitersCombo = new ComboBox<>(systemMessages.getString("waiter"));
        waitersCombo.setRequiredIndicatorVisible(true);
        waitersCombo.setEmptySelectionAllowed(false);
        waitersCombo.setItems(waiters);
        waitersCombo.setItemCaptionGenerator((ItemCaptionGenerator<Waiter>) Waiter::toString);
        return waitersCombo;
    }

    private ComboBox<Table> createTableCombo(){
        List<Table> tables = loadDataManager.loadAllData(Table.class);
        tableCombo = new ComboBox<>(systemMessages.getString("table"));
        tableCombo.setRequiredIndicatorVisible(true);
        tableCombo.setEmptySelectionAllowed(false);
        tableCombo.setItems(tables);
        tableCombo.setItemCaptionGenerator((ItemCaptionGenerator<Table>) Table::toString);
        return tableCombo;
    }

    private ComboBox<Chef> createChefCombo(){
        List<Chef> chefs = loadDataManager.loadAllData(Chef.class);
        chefCombo = new ComboBox<>(systemMessages.getString("chef"));
        chefCombo.setRequiredIndicatorVisible(true);
        chefCombo.setEmptySelectionAllowed(false);
        chefCombo.setItems(chefs);
        chefCombo.setItemCaptionGenerator((ItemCaptionGenerator<Chef>) Chef::toString);
        return chefCombo;
    }

    private Button createInvoiceSummitButton(){
        Button button = new Button(systemMessages.getString("save"));
        button.addClickListener((Button.ClickListener) clickEvent -> {
            InvoicePersistence persistence = new InvoicePersistence();
            Invoice invoice = createInvoice();
            InvoiceDetails invoiceDetails =  createInvoiceDetails(invoice);
            persistence.save(invoice);
            persistence.save(invoiceDetails);
        });
        return button;
    }

    private Invoice createInvoice(){
        Invoice invoice = new Invoice();
        invoice.setClient(clientCombo.getValue());
        invoice.setWaiter(waitersCombo.getValue());
        invoice.setTable(tableCombo.getValue());
        invoice.setInvoiceDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        return invoice;
    }

    private InvoiceDetails createInvoiceDetails(Invoice invoice){
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setInvoice(invoice);
        invoiceDetails.setChef(chefCombo.getValue());
        invoiceDetails.setMeal(meal.getValue());
        invoiceDetails.setCost(Double.valueOf(cost.getValue()));
        return invoiceDetails;
    }
}
