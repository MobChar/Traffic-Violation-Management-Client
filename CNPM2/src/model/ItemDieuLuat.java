package model;

import java.io.Serializable;
import java.util.List;

public class ItemDieuLuat implements Serializable{
	private Integer maDieuLuat;
	private String tenDieuLuat;
	
	private List<ItemKhoan> khoan_ls;
	public ItemDieuLuat(Integer maDieuLuat, String tenDieuLuat,List<ItemKhoan> khoan_ls) {
		this.maDieuLuat=maDieuLuat;
		this.tenDieuLuat=tenDieuLuat;
		this.khoan_ls=khoan_ls;
	}
	public Integer getMaDieuLuat() {
		return maDieuLuat;
	}
	public String getTenDieuLuat() {
		return tenDieuLuat;
	}
	public List<ItemKhoan> getKhoan_ls() {
		return khoan_ls;
	}
	
	
}
