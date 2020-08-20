package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ItemQuyetDinh implements Serializable{
	private Integer soQDXPHC,soBBVPHC,maCanBo;
	private String hoTenCanBo,soCMND,hoTenNguoiViPham;
	private Date ngayLap,ngaySinh,hanNop;
	private String diaChi;
	private String ghiChu,sdt;
	private Integer soTienCanNop;
	private List<ItemXuPhat> xp_ls;
	
	
	public ItemQuyetDinh(
		Integer soQDXPHC,
		Integer soBBVPHC,
		Integer maCanBo,
		String hoTenCanBo,
		String soCMND,
		String hoTenNguoiViPham,
		Date ngayLap, 
		Date ngaySinh, 
		Date hanNop,
		String diaChi,
		String sdt,
		String ghiChu
	) {
		this.soQDXPHC=soQDXPHC;
		this.soBBVPHC=soBBVPHC;
		this.maCanBo=maCanBo;
		this.hoTenCanBo=hoTenCanBo;
		this.soCMND=soCMND;
		this.hoTenNguoiViPham=hoTenNguoiViPham;
		this.ngayLap=ngayLap;
		this.ngaySinh=ngaySinh;
		this.hanNop=hanNop;
		this.diaChi=diaChi;
		this.ghiChu=ghiChu;
		this.sdt=sdt;
		
		 xp_ls=new ArrayList<ItemXuPhat>();
		
	}
	
	public void tinhSoTienCanNop() {
		this.soTienCanNop=0;
		for(ItemXuPhat itxp: xp_ls) {
			this.soTienCanNop+=itxp.soTienNop;
		}
	}
	


	public Integer getSoQDXPHC() {
		return soQDXPHC;
	}

	public Integer getSoBBVPHC() {
		return soBBVPHC;
	}

	public Integer getMaCanBo() {
		return maCanBo;
	}

	public String getHoTenCanBo() {
		return hoTenCanBo;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public String getHoTenNguoiViPham() {
		return hoTenNguoiViPham;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public Date getHanNop() {
		return hanNop;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public Integer getSoTienCanNop() {
		return soTienCanNop;
	}



	public List<ItemXuPhat> getXp_ls() {
		return xp_ls;
	}

	public String getSdt() {
		return sdt;
	}
	
	
	
	
}
