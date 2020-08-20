package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import application.MenuQDController.FindQDHandler;
import client_services.BillPrinter;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemBienBan;
import model.ItemBienLai;
import model.ItemCanBo;
import model.ItemNguoiViPham;
import model.ItemTangVat;
import model.PagingWrapper;

public class MenuBLController {
	@FXML Pagination pagination_table;
	@FXML Button Button_Add;
	@FXML Button Button_Edit;
	@FXML Button Button_Print;
	@FXML TextField TextField_Search;
	@FXML TableView<ItemBienLai> TableView_1;
	@FXML TableColumn Column_IdQD;
	@FXML TableColumn Column_IdBL;
	@FXML TableColumn Column_NameNP;
	@FXML TableColumn Column_NameNL;
	@FXML TableColumn Column_DateFound;
	@FXML TableColumn Column_Decription;
	@FXML TableColumn Column_DatePay;
	@FXML TableColumn Column_Fines;

	
	
	@FXML 
	private void initialize() {//Ä�Æ°á»£c rá»“i Ã¡, giá»� thÃªm thanh tÃ¬m kiáº¿m Ä‘i. Cai add t chua xong :V lÃ m thanh  tÃ¬m kiáº¿m  rá»“i má»›i test dc Ä‘c chá»© :v
		if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_BL)) {
			Button_Add.setDisable(true);
			Button_Edit.setDisable(true);
		}
		Button_Add.setOnAction((e)->{
			 Stage AddBL = new Stage();
			 AddBL.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingBL.fxml"));
				fxmlLoader.setController(new AddBLController(this,AddBL));
				AddBL.setScene(new Scene(fxmlLoader.load()));
				AddBL.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button_Edit.setOnAction((e)->{
			ItemBienLai itBL=TableView_1.getSelectionModel().getSelectedItem();
			if(itBL==null) {
				Notifycation.error("Hãy chọn một biên lai để hiệu chỉnh");
				return;
			}
			
			Stage EditBL = new Stage();
			EditBL.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditingBL.fxml"));
				fxmlLoader.setController(new EditBLController(this,EditBL,itBL));
				EditBL.setScene(new Scene(fxmlLoader.load()));
				EditBL.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		TextField_Search.textProperty().addListener((observable, oldValue, newValue) -> {
			refeshTable(0);
		});
		
		pagination_table.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
            	refeshTable(pageIndex);
                return  TableView_1;
            }
        });
		
		
		Button_Print.setOnAction((e)->{
			ItemBienLai itBienLai=TableView_1.getSelectionModel().getSelectedItem();
			if(itBienLai==null) {
				Notifycation.error("Hãy chọn một mục để in biên lai");	
				return;
			}
			BillPrinter billPrinter=new BillPrinter();
			billPrinter.exportBill(itBienLai);
			
		});
		
		
		TableView_1.setRowFactory(tv -> {
		    TableRow<ItemBienLai> row = new TableRow<ItemBienLai>();
		    OptionBox option=new OptionBox();
        	option.create(1);
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY&&event.getClickCount() == 2) {
		        	
		        	ItemBienLai clickedRow = row.getItem();
		        	
		        	
		        	option.show(row, event.getScreenX(), event.getScreenY());
		       
		        	
		        	option.getBox(0).setText("Tim kiếm cán bộ theo họ tên");
		        	option.getBox(0).setOnAction((e)->{
		        		ClientConfigure.controller.changeToCBTab().getTextField_Search().setText(clickedRow.getHoTenCanBo());
		        	});
		        	
		        	
		        	
		      
		            
		        }
		    });
		    return row ;
		});
		
		refeshTable(0);
			
	}
	private void refeshTable(int page_index) {
		try {
			MenuController.serverServices.findBienLaiNopPhatTheoTenCanBo(TextField_Search.getText(),page_index, new FindBienLaiNopPhatTheoTenCanBoHandler());
		
	} catch (RemoteException e1) {
		Platform.runLater(()->{
			Notifycation.error("Lỗi kêt nối tới server");
		});
	}
	}
	
	public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
	
	
	class FindBienLaiNopPhatTheoTenCanBoHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindBienLaiNopPhatTheoTenCanBoHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}
		//ok//Con cai Add lam cho xong luon duoc k Ä‘Æ°á»›i ti láº¥y code Ä‘Ã£ 

		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(()->{
				//Cháº¡y ngon mÃ  t chÆ°ua cÃ³ cho dá»¯ liá»‡u vÃ o Ä‘Ã¢u. CÃ³ 1 cÃ¡i BL sáºµn rá»“i mÃ ?
				Column_IdQD.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("soQDXPHC")); 		
				Column_IdBL.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("maBienLai"));
				Column_NameNP.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("hoTenNguoiNopTien"));
				Column_DateFound.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("ngayLapBienLai"));
				Column_Decription.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("lyDoNopPhat"));
				Column_Fines.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("soTienNop"));
				Column_NameNL.setCellValueFactory(new PropertyValueFactory<ItemBienLai,String>("hoTenCanBo"));
				PagingWrapper<ArrayList<ItemBienLai>> ls=(PagingWrapper<ArrayList<ItemBienLai>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
				ObservableList<ItemBienLai> data = FXCollections.observableArrayList(ls.getPage_data());
				int selected_index=pagination_table.getCurrentPageIndex();
				pagination_table.setPageCount((int) Math.ceil(ls.getTotal()/20.));
				pagination_table.setCurrentPageIndex(Math.min(selected_index, ((int) Math.ceil(ls.getTotal()/20.))-1));
				TableView_1.setItems(data);
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
