package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.ItemQuyetDinh;
import model.ItemXuPhat;

public class DetailQDController {
	@FXML
	TextArea textArea_ghiChu;
	@FXML
	Text text_QD;

	@FXML
	Text text_BB;
	@FXML
	Text text_hoTenCanBo;

	@FXML
	Text text_ngayLap;
	@FXML
	Text text_hanNop;

	@FXML
	Text text_diaChi;
	@FXML
	Text text_ngaySinh;
	@FXML
	Text text_hoTenNguoiViPham;
	@FXML
	Text text_soCMND;
	@FXML
	Text text_soDienThoai;

	@FXML
	Label text_sum;

	@FXML
	TableColumn col_diem;
	@FXML
	TableColumn col_khoan;
	@FXML
	TableColumn col_dieuLuat;
	@FXML
	TableColumn col_soTien;
	@FXML
	TableView<ItemXuPhat> tableView_table;

	private ItemQuyetDinh qd;

	public DetailQDController(ItemQuyetDinh qd) {
		this.qd = qd;
	}

	@FXML
	private void initialize() {
		textArea_ghiChu.setText(qd.getGhiChu());
		text_QD.setText(qd.getSoQDXPHC() + "");
		text_BB.setText(qd.getSoBBVPHC() + "");
		text_hoTenCanBo.setText(qd.getHoTenCanBo());
		text_ngayLap.setText(qd.getNgayLap().toString());
		text_hanNop.setText(qd.getHanNop().toString());
		text_diaChi.setText(qd.getDiaChi());
		text_ngaySinh.setText(qd.getNgaySinh().toString());
		text_hoTenNguoiViPham.setText(qd.getHoTenNguoiViPham());
		text_soCMND.setText(qd.getSoCMND());
		text_soDienThoai.setText(qd.getSdt());

		text_sum.setText(qd.getSoTienCanNop() + "");

		col_diem.setCellValueFactory(new PropertyValueFactory<ItemXuPhat, String>("diem"));
		col_khoan.setCellValueFactory(new PropertyValueFactory<ItemXuPhat, String>("khoan"));
		col_dieuLuat.setCellValueFactory(new PropertyValueFactory<ItemXuPhat, String>("dieuLuat"));
		col_soTien.setCellValueFactory(new PropertyValueFactory<ItemXuPhat, String>("soTienNop"));

		ObservableList<ItemXuPhat> data = FXCollections.observableArrayList(qd.getXp_ls());
		tableView_table.setItems(data);

	}
}
