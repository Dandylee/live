package com.mama.dandy.vo;

import java.util.List;

public class CommonVo {
	
	@SuppressWarnings("rawtypes")
	private List rows;
	
	@SuppressWarnings("rawtypes")
	private List footer; 
	
	private int total;
	
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getFooter() {
		return footer;
	}
	
	@SuppressWarnings("rawtypes")
	public void setFooter(List footer) {
		this.footer = footer;
	}

}
