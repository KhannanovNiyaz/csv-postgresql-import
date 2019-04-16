package com.javasampleapproach.batchcsvpostgresql.model;

public class Customer {
	private String ssoid;
	private String ts;
	private String grp;
	private String type;
	private String subtype;
	private String url;
	private String orgid;
	private String formid;
	private String code;
	private String ltpa;
	private String sudirresponse;
	private String ymdh;

	public Customer() {
	}

	public Customer( String ssoid, String ts, String grp, String type, String subtype, String url, String orgid, String formid, String code, String ltpa, String sudirresponse, String ymdh) {
		this.ssoid = ssoid;
		this.ts = ts;
		this.grp = grp;
		this.type = type;
		this.subtype = subtype;
		this.url = url;
		this.orgid = orgid;
		this.formid = formid;
		this.code = code;
		this.ltpa = ltpa;
		this.sudirresponse = sudirresponse;
		this.ymdh = ymdh;
	}

	public String getSsoid() {
		return ssoid;
	}

	public void setSsoid(String ssoid) {
		this.ssoid = ssoid;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLtpa() {
		return ltpa;
	}

	public void setLtpa(String ltpa) {
		this.ltpa = ltpa;
	}

	public String getSudirresponse() {
		return sudirresponse;
	}

	public void setSudirresponse(String sudirresponse) {
		this.sudirresponse = sudirresponse;
	}

	public String getYmdh() {
		return ymdh;
	}

	public void setYmdh(String ymdh) {
		this.ymdh = ymdh;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"; ssoid='" + ssoid + '\'' +
				"; ts='" + ts + '\'' +
				"; grp='" + grp + '\'' +
				"; type='" + type + '\'' +
				"; subtype='" + subtype + '\'' +
				"; url='" + url + '\'' +
				"; orgid='" + orgid + '\'' +
				"; formid='" + formid + '\'' +
				"; code='" + code + '\'' +
				"; ltpa='" + ltpa + '\'' +
				"; sudirresponse='" + sudirresponse + '\'' +
				"; ymdh='" + ymdh + '\'' +
				'}';
	}
}
