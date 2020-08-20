package model;

import java.io.Serializable;
import java.sql.Date;

public class ItemBienLai implements Serializable{
	private Integer maBienLai,soQDXPHC,maCanBo;
	private Date ngayLapBienLai;
	String hoTenCanBo,hoTenNguoiNopTien,lyDoNopPhat;
	private Integer soTienNop;
	
	public ItemBienLai(Integer maBienLai, Integer soQDXPHC,Date ngayLapBienLai,Integer maCanBo,String hoTenCanBo,String hoTenNguoiNopTien,String lyDoNopPhat,Integer soTienNop)
	{
		this.maBienLai=maBienLai;
		this.soQDXPHC=soQDXPHC;
		this.maCanBo=maCanBo;
		this.hoTenCanBo=hoTenCanBo;
		this.hoTenNguoiNopTien=hoTenNguoiNopTien;
		this.lyDoNopPhat=lyDoNopPhat;
		this.soTienNop=soTienNop;
		this.ngayLapBienLai=ngayLapBienLai;
	}

	public Integer getMaBienLai() {
		return maBienLai;
	}

	public Integer getSoQDXPHC() {
		return soQDXPHC;
	}

	public Integer getMaCanBo() {
		return maCanBo;
	}

	public String getHoTenCanBo() {
		return hoTenCanBo;
	}

	public String getHoTenNguoiNopTien() {
		return hoTenNguoiNopTien;
	}

	public String getLyDoNopPhat() {
		return lyDoNopPhat;
	}

	public Integer getSoTienNop() {
		return soTienNop;
	}

	public Date getNgayLapBienLai() {
		return ngayLapBienLai;
	}
	
	
	

}
