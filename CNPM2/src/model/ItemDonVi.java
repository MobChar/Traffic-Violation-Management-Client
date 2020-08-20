package model;

import java.io.Serializable;

public class ItemDonVi implements Serializable{
	private Integer maDonVi,maKhuVuc;
	private String tenDonVi,tenKhuVuc,ghiChu;
	
	public ItemDonVi(Integer maDonVi, Integer maKhuVuc, String tenDonVi, String tenKhuVuc, String ghiChu) {
		this.maDonVi=maDonVi;
		this.maKhuVuc=maKhuVuc;
		this.tenDonVi=tenDonVi;
		this.tenKhuVuc=tenKhuVuc;
		this.ghiChu=ghiChu;
	}

	public Integer getMaDonVi() {
		return maDonVi;
	}

	public Integer getMaKhuVuc() {
		return maKhuVuc;
	}

	public String getTenDonVi() {
		return tenDonVi;
	}

	public String getTenKhuVuc() {
		return tenKhuVuc;
	}

	public String getGhiChu() {
		return ghiChu;
	}
	
	@Override
	public String toString() { //Cái server k có cái này phải qua server thêm vào
		return maDonVi+" - "+tenDonVi;
	}
	
	
}
