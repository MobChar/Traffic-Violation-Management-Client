package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemDonVi;
import model.ItemQueQuan;

class AddCBController {
	@FXML
	Button Button_Accept;
	@FXML
	TextField TextField_NameADD;
	@FXML
	ComboBox ComboBox_DonViADD;
	@FXML
	TextField TextField_PositionADD;
	@FXML
	DatePicker DatePicker_DateBirthADD;
	@FXML
	ComboBox ComboBox_QueQuanADD;
	@FXML
	TextArea TextArea_NoteADD;
	@FXML
	TextField TextField_CMNDADD;

	MenuCBController parent;

	private Stage root;

	public AddCBController(MenuCBController parent, Stage root) {
		this.root = root;
		this.parent = parent;
	}

	@FXML
	private void initialize() {
		TextField_NameADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_NameADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_NameADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_PositionADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_PositionADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_PositionADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_CMNDADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find()
					|| (newValue.length() != 9 && newValue.length() != 12))
				TextField_CMNDADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_CMNDADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		DatePicker_DateBirthADD.setValue(LocalDate.now());

		Button_Accept.setOnAction((e) -> {

			String Name = null;
			String Position = null;
			String CMND = null;
			String Note = null;
			Integer QueQuan = null;
			Integer DonVi = null;

			Name = TextField_NameADD.getText();// Hinh nhu dc roi a. M set Controlláº» chua , roi set
			DonVi = Integer.parseInt(ComboBox_DonViADD.getValue().toString().split("-")[0].trim());
			Position = TextField_PositionADD.getText();
			java.sql.Date date = java.sql.Date.valueOf(DatePicker_DateBirthADD.getValue());
			QueQuan = Integer.parseInt(
					ComboBox_QueQuanADD.getSelectionModel().getSelectedItem().toString().split("-")[0].trim());
			Note = TextArea_NoteADD.getText();
			CMND = TextField_CMNDADD.getText();

			// Check Input
			boolean check = true;
			if (Name.isEmpty() || Position.isEmpty() || (CMND.length() != 9 && CMND.length() != 12))
				check = false;
			check &= !Notifycation.containSpecialReg.matcher(Name).find()
					& !Notifycation.containSpecialReg.matcher(Position).find()
					& Notifycation.allNumberReg.matcher(CMND).find();

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}

			try {
				MenuController.serverServices.addCanBo(CMND, Name, date, Position, QueQuan, DonVi, Note,
						new AddCanBoHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
			;

		});

		FindQueQuanHandler findqq;
		FindDonViHandler finddv;
		try {
			findqq = new FindQueQuanHandler();
			finddv = new FindDonViHandler();
			MenuController.serverServices.findQueQuan("", findqq);
			MenuController.serverServices.findDonVi("", finddv);

		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			Platform.runLater(() -> {
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}

	}

	class FindQueQuanHandler extends UnicastRemoteObject implements CallBackHandler {// CÃ¡i nÃ y thá»±c thi
																						// CallBackHandler nÃ y, m Ä‘Æ°a
																						// cho servÃªr cÃ¡i nÃ y
		public FindQueQuanHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				List<ItemQueQuan> ls = (List<ItemQueQuan>) payload;// Dá»¯ liá»‡u tráº£ vá»� array list nÃªn pháº£i Ã©p
																	// kiá»ƒu vá»�,quÃ¡ server xem code luÃ´n ha. ua

				ComboBox_QueQuanADD.setItems(FXCollections.observableArrayList(ls));// CÃ¡i nÃ y gá»�i hÃ m toString
																					// Ä‘á»ƒ in ra item nÃ¨
				ComboBox_QueQuanADD.getSelectionModel().selectFirst();

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

	class FindDonViHandler extends UnicastRemoteObject implements CallBackHandler {

		protected FindDonViHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub

			Platform.runLater(() -> {
				List<ItemDonVi> ls = (List<ItemDonVi>) payload;
				Platform.runLater(() -> {
					ComboBox_DonViADD.setItems(FXCollections.observableArrayList(ls));
					ComboBox_DonViADD.getSelectionModel().selectFirst();
				});
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

	class AddCanBoHandler extends UnicastRemoteObject implements CallBackHandler {

		protected AddCanBoHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(() -> {
				Notifycation.sucessed("Thêm cán bộ thành công");

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