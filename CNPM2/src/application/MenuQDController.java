package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.ItemBienBan;
import model.ItemBienLai;
import model.ItemQuyetDinh;
import model.PagingWrapper;

public class MenuQDController {
	@FXML Pagination pagination_table;
	@FXML Button button_themKCBB;
	@FXML Button button_themCBB;
	@FXML Button button_chiTiet;
	
	@FXML TableView<ItemQuyetDinh> TableView_1;
	@FXML TableColumn Column_IdQD;
	@FXML TableColumn Column_IdBB;
	@FXML TableColumn Column_IdCB;
	@FXML TableColumn Column_DateFound;
	@FXML TableColumn Column_IdNVP;
	@FXML TableColumn Column_NameNVP;
	@FXML TableColumn Column_DateBirth;
	@FXML TableColumn Column_Address;
	@FXML TableColumn Column_Fines;
	@FXML TableColumn Column_DatePay;
	@FXML TableColumn Column_Note;
	@FXML TableColumn Column_sdt;
	@FXML TextField TextField_Search;
	
	
	
	@FXML
	private void initialize() {
		if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_QD)) {
			button_themKCBB.setDisable(true);
			button_themCBB.setDisable(true);
		}
		
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
		
		TableView_1.setRowFactory(tv -> {
		    TableRow<ItemQuyetDinh> row = new TableRow<ItemQuyetDinh>();
		    OptionBox option=new OptionBox();
        	option.create(4);
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY&&event.getClickCount() == 2) {
		        	
		        	ItemQuyetDinh clickedRow = row.getItem();
		        	
		        	
		        	option.show(row, event.getScreenX(), event.getScreenY());
		        	
		        	option.getBox(0).setText("Tim kiếm người vi phạm theo CMND");
		        	option.getBox(0).setOnAction((e)->{
		        		ClientConfigure.controller.changeToNVPTab().getTextField_Search().setText(clickedRow.getSoCMND());
		        	});
		        	
		        	option.getBox(1).setText("Tim kiếm cán bộ theo họ tên");
		        	option.getBox(1).setOnAction((e)->{
		        		ClientConfigure.controller.changeToCBTab().getTextField_Search().setText(clickedRow.getHoTenCanBo());
		        	});
		        	
		        	option.getBox(2).setText("Tim kiếm tang vật theo số QDXPHC");
		        	option.getBox(2).setOnAction((e)->{
		        		ClientConfigure.controller.changeToTVTab().getTextField_Search().setText(clickedRow.getSoQDXPHC()+"");
		        		ClientConfigure.controller.changeToTVTab().getComboBox_searchType().getSelectionModel().select("QDXPHC");
		        	});
		        	
		        	option.getBox(3).setText("Tim kiếm người biên bản theo CMND");
		        	option.getBox(3).setOnAction((e)->{
		        		if(clickedRow.getSoBBVPHC()==0) {
		        			Notifycation.error("Quyết định này không dựa trên biên bản");
		        			return;
		        		}
		        		ClientConfigure.controller.changeToBBTab().getBB_TextField_Search().setText(clickedRow.getSoCMND()+"");
		        		
		        	});
		      
		            
		        }
		    });
		    return row ;
		});
		
		button_themKCBB.setOnAction((e)->{
			Stage AddQD = new Stage();
			 AddQD.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingQDKCBB.fxml"));
				fxmlLoader.setController(new AddQDXPKCBBController(this,AddQD));
				AddQD.setScene(new Scene(fxmlLoader.load()));
				AddQD.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		button_themCBB.setOnAction((e)->{
			Stage AddQD = new Stage();
			 AddQD.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingQDCBB.fxml"));
				fxmlLoader.setController(new AddQDXPCBBController(this,AddQD));
				AddQD.setScene(new Scene(fxmlLoader.load()));
				AddQD.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		button_chiTiet.setOnAction((e)->{
			ItemQuyetDinh itQD=TableView_1.getSelectionModel().getSelectedItem();
			if(itQD==null) {
				Notifycation.error("Hãy chọn một quyết định");
				return;
			}
			
			Stage DetailQD = new Stage();
			DetailQD.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailQD.fxml"));
				fxmlLoader.setController(new DetailQDController(itQD));
				DetailQD.setScene(new Scene(fxmlLoader.load()));
				DetailQD.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		refeshTable(0);
	}
	
	private void refeshTable(int page_index) {
		try {
			MenuController.serverServices.findQuyetDinhTheoCMND(TextField_Search.getText(),page_index,new  FindQDHandler());
	
//		String nameCB = TextField_Search.getText();
//		FindCanBoHandler findcb = new FindCanBoHandler();
//		MenuController.serverServices.findCanBo(nameCB,findcb);
		
		
	} catch (RemoteException e1) {
		Platform.runLater(()->{
			Notifycation.error("Lỗi kêt nối tới server");
		});
	}
	}
	
	public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
	
	
	
	
	
	class FindQDHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindQDHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {

			
		
		
			
			Platform.runLater(()->{
			
			//Set Collumn_Name vá»›i dá»¯ liá»‡u cÃ³ tÃªn lÃ  hoTen
		    Column_IdQD.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("soQDXPHC")); // hay pháº£i theo thá»© tá»±?, m Ä‘Ã£ map vÃ´ cá»™t rá»“i thÃ¬ Ä‘au cáº§n thá»© tá»±
			// nÃ³ Ä‘Ã¢u phÃ¢n biá»‡t dá»¯ liá»‡u vÃ o lÃ  cá»™t nÃ o Ä‘Ã¢u?
		
		    Column_IdBB.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("soBBVPHC"));
		    Column_DateFound.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("ngayLap"));
		    Column_IdCB.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("hoTenCanBo"));
		    Column_NameNVP.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("hoTenNguoiViPham"));
		    Column_IdNVP.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("soCMND"));
		    Column_DateBirth.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("ngaySinh"));
		    Column_Address.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("diaChi"));
		    Column_DatePay.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("hanNop"));
		    Column_sdt.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("sdt"));
		    Column_Fines.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("soTienCanNop"));
		    Column_Note.setCellValueFactory(new PropertyValueFactory<ItemQuyetDinh,String>("ghiChu"));
		    PagingWrapper<ArrayList<ItemQuyetDinh>> ls=(PagingWrapper<ArrayList<ItemQuyetDinh>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
			for(ItemQuyetDinh qd: ls.getPage_data()) {
				qd.tinhSoTienCanNop();
			}
			ObservableList<ItemQuyetDinh> data = FXCollections.observableArrayList(ls.getPage_data());
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
