package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import application.AddTVController.AddTangVatHandler;
import client_services.CallBackHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.ItemCanBo;
import model.ItemDonVi;
import model.ItemQueQuan;
import model.ItemTangVat;
import model.ThongTinTK;
import server_services.ServerServices;

public class ClientConfigure extends Application {

	Alert alert = new Alert(AlertType.INFORMATION);
	public static MenuController controller;

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		MenuController.serverServices = (ServerServices) Naming.lookup("rmi://localhost:1090/QLVPGT");// Káº¿t ná»‘i
																										// vá»›i serváº»
																										// nÃ¨, bá»‹ j
																										// váº­y ta :|
		launch(args);
	}

	Stage root;

	public void start(Stage arg0) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
		fxmlLoader.setController(new LoginController());
		arg0.setScene(new Scene(fxmlLoader.load()));
		arg0.setTitle("QLVPGT");
		arg0.show();

		Screen screen = Screen.getPrimary();
		root = arg0;

	}

	class LoginController {
		@FXML
		TextField TextField_Username;
		@FXML
		PasswordField TextField_Password;
		@FXML
		Button Button_login;

		@FXML
		private void initialize() {
			TextField_Username.setText("admin");
			TextField_Password.setText("admin");

			Button_login.setOnAction((e) -> {
				try {
					MenuController.serverServices.dangNhap(TextField_Username.getText(), TextField_Password.getText(),
							new LoginHandler());

				} catch (RemoteException e1) {
					System.out.println(e1.getMessage());
					Platform.runLater(() -> {
						Notifycation.error("Lỗi kết nối tới server");
					});
				}
			});
		}
	}

	class LoginHandler extends UnicastRemoteObject implements CallBackHandler {

		protected LoginHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			UserProfile.profile = (ThongTinTK) payload;
			Platform.runLater(() -> {
				Notifycation.sucessed("Đăng nhập thành công");

				root.close();

				Screen screen = Screen.getPrimary();
				Stage menuStage = new Stage();
				Rectangle2D bounds = screen.getVisualBounds();

				menuStage.setMaximized(true);
				menuStage.setX(bounds.getMinX());
				menuStage.setY(bounds.getMinY());

				menuStage.setTitle("QLVPGT");
				FXMLLoader menu = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
				menu.setController(controller = new MenuController());
				try {
					menuStage.setScene(new Scene(menu.load()));
					menuStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				Notifycation.error("Đăng nhập thất bại");
			});
		}

	}

}