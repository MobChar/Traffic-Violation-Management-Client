package model;

import java.io.Serializable;

public class ItemQueQuan implements Serializable{
	private int maVung;
	private String tenVung;
	
	public ItemQueQuan(int ma_vung, String ten_vung) {
		this.maVung=ma_vung;
		this.tenVung=ten_vung;
	}

	public int getMaVung() {
		return maVung;
	}

	public String getTenVung() {
		return tenVung;
	}
	
	@Override
	public String toString() { //Cái server k có cái này phải qua server thêm vào
		return maVung+" - "+tenVung;
	}
	
	
}
