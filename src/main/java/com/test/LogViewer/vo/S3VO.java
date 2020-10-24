package com.test.LogViewer.vo;

public class S3VO {
    private String cs_uri_stem;
    private String cs_Host;
    private String cs_method;
    private String c_ip;
    private String x_edge_location;
    private int sc_status;
    private String x_edge_request_id;
    private String x_host_header;
    private String cs_protocol_version;
    private int c_port;
    private String sc_content_type;
    private String date;
	private String time;
	private String wdate;

	private String msg;
	private String keyword;
	private int val;
    
    public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public String getCs_uri_stem() {
		return cs_uri_stem;
	}
	public void setCs_uri_stem(String cs_uri_stem) {
		this.cs_uri_stem = cs_uri_stem;
	}
	public String getCs_Host() {
		return cs_Host;
	}
	public void setCs_Host(String cs_Host) {
		this.cs_Host = cs_Host;
	}
	public String getCs_method() {
		return cs_method;
	}
	public void setCs_method(String cs_method) {
		this.cs_method = cs_method;
	}
	public String getC_ip() {
		return c_ip;
	}
	public void setC_ip(String c_ip) {
		this.c_ip = c_ip;
	}
	public String getX_edge_location() {
		return x_edge_location;
	}
	public void setX_edge_location(String x_edge_location) {
		this.x_edge_location = x_edge_location;
	}
	public int getSc_status() {
		return sc_status;
	}
	public void setSc_status(int sc_status) {
		this.sc_status = sc_status;
	}
	public String getX_edge_request_id() {
		return x_edge_request_id;
	}
	public void setX_edge_request_id(String x_edge_request_id) {
		this.x_edge_request_id = x_edge_request_id;
	}
	public String getX_host_header() {
		return x_host_header;
	}
	public void setX_host_header(String x_host_header) {
		this.x_host_header = x_host_header;
	}
	public String getCs_protocol_version() {
		return cs_protocol_version;
	}
	public void setCs_protocol_version(String cs_protocol_version) {
		this.cs_protocol_version = cs_protocol_version;
	}
	public int getC_port() {
		return c_port;
	}
	public void setC_port(int c_port) {
		this.c_port = c_port;
	}
	public String getSc_content_type() {
		return sc_content_type;
	}
	public void setSc_content_type(String sc_content_type) {
		this.sc_content_type = sc_content_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
