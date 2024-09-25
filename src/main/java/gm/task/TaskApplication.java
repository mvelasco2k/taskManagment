package gm.task;

import gm.task.presentation.TaskManagmentFX;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
//		SpringApplication.run(TaskApplication.class, args);
		Application.launch(TaskManagmentFX.class, args);
	}

}
