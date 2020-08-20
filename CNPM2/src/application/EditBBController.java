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
import model.ItemBienBan;
import javafx.scene.control.Alert.AlertType;

public class EditBBController {
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
	private ItemBienBan bb;

	public EditBBController(MenuBBController parent, Stage root, ItemBienBan bb) {
		this.root = root;
		this.parent = parent;
		this.bb = bb;
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

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}

			try {
				MenuController.serverServices.modifyBBVPHC(bb.getSoBBVPHC(), UserProfile.profile.getMaCanBo(),
						java.sql.Date.valueOf(DatePicker_DateFoundBBADD.getValue()), TextField_CMNDBBADD.getText(),
						TextArea_DecriptionBBADD.getText(), new EditBBHandler());
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

		loadBienBan();
	}

	public void loadBienBan() {
		DatePicker_DateFoundBBADD.setValue(bb.getNgayLap().toLocalDate());
		TextArea_DecriptionBBADD.setText(bb.getMoTaHanhVi());
		TextField_CMNDBBADD.setText(bb.getSoCMND());
	}

	class EditBBHandler extends UnicastRemoteObject implements CallBackHandler {

		protected EditBBHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			Platform.runLater(() -> {
				Notifycation.sucessed("Hiệu chỉnh biên bản thành công");

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
