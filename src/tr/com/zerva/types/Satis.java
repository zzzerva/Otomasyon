package tr.com.zerva.types;

import java.util.Date;

public class Satis {

	private int id;
	private int urunId;
	private int adet;
	private Date tarih;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUrunId() {
		return urunId;
	}

	public void setUrunId(int urunId) {
		this.urunId = urunId;
	}

	public int getAdet() {
		return adet;
	}

	public void setAdet(int adet) {
		this.adet = adet;
	}
	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public String Yazdir() {
		return id + " " + urunId + " " + adet + " " + tarih;
	}

}
