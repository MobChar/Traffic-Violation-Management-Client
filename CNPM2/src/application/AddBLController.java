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

import application.MenuCBController.FindCanBoHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemCanBo;
import model.ItemDonVi;
import model.ItemQueQuan;
import model.ItemQuyetDinh;
import model.PagingWrapper;

class AddBLController {
	@FXML
	Button Button_Accept;
	@FXML
	TextField TextField_NameBLADD;
	@FXML
	Label Label_DonViBLADD;
	@FXML
	Label Label_FinesBLADD;
	@FXML
	Label Label_NameQDBLADD;
	@FXML
	TextField TextField_ReasonBLADD;
	@FXML
	DatePicker DatePicker_DateFoundQDBLADD;
	@FXML
	TextField TextField_IdQDBLADD;

	MenuBLController parent;

	private Stage root;

	public AddBLController(MenuBLController parent, Stage root) {
		this.root = root;
		this.parent = parent;
	}

	@FXML
	private void initialize() {
		TextField_IdQDBLADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find())
				TextField_IdQDBLADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_IdQDBLADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_ReasonBLADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_ReasonBLADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_ReasonBLADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_NameBLADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_NameBLADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_NameBLADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		QuyetDinhAutoCompletion auto_cpl = new QuyetDinhAutoCompletion(TextField_IdQDBLADD, Label_NameQDBLADD,
				Label_FinesBLADD);
		auto_cpl.bind();

		Label_DonViBLADD.setText(UserProfile.profile.getTenDonVi());
		DatePicker_DateFoundQDBLADD.setValue(LocalDate.now());

		Button_Accept.setOnAction((e) -> {

			String Name = null;
			String Reason = null;
			Integer idQD = null;
			Integer Fines = null;
			boolean check = true;

			try {
				Name = TextField_NameBLADD.getText();// Hinh nhu dc roi a. M set ControllÃ¡ÂºÂ» chua , roi set
				idQD = Integer.parseInt(TextField_IdQDBLADD.getText());
				Reason = TextField_ReasonBLADD.getText();
				Fines = Integer.parseInt(Label_FinesBLADD.getText());
				if (Name.isEmpty() || Reason.isEmpty() || TextField_IdQDBLADD.getText().isEmpty())
					check = false;
				check &= !Notifycation.containSpecialReg.matcher(Name).find()
						& !Notifycation.containSpecialReg.matcher(Reason).find();
			} catch (Exception ex) {
				check = false;
			}

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}

			try {
				MenuController.serverServices.addBienLaiNopPhat(idQD, UserProfile.profile.getMaCanBo(), Name,
						java.sql.Date.valueOf(DatePicker_DateFoundQDBLADD.getValue()), Reason, Fines,
						new AddBienLaiNopPhatHandler());// lÃ m tÃ­p Ä‘i t Ä‘i táº¯m dÃ£. OKie, cai nay thieu cai
														// handler
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				Platform.runLater(() -> {
					Notifycation.error("Lá»—i kÃªt ná»‘i tá»›i server");
				});
			}
			;

		});

	}

//	class FindQDHandler extends  UnicastRemoteObject implements CallBackHandler{
//
//		protected FindQDHandler() throws RemoteException {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//
//		@Override
//		public void callBack(Object payload) throws RemoteException {
//			
//			//Gan du lieu vao sao?
//			//cÃ¡i nÃ y  láº¥y tá»« lÃºc Ä‘Äƒng nháº­p áº¥y cáº§n khu vá»¥c ná»¯a khÃ´ng? T can cai han nop, tenCB voi don vi, voi cai so tien nop
//			//háº¡n ná»™p áº¥t nÃºt nÃ³ má»›i  hiá»‡n ra m Æ¡i sá»‘t iá»�n ná»�p cÃ³ trong service cÃ³ cáº§n bá»� han háº¡n ná»™p vÃ o khÃ´ng
//			// TODO Auto-generated method stub
//			Platform.runLater(()->{
//				if(payload==null) return;
//				ItemQuyetDinh qd=(ItemQuyetDinh)payload;
//					qd.tinhSoTienCanNop();
//					DatePicker_DateFoundQDBLADD.setValue(qd.getNgayLap().toLocalDate());
//					Label_NameQDBLADD.setText(qd.getHoTenCanBo());
//					Label_FinesBLADD.setText(qd.getSoTienCanNop().toString()); 
//				
//				});
//			}
//
//		@Override
//		public void errorCallBack(String message) throws RemoteException {
//			// TODO Auto-generated method stub
//			Platform.runLater(()->{
//				Notifycation.error(message);
//			});
//		}
//		
//	}
	class AddBienLaiNopPhatHandler extends UnicastRemoteObject implements CallBackHandler {

		protected AddBienLaiNopPhatHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				Notifycation.sucessed("Thêm biên lai thành công");
				parent.updateTable();
				root.close();
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