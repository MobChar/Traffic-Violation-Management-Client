package model;

import java.io.Serializable;

public class ItemTangVat implements Serializable{
	private Integer maTangVat,soLuong,maCoSoLuuTru;
	private String tenTangVat,trangThai,tenCoSoLuuTru,ghiChu;
	public ItemTangVat(Integer maTangVat,String tenTangVat, Integer soLuong, String trangThai, Integer maCoSoLuuTru, String tenCoSoLuuTru, String ghiChu) {
		this.maTangVat=maTangVat;
		this.soLuong=soLuong;
		this.tenTangVat=tenTangVat;
		this.trangThai=trangThai;
		this.maCoSoLuuTru=maCoSoLuuTru;
		this.tenCoSoLuuTru=tenCoSoLuuTru;
		this.ghiChu=ghiChu;
	}
	public Integer getMaTangVat() {
		return maTangVat;
	}
	public Integer getSoLuong() {
		return soLuong;
	}
	public String getTenTangVat() {
		return tenTangVat;
	}
	public String getTrangThai() {
		return trangThai;
	}

	public String getGhiChu() {
		return ghiChu;
	}
	public Integer getMaCoSoLuuTru() {
		return maCoSoLuuTru;
	}
	public String getTenCoSoLuuTru() {
		return tenCoSoLuuTru;
	}
	
	
	
}
