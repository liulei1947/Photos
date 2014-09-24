package com.liulei1947.photos.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 创建失物对象
  * @ClassName: Lost
  * @Description: TODO
  * @author smile
  * @date 2014-5-21 上午11:27:03
  */
public class PhotosIndex extends BmobObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageUrl;
	private String title;//标题
	private String englishTitle;//英语标题用于生产动态Url
	private String describe;//描述
	private String comment;//评论
	private String tag;//标签
	private String htmlUrl;//标签
	private int imageCount;//图片总数
	private BmobFile image;//图片文件
	//
	public String getTitle() {
		return title;
	}
	public void setenglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}
	public String getenglishTitle() {
		return englishTitle;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getimageUrl() {
		return imageUrl;
	}
	public void setimageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getcomment() {
		return comment;
	}
	public void setcomment(String comment) {
		this.comment = comment;
	}
	public String gettag() {
		return tag;
	}
	public void settag(String tag) {
		this.tag = tag;
	}
	public int getimageCount() {
		return imageCount;
	}
	public void setimageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	
	public BmobFile getimage() {
		return image;
	}
	public void setimage(BmobFile image) {
		this.image = image;
	}
	public String gethtmlUrl() {
		return htmlUrl;
	}
	public void sethtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	
	
	
	
	
}
