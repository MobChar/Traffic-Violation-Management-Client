package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.AddBBController.AddBBHandler;
import application.AddQDXPCBBController.AddXuPhatHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ItemDiem;
import model.ItemDieuLuat;
import model.ItemKhoan;

public class AddQDXPKCBBController {
	@FXML
	TextField textField_cmnd;
	@FXML
	DatePicker datePicker_ngayLap;
	@FXML
	AnchorPane anchorPane_themItemLayout;
	@FXML
	VBox vbox_xuPhatLayout;
	@FXML
	Button button_saveQD;
	@FXML
	ComboBox comboBox_dieuLuat;
	@FXML
	ComboBox comboBox_khoan;
	@FXML
	ComboBox comboBox_diem;
	@FXML
	TextField textField_soTienNop;
	@FXML
	Button button_add;
	@FXML
	Button button_luuXuPhat;

	@FXML
	AnchorPane anchorPane_xuPhat;
	@FXML
	Label label_diem;
	@FXML
	Label label_diem_value;
	@FXML
	Label label_khoan;
	@FXML
	Label label_khoan_value;
	@FXML
	Label label_dieuLuat;
	@FXML
	Label label_dieuLuat_value;
	@FXML
	Label label_soTien;
	@FXML
	Label label_soTien_value;
	@FXML
	Button button_deleteItem;

	private Stage root;
	private MenuQDController parent;
	List<ItemDieuLuat> dieuLuat_ls;

	private int idx_dieuLuat, idx_khoan, idx_diem;
	private int so_qdxphc;

	@FXML
	Button button_addNVP;

