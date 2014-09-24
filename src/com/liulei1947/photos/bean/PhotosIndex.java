package com.liulei1947.photos.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** ����ʧ�����
  * @ClassName: Lost
  * @Description: TODO
  * @author smile
  * @date 2014-5-21 ����11:27:03
  */
public class PhotosIndex extends BmobObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageUrl;
	private String title;//����
	private String englishTitle;//Ӣ���������������̬Url
	private String describe;//����
	private String comment;//����
	private String tag;//��ǩ
	private String htmlUrl;//��ǩ
	private int imageCount;//ͼƬ����
	private BmobFile image;//ͼƬ�ļ�
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
