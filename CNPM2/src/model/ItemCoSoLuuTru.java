package model;

import java.io.Serializable;

public class ItemCoSoLuuTru implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer maCoSo;
	private String tenCoSo;
	
	public ItemCoSoLuuTru(Integer maCoSo, String tenCoSo) {
		this.maCoSo=maCoSo;
		this.tenCoSo=tenCoSo;
	}

	public Integer getMaCoSo() {
		return maCoSo;
	}

	public String getTenCoSo() {
		return tenCoSo;
	}
	
	
	@Override 
	public String toString() 
	{
		return maCoSo+"-"+tenCoSo;
	}
	
	
	
}
