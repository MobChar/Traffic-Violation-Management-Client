package model;

import java.io.Serializable;

public class ItemKhuVuc implements Serializable {
	private Integer maKhuVuc;
	private String tenKhuVuc;
	
	public ItemKhuVuc(Integer maKhuVuc, String tenKhuVuc) {
		this.maKhuVuc=maKhuVuc;
		this.tenKhuVuc=tenKhuVuc;
	
	}
	
	public Integer getMaKhuVuc() {
		return maKhuVuc;
	}
	public String getTenKhuVuc() {
		return tenKhuVuc;
	}
	
	
	
	@Override
	public String toString() { //Cái server k có cái này phải qua server thêm vào
		return maKhuVuc+" - "+tenKhuVuc;
	}
	
	
}
