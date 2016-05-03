package calculator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

// http://docs.oracle.com/javase/8/javafx/fxml-tutorial/why_use_fxml.htm#CHDGAFHF
public class Controller implements Initializable {
    @FXML Label expression;
    @FXML Label number;
    @FXML Label memory;
    @FXML BorderPane root;

    private boolean numberToClear = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonListeners(root);
        root.setOnKeyTyped(event -> Controller.this.handleKeyAction(event.getCharacter()));
        number.setText("0");
        numberToClear = true;
    }

    void onCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent cc = new ClipboardContent();
        cc.putString(number.getText());
        clipboard.setContent(cc);
    }
    void onPaste() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String exp = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        exp = exp.replaceAll("[^0123456789\\.\\+\\-\\*/\\(\\)\\=（）]", "");
        exp = exp.replaceAll("（", "(");
        exp = exp.replaceAll("）", ")");
        exp = exp.replaceAll("=", "\r");
        for (int i=0; i<exp.length(); i++) {
            handleKeyAction(exp.substring(i, i+1));
        }
        System.out.println(exp);
    }

    private String getAnswer(String exp) {
        ExpressionParser ep = new ExpressionParser(exp);
        return ep.getResult();
    }

    private void setButtonListeners(Parent root) {
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Parent) {
                setButtonListeners((Parent)node);
            }
            if (node instanceof Button) {
                ((Button)node).setOnMouseClicked(event -> Controller.this.handleButtonAction((Button)node));
            }
        }
    }

    private void handleKeyAction(String key) {
        final String Numbers = "0123456789.";
        final String BinOp = "+-*/";
        final String Parenthese = "()";
        if (Numbers.contains(key)) {
            String newNum;
            if (numberToClear) {
                numberToClear = false;
                newNum = " " + key;
            } else {
                newNum = number.getText();
                if (!(number.getText().contains(".") && key.equals("."))) {
                    newNum += key;
                }
            }
            number.setText(newNum);
        } else if (BinOp.contains(key)) {
            if (!numberToClear) {
                expression.setText(expression.getText() + number.getText());
                numberToClear = true;
            }
            expression.setText(expression.getText() + " " + key);
            numberToClear = true;
        } else if (Parenthese.contains(key)) {
            if (key.equals(")")) {
                expression.setText(expression.getText() + number.getText());
                numberToClear = true;
            }
            expression.setText(expression.getText() + " " + key);
        } else if (key.equals("\b")) {
            String num = number.getText();
            String exp = expression.getText();
            if (!numberToClear && num.length() > 0) {
                number.setText(num.substring(0, num.length() - 1));
            } else if (exp.length() > 0){
                expression.setText(exp.substring(0, exp.length() - 1));
            }
            if (numberToClear) {
                number.setText("");
            }
        } else if (key.equals("C") || key.equals("c")) {
            number.setText("0");
            expression.setText("");
            numberToClear = true;
        } else if (key.equals("\r")) { //Enter
            if (!numberToClear) {
                expression.setText(expression.getText() + number.getText());
                numberToClear = true;
            }
            String answer = getAnswer(expression.getText());
            number.setText(answer);
            expression.setText("");
            numberToClear = true;
        }
    }

    private void handleButtonAction(Button button) {
        String t = button.getText();
        if (t.equals("←")) {
            t = "\b";
        } else if (t.equals("=")) { //Enter
            t = "\r";
        }
        this.handleKeyAction(t);
    }
}
