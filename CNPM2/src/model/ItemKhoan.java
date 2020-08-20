package model;

import java.io.Serializable;
import java.util.List;

public class ItemKhoan implements Serializable{
	private Integer maKhoan, maDieuLuat;
	private String moTa;
	
	private List<ItemDiem> diem_ls;
	public ItemKhoan(Integer maKhoan, Integer maDieuLuat, String moTa,List<ItemDiem> diem_ls) {
		this.maKhoan=maKhoan;
		this.maDieuLuat=maDieuLuat;
		this.moTa=moTa;
		
		this.diem_ls=diem_ls;
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
	public List<ItemDiem> getDiem_ls() {
		return diem_ls;
	}
	
	
}