	public AddQDXPKCBBController(MenuQDController parent, Stage root) {
		this.root = root;
		this.parent = parent;
		root.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				// TODO Auto-generated method stub

				if (button_luuXuPhat.isDisable() == false) {
					arg0.consume();
					Notifycation.error("Hãy chọn lưu quyết định");
				}
			}
		});
	}

	@FXML
	private void initialize() {
		textField_cmnd.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find())
				textField_cmnd.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_cmnd.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		textField_soTienNop.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find())
				textField_soTienNop.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_soTienNop.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		NguoiViPhamAutoCompletion auto_cpl = new NguoiViPhamAutoCompletion(textField_cmnd);
		auto_cpl.bind();

		button_luuXuPhat.setDisable(true);
		datePicker_ngayLap.setValue(LocalDate.now());
		vbox_xuPhatLayout.getChildren().clear();

		anchorPane_themItemLayout.setDisable(true);
		button_saveQD.setOnAction((e) -> {
			boolean check = true;
			if (textField_cmnd.getText().isEmpty()) {
				check = false;
			}
			if (textField_cmnd.getText().length() != 9 && textField_cmnd.getText().length() != 12)
				check = false;
			if (!Notifycation.allNumberReg.matcher(textField_cmnd.getText()).find())
				check = false;

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");

				return;
			}

			try {
				MenuController.serverServices.addQDXPHCKhongCanBienBan(UserProfile.profile.getMaCanBo(),
						java.sql.Date.valueOf(datePicker_ngayLap.getValue()), textField_cmnd.getText(),
						new AddQDPHCKCBB());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}

		});

		button_add.setOnAction((e) -> {
			if (vbox_xuPhatLayout.getChildren().size() >= 5) {
				Notifycation.error("Chỉ nhập tối đa 5 xử phạt");
				return;
			}
			// Check input
			idx_dieuLuat = comboBox_dieuLuat.getSelectionModel().getSelectedIndex();
			idx_khoan = comboBox_khoan.getSelectionModel().getSelectedIndex();
			idx_diem = comboBox_diem.getSelectionModel().getSelectedIndex();

			boolean check = true;
			if (idx_dieuLuat == -1 || idx_khoan == -1 || idx_diem == -1 || textField_soTienNop.getText().isEmpty())
				check = false;
			if (!Notifycation.allNumberReg.matcher(textField_soTienNop.getText()).find())
				check = false;
			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
				return;
			}

			vbox_xuPhatLayout.getChildren().add(addItemXuPhat());

		});

		button_addNVP.setOnAction((e) -> {
			((MenuNVPController) MenuController.nvp_loader.getController()).initAdd(textField_cmnd);
		});

		// luat picker

		comboBox_dieuLuat.setOnAction((e) -> {
			idx_dieuLuat = comboBox_dieuLuat.getSelectionModel().getSelectedIndex();

			if (idx_dieuLuat == -1)
				return;

			List<ItemKhoan> k_ls = dieuLuat_ls.get(idx_dieuLuat).getKhoan_ls();
			List<String> items = new ArrayList<String>();
			for (ItemKhoan khoan : k_ls) {
				items.add(khoan.getMoTa());
			}

			comboBox_khoan.setItems(FXCollections.observableList(items));
			comboBox_khoan.getSelectionModel().selectFirst();
		});

		comboBox_khoan.setOnAction((e) -> {
			idx_khoan = comboBox_khoan.getSelectionModel().getSelectedIndex();

			if (idx_khoan == -1)
				return;

			List<ItemDiem> d_ls = dieuLuat_ls.get(idx_dieuLuat).getKhoan_ls().get(idx_khoan).getDiem_ls();
			List<String> items = new ArrayList<String>();
			for (ItemDiem diem : d_ls) {
				items.add(diem.getMoTa());
			}

			comboBox_diem.setItems(FXCollections.observableList(items));
			comboBox_diem.getSelectionModel().selectFirst();
		});

		////
		// Luu xu phat
		button_luuXuPhat.setOnAction((e) -> {
			if (vbox_xuPhatLayout.getChildren().size() == 0) {
				Notifycation.error("Phải nhập ít nhất 1 xử phạt");
				return;
			}
			try {

				AddXuPhatHandler handler = new AddXuPhatHandler();
				for (Node nod : vbox_xuPhatLayout.getChildren()) {

					AnchorPane pane = (AnchorPane) nod;

					Integer diem, khoan, dieuLuat, mucPhat;
					try {
						diem = Integer.parseInt(((Label) pane.getChildren().get(1)).getText());
						khoan = Integer.parseInt(((Label) pane.getChildren().get(3)).getText());
						dieuLuat = Integer.parseInt(((Label) pane.getChildren().get(5)).getText());
						mucPhat = Integer.parseInt(((Label) pane.getChildren().get(7)).getText());
						MenuController.serverServices.addXuPhat(so_qdxphc, diem, khoan, dieuLuat, mucPhat, handler);
					} catch (NumberFormatException e2) {
						Notifycation.error("Dữ liệu nhập vào không hợp lệ");
					}

				}

			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
			}

		});

		try {
			MenuController.serverServices.findLuat(new FindLuatHandler());
		} catch (RemoteException e1) {
			Platform.runLater(() -> {
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	}

	private AnchorPane addItemXuPhat() {
		AnchorPane ret = new AnchorPane();
		ret.setPrefSize(anchorPane_xuPhat.getWidth(), anchorPane_xuPhat.getHeight());

		Label diem = new Label(label_diem.getText());
		diem.setLayoutX(label_diem.getLayoutX());
		diem.setLayoutY(label_diem.getLayoutY());
		diem.setStyle(label_diem.getStyle());

		Label diem_value = new Label(String.valueOf(
				dieuLuat_ls.get(idx_dieuLuat).getKhoan_ls().get(idx_khoan).getDiem_ls().get(idx_diem).getMaDiem()));
		diem_value.setLayoutX(label_diem_value.getLayoutX());
		diem_value.setLayoutY(label_diem_value.getLayoutY());
		diem_value.setStyle(label_diem_value.getStyle());

		Label khoan = new Label(label_khoan.getText());
		khoan.setLayoutX(label_khoan.getLayoutX());
		khoan.setLayoutY(label_khoan.getLayoutY());
		khoan.setStyle(label_khoan.getStyle());

		Label khoan_value = new Label(String.valueOf(
				dieuLuat_ls.get(idx_dieuLuat).getKhoan_ls().get(idx_khoan).getDiem_ls().get(idx_diem).getMaKhoan()));
		khoan_value.setLayoutX(label_khoan_value.getLayoutX());
		khoan_value.setLayoutY(label_khoan_value.getLayoutY());
		khoan_value.setStyle(label_khoan_value.getStyle());

		Label dieuLuat = new Label(label_dieuLuat.getText());
		dieuLuat.setLayoutX(label_dieuLuat.getLayoutX());
		dieuLuat.setLayoutY(label_dieuLuat.getLayoutY());
		dieuLuat.setStyle(label_dieuLuat.getStyle());

		Label dieuLuat_value = new Label(String.valueOf(dieuLuat_ls.get(idx_dieuLuat).getMaDieuLuat()));
		dieuLuat_value.setLayoutX(label_dieuLuat_value.getLayoutX());
		dieuLuat_value.setLayoutY(label_dieuLuat_value.getLayoutY());
		dieuLuat_value.setStyle(label_dieuLuat_value.getStyle());

		Label soTien = new Label(label_soTien.getText());
		soTien.setLayoutX(label_soTien.getLayoutX());
		soTien.setLayoutY(label_soTien.getLayoutY());
		soTien.setStyle(label_soTien.getStyle());

		Label soTien_value = new Label(textField_soTienNop.getText());
		soTien_value.setLayoutX(label_soTien_value.getLayoutX());
		soTien_value.setLayoutY(label_soTien_value.getLayoutY());
		soTien_value.setStyle(label_soTien_value.getStyle());

		Button delete = new Button(button_deleteItem.getText());
		delete.setLayoutX(button_deleteItem.getLayoutX());
		delete.setLayoutY(button_deleteItem.getLayoutY());
		delete.getStyleClass().add("delete");
		delete.setOnAction((e) -> {
			vbox_xuPhatLayout.getChildren().remove(ret);
		});

		ret.setStyle(anchorPane_xuPhat.getStyle());

		ret.getChildren().addAll(diem, diem_value, khoan, khoan_value, dieuLuat, dieuLuat_value, soTien, soTien_value,
				delete);
		return ret;

	}

	class FindLuatHandler extends UnicastRemoteObject implements CallBackHandler {

		protected FindLuatHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			List<ItemDieuLuat> dl_ls = (List<ItemDieuLuat>) payload;
			List<String> items = new ArrayList<String>();
			for (ItemDieuLuat dl : dl_ls) {
				items.add(dl.getMaDieuLuat() + "." + dl.getTenDieuLuat());
			}

			comboBox_dieuLuat.setItems(FXCollections.observableList(items));

			dieuLuat_ls = dl_ls;
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			Platform.runLater(() -> {
				Notifycation.error(message);
			});

		}

	}

	class AddQDPHCKCBB extends UnicastRemoteObject implements CallBackHandler {

		protected AddQDPHCKCBB() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			so_qdxphc = (int) payload;
			Platform.runLater(() -> {
				button_luuXuPhat.setDisable(false);

				Notifycation.sucessed("Tạo lập quyết định thành công");

				anchorPane_themItemLayout.setDisable(false);

				vbox_xuPhatLayout.setVisible(true);
				vbox_xuPhatLayout.getChildren().clear();
				button_saveQD.setDisable(true);

				MenuTVController aTVC = (MenuTVController) MenuController.tv_loader.getController();
				aTVC.preLoadQDXPHC((Integer) payload);

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

	class AddXuPhatHandler extends UnicastRemoteObject implements CallBackHandler {

		int i = 0;

		protected AddXuPhatHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			++i;
			if (i == vbox_xuPhatLayout.getChildren().size())
				Platform.runLater(() -> {
					Notifycation.sucessed("Lưu quyết định thành công");

					root.close();
					parent.updateTable();

				});
		}

		@Override
		public void errorCallBack(String message) throws RemoteException {
			// TODO Auto-generated method stub
			// Error khi chen vao
			Platform.runLater(() -> {
				Notifycation.error(message);
			});

		}

	}
}
