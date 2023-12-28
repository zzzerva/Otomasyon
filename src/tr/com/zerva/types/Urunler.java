package tr.com.zerva.types;

import java.util.Date;

public class Urunler {

	private int id;
	private String adi;
	private String kategoriAdi;
	private float fiyat;
	private Date tarih;

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public float getFiyat() {
		return fiyat;
	}

	public void setFiyat(float fiyat) {
		this.fiyat = fiyat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getKategoriAdi() {
		return kategoriAdi;
	}

	public void setKategoriAdi(String kategoriAdi) {
		this.kategoriAdi = kategoriAdi;
	}

	public String Yazdir() {
		return id + " " + adi + " " + kategoriAdi + " " + fiyat + " " + tarih ;
	}
}
