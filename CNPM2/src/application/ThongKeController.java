package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import application.AddCBController.FindDonViHandler;
import application.AddCBController.FindQueQuanHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.ItemDonVi;
import model.ItemKhuVuc;
import model.ItemThongKe;

public class ThongKeController {
	@FXML BarChart<String,Number> barChart_thongKe;
	@FXML ComboBox comboBox_khuVuc;
	@FXML ComboBox comboBox_nam;
	@FXML Button button_thongKe;
	
	@FXML
	private void initialize()
	{	

			comboBox_nam.setItems(FXCollections.observableArrayList(2015,2016,2017,2018,2019,2020));
			comboBox_nam.getSelectionModel().selectLast();
			button_thongKe.setOnAction((e)->{
				refesh();
			});

			try {
				MenuController.serverServices.findKhuVuc("",new  FindKhuVucHandler());
				
					
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				Platform.runLater(()->{
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
	}
	
	
	void refesh() {
		try {
			
			if(comboBox_khuVuc.getValue().toString().equals("Tất cả"))
			MenuController.serverServices.thongKe(Integer.parseInt(comboBox_nam.getValue().toString()),0,new ThongKeHandler());
			else
			MenuController.serverServices.thongKe(Integer.parseInt(comboBox_nam.getValue().toString()),Integer.parseInt(comboBox_khuVuc.getValue().toString().split("-")[0].trim()),new ThongKeHandler());
			
				
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			Platform.runLater(()->{
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	}
	
	
	class FindKhuVucHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected  FindKhuVucHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			List<ItemKhuVuc> ls=(List<ItemKhuVuc>) payload;
			List<String> sls=new ArrayList<String>();
			sls.add("Tất cả");
			for(ItemKhuVuc it: ls) {
				sls.add(it.toString());
			}
			Platform.runLater(()->{
				comboBox_khuVuc.setItems(FXCollections.observableArrayList(sls));
				comboBox_khuVuc.getSelectionModel().selectFirst();
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
	
	
	class ThongKeHandler extends  UnicastRemoteObject implements CallBackHandler{

		protected  ThongKeHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}


		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			List<ItemThongKe> ls=(List<ItemThongKe>) payload;
			Platform.runLater(()->{
				
				barChart_thongKe.getData().clear();
				XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
				dataSeries1.setName("Biên bản");
				for(ItemThongKe it:ls) {
				   
				   dataSeries1.getData().add(new XYChart.Data<String, Number>("Tháng "+it.getThang(), it.getSoLuongBB()));
				  
				}
				
				 
				barChart_thongKe.getData().add(dataSeries1);
				   
				XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
				dataSeries2.setName("Quyết định");
				for(ItemThongKe it:ls) {
					   
					dataSeries2.getData().add(new XYChart.Data<String, Number>("Tháng "+it.getThang(), it.getSoLuongQD()));

					  
				}
					
					 
				barChart_thongKe.getData().add(dataSeries2);
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
