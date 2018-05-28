package com.wxy.dg.modules.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组后的容器，一个此对象代表一组数据
 * 
 */
public class GroupContiner {
	private String groupID;
	private List<Photo> photoList;

	public List<Photo> getPhotoList() {
		if (null == photoList) {
			photoList = new ArrayList<Photo>();
		}
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

}