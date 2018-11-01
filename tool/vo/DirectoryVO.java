package com.compareFile.tool.vo;

import com.compareFile.tool.component.SortList;
import com.compareFile.tool.iface.IComparable;

import java.io.File;
import java.util.HashMap;


public class DirectoryVO extends File implements IComparable<DirectoryVO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectoryVO(String pathname) throws Exception
	{
		super(pathname);
	}
	
	protected int directorynum;
	protected int filenum;
	protected HashMap<String, Integer> filetype;
	protected SortList<DirectoryVO> childDirectorys;
	protected SortList<FileVO> childFiles;
	
	protected boolean isAnalysis = false;
	
	public boolean isAnalysis()
	{
		return this.isAnalysis;
	}
	
	public void analysis() throws Exception
	{
		this.directorynum = 0;
		this.filenum = 0;
		this.filetype = new HashMap<String, Integer>();
		this.childDirectorys = new SortList<DirectoryVO>();
		this.childFiles = new SortList<FileVO>();
		this.analysisDirectory(this);
		this.isAnalysis = true;
	}
	
	protected void analysisDirectory(File dir) throws Exception
	{
		File[] childFiles = dir.listFiles();
		for(File childFile : childFiles)
		{
			if (childFile.isFile())
			{
				this.filenum ++;
				this.childFiles.add(new FileVO(childFile.getAbsolutePath(),this.getAbsolutePath()));
				String filetypeStr = FileVO.getFiletype(childFile);
				if (this.filetype.containsKey(filetypeStr))
				{
					int typeCount = this.filetype.get(filetypeStr).intValue();
					this.filetype.put(filetypeStr, Integer.valueOf(typeCount +1));
				}
				else
				{
					this.filetype.put(filetypeStr, new Integer(1));
				}
			}
			else if (childFile.isDirectory())
			{
				this.directorynum ++;
				this.childDirectorys.add(new DirectoryVO(childFile.getAbsolutePath()));
				this.analysisDirectory(childFile);
			}
		}
	}

	public int getDirectorynum() {
		return directorynum;
	}

	public int getFilenum() {
		return filenum;
	}

	public HashMap<String, Integer> getFiletype() {
		return filetype;
	}

	public SortList<DirectoryVO> getChildDirectorys() {
		return childDirectorys;
	}

	public SortList<FileVO> getChildFiles() {
		return childFiles;
	}

	public boolean isBiggerThan(DirectoryVO obj) {
		int compareValue = this.getCompareValue(obj);
		if (compareValue > IComparable.EQUAL)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isEquals(DirectoryVO obj) {
		int compareValue = this.getCompareValue(obj);
		if (compareValue == IComparable.EQUAL)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isSmallerThan(DirectoryVO obj) {
		int compareValue = this.getCompareValue(obj);
		if (compareValue < IComparable.EQUAL)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getCompareValue(DirectoryVO obj) {
		int result = this.getAbsolutePath().compareTo(obj.getAbsolutePath());
		if (result < 0){
			return IComparable.SMALLER;
		}
		if (result > 0){
			return IComparable.BIGGER;
		}
		return IComparable.EQUAL;
	}
}
