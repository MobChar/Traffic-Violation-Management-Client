package application;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.MenuCBController.DeleteCanBoHandler;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemCanBo;
import model.ItemNguoiViPham;
import model.PagingWrapper;

public class MenuNVPController {
	@FXML Pagination pagination_table;
	
	@FXML TableView<ItemNguoiViPham> TableView_1;
	@FXML TextField TextField_Search;
	@FXML Button Button_Delete;
	@FXML Button Button_Add;
	@FXML Button Button_Edit;
    
    @FXML TableColumn Column_CMND;
    @FXML TableColumn Column_NameNVP;
    @FXML TableColumn Column_DateBirth;
    @FXML TableColumn Column_Address;
    @FXML TableColumn Column_PhoneNum;
   
    
  
    
    @FXML 
   	private void initialize() {
    	if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_NVP)) {
			Button_Add.setDisable(true);
			Button_Edit.setDisable(true);
			Button_Edit.setDisable(true);
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
    	
   		Button_Add.setOnAction((e)->{
   			initAdd(null);
   		});
   		
   		Button_Edit.setOnAction((e)->{
   			ItemNguoiViPham itNVP=TableView_1.getSelectionModel().getSelectedItem();
			if(itNVP==null) {
				Notifycation.error("Hãy chọn một người vi phạm để hiệu chỉnh");
				
				return;
			}
   			
   			Stage EditNVP = new Stage();
			EditNVP.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditingNVP.fxml"));
				fxmlLoader.setController(new EditNVPController(this,EditNVP,itNVP));
				EditNVP.setScene(new Scene(fxmlLoader.load()));
				EditNVP.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   		});
   		
   		
   		Button_Delete.setOnAction((e)->{
			ItemNguoiViPham itNVP=TableView_1.getSelectionModel().getSelectedItem();
			if(itNVP==null) {
				Notifycation.error("Hãy chọn một người vi phạm để xóa");	
				return;
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Delete Người Vi Phạm");
	        alert.setHeaderText("Bạn có chắc muốn xóa người vi phạm này chứ");
	      
	        // option != null.
	        Optional<ButtonType> option = alert.showAndWait();
	 
	        if (option.get() == null) {
	           
	        } else if (option.get() == ButtonType.OK) {
	        	try {
					MenuController.serverServices.deleteNguoiViPham(itNVP.getSoCMND(), new DeleteNguoiViPhamHandler());
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
    
    public TextField getTextField_Search() {
		return TextField_Search;
	}

	private void refeshTable(int page_index) {
  
    	try {
   			FindNguoiViPhamHandler findNVP = new FindNguoiViPhamHandler();
			MenuController.serverServices.findNguoiViPhamTheoCMND(TextField_Search.getText(),page_index,findNVP);
			
		} catch (RemoteException e1) {
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
    }
    
    public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
    
    
    public void initAdd(TextField ret) {
    	Stage AddNVP = new Stage();
		AddNVP.initModality(Modality.APPLICATION_MODAL);
		 try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingNVP.fxml"));
			fxmlLoader.setController(new AddNVPController(ret,this,AddNVP));
			AddNVP.setScene(new Scene(fxmlLoader.load()));
			AddNVP.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    
	class FindNguoiViPhamHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindNguoiViPhamHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
				//Set Collumn_Name vá»›i dá»¯ liá»‡u cÃ³ tÃªn lÃ  hoTen
				Column_NameNVP.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("hoTen")); // hay pháº£i theo thá»© tá»±?, m Ä‘Ã£ map vÃ´ cá»™t rá»“i thÃ¬ Ä‘au cáº§n thá»© tá»±
				// nÃ³ Ä‘Ã¢u phÃ¢n biá»‡t dá»¯ liá»‡u vÃ o lÃ  cá»™t nÃ o Ä‘Ã¢u?
			
				Column_Address.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("diaChi"));
				Column_PhoneNum.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("soDienThoai"));
				Column_CMND.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("soCMND"));
				Column_DateBirth.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("ngaySinh"));
				// TODO Auto-generated method stub
				PagingWrapper<ArrayList<ItemNguoiViPham>> ls=(PagingWrapper<ArrayList<ItemNguoiViPham>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
				ObservableList<ItemNguoiViPham> data = FXCollections.observableArrayList(ls.getPage_data());
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
	
	class DeleteNguoiViPhamHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected  DeleteNguoiViPhamHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
				Notifycation.sucessed("Xóa người vi phạm thành công");
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
