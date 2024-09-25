package gm.task.presentation;

import gm.task.TaskApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class TaskManagmentFX extends Application {

    private ConfigurableApplicationContext applicationContext;

//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void init(){
        //Here load the SpringFactory to unite with JavaFX
        //Spring executes first this method and then the start method
        this.applicationContext = new SpringApplicationBuilder(TaskApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean); //We get the Spring methods and send to JavaFX view
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
