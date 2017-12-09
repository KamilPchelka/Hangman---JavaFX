package pl.kamilpchelka.codecool.hangman.popups;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WordInputPopup extends TextInputDialog {

    private final boolean theTextMustBeTyped;
    private final int minTextLength;
    private Stage stage;

    /**
     * Creates a new TextInputDialog without a default value entered into the
     * dialog {@link TextField}.
     */
    public WordInputPopup(String text, boolean theTextMustBeTyped, Stage stage, int minTextLength) {
        this.theTextMustBeTyped = theTextMustBeTyped;
        this.minTextLength = minTextLength;
        this.setHeaderText(text);
        this.stage = stage;
    }

    public String getUserInputText() {
        final String[] userInput = {null};
        DialogPane dialogPane = this.getDialogPane();
        TextField textField = this.getEditor();
        Stage newStage = new Stage();
        newStage.setAlwaysOnTop(true);
        newStage.setResizable(false);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(this.stage);
        newStage.setScene(dialogPane.getScene());
        newStage.setOnCloseRequest(event -> event.consume());
        dialogPane.lookupButton(ButtonType.CANCEL).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!theTextMustBeTyped) {
                userInput[0] = "";
                newStage.close();
            }

            event.consume();
        });
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if ((textField.getText().length() >= minTextLength)) {
                newStage.close();
                userInput[0] = textField.getText();
            }
            event.consume();
        });
        newStage.showAndWait();
        return userInput[0];
    }
}
