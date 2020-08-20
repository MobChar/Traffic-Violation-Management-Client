package model;

import java.io.Serializable;
import java.sql.Date;

public class ItemBienBan implements Serializable{
	private Integer soBBVPHC,maCanBo;
	private String soCMND,moTaHanhVi,hoTenNguoiViPham,diaChi,sdt,hoTenCanBo;
	private Date ngayLap, ngaySinh;
	
	public ItemBienBan(Integer soBBVPHC,Integer maCanBo,String hoTenCanBo, Date ngayLap, String soCMND, String hoTenNguoiViPham, Date ngaySinh,String diaChi, String sdt,String moTaHanhVi)
	{
		this.soBBVPHC=soBBVPHC;
		this.maCanBo=maCanBo;
		this.ngayLap=ngayLap;
		this.soCMND=soCMND;
		this.hoTenCanBo=hoTenCanBo;
		this.hoTenNguoiViPham=hoTenNguoiViPham;
		this.ngaySinh=ngaySinh;
		this.diaChi=diaChi;
		this.sdt=sdt;
		this.moTaHanhVi=moTaHanhVi;
	}

	public Integer getSoBBVPHC() {
		return soBBVPHC;
	}

	public Integer getMaCanBo() {
		return maCanBo;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public String getMoTaHanhVi() {
		return moTaHanhVi;
	}

	

	public String getHoTenCanBo() {
		return hoTenCanBo;
	}

	public String getHoTenNguoiViPham() {
		return hoTenNguoiViPham;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public String getSdt() {
		return sdt;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}
	
	
	
}
