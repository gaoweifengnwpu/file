package com.compareFile.tool.iface;

public interface IComparable<T>{
	public static int BIGGER = 1;
	public static int EQUAL = 0;
	public static int SMALLER = -1;
	public boolean isBiggerThan(T obj);
	public boolean isEquals(T obj);
	public boolean isSmallerThan(T obj);
	public int getCompareValue(T obj);
}
