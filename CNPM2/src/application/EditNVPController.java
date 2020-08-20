package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.regex.Pattern;

import application.AddNVPController.AddNguoiViPhamHandler;
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
import model.ItemNguoiViPham;

public class EditNVPController {
	 @FXML TextField TextField_NameEDIT;
	 @FXML TextField TextField_AddressEDIT; 
     @FXML DatePicker DatePicker_DateBirthEDIT; 
     @FXML Button Button_Accept; 
     @FXML TextField TextField_PhoneNumberEDIT;
     @FXML TextField TextField_CMNDEDIT;
     
     
     private Stage root;
     private MenuNVPController parent;
   
     
     ItemNguoiViPham nvp;
 
     
     public EditNVPController(MenuNVPController parent,Stage root, ItemNguoiViPham nvp) {
    	 this.root=root;
    	 this.parent=parent;
    	 this.nvp=nvp;
     }
     
     @FXML 
	private void initialize() {
     	 TextField_NameEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
  			if(newValue.isEmpty()||Notifycation.containSpecialReg.matcher(newValue).find())
  				TextField_NameEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
  			else
  				TextField_NameEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
  		});
    
  		TextField_CMNDEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
  			if(!Notifycation.allNumberReg.matcher(newValue).find()||(newValue.length()!=9&&newValue.length()!=12))
  				TextField_CMNDEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
  			else
  				TextField_CMNDEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
  		});
  		
  		TextField_PhoneNumberEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
  			if(!Notifycation.allNumberReg.matcher(newValue).find()||(newValue.length()>20))
  				TextField_PhoneNumberEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
  			else
  				TextField_PhoneNumberEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
  		});
    	 
    	 DatePicker_DateBirthEDIT.setValue(LocalDate.now());
    	 TextField_CMNDEDIT.setText(nvp.getSoCMND());
    	 Button_Accept.setOnAction((e)->{
 			
    			
 			String Name = null;
 			String Address = null;

 			String PhoneNumber =null;
 			
 			Name=TextField_NameEDIT.getText();
 			Address=TextField_AddressEDIT.getText();
 			java.sql.Date date = java.sql.Date.valueOf(DatePicker_DateBirthEDIT.getValue());
 			PhoneNumber=TextField_PhoneNumberEDIT.getText();
 			
 			boolean check=true;
 	 		
			if(Name.isEmpty()||Address.isEmpty()||PhoneNumber.isEmpty()||(PhoneNumber.length()>=20)) check=false;
			check&=!Notifycation.containSpecialReg.matcher(Name).find()&Notifycation.allNumberReg.matcher(PhoneNumber).find();
 			
 			
 			if(!check) {
 				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				
				return;
			}

			try {
				MenuController.serverServices.modifyNguoiViPham(nvp.getSoCMND(), Name, date, Address, PhoneNumber,new EditNguoiViPhamHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			};
    	 });	
    	 
    	 loadNguoiViPham();
	}
     
     public void loadNguoiViPham() {
    	 TextField_NameEDIT.setText(nvp.getHoTen());
    	 TextField_AddressEDIT.setText(nvp.getDiaChi());
    	 DatePicker_DateBirthEDIT.setValue(nvp.getNgaySinh().toLocalDate());
    	 TextField_PhoneNumberEDIT.setText(nvp.getSoDienThoai());
     }
     
     
     
     class EditNguoiViPhamHandler extends  UnicastRemoteObject implements CallBackHandler{

 		protected EditNguoiViPhamHandler() throws RemoteException {
 			super();
 			// TODO Auto-generated constructor stub
 		}


 		@Override
 		public void callBack(Object payload) throws RemoteException {
 			Platform.runLater(()->{
 				Notifycation.sucessed("Hiệu chỉnh người vi phạm thành công");
 				
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
