package model;

import java.io.Serializable;

public class ItemDiem implements Serializable {
	private Integer maDiem, maKhoan, maDieuLuat;
	private String moTa;
	
	public ItemDiem(Integer maDiem, Integer maKhoan, Integer maDieuLuat, String moTa) {
		this.maDiem=maDiem;
		this.maKhoan=maKhoan;
		this.maDieuLuat=maDieuLuat;
		this.moTa=moTa;
	}

	public Integer getMaDiem() {
		return maDiem;
	}

	public Integer getMaKhoan() {
		return maKhoan;
	}

	public Integer getMaDieuLuat() {
		return maDieuLuat;
	}

	public String getMoTa() {
		return moTa;
	}
	
	
}
