package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import server_services.ServerServices;

public class MenuController {
//	@FXML Tab tab_canBo;
//	@FXML Tab tab_nguoiViPham;
//	@FXML Tab tab_tangVat;
//	@FXML Tab tab_bienLai;
//	@FXML Tab tab_bienBan;
//	@FXML Tab tab_quyetDinh;
	@FXML Label label_canBoName;
	@FXML TabPane tabPane_mainPane;
	
	public static ServerServices serverServices;
	public static FXMLLoader cb_loader ;
	public static FXMLLoader nvp_loader ;
	public static FXMLLoader tv_loader;
	public static FXMLLoader bl_loader;
	public static FXMLLoader bb_loader;
	public static FXMLLoader qd_loader;
	public static FXMLLoader tk_loader;
	
	public static Tab tab_nguoiViPham;
	public Tab tab_canBo;
	Tab tab_tangVat;
	Tab tab_bienBan;
	Tab tab_quyetDinh;
	
	public static final String QUYEN_CB="CB",QUYEN_NVP="NVP",QUYEN_TV="TV",QUYEN_BL="BL",QUYEN_QD="QD",QUYEN_BB="BB",QUYEN_TK="TK";
	
	
	public  MenuNVPController changeToNVPTab() {
		tabPane_mainPane.getSelectionModel().select(tab_nguoiViPham);
		return (MenuNVPController) MenuController.nvp_loader.getController();
	}
	
	public  MenuCBController changeToCBTab() {
		tabPane_mainPane.getSelectionModel().select(tab_canBo);
		return (MenuCBController) MenuController.cb_loader.getController();
	}
	
	public  MenuTVController changeToTVTab() {
		tabPane_mainPane.getSelectionModel().select(tab_tangVat);
		return (MenuTVController) MenuController.tv_loader.getController();
	}
	
	public  MenuBBController changeToBBTab() {
		tabPane_mainPane.getSelectionModel().select(tab_bienBan);
		return (MenuBBController) MenuController.bb_loader.getController();
	}
	
	public  MenuQDController changeToQDTab() {
		tabPane_mainPane.getSelectionModel().select(tab_quyetDinh);
		return (MenuQDController) MenuController.qd_loader.getController();
	}
	
	



	@FXML
	private void initialize() throws IOException {
		label_canBoName.setText(UserProfile.profile.getTenCanBo());
		

		
	
//		arg0.setScene(new Scene(cb_loader.load()));
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_NVP)) {
			tab_nguoiViPham=new Tab("Người vi phạm");
			nvp_loader = new FXMLLoader(getClass().getResource("Menu_NVP.fxml"));
			nvp_loader.setController(new MenuNVPController());
			tab_nguoiViPham.setContent(nvp_loader.load());
			tabPane_mainPane.getTabs().add(tab_nguoiViPham);
		} 
//		arg0.setScene(new Scene(nvp_loader.load()));
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_TV)) {
			tab_tangVat=new Tab("Tang vật");
			tv_loader = new FXMLLoader(getClass().getResource("Menu_TangVat.fxml"));
			tv_loader.setController(new MenuTVController());
			tab_tangVat.setContent(tv_loader.load());
			tabPane_mainPane.getTabs().add(tab_tangVat);
		} 
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_BL)) {
			Tab tab_bienLai=new Tab("Biên lai");
			bl_loader = new FXMLLoader(getClass().getResource("Menu_BienLai.fxml"));
			bl_loader.setController(new MenuBLController());
			tab_bienLai.setContent(bl_loader.load());
			tabPane_mainPane.getTabs().add(tab_bienLai);
		} 
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_BB)) {
			tab_bienBan=new Tab("Biên bản");
			bb_loader = new FXMLLoader(getClass().getResource("Menu_BB.fxml"));
			bb_loader.setController(new MenuBBController());
			tab_bienBan.setContent(bb_loader.load());
			tabPane_mainPane.getTabs().add(tab_bienBan);
		}
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_QD)) {
			tab_quyetDinh=new Tab("Quyết định");
			qd_loader = new FXMLLoader(getClass().getResource("Menu_QD.fxml"));
			qd_loader.setController(new MenuQDController());
			tab_quyetDinh.setContent(qd_loader.load());
			tabPane_mainPane.getTabs().add(tab_quyetDinh);
		}
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_TK)) {
			Tab tab_thongKe=new Tab("Thống kê");
			tk_loader = new FXMLLoader(getClass().getResource("Statistical.fxml"));
			tk_loader.setController(new ThongKeController());
			tab_thongKe.setContent(tk_loader.load());
			tabPane_mainPane.getTabs().add(tab_thongKe);
		}
		
		
		if(UserProfile.profile.checkQuyenDoc(QUYEN_CB)) {
			tab_canBo=new Tab("Cán bộ");
			cb_loader = new FXMLLoader(getClass().getResource("Menu_CanBo.fxml"));
			cb_loader.setController(new MenuCBController());
			tab_canBo.setContent(cb_loader.load());
			tabPane_mainPane.getTabs().add(tab_canBo);
		}
		
	}
	
	
	
	
}
