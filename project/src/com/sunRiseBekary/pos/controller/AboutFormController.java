package com.sunRiseBekary.pos.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.sunRiseBekary.pos.util.UILoader;

import java.io.IOException;

public class AboutFormController {
    public AnchorPane AboutContext;
    //--------------------------------UI close-------------------------------//
    public void CloseOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.CloseStage(AboutContext);
    }
}
