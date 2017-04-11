package com.apple.http.entity;


/**
 * 下载返回实体类
 * @author  hushaoping
 */
public class DownEntity {

	public String name;
	public String path;
	public String url;
	public int httpCode;
	//下载是否完成
	public  int statue=0;
	public long currentByte;
	public long totalByte;
	public boolean isExecuted;
	public String dir;

	public boolean isCanceled;
	public String message;
	/**
	 */
	public DownEntity() {
		this.url = "";
		this.path = "";
		this.statue = 0;
		this.httpCode =-1;
		this.totalByte = 0;
		this.currentByte = 0;
		this.isCanceled=false;
		this.isExecuted=false;
		this.dir="";
		this.message="";
	}

	public DownEntity downDir(String dir) {
		this.dir=dir;
		return this;
	}

	public DownEntity downMessage(String message) {
		this.message=message;
		return this;
	}
	public DownEntity downName(String name) {
		this.name=name;
		return this;
	}
	public DownEntity downPath(String path) {
		this.path=path;
		return this;
	}
	public DownEntity downUrl(String url) {
		this.url=url;
		return this;
	}
	public DownEntity downCode(int code) {
		this.httpCode=code;
		return this;
	}

	public DownEntity downStatue(int statue) {
		this.statue = statue;
		return this;
	}


	public DownEntity currentByte(long currentByte) {
		this.currentByte=currentByte;
		return this;
	}
	public DownEntity totalByte(long totalByte) {
		this.totalByte=totalByte;
		return this;
	}

//	public DownEntity build() {
//		return new DownEntity();
//	}

}
