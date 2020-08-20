package model;

import java.io.Serializable;
import java.sql.Date;

public class ItemNguoiViPham implements Serializable{
	private String soCMND,hoTen,diaChi,soDienThoai;
	private Date ngaySinh;
	public String getSoCMND() {
		return soCMND;
	}
	public String getHoTen() {
		return hoTen;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	
	public ItemNguoiViPham(String soCMND, String hoTen, Date ngaySinh, String diaChi, String soDienThoai) {
		this.soCMND=soCMND;
		this.hoTen=hoTen;
		this.diaChi=diaChi;
		this.soDienThoai=soDienThoai;
		this.ngaySinh=ngaySinh;
	}
}
