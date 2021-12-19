package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import uet.oop.bomberman.INextPanel;
import uet.oop.bomberman.sound.SoundManager;

import java.io.IOException;
import java.nio.file.Paths;

public class MenuPanel extends JFXPanel {
    private static final SoundManager intro = new SoundManager("/audios/intro.wav");
    private static MenuPanel INSTANCE = null;

    public static SoundManager getIntro() {
        return intro;
    }

    private MenuPanel(INextPanel nextPanel) {
        Platform.runLater(() -> initFX(nextPanel));
    }

    public static MenuPanel getInstance(INextPanel nextPanel) {
        intro.loop();
        if (INSTANCE == null) {
            INSTANCE = new MenuPanel(nextPanel);
        }
        return INSTANCE;
    }

    private void initFX(INextPanel nextPanel) {
        try {
            String path = "src/uet/oop/bomberman/menu/MenuPanel.fxml";
            FXMLLoader loader = new FXMLLoader(Paths.get(path).toUri().toURL());
            Parent root = loader.load();
            ((MenuPanelController) loader.getController()).setNextToPlayPanel(nextPanel);
            Scene scene = new Scene(root);
            setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
