package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    static ProgressBar createProgressBar(int x, int y) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setTranslateX(x);
        progressBar.setTranslateY(y);
        return progressBar;
    }

    static void run(ProgressBar bar, double speed, long delay) {
        while(bar.getProgress() < 1){
            Platform.runLater(() -> bar.setProgress(bar.getProgress() + speed));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();

        final ProgressBar bar1 = createProgressBar(0,0);
        final ProgressBar bar2 = createProgressBar(0,50);

        root.getChildren().addAll(bar1, bar2);

        primaryStage.setTitle("Threads");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        new Thread(() -> {
            run(bar1, 0.01f, 150);
        }).start();

        new Thread(() -> {
            run(bar2, 0.01f, 200);
        }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
