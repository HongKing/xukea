package com.xukea.common.util.sequence.model;

import com.xukea.framework.base.BaseEntity;


public class Sequence extends BaseEntity {
	private String tabName;
	private long nextId;
	
	public Sequence() {
	}
  
	public Sequence(String tabName, long nextId) {
		super();
		this.tabName = tabName;
		this.nextId = nextId;
	}

	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public long getNextId() {
		return nextId;
	}
	public void setNextId(long nextId) {
		this.nextId = nextId;
	}
}
