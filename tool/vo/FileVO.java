package com.compareFile.tool.vo;

import com.compareFile.tool.component.DigestMD5;
import com.compareFile.tool.iface.IComparable;

import java.io.File;


public class FileVO extends File implements IComparable<FileVO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String filemd5;
	protected String relativePath;
	
	public FileVO(String pathname,String dirPath) throws Exception
	{
		super(pathname);
		if (!this.exists())
		{
			throw new Exception("file is not exist");
		}
		if (!this.isFile())
		{
			throw new Exception("is not file");
		}
		this.filemd5 = DigestMD5.getInstance().getMD5File(this);
		this.relativePath = pathname.substring(dirPath.length());
	}
	
	public static String getFiletype(File file)
	{
		String filename = file.getName();
		int index = file.getName().lastIndexOf(".");
		if (index == -1 || index +1 == filename.length())
		{
			return ".null";
		}
		else
		{
			return filename.substring(index +1);
		}
	}
	
	public String getMD5()
	{
		return this.filemd5;
	}

	public boolean isBiggerThan(FileVO obj) {
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

	public boolean isEquals(FileVO obj) {
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

	public boolean isSmallerThan(FileVO obj) {
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

	public int getCompareValue(FileVO obj) {
		int result = this.getRelativePath().compareTo(obj.getRelativePath());
		if (result < 0){
			return IComparable.SMALLER;
		}
		if (result > 0){
			return IComparable.BIGGER;
		}
		return IComparable.EQUAL;
	}
	
	public boolean isSameByMD5(FileVO file)
	{
		int result = this.getMD5().compareTo(file.getMD5());
		return result == 0 ? true : false;
	}
	
	public String getRelativePath()
	{
		return this.relativePath;
	}
}
