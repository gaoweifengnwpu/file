package com.compareFile.tool.vo;


import com.compareFile.tool.component.SortList;
import com.compareFile.tool.iface.IComparable;

public class FileListCompareResultVO<T extends IComparable<T>> extends DirListCompareResultVO<T> {
	protected SortList<T> diffA;
	protected SortList<T> diffB;
	
	public FileListCompareResultVO()
	{
		this.diffA = new SortList<T>();
		this.diffB = new SortList<T>();
	}

	public SortList<T> getDiffA() {
		return diffA;
	}

	public void setDiffA(SortList<T> diffA) {
		this.diffA = diffA;
	}

	public SortList<T> getDiffB() {
		return diffB;
	}

	public void setDiffB(SortList<T> diffB) {
		this.diffB = diffB;
	}
}
