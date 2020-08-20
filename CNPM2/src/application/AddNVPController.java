package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

import application.AddCBController.AddCanBoHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddNVPController {
	@FXML
	TextField TextField_NameADD;
	@FXML
	TextField TextField_AddressADD;
	@FXML
	DatePicker DatePicker_DateBirthADD;
	@FXML
	Button Button_Accept;
	@FXML
	TextField TextField_PhoneNumberADD;
	@FXML
	TextField TextField_CMNDADD;

	private Stage root;
	private MenuNVPController parent;
	Alert alert = new Alert(AlertType.INFORMATION);
	private TextField ret;

	public AddNVPController(TextField ret, MenuNVPController parent, Stage root) {
		this.root = root;
		this.parent = parent;
		this.ret = ret;

	}

	@FXML
	private void initialize() {
		TextField_NameADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_NameADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_NameADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		TextField_CMNDADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find()
					|| (newValue.length() != 9 && newValue.length() != 12))
				TextField_CMNDADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_CMNDADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		TextField_PhoneNumberADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find() || (newValue.length() > 20))
				TextField_PhoneNumberADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_PhoneNumberADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		DatePicker_DateBirthADD.setValue(LocalDate.now());

		Button_Accept.setOnAction((e) -> {

			String Name = null;
			String Address = null;
			String CMND = null;

			String PhoneNumber = null;

			Name = TextField_NameADD.getText();
			Address = TextField_AddressADD.getText();
			CMND = TextField_CMNDADD.getText();
			java.sql.Date date = java.sql.Date.valueOf(DatePicker_DateBirthADD.getValue());
			PhoneNumber = TextField_PhoneNumberADD.getText();

			boolean check = true;

			if (Name.isEmpty() || Address.isEmpty() || CMND.isEmpty() || PhoneNumber.isEmpty()
					|| (CMND.length() != 9 && CMND.length() != 12) || (PhoneNumber.length() >= 20))
				check = false;
			check &= !Notifycation.containSpecialReg.matcher(Name).find()
					& Notifycation.allNumberReg.matcher(CMND).find()
					& Notifycation.allNumberReg.matcher(PhoneNumber).find();

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");

				return;
			}

			try {
				MenuController.serverServices.addNguoiViPham(CMND, Name, date, Address, PhoneNumber,
						new AddNguoiViPhamHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
			;
		});
	}

	class AddNguoiViPhamHandler extends UnicastRemoteObject implements CallBackHandler {

		protected AddNguoiViPhamHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(() -> {
				Notifycation.sucessed("Thêm người vi phạm thành công");

				if (ret != null) {
					ret.setText(TextField_CMNDADD.getText());
				}

				root.close();
				parent.updateTable();
			});
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				Notifycation.error(message);
			});

		}

	}

}
