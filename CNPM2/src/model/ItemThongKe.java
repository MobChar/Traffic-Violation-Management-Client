package model;

import java.io.Serializable;

public class ItemThongKe implements Serializable{
	private Integer soLuongBB;
	private Integer soLuongQD;
	private Integer thang,nam;
	
	public ItemThongKe(Integer soLuongBB,Integer soLuongQD, Integer thang, Integer nam) {
		this.soLuongBB=soLuongBB;
		this.soLuongQD=soLuongQD;
		this.thang=thang;
		this.nam=nam;
	}

	public Integer getSoLuongBB() {
		return soLuongBB;
	}

	public Integer getSoLuongQD() {
		return soLuongQD;
	}

	public Integer getThang() {
		return thang;
	}

	public Integer getNam() {
		return nam;
	}
	
	
	
}
