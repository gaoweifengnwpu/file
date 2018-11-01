package com.compareFile.tool.vo;

import com.compareFile.tool.component.SortList;
import com.compareFile.tool.iface.IComparable;

public class DirListCompareResultVO<T extends IComparable<T>> {
	protected SortList<T> onlyA;
	protected SortList<T> sameA;
	protected SortList<T> onlyB;
	protected SortList<T> sameB;
	protected String pathA;
	protected String pathB;
	
	public DirListCompareResultVO()
	{
		this.onlyA = new SortList<T>();
		this.sameA = new SortList<T>();
		this.onlyB = new SortList<T>();
		this.sameB = new SortList<T>();
	}
	
	public SortList<T> getOnlyA() {
		return onlyA;
	}
	public void setOnlyA(SortList<T> onlyA) {
		this.onlyA = onlyA;
	}
	public SortList<T> getSameA() {
		return sameA;
	}
	public void setSameA(SortList<T> sameA) {
		this.sameA = sameA;
	}
	public SortList<T> getOnlyB() {
		return onlyB;
	}
	public void setOnlyB(SortList<T> onlyB) {
		this.onlyB = onlyB;
	}
	public SortList<T> getSameB() {
		return sameB;
	}
	public void setSameB(SortList<T> sameB) {
		this.sameB = sameB;
	}

	public String getPathA() {
		return pathA;
	}

	public void setPathA(String pathA) {
		this.pathA = pathA;
	}

	public String getPathB() {
		return pathB;
	}

	public void setPathB(String pathB) {
		this.pathB = pathB;
	}
	
	
}
