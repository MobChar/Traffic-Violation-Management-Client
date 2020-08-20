package model;

import java.io.Serializable;

public class PagingWrapper<T> implements Serializable{
	private T page_data;
	private int total;
	public PagingWrapper(T data,int total) {
		this.page_data=data;
		this.total=total;
		
	}
	public T getPage_data() {
		return page_data;
	}
	public int getTotal() {
		return total;
	}
	
	
	
	
	
}
