package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.MenuCBController.FindCanBoHandler;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemBienBan;
import model.ItemCanBo;
import model.ItemNguoiViPham;
import model.ItemTangVat;
import model.PagingWrapper;

public class MenuTVController {
	@FXML Pagination pagination_table;
	@FXML TableColumn Column_IdCS;
	@FXML TableColumn Column_TangVat;
	@FXML TableColumn Column_Number;
	@FXML TableColumn Column_Status;
	@FXML TableColumn Column_Note;
	@FXML TableView<ItemTangVat> TableView_1;
	@FXML TextField TextField_Search;
    @FXML Button Button_Add;
    @FXML Button Button_Delete;
    @FXML Button Button_Edit;
    @FXML ComboBox comboBox_searchType;//Edit
    
    
	
	  public TextField getTextField_Search() {
		return TextField_Search;
	}


	private Integer soBB=0;//Add
	  private boolean isQD=true;
	@FXML
	private void initialize() {
		comboBox_searchType.setDisable(true);
		if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_TV)) {
			Button_Add.setDisable(true);
			Button_Edit.setDisable(true);
			Button_Delete.setDisable(true);
		}
		
		comboBox_searchType.setItems(FXCollections.observableArrayList("QDXPHC","BBVPHC"));
		comboBox_searchType.getSelectionModel().selectFirst();
		comboBox_searchType.setOnAction((e)->{
			isQD=comboBox_searchType.getValue().toString().equals("QDXPHC")?true:false;
			refeshTable(0);
		});
		TextField_Search.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty())comboBox_searchType.setDisable(false);
			else comboBox_searchType.setDisable(true);
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
			initAdd();
		});
		
		Button_Edit.setOnAction((e)->{
			ItemTangVat itTangVat=TableView_1.getSelectionModel().getSelectedItem();
			if(itTangVat==null) {
				Notifycation.error("Hãy chọn một tang vật để hiệu chỉnh");
				
				return;
			}
			
			if(TextField_Search.getText().isEmpty()) {
				Notifycation.error("Hãy tìm kiếm tang  vật theo số QDXPHC hoặc BBVPHC rồi hiệu chỉnh");
				
				return;
			}
			
			Stage EditTV = new Stage();
			EditTV.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditingTV.fxml"));
				fxmlLoader.setController(new EditTVController(this,EditTV,itTangVat,Integer.parseInt(TextField_Search.getText()),isQD));
				
				EditTV.setScene(new Scene(fxmlLoader.load()));
				EditTV.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 catch (NumberFormatException e2) {
			 		Notifycation.error("Dữ liệu nhập vào không hợp lệ");
			}
		});
		
		
		Button_Delete.setOnAction((e)->{
			ItemTangVat itTangVat=TableView_1.getSelectionModel().getSelectedItem();
			if(itTangVat==null) {
				Notifycation.error("Hãy chọn một tang vật để xóa");	
				return;
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Delete TangVat");
	        alert.setHeaderText("Bạn có chắc muốn xóa tang vật này chứ");
	      
	        // option != null.
	        Optional<ButtonType> option = alert.showAndWait();
	 
	        if (option.get() == null) {
	           
	        } else if (option.get() == ButtonType.OK) {
	        	try {
					MenuController.serverServices.deleteTangVat(itTangVat.getMaTangVat(), new DeleteTangVatHandler());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					Platform.runLater(()->{
						Notifycation.error("Lỗi kêt nối tới server");
					});
				}
	        	
	        } else if (option.get() == ButtonType.CANCEL) {
	            
	        }
			
			
			
			
		});
		
		refeshTable(0);
	}
	
	public void initAdd() {
		 Stage AddTV = new Stage();
		 AddTV.initModality(Modality.APPLICATION_MODAL);
		 try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingTV.fxml"));
			fxmlLoader.setController(new AddTVController(this,AddTV,soBB,isQD));
			AddTV.setScene(new Scene(fxmlLoader.load()));
			AddTV.showAndWait();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ComboBox getComboBox_searchType() {
		return comboBox_searchType;
	}

	public void preLoadBBVPHC(Integer so_bbvphc) {
		  this.soBB=so_bbvphc;
		  comboBox_searchType.getSelectionModel().select("BBVPHC");
		  initAdd();
	  }
	  
	  public void preLoadQDXPHC(Integer so_qdxphc) {
		  this.soBB=so_qdxphc;
		  comboBox_searchType.getSelectionModel().select("QDXPHC");
		  initAdd();
	  }
	
	private void refeshTable(int page_index) {
		
		if(TextField_Search.getText().isEmpty()) {
			try {
				
				MenuController.serverServices.findTatCaTangVat(page_index,new  FindTangVatHandler());
				
//				String nameCB = TextField_Search.getText();
//				FindCanBoHandler findcb = new FindCanBoHandler();
//				MenuController.serverServices.findCanBo(nameCB,findcb);
				
				
			} catch (RemoteException e1) {
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
		}
		
		else 
		{
			try {
				Integer.parseInt(TextField_Search.getText());
			}catch(Exception ex) {
				return;
			}
			try {
				if(isQD) {
					MenuController.serverServices.findTangVatTheoSoQDXPHC(TextField_Search.getText(),page_index,new  FindTangVatHandler());
				}
				else {
					MenuController.serverServices.findTangVatTheoSoBBVPHC(TextField_Search.getText(),page_index,new  FindTangVatHandler());
				}
	//			String nameCB = TextField_Search.getText();
	//			FindCanBoHandler findcb = new FindCanBoHandler();
	//			MenuController.serverServices.findCanBo(nameCB,findcb);
				
				
			} catch (RemoteException e1) {
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
		}
	}
	
	public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
	
	class FindTangVatHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindTangVatHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
			//Set Collumn_Name vá»›i dá»¯ liá»‡u cÃ³ tÃªn lÃ  hoTen
				Column_IdCS.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("tenCoSoLuuTru")); // hay pháº£i theo thá»© tá»±?, m Ä‘Ã£ map vÃ´ cá»™t rá»“i thÃ¬ Ä‘au cáº§n thá»© tá»±
				// nÃ³ Ä‘Ã¢u phÃ¢n biá»‡t dá»¯ liá»‡u vÃ o lÃ  cá»™t nÃ o Ä‘Ã¢u?
			
				Column_TangVat.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("tenTangVat"));
				Column_Number.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("soLuong"));
				Column_Status.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("trangThai"));
				Column_Note.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("ghiChu"));
				
				PagingWrapper<ArrayList<ItemTangVat>> ls=(PagingWrapper<ArrayList<ItemTangVat>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
				ObservableList<ItemTangVat> data = FXCollections.observableArrayList(ls.getPage_data());
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
	
	
	class DeleteTangVatHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected DeleteTangVatHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
				Notifycation.sucessed("Xóa tang vật thành công");
				updateTable();
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
