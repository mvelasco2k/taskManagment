package gm.task.controller;

import gm.task.model.Task;
import gm.task.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private final ObservableList<Task> listTask = FXCollections.observableArrayList();
    private Integer idTaskIntern;

    @Autowired
    private TaskService taskService;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, Integer> idTaskColumn;
    @FXML
    private TableColumn<Task, String> nameTaskColumn;
    @FXML
    private TableColumn<Task, String> responsableTaskColumn;
    @FXML
    private TableColumn<Task, String> statusTaskColumn;
    @FXML
    private TextField taskTextField;
    @FXML
    private TextField responsableTextField;
    @FXML
    private TextField statusTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurateColumns();
        listTask();
    }

    private void configurateColumns(){
        //The same name that we define in model
        idTaskColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        responsableTaskColumn.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        statusTaskColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void listTask(){
        logger.info("Ejecutando listado de tareas");
        listTask.clear();
        listTask.addAll(taskService.listTask());
        taskTable.setItems(listTask);
    }

    public void addTask(){
        if(taskTextField.getText().isEmpty()){
            showMessage("Error de validación","Debe proporcionar una tarea");
            taskTextField.requestFocus();
            return;
        }else{
            var task = new Task();
            getFormData(task);
            task.setId(null);
            taskService.saveTask(task);
            showMessage("Información","Tarea agregada");
            cleanForm();
            listTask();
        }
    }

    public void loadSelectedTaskForm(){
        var selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if(selectedTask != null){
            idTaskIntern = selectedTask.getId();
            taskTextField.setText(selectedTask.getName());
            responsableTextField.setText(selectedTask.getResponsable());
            statusTextField.setText(selectedTask.getStatus());
        }
    }

    private void showMessage(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void getFormData(Task task){
        if(idTaskIntern != null){
            task.setId(idTaskIntern);
        }
        task.setName(taskTextField.getText());
        task.setResponsable(responsableTextField.getText());
        task.setStatus(statusTextField.getText());
    }

    public void cleanForm(){
        idTaskIntern = null;
        taskTextField.clear();
        responsableTextField.clear();
        statusTextField.clear();
    }

    public void updateTask(){
        if(idTaskIntern == null){
            showMessage("Información","Debe seleccionar una tarea.");
            return;
        }
        if(taskTextField.getText().isEmpty()){
            showMessage("Error de Validación","Debe proporcionar una tarea");
            taskTextField.requestFocus();
            return;
        }
        var task = new Task();
        getFormData(task);
        taskService.saveTask(task);
        showMessage("Información", "Tarea Modificada");
        cleanForm();
        listTask();
    }

    public void deleteTask(){
        var taskDelete = taskTable.getSelectionModel().getSelectedItem();

        if(taskDelete != null){
            logger.info("Registro a eliminar: "+ taskDelete.toString());
            taskService.deleteTask(taskDelete);
            showMessage("Información","Tarea eliminada");
            cleanForm();
            listTask();
        }else{
            showMessage("Error","No se ha seleccionado ninguna tarea");
        }
    }
}
