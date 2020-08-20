package server_services;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;

import client_services.CallBackHandler;


public interface ServerServices extends Remote{
	public void findQueQuan(String reg_name,CallBackHandler handler) throws RemoteException;
	public void findDonVi(String reg_name,CallBackHandler handler) throws RemoteException;
	public void findKhuVuc(String reg_name,CallBackHandler handler) throws RemoteException;
	public void addCanBo(String so_cmnd,String ho_ten, Date ngay_sinh, String chuc_vu,Integer ma_que_quan,
	Integer ma_don_vi,String ghi_chu,CallBackHandler handler) throws RemoteException;
	public void modifyCanBo(
			Integer ma_can_bo,
			String so_cmnd ,
		    String ho_ten ,
		    Date ngay_sinh,
		    String chuc_vu ,
		    Integer ma_que_quan,
		    Integer ma_don_vi,
		    String ghi_chu,
		    CallBackHandler handler
	) throws RemoteException;
	
	public void findCanBo(String reg_name,Integer page_offset,CallBackHandler handler) throws RemoteException;
	public void findCoSoLuuTru(String reg_name, CallBackHandler handler) throws RemoteException;
	
	public void addNguoiViPham(
		    String  so_cmnd,
		    String ho_ten,
		    Date ngay_sinh,
		    String dia_chi,
		    String so_dien_thoai,
		    CallBackHandler handler
	)throws RemoteException;
	
	public void modifyNguoiViPham(
		    String  so_cmnd,
		    String ho_ten,
		    Date ngay_sinh,
		    String dia_chi,
		    String so_dien_thoai,
		    CallBackHandler handler
	)throws RemoteException;
	
	public void findNguoiViPhamTheoCMND(String cmnd,Integer page_offset, CallBackHandler handler) throws RemoteException;
	public void findNguoiViPhamTheoHoTen(String ho_ten, CallBackHandler handler) throws RemoteException;
	public void addTangVatBBVPHC(Integer so_bbvphc,String ten_tang_vat, Integer so_luong, String trang_thai, Integer co_so_luu_tru, String ghi_chu,CallBackHandler handler) throws RemoteException;
	public void addTangVatQDXPHC(Integer so_qdxphc,String ten_tang_vat, Integer so_luong, String trang_thai, Integer co_so_luu_tru, String ghi_chu,CallBackHandler handler) throws RemoteException;
	public void findTangVatTheoSoQDXPHC(String so_qdxphc,Integer page_offset, CallBackHandler handler) throws RemoteException;
	public void findTangVatTheoSoBBVPHC(String so_bbvphc,Integer page_offset, CallBackHandler handler) throws RemoteException;
	public void findTatCaTangVat(Integer page_offset, CallBackHandler handler) throws RemoteException;
	public void modifyTangVatBBVPHC(Integer ma_tang_vat,Integer so_bbvphc,String ten_tang_vat, Integer so_luong, String trang_thai, Integer co_so_luu_tru, String ghi_chu,CallBackHandler handler) throws RemoteException;
	public void modifyTangVatQDXPHC(Integer ma_tang_vat,Integer so_qdxphc,String ten_tang_vat, Integer so_luong, String trang_thai, Integer co_so_luu_tru, String ghi_chu,CallBackHandler handler) throws RemoteException;
	public void deleteTangVat(Integer ma_tang_vat,CallBackHandler handler) throws RemoteException;
	public void deleteCanBo(Integer ma_can_bo,CallBackHandler handler) throws RemoteException;
	public void deleteNguoiViPham(String so_cmnd,CallBackHandler handler) throws RemoteException;
	
	public void addBBVPHC(Integer ma_can_bo,
		    Date ngay_lap,
		    String so_cmnd,
		    String mo_ta_hanh_vi ,
		    CallBackHandler handler
	) throws RemoteException;//CallBackHanlder tra ve soBBVPHC vua lap
	public void modifyBBVPHC(
			Integer so_bbvphc,
			Integer ma_can_bo,
		    Date ngay_lap,
		    String so_cmnd,
		    String mo_ta_hanh_vi ,
		    CallBackHandler handler
	) throws RemoteException;
	
	public void findBienBanTheoCMND(
		String so_cmnd,
		Integer page_offset,
		CallBackHandler handler
	)throws RemoteException;
	public void findBienBanTheoSoBBVPHC(
		String so_bbvphc,
		Integer page_offset,
		CallBackHandler handler
	)throws RemoteException;
	public void addQDXPHCKhongCanBienBan(
			Integer ma_can_bo,
		    Date ngay_lap,
		    String so_cmnd,
		    CallBackHandler handler
	)throws RemoteException;//CallBackHandler tra ve soQDXPHC vua lap
	public void addQDXPHCK(
		Integer so_bbvphc,
		Integer ma_can_bo,
	    Date ngay_lap,
//	    String so_cmnd,/So cmnd dc lay tu tien ban
	    CallBackHandler handler
	)throws RemoteException;
	public void findQuyetDinhTheoCMND(
		String so_cmnd,
		Integer page_offset,
		CallBackHandler handler
	)throws RemoteException;
	public void findQuyetDinhTheoSoQDXPHC(
		String so_qdxphc,
		Integer page_offset,
		CallBackHandler handler
	)throws RemoteException;
	public void findLuat(CallBackHandler handler)throws RemoteException;
	
	public void addXuPhat(
			Integer so_qdxphc,
		    Integer diem_vp,
		    Integer khoan_vp,
		    Integer dieu_luat_vp,
		    Integer muc_phat,
		    CallBackHandler handler
	)throws RemoteException;
	public void sumXuPhat(Integer so_qdxphc, CallBackHandler handler) throws RemoteException;
	
	public void addBienLaiNopPhat(
			Integer so_qdxphc,
			Integer ma_can_bo,
		    String ho_ten,
		    Date ngay_lap_bien_lai,
		    String ly_do_nop_phat,
		    Integer so_tien_nop,
		    CallBackHandler handler
	) throws RemoteException;
	public void modifyBienLaiNopPhat(
			Integer ma_bien_lai,
			Integer so_qdxphc,
			Integer ma_can_bo,
		    String ho_ten,
		    Date ngay_lap_bien_lai,
		    String ly_do_nop_phat,
		    Integer so_tien_nop,
		    CallBackHandler handler
	)throws RemoteException;
	public void findBienLaiNopPhatTheoTenCanBo(
			String reg_name,
			Integer page_offset,
			CallBackHandler handler
	)throws RemoteException;
	
	public void dangNhap(
			String tai_khoan,
			String mat_khau,
			CallBackHandler handler
	)throws RemoteException;
	
	public void thongKe(Integer year, Integer ma_khu_vuc,CallBackHandler handler) throws RemoteException;
	
}
