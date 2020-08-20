package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;

import application.AddTVController.AddTangVatHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class AddBBController {
	@FXML
	DatePicker DatePicker_DateFoundBBADD;
	@FXML
	TextArea TextArea_DecriptionBBADD;
	@FXML
	Button Button_Accept;
	@FXML
	TextField TextField_CMNDBBADD;

	@FXML
	Button button_addNVP;

	private Stage root;
	private MenuBBController parent;

	public AddBBController(MenuBBController parent, Stage root) {
		this.root = root;
		this.parent = parent;

	}

	@FXML
	private void initialize() {
		TextField_CMNDBBADD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find()
					|| (newValue.length() != 9 && newValue.length() != 12))
				TextField_CMNDBBADD.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				TextField_CMNDBBADD.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		NguoiViPhamAutoCompletion auto_cpl = new NguoiViPhamAutoCompletion(TextField_CMNDBBADD);
		auto_cpl.bind();

		DatePicker_DateFoundBBADD.setValue(LocalDate.now());
		Button_Accept.setOnAction((e) -> {
			boolean check = true;
			if (TextArea_DecriptionBBADD.getText().isEmpty() || TextField_CMNDBBADD.getText().isEmpty())
				check = false;
			if (TextField_CMNDBBADD.getText().length() != 9 && TextField_CMNDBBADD.getText().length() != 12)
				check = false;

			if (!Notifycation.allNumberReg.matcher(TextField_CMNDBBADD.getText()).find())
				check = false;

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}

			try {
				MenuController.serverServices.addBBVPHC(UserProfile.profile.getMaCanBo(),
						java.sql.Date.valueOf(DatePicker_DateFoundBBADD.getValue()), TextField_CMNDBBADD.getText(),
						TextArea_DecriptionBBADD.getText(), new AddBBHandler());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}
		});

		button_addNVP.setOnAction((e) -> {
			((MenuNVPController) MenuController.nvp_loader.getController()).initAdd(TextField_CMNDBBADD);
		});
	}

	class AddBBHandler extends UnicastRemoteObject implements CallBackHandler {

		protected AddBBHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(() -> {
				Notifycation.sucessed("Thêm biên bản thành công");

				MenuTVController aTVC = (MenuTVController) MenuController.tv_loader.getController();
				aTVC.preLoadBBVPHC((Integer) payload);

				root.close();
				parent.updateTable();
			});
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				Notifycation.error(message);
			});

		}

	}

}
