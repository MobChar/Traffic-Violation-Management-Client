package application;

import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Notifycation {
	 public static final Alert success = new Alert(AlertType.INFORMATION);
	 public static final Alert error= new Alert(AlertType.ERROR);
	 public static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
	 public static final Pattern containSpecialReg = Pattern.compile("[$&+,:;=?@#|'<>.^*()%!-]",Pattern.CASE_INSENSITIVE);
	 public static final Pattern allNumberReg = Pattern.compile("^\\d+$",Pattern.CASE_INSENSITIVE);
	 
	 
	 public static void sucessed(String message) {
			 success.setTitle("Thành công");
			 success.setHeaderText(message);
			 success.showAndWait();
			 
	 }
	 
	 public static void error(String message) {
		
			 success.setTitle("Lỗi");
			 success.setHeaderText(message);
			 success.showAndWait();
		
	 };
}
