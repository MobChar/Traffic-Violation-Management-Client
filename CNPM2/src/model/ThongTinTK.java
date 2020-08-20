package model;

import java.io.Serializable;

public class ThongTinTK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taiKhoan,tenCanBo;
	private Integer maCanBo,maDonVi;
	private String tenDonVi;
	private String[] quyen;
	
	public ThongTinTK(String taiKhoan, String tenCanBo,Integer maCanBo,Integer maDonVi, String tenDonVi, String quyen) {
		this.taiKhoan=taiKhoan;
		this.tenCanBo=tenCanBo;
		this.maCanBo=maCanBo;
		this.maDonVi=maDonVi;
		this.tenDonVi=tenDonVi;
		this.quyen=quyen.split(" ");
		
	}
	
	
	public boolean checkQuyenDoc(String role) {
		for(String q: quyen) {
			String[] s=q.split(":");
			if(s[0].equals(role)) {
				String[] tmp=s[1].split("-");
				for(String srole: tmp) {
					if(srole.equals("r")) return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkQuyenViet(String role) {
		for(String q: quyen) {
			String[] s=q.split(":");
			if(s[0].equals(role)) {
				String[] tmp=s[1].split("-");
				for(String srole: tmp) {
					if(srole.equals("w")) return true;
				}
			}
		}
		return false;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public String getTenCanBo() {
		return tenCanBo;
	}

	public Integer getMaCanBo() {
		return maCanBo;
	}

	public String[] getQuyen() {
		return quyen;
	}

	public Integer getMaDonVi() {
		return maDonVi;
	}

	public String getTenDonVi() {
		return tenDonVi;
	}
	
	

	
}
