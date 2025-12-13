package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewListCtrl implements Initializable {

    private MainCtrl mainCtrl;
    private ServerUtils server;

    @Inject
    public OverviewListCtrl(MainCtrl mainCtrl, ServerUtils server){
        this.mainCtrl=mainCtrl;
        this.server=server;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




}
