package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.regex.Pattern;

import application.AddTVController.AddTangVatHandler;
import application.AddTVController.FindCoSoLuuTruHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ItemCanBo;
import model.ItemCoSoLuuTru;
import model.ItemDonVi;
import model.ItemTangVat;

public class EditTVController {
	@FXML TextField TextField_NameTVEDIT;
	@FXML TextField TextField_NumberTVEDIT;
	@FXML TextArea TextArea_NoteTVEDIT;
    @FXML Button Button_Accept;
    @FXML ComboBox ComboBox_CosoTVEDIT;
    @FXML TextField TextField_IdTVEDIT;
    @FXML TextField TextField_StatusTVEDIT;
    @FXML Label label_soBB;
   
    private MenuTVController parent;
    private Stage root;
    private ItemTangVat tv;
    
    private Integer maBB;
    private boolean isQD;
    public EditTVController(MenuTVController parent, Stage root, ItemTangVat tv,Integer maBB, boolean isQD) {
    	this.parent=parent;
    	this.root=root;
    	this.tv=tv;
    	
    	this.maBB=maBB;
    	this.isQD=isQD;
    	
    }
    @FXML
	private void initialize() {
    	TextField_IdTVEDIT.setEditable(false);
    	TextField_IdTVEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
 			if(!Notifycation.allNumberReg.matcher(newValue).find())
 				TextField_IdTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
 			else
 				TextField_IdTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
 		});
    	TextField_NumberTVEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
 			if(!Notifycation.allNumberReg.matcher(newValue).find())
 				TextField_NumberTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
 			else
 				TextField_NumberTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
 		});
	  
    	TextField_NameTVEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
	 			if(newValue.isEmpty()||Notifycation.containSpecialReg.matcher(newValue).find())
	 				TextField_NameTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
	 			else
	 				TextField_NameTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
	 	});
    	
    	TextField_StatusTVEDIT.textProperty().addListener((observable, oldValue, newValue) -> {
	 			if(newValue.isEmpty()||Notifycation.containSpecialReg.matcher(newValue).find())
	 				TextField_StatusTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, true);
	 			else
	 				TextField_StatusTVEDIT.pseudoClassStateChanged(Notifycation.errorClass, false);
	 	});
	  
	  
    	if(!isQD) {
			label_soBB.setText("số BBVPHC");
	
		}
		else {
			label_soBB.setText("số QDXPHC");
			
		}
    	Button_Accept.setOnAction((e)->{
   
    		
    		boolean check=true;
			if(TextField_IdTVEDIT.getText().isEmpty()||TextField_NameTVEDIT.getText().isEmpty()||TextField_NumberTVEDIT.getText().isEmpty()||TextField_StatusTVEDIT.getText().isEmpty()) check=false;
		
			check&=!Notifycation.containSpecialReg.matcher(TextField_NameTVEDIT.getText()).find()&!Notifycation.containSpecialReg.matcher(TextField_StatusTVEDIT.getText()).find()
			&Notifycation.allNumberReg.matcher(TextField_NumberTVEDIT.getText()).find()&Notifycation.allNumberReg.matcher(TextField_IdTVEDIT.getText()).find();
			

			if(!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				
				return;
			}
			
			
			Integer id_co_so_luu_tru=Integer.parseInt(ComboBox_CosoTVEDIT.getValue().toString().split("-")[0].trim());
			
			
			
			try {
				if(isQD) 
				MenuController.serverServices.modifyTangVatQDXPHC(tv.getMaTangVat(), Integer.parseInt(TextField_IdTVEDIT.getText()), TextField_NameTVEDIT.getText(), Integer.parseInt(TextField_NumberTVEDIT.getText()), TextField_StatusTVEDIT.getText(),id_co_so_luu_tru, TextArea_NoteTVEDIT.getText(),new EditTangVatHandler());
				else
				MenuController.serverServices.modifyTangVatBBVPHC(tv.getMaTangVat(), Integer.parseInt(TextField_IdTVEDIT.getText()), TextField_NameTVEDIT.getText(), Integer.parseInt(TextField_NumberTVEDIT.getText()), TextField_StatusTVEDIT.getText(),id_co_so_luu_tru, TextArea_NoteTVEDIT.getText(),new EditTangVatHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
    		
    	
    		
    	});
    	FindCoSoLuuTruHandler cslt;
		try {
			cslt = new FindCoSoLuuTruHandler();
			MenuController.serverServices.findCoSoLuuTru("",cslt);
				
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	  
    	
    	
    }

	private void loadTangVat() {
		TextField_NameTVEDIT.setText(tv.getTenTangVat());
    	TextField_NumberTVEDIT.setText(tv.getSoLuong().toString());
    	TextArea_NoteTVEDIT.setText(tv.getGhiChu());
    	TextField_IdTVEDIT.setText(maBB.toString());
    	TextField_StatusTVEDIT.setText(tv.getTrangThai());
    
			 
			ObservableList<ItemCoSoLuuTru> ol_cslt=ComboBox_CosoTVEDIT.getItems();
			for(ItemCoSoLuuTru iclst: ol_cslt) {
				if(iclst.getMaCoSo().equals(tv.getMaCoSoLuuTru())) {
					ComboBox_CosoTVEDIT.getSelectionModel().select(iclst);
				}
			}
	}
	
	class EditTangVatHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected EditTangVatHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(()->{
				Notifycation.sucessed("Hiệu chỉnh tang vật thành công");
				
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
	
	
	  class FindCoSoLuuTruHandler extends  UnicastRemoteObject implements CallBackHandler{
			public FindCoSoLuuTruHandler() throws RemoteException {
				super();
				// TODO Auto-generated constructor stub
			}

			@Override
			public void callBack(Object payload) throws RemoteException {
				// TODO Auto-generated method stub
				Platform.runLater(()->{
					List<ItemCoSoLuuTru> ls=(List<ItemCoSoLuuTru>) payload;
					ComboBox_CosoTVEDIT.setItems(FXCollections.observableArrayList(ls));
					ComboBox_CosoTVEDIT.getSelectionModel().selectFirst();
					
					loadTangVat();
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
