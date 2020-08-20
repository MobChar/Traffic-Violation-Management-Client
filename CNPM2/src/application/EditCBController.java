package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import application.AddCBController.AddCanBoHandler;
import application.AddCBController.FindDonViHandler;
import application.AddCBController.FindQueQuanHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import model.ItemCanBo;
import model.ItemDonVi;
import model.ItemQueQuan;

public class EditCBController {
	
	@FXML
	Button Button_Accept;
	@FXML
	TextField TextField_NameEDIT;
	@FXML
	ComboBox ComboBox_DonViEDIT;
	@FXML
	TextField TextField_PositionEDIT;
	@FXML
	DatePicker DatePicker_DateBirthEDIT;
	@FXML
	ComboBox ComboBox_QueQuanEDIT;
	@FXML
	TextArea TextArea_NoteEDIT;
	@FXML
	TextField TextField_CMNDEDIT;
	private List<ItemQueQuan> qq_ls;
	private List<ItemDonVi> dv_ls;
	
	
	
	private ItemCanBo cb;
	private Stage root;
	private MenuCBController parent;
	public EditCBController(MenuCBController parent,Stage root,ItemCanBo cb) {
		this.cb=cb;
		this.root=root;
		this.parent=parent;
		
	}
	
	
	@FXML
	private void initialize() {
		TextField_NameEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.isEmpty()||Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_NameEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_NameEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_PositionEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.isEmpty()||Notifycation.containSpecialReg.matcher(newValue).find())
				TextField_PositionEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_PositionEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		TextField_CMNDEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!Notifycation.allNumberReg.matcher(newValue).find()||(newValue.length()!=9&&newValue.length()!=12))
				TextField_CMNDEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_CMNDEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		
		DatePicker_DateBirthEDIT.setValue(LocalDate.now());
		
		 

		Button_Accept.setOnAction((e)->{
			
		
			String Name = null;
			String Position = null;
			String CMND = null;
			String Note = null;
			Integer QueQuan =null;
			Integer DonVi = null;			
			java.sql.Date date=null;
			boolean check=true;
			try {
				Name = TextField_NameEDIT.getText();//Hinh nhu dc roi a. M set Controlláº» chua , roi set 
				DonVi = Integer.parseInt((ComboBox_DonViEDIT.getSelectionModel().getSelectedItem()).toString().split("-")[0].trim());
				Position = TextField_PositionEDIT.getText();
				date = java.sql.Date.valueOf(DatePicker_DateBirthEDIT.getValue());
				QueQuan = Integer.parseInt((ComboBox_QueQuanEDIT.getSelectionModel().getSelectedItem()).toString().split("-")[0].trim());				
				Note = TextArea_NoteEDIT.getText();
				CMND = TextField_CMNDEDIT.getText();
			}catch(Exception e1) {
				check=false;
			}
			
			
			if(Name.isEmpty()||Position.isEmpty()||(CMND.length()!=9&&CMND.length()!=12)) check=false;
			check&=!Notifycation.containSpecialReg.matcher(Name).find()&!Notifycation.containSpecialReg.matcher(Position).find()
			&Notifycation.allNumberReg.matcher(CMND).find();
			
		
			if(!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}
			
			try {
				MenuController.serverServices.modifyCanBo(cb.getMaCanBo(),CMND,Name, date, Position,QueQuan,
						DonVi,Note, new EditCanBoHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			};
			
		});
		
		FindQueQuanHandler findqq;
		 FindDonViHandler finddv;
		try {
			findqq = new FindQueQuanHandler();
			finddv = new FindDonViHandler();
			MenuController.serverServices.findQueQuan("",findqq);
			MenuController.serverServices.findDonVi("",finddv);
				
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
		
		
		
	}
	
	
	public void loadCanBo(ItemCanBo canBo) {
		TextField_NameEDIT.setText(canBo.getHoTen());
		TextField_PositionEDIT.setText(canBo.getChucVu());
		TextArea_NoteEDIT.setText(canBo.getGhiChu());
		TextField_CMNDEDIT.setText(canBo.getCmnd());
		 DatePicker_DateBirthEDIT.setValue(canBo.getNgaySinh().toLocalDate());
		 
		ObservableList<ItemDonVi> ol_dv=ComboBox_DonViEDIT.getItems();
		for(ItemDonVi it: ol_dv) {
			if(it.getMaDonVi().equals(cb.getMaDonVi())) {
				ComboBox_DonViEDIT.getSelectionModel().select(it);
			}
		}
		
		ObservableList<ItemQueQuan> ol_qq =ComboBox_QueQuanEDIT.getItems();
		for(ItemQueQuan it: ol_qq) {
			if(cb.getMaQueQuan().equals(it.getMaVung())) {
				ComboBox_QueQuanEDIT.getSelectionModel().select(it);
			}
		}
		
//		TextField TextField_NameEDIT;
//		@FXML
//		ComboBox ComboBox_DonViEDIT;
//		@FXML
//		TextField TextField_PositionEDIT;
//		@FXML
//		DatePicker DatePicker_DateBirthEDIT;
//		@FXML
//		ComboBox ComboBox_QueQuanEDIT;
//		@FXML
//		TextArea TextArea_NoteEDIT;
//		@FXML
//		TextField TextField_CMNDEDIT;
	}
	
	class FindQueQuanHandler extends  UnicastRemoteObject implements CallBackHandler{//CÃ¡i nÃ y thá»±c thi CallBackHandler nÃ y, m Ä‘Æ°a cho servÃªr cÃ¡i nÃ y
		public FindQueQuanHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			
			Platform.runLater(()->{
				List<ItemQueQuan> ls=(List<ItemQueQuan>) payload;//Dá»¯ liá»‡u tráº£ vá»� array list nÃªn pháº£i Ã©p kiá»ƒu vá»�,quÃ¡ server xem code luÃ´n ha. ua
	
				
				ComboBox_QueQuanEDIT.setItems(FXCollections.observableArrayList(ls));//CÃ¡i nÃ y gá»�i hÃ m toString Ä‘á»ƒ in ra item nÃ¨
				ComboBox_QueQuanEDIT.getSelectionModel().selectFirst();
				qq_ls=ls;
			});
			
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
				Notifycation.error(message);
			});
			
		}
		
	}
	class FindDonViHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindDonViHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			
			Platform.runLater(()->{
				List<ItemDonVi> ls=(List<ItemDonVi>) payload;
				ComboBox_DonViEDIT.setItems(FXCollections.observableArrayList(ls));
				ComboBox_DonViEDIT.getSelectionModel().selectFirst();
				dv_ls=ls;
				
				loadCanBo(cb);
			});
			
		}


		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
				Notifycation.error(message);
			});
		}
		
	}
	class EditCanBoHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected EditCanBoHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(()->{
				Notifycation.sucessed("Hiệu chỉnh thành công");
				root.close();
				parent.updateTable();
			});
		}


		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(()->{
				Notifycation.error(message);
			});
			
		}
		
	}
}
