package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import application.AddCBController.FindDonViHandler;
import application.AddCBController.FindQueQuanHandler;
import client_services.CallBackHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ItemCoSoLuuTru;
import model.ItemDonVi;

public class AddTVController {
	@FXML
	Label label_maBB;
	@FXML
	TextField textField_ma;
	@FXML
	TextField textField_tenTV;
	@FXML
	TextField textField_trangThaiTV;
	@FXML
	TextField textField_soLuongTV;
	@FXML
	ComboBox comboBox_coSoTV;
	@FXML
	TextArea textArea_ghiChuTV;
	@FXML
	Button button_themTV;
	@FXML
	Button button_xoaTV;
	@FXML
	Button button_addAll;
	@FXML
	VBox vbox_layout;
	@FXML
	AnchorPane anchorPane_form;
	@FXML
	ScrollPane scrollPane_layout;

	private List<AddTVForm> input_values = new ArrayList<AddTVForm>();

	private Stage root;
	private MenuTVController parent;
	private Integer soBB;
	private boolean isQD;

	public AddTVController(MenuTVController parent, Stage root, Integer soBB, boolean isQD) {
		this.parent = parent;
		this.root = root;
		this.isQD = isQD;
		this.soBB = soBB;

	}

	@FXML
	private void initialize() {
		textField_ma.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find())
				textField_ma.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_ma.pseudoClassStateChanged(Notifycation.errorClass, false);
		});
		textField_soLuongTV.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Notifycation.allNumberReg.matcher(newValue).find())
				textField_soLuongTV.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_soLuongTV.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		textField_tenTV.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty() || Notifycation.containSpecialReg.matcher(newValue).find())
				textField_tenTV.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_tenTV.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		textField_trangThaiTV.textProperty().addListener((observable, oldValue, newValue) -> {
			if (Notifycation.containSpecialReg.matcher(newValue).find())
				textField_trangThaiTV.pseudoClassStateChanged(Notifycation.errorClass, true);
			else
				textField_trangThaiTV.pseudoClassStateChanged(Notifycation.errorClass, false);
		});

		if (!isQD) {
			label_maBB.setText("số BBVPHC");
			textField_ma.setText(soBB.toString());
		} else {
			label_maBB.setText("số QDXPHC");
			textField_ma.setText(soBB.toString());
		}
		button_xoaTV.setVisible(false);
		button_themTV.setOnAction((e) -> {
			// Check condition
			boolean check = true;
			if (textField_tenTV.getText().isEmpty() || textField_soLuongTV.getText().isEmpty()
					|| textField_trangThaiTV.getText().isEmpty())
				check = false;

			check &= !Notifycation.containSpecialReg.matcher(textField_tenTV.getText()).find()
					& !Notifycation.containSpecialReg.matcher(textField_trangThaiTV.getText()).find()
					& Notifycation.allNumberReg.matcher(textField_soLuongTV.getText()).find()
					& Notifycation.allNumberReg.matcher(textField_ma.getText()).find();

			if (!check) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");

				return;
			}

			AddTVForm form = createNewInputForm();
			form.lock();
			vbox_layout.getChildren().add(form);
			input_values.add(form);

		});

		button_addAll.setOnAction((e) -> {
			if (input_values.size() == 0) {
				Notifycation.error("Không có dữ liệu tang vật");
				return;
			}

			AddTangVatHandler handler = null;
			try {
				handler = new AddTangVatHandler();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				Platform.runLater(() -> {
					Notifycation.error("Lỗi kêt nối tới server");
				});
				return;

			}

			if (isQD == false)
				for (AddTVForm f : input_values) {
					try {
						MenuController.serverServices.addTangVatBBVPHC(Integer.parseInt(textField_ma.getText()),
								f.getTenTV(), f.getSoLuongTV(), f.getTrangThai(), f.getMaCoSo(), f.getGhiChu(),
								handler);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Platform.runLater(() -> {
							Notifycation.error("Lỗi kêt nối tới server");
						});
						return;
					} catch (NumberFormatException e2) {
						Notifycation.error("Dữ liệu nhập vào không hợp lệ");
					}
				}
			else
				for (AddTVForm f : input_values) {
					try {
						MenuController.serverServices.addTangVatQDXPHC(Integer.parseInt(textField_ma.getText()),
								f.getTenTV(), f.getSoLuongTV(), f.getTrangThai(), f.getMaCoSo(), f.getGhiChu(),
								handler);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Platform.runLater(() -> {
							Notifycation.error("Lỗi kêt nối tới server");
						});
						return;
					}
				}

		});

		// Pre load
		FindCoSoLuuTruHandler cslt;
		try {
			cslt = new FindCoSoLuuTruHandler();
			MenuController.serverServices.findCoSoLuuTru("", cslt);

		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Platform.runLater(() -> {
				Notifycation.error("Lỗi kêt nối tới server");
			});
		}
	}

	class FindCoSoLuuTruHandler extends UnicastRemoteObject implements CallBackHandler {
		public FindCoSoLuuTruHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			List<ItemCoSoLuuTru> ls = (List<ItemCoSoLuuTru>) payload;
			Platform.runLater(() -> {
				comboBox_coSoTV.setItems(FXCollections.observableArrayList(ls));
				comboBox_coSoTV.getSelectionModel().selectFirst();
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

	class AddTangVatHandler extends UnicastRemoteObject implements CallBackHandler {
		int i = 0;

		public AddTangVatHandler() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void callBack(Object payload) throws RemoteException {
			// TODO Auto-generated method stub
			++i;
			if (i == input_values.size())
				Platform.runLater(() -> {
					Notifycation.sucessed("Thêm tang vật thành công");
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

	class AddTVForm extends AnchorPane {
		public void lock() {
			ObservableList<Node> data = this.getChildren();
			for (Node n : data) {
				if (n instanceof Button)
					continue;
				n.setDisable(true);
			}
		}

		public String getTenTV() {
			return ((TextField) this.getChildren().get(0)).getText();
		}

		public Integer getSoLuongTV() {
			try {
				return Integer.parseInt(((TextField) this.getChildren().get(1)).getText());
			} catch (NumberFormatException e2) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
			}
			return 0;
		}

		public String getTrangThai() {
			return ((TextField) this.getChildren().get(2)).getText();
		}

		public Integer getMaCoSo() {
			try {
				return Integer
						.parseInt(((ComboBox) this.getChildren().get(3)).getValue().toString().split("-")[0].trim());
			} catch (NumberFormatException e2) {
				Notifycation.error("Dữ liệu nhập vào không hợp lệ");
			}
			return 0;
		}

		public String getGhiChu() {
			return ((TextArea) this.getChildren().get(4)).getText();
		}
	}

	private AddTVForm createNewInputForm() {
//		  ObservableList<Node> data =anchorPane_form.getChildren();
//		  ArrayList<Node> ls=new ArrayList<Node>(data);
//		  
//		  TextField tmp;
//		  
//		  tmp=(TextField)ls.get(0);

		AddTVForm ret = new AddTVForm();
		TextField tTV = new TextField();
		tTV.setPrefSize(textField_tenTV.getWidth(), textField_tenTV.getHeight());
		tTV.setLayoutX(textField_tenTV.getLayoutX());
		tTV.setLayoutY(textField_tenTV.getLayoutY());
		tTV.setText(textField_tenTV.getText());

//		  tmp=(TextField)ls.get(1);
		TextField slTV = new TextField();
		slTV.setPrefSize(textField_soLuongTV.getWidth(), textField_soLuongTV.getHeight());
		slTV.setLayoutX(textField_soLuongTV.getLayoutX());
		slTV.setLayoutY(textField_soLuongTV.getLayoutY());
		slTV.setText(textField_soLuongTV.getText());

//		  tmp=(TextField)ls.get(2);
		TextField ttTV = new TextField();
		ttTV.setPrefSize(textField_trangThaiTV.getWidth(), textField_trangThaiTV.getHeight());
		ttTV.setLayoutX(textField_trangThaiTV.getLayoutX());
		ttTV.setLayoutY(textField_trangThaiTV.getLayoutY());
		ttTV.setText(textField_trangThaiTV.getText());

//		  ComboBox tmpCb=(ComboBox)ls.get(3);
		ComboBox csTV = new ComboBox();
		csTV.setPrefSize(comboBox_coSoTV.getWidth(), comboBox_coSoTV.getHeight());
		csTV.setLayoutX(comboBox_coSoTV.getLayoutX());
		csTV.setLayoutY(comboBox_coSoTV.getLayoutY());
		csTV.setItems(FXCollections.observableArrayList(comboBox_coSoTV.getValue()));
		csTV.getSelectionModel().selectFirst();

//		  TextArea tmpTA=(TextArea)ls.get(4);
		TextArea gcTV = new TextArea();
		gcTV.setPrefSize(textArea_ghiChuTV.getWidth(), textArea_ghiChuTV.getHeight());
		gcTV.setLayoutX(textArea_ghiChuTV.getLayoutX());
		gcTV.setLayoutY(textArea_ghiChuTV.getLayoutY());
		gcTV.setText(textArea_ghiChuTV.getText());

		Button xBTV = new Button();
		xBTV.setPrefSize(button_xoaTV.getWidth(), button_xoaTV.getHeight());
		xBTV.setLayoutX(button_xoaTV.getLayoutX());
		xBTV.setLayoutY(button_xoaTV.getLayoutY());
		xBTV.setText(button_xoaTV.getText());
		xBTV.getStyleClass().add("delete");
		xBTV.setOnAction((e) -> {
			input_values.remove(ret);
			vbox_layout.getChildren().remove(ret);
		});

		ret.getChildren().addAll(tTV, slTV, ttTV, csTV, gcTV, xBTV);
		return ret;
	}

}
