package tr.com.zerva.types;

public class Accounts {
	
	private int id;
	private int yetkiId;
	private String adiSoyadi;
	private String sifre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYetkiId() {
		return yetkiId;
	}

	public String getAdiSoyadi() {
		return adiSoyadi;
	}

	public void setAdiSoyadi(String adiSoyadi) {
		this.adiSoyadi = adiSoyadi;
	}

	public void setYetkiId(int yetkiId) {
		this.yetkiId = yetkiId;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public String Yazdir() {
		return id + " " + yetkiId + " " + sifre + " " + adiSoyadi;
	}

}
