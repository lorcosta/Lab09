package it.polito.tdp.borders.model;

public class Country {
	private Integer countryCode;
	private String countryName;
	private String countryAbbr;
	/**
	 * @param countryCode
	 * @param countryName
	 * @param countryAbbr
	 */
	public Country(Integer countryCode, String countryName, String countryAbbr) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.countryAbbr = countryAbbr;
	}
	public Integer getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryAbbr() {
		return countryAbbr;
	}
	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "" + countryName;
	}
	
	
}
