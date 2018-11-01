package com.compareFile.tool.component;

import com.compareFile.tool.iface.IComparable;

import java.util.ArrayList;



public class SortList<E extends IComparable<E>> extends ArrayList<E>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void sort() {
		for (int i=0;i<this.size()-1;i++)
		{
			boolean isOrder = true;
			for (int j=0;j<this.size()-1-i;j++)
			{
				E element0 = this.get(j);
				E element1 = this.get(j+1);
				if (element0.isBiggerThan(element1))
				{
					this.set(j, element1);
					this.set(j+1, element0);
					isOrder = false;
				}
			}
			if (isOrder)
			{
				return;
			}
		}
	}
}
