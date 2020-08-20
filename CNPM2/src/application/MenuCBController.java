package application;
	
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.MenuTVController.DeleteTangVatHandler;
import client_services.CallBackHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ItemCanBo;
import model.ItemTangVat;
import model.PagingWrapper;
import server_services.ServerServices;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

//java -jar D:\b.jar


public class MenuCBController{
	@FXML Pagination pagination_table;
	@FXML AnchorPane aa;
	@FXML
	public  TextField textField_Search;
	@FXML
	public  Button Button_Add;
	@FXML
	public  Button Button_Edit;
	


	@FXML
	public  Button Button_Delete;
	@FXML
	public  TextField BB_TextField_Search;
	@FXML
	public  TextField TextField_NameADD;
	@FXML
	public  ComboBox ComboxBox_QueQuanADD;
	@FXML
	public  TextField TextField_DonViADD;
	@FXML
	public  TextField TextField_PositionADD;
	@FXML
	public  TextField TextField_KhuVucADD;
	@FXML
	public  TextArea TextArea_GhichuADD;
	@FXML
	public  DatePicker DatePicker_DateBirthADD;
	@FXML
	private TableColumn Column_IdCB;
	@FXML
	private TableView<ItemCanBo> TableView_1;
	@FXML
	private TableColumn Column_Name; // cáº§n Ä‘áº·t giÃ¡ trá»‹ Ä‘Ã¢y khÃ´ng? tnghÄ© cháº¯c k Ä‘Ã¢u thá»­ map háº¿t thá»­u coi
	@FXML
	private TableColumn Column_CMND;
	@FXML
	private TableColumn Column_DateBirth;
	@FXML
	private TableColumn Column_QueQuan;
	
	@FXML
	private TableColumn Column_Position;
	@FXML
	private TableColumn Column_Unit;
	@FXML
	private TableColumn Column_Note;
	static ServerServices serverServices;
	
	
	public TextField getTextField_Search() {
		return textField_Search;
	}

	@FXML
	private void initialize() {
		if(!UserProfile.profile.checkQuyenViet(MenuController.QUYEN_CB)) {
			Button_Add.setDisable(true);
			Button_Edit.setDisable(true);
			Button_Delete.setDisable(true);
		}
		textField_Search.textProperty().addListener((observable, oldValue, newValue) -> {
			
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
			 Stage AddCB = new Stage();
			 AddCB.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddingCB.fxml"));
				fxmlLoader.setController(new AddCBController(this,AddCB));
				AddCB.setScene(new Scene(fxmlLoader.load()));
				AddCB.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button_Edit.setOnAction((e)->{
			ItemCanBo itCanBo=TableView_1.getSelectionModel().getSelectedItem();
			if(itCanBo==null) {
				Notifycation.error("Hãy chọn một cán bộ để hiệu chỉnh");
				return;
			}
			
			Stage EditCB = new Stage();
			EditCB.initModality(Modality.APPLICATION_MODAL);
			 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditingCB.fxml"));
				fxmlLoader.setController(new EditCBController(this,EditCB,itCanBo));
				EditCB.setScene(new Scene(fxmlLoader.load()));
				EditCB.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button_Delete.setOnAction((e)->{
			ItemCanBo itCanBo=TableView_1.getSelectionModel().getSelectedItem();
			if(itCanBo==null) {
				Notifycation.error("Hãy chọn một cán bộ để xóa");	
				return;
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Delete Cán Bộ");
	        alert.setHeaderText("Bạn có chắc muốn xóa cán bộ này chứ");
	      
	        // option != null.
	        Optional<ButtonType> option = alert.showAndWait();
	 
	        if (option.get() == null) {
	           
	        } else if (option.get() == ButtonType.OK) {
	        	try {
					MenuController.serverServices.deleteCanBo(itCanBo.getMaCanBo(), new DeleteCanBoHandler());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					Platform.runLater(()->{
						Notifycation.error("Lỗi kêt nối tới server");
					});
				}
	        	
	        } else if (option.get() == ButtonType.CANCEL) {
	            
	        }
			
			
			
			
		});
		
		
		
		
		//Load data
		refeshTable(0);
	
	}
	
	private  void refeshTable(int page_index) {//Tim kiem only
		try {
			FindCanBoHandler findcb = new FindCanBoHandler();
			MenuController.serverServices.findCanBo(textField_Search.getText(),page_index,findcb);
			
		} catch (RemoteException e1) {
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	}
	
	public void updateTable() {//Add, Delete, Modify update
		refeshTable(pagination_table.getCurrentPageIndex());
	}
	class FindCanBoHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindCanBoHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: 
			//Set Collumn_Name vá»›i dá»¯ liá»‡u cÃ³ tÃªn lÃ  hoTen
			Platform.runLater(()->{
				Column_Name.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("hoTen")); // hay pháº£i theo thá»© tá»±?, m Ä‘Ã£ map vÃ´ cá»™t rá»“i thÃ¬ Ä‘au cáº§n thá»© tá»±
				// nÃ³ Ä‘Ã¢u phÃ¢n biá»‡t dá»¯ liá»‡u vÃ o lÃ  cá»™t nÃ o Ä‘Ã¢u?
				Column_IdCB.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("maCanBo"));
				Column_QueQuan.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("tenQueQuan"));
				Column_Position.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("chucVu"));
				Column_Unit.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("tenDonVi"));
				Column_CMND.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("cmnd"));
				Column_DateBirth.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("ngaySinh"));
				Column_Note.setCellValueFactory(new PropertyValueFactory<ItemCanBo,String>("ghiChu"));
				// TODO Auto-generated method stub
				PagingWrapper<ArrayList<ItemCanBo>> ls=(PagingWrapper<ArrayList<ItemCanBo>>) payload;//M bá»� cÃ¡n bá»™ vÃ o Ä‘Ã¢u, táº¡o cÃ¡i Row rá»“i bá»� vÃ o tá»«ng cell
				ObservableList<ItemCanBo> data = FXCollections.observableArrayList(ls.getPage_data());
				
				
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
	
	
	
	class DeleteCanBoHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected DeleteCanBoHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
				Notifycation.sucessed("Xóa cán bộ thành công");
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


