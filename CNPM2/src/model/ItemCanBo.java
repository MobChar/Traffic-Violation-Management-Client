package model;

import java.io.Serializable;
import java.sql.Date;

public class ItemCanBo implements Serializable{
	private Integer maCanBo,maQueQuan,maDonVi;
	private String tenQueQuan, tenDonVi;
	private String cmnd, hoTen, chucVu, ghiChu;
	private Date ngaySinh;
	
	
	public ItemCanBo(Integer maCanBo, Integer  maQueQuan, Integer maDonVi,
					String tenQueQuan, String tenDonVi, String cmnd, String hoTen, String chucVu, String ghiChu,
					Date ngaySinh
	) 
	{
		this.maCanBo=maCanBo;
		this.maQueQuan=maQueQuan;
		this.maDonVi=maDonVi;
		this.tenQueQuan=tenQueQuan;
		this.tenDonVi=tenDonVi;
		this.cmnd=cmnd;
		this.hoTen=hoTen;
		this.chucVu=chucVu;
		this.ghiChu=ghiChu;
		this.ngaySinh=ngaySinh;
		
	}
	
	
	
	public Integer getMaCanBo() {
		return maCanBo;
	}
	public Integer getMaQueQuan() {
		return maQueQuan;
	}
	public Integer getMaDonVi() {
		return maDonVi;
	}
	public String getTenQueQuan() {
		return tenQueQuan;
	}
	public String getTenDonVi() {
		return tenDonVi;
	}
	public String getCmnd() {
		return cmnd;
	}
	public String getHoTen() {
		return hoTen;
	}
	public String getChucVu() {
		return chucVu;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	
	@Override
	public String toString() { //Cái server k có cái này phải qua server thêm vào
		return maCanBo+" - "+hoTen;//Thêm hết vô tới hồi m làm combobõ co dễ
	}
	
	
}
