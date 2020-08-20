package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;



import application.MenuNVPController.FindNguoiViPhamHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemCanBo;
import model.ItemNguoiViPham;
import model.PagingWrapper;

public class NguoiViPhamAutoCompletion {
	public static final int MAX_SUGGEST=5;
	private TextField root;
	private OptionBox box=new OptionBox();
	public NguoiViPhamAutoCompletion(TextField root) {
		this.root=root;
	}
	public void bind() {
		root.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
	   			FindNguoiViPhamHandler findNVP = new FindNguoiViPhamHandler();
				MenuController.serverServices.findNguoiViPhamTheoCMND(root.getText(),0,findNVP);
				
			} catch (RemoteException e1) {
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
		});
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
				PagingWrapper<ArrayList<ItemNguoiViPham>> ls=(PagingWrapper<ArrayList<ItemNguoiViPham>>) payload;
				
				
				
			
				box.create(Math.min(MAX_SUGGEST,  ls.getPage_data().size()));
				for(int i=0;i<Math.min(MAX_SUGGEST, ls.getPage_data().size());i++) {
					box.getBox(i).setText(ls.getPage_data().get(i).getSoCMND()+"-"+ls.getPage_data().get(i).getHoTen());
					
					Integer k=i;
					box.getBox(i).setOnAction((e)->{
						box.hide();
						root.setText(ls.getPage_data().get(k).getSoCMND());
					});
				}
				Point2D des=root.localToScreen(0,root.getHeight()+5);
				box.show(root,des.getX(),des.getY());
//				TextFields.bindAutoCompletion(root,cpls);
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
