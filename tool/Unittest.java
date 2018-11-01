package com.compareFile.tool;

import com.compareFile.tool.component.DirectoryCompare;
import com.compareFile.tool.vo.DirectoryVO;

import java.util.Iterator;
import java.util.Map.Entry;


public class Unittest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DirectoryVO dirA = new DirectoryVO("D:\\file\\old");
			dirA.analysis();
			System.out.println("Child Directory num :" + dirA.getDirectorynum());
			System.out.println("Child File num :" + dirA.getFilenum());
			//get child file type and num
			Iterator<Entry<String, Integer>> it = dirA.getFiletype().entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String, Integer> entryFiletype = it.next();
				System.out.println("File type :" + entryFiletype.getKey() + "; type num :" + entryFiletype.getValue());
			}
			
			
			DirectoryVO dirB = new DirectoryVO("D:\\file\\new");
			dirB.analysis();
			DirectoryCompare.separateDirectorys(DirectoryCompare.compareFileList(dirA, dirB), "D:\\file\\lock");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
