package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import application.BienBanAutoCompletion.FindBBHandler;
import application.MenuQDController.FindQDHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ItemBienBan;
import model.ItemQuyetDinh;
import model.PagingWrapper;

public class QuyetDinhAutoCompletion {
	public static final int MAX_SUGGEST=5;
	private TextField root;
	private OptionBox box=new OptionBox();
	private Label hoTen, soTien;
	public QuyetDinhAutoCompletion(TextField root,Label hoTen, Label soTien) {
		this.root=root;
		this.hoTen=hoTen;
		this.soTien=soTien;
	}
	public void bind() {
		root.textProperty().addListener((observable, oldValue, newValue) -> {
				try {
					MenuController.serverServices.findQuyetDinhTheoSoQDXPHC(root.getText(),0,new  FindQDHandler());
			
	//			String nameCB = TextField_Search.getText();
	//			FindCanBoHandler findcb = new FindCanBoHandler();
	//			MenuController.serverServices.findCanBo(nameCB,findcb);
				
				
			} catch (RemoteException e1) {
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
		});
	}
	
	
	class FindQDHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected FindQDHandler () throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}



		@Override
		public void callBack(Object payload) throws RemoteException {
			//Vd: Column_CMND;
			Platform.runLater(()->{
				PagingWrapper<ArrayList<ItemQuyetDinh>> ls=(PagingWrapper<ArrayList<ItemQuyetDinh>>) payload;
				
				
				
			
				box.create(Math.min(MAX_SUGGEST,  ls.getPage_data().size()));
				for(int i=0;i<Math.min(MAX_SUGGEST, ls.getPage_data().size());i++) {
					box.getBox(i).setText(ls.getPage_data().get(i).getSoQDXPHC()+"-"+ls.getPage_data().get(i).getHoTenCanBo());
					ls.getPage_data().get(i).tinhSoTienCanNop();
					Integer k=i;
					box.getBox(i).setOnAction((e)->{
						box.hide();
						root.setText(ls.getPage_data().get(k).getSoQDXPHC()+"");
						hoTen.setText(ls.getPage_data().get(k).getHoTenCanBo());
						soTien.setText(ls.getPage_data().get(k).getSoTienCanNop()+"");
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
