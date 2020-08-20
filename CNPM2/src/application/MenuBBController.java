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
import javafx.util.Callback;
import model.ItemBienBan;
import model.ItemCanBo;
import model.ItemNguoiViPham;
import model.ItemQuyetDinh;
import model.ItemTangVat;
import model.PagingWrapper;

public class MenuBBController {
	@FXML Pagination pagination_table;
	@FXML Button Button_Add;
	@FXML Button Button_Edit;
	@FXML TextField BB_TextField_Search;
	
	@FXML TableColumn Column_IdQD;
    @FXML TableColumn Column_IdCB;
    @FXML TableColumn Collum_sdt;
    @FXML TableColumn Column_DateFound;
    @FXML TableColumn Column_IdNVP;
    @FXML TableColumn Column_NameNVP;
    @FXML TableColumn Column_DateBirth;
    @FXML TableColumn Column_Address;
    @FXML TableColumn Column_Description;
    @FXML TableView<ItemBienBan> TableView_1;
    
    
	public TextField getBB_TextField_Search() {
		return BB_TextField_Search;
	}



	@FXML
	private void initialize() {
		if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_BB)) {
			Button_Add.setDisable(true);
			Button_Edit.setDisable(true);
		}
		
		BB_TextField_Search.textProperty().addListener((observable, oldValue, newValue) -> {
			refeshTable(0);
		});
		
		pagination_table.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
            	refeshTable(pageIndex);
                return  TableView_1;
            }
        });
		
		Button_Add.setOnAction((e)->{
			Stage AddBB = new Stage();
			 AddBB.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingBB.fxml"));
				fxmlLoader.setController(new AddBBController(this,AddBB));
				AddBB.setScene(new Scene(fxmlLoader.load()));
				AddBB.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
		});
		
		Button_Edit.setOnAction((e)->{
			
			ItemBienBan itBienBan=TableView_1.getSelectionModel().getSelectedItem();
			if(itBienBan==null) {
				Notifycation.error("Hãy chọn một biên bản để hiệu chỉnh");
				return;
			}
			
			
			Stage EditBB = new Stage();
			 EditBB.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditingBB.fxml"));
				fxmlLoader.setController(new EditBBController(this,EditBB,itBienBan));
				EditBB.setScene(new Scene(fxmlLoader.load()));
				EditBB.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		TableView_1.setRowFactory(tv -> {
		    TableRow<ItemBienBan> row = new TableRow<ItemBienBan>();
		    OptionBox option=new OptionBox();
        	option.create(3);
        	
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY&&event.getClickCount() == 2) {
		        	
		        	ItemBienBan clickedRow = row.getItem();
		        	
		        	
		        	option.show(row, event.getScreenX(), event.getScreenY());
		        	
		        	option.getBox(0).setText("Tim kiếm người vi phạm theo CMND");
		        	option.getBox(0).setOnAction((e)->{
		        		ClientConfigure.controller.changeToNVPTab().getTextField_Search().setText(clickedRow.getSoCMND());
		        	});
		        	
		        	option.getBox(1).setText("Tim kiếm cán bộ theo họ tên");
		        	option.getBox(1).setOnAction((e)->{
		        		ClientConfigure.controller.changeToCBTab().getTextField_Search().setText(clickedRow.getHoTenCanBo());
		        	});
		        	
		        	option.getBox(2).setText("Tim kiếm tang vật theo số BBVPHC");
		        	option.getBox(2).setOnAction((e)->{
		        		ClientConfigure.controller.changeToTVTab().getTextField_Search().setText(clickedRow.getSoBBVPHC()+"");
		        		ClientConfigure.controller.changeToTVTab().getComboBox_searchType().getSelectionModel().select("BBVPHC");
		        	});
		        	
		        	
		      
		            
		        }
		    });
		    return row ;
		});
		
		refeshTable(0);
	}
	
	
	
	private void refeshTable(int page_index) {
		try {
				MenuController.serverServices.findBienBanTheoCMND(BB_TextField_Search.getText(),page_index,new FindBBHandler() );
		} catch (RemoteException e1) {
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	}
	
	public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
	
	class FindBBHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindBBHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(()->{
			    Column_IdQD.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("soBBVPHC")); 
				
			    Column_IdCB.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("hoTenCanBo"));
			    Column_DateFound.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("ngayLap"));
			    Column_IdNVP.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("soCMND"));
			    Column_NameNVP.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("hoTenNguoiViPham"));
			    Column_DateBirth.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("ngaySinh"));
			    Column_Address.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("diaChi"));
			    Column_Description.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("moTaHanhVi"));
			    Collum_sdt.setCellValueFactory(new PropertyValueFactory<ItemBienBan,String>("sdt"));
			    
			    PagingWrapper<ArrayList<ItemBienBan>> ls=(PagingWrapper<ArrayList<ItemBienBan>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
				ObservableList<ItemBienBan> data = FXCollections.observableArrayList(ls.getPage_data());
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
