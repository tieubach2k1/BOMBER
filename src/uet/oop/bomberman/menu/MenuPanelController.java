package uet.oop.bomberman.menu;

import javafx.fxml.FXML;
import uet.oop.bomberman.INextPanel;

public class MenuPanelController {
    private INextPanel nextToPlayPanel;

    public void setNextToPlayPanel(INextPanel nextToPlayPanel) {
        this.nextToPlayPanel = nextToPlayPanel;
    }

    @FXML
    private void openPlayPanel() {
        nextToPlayPanel.next();
    }

}
