package com.compareFile.tool.component;

import com.compareFile.tool.iface.IComparable;
import com.compareFile.tool.vo.DirectoryVO;
import com.compareFile.tool.vo.FileListCompareResultVO;
import com.compareFile.tool.vo.FileVO;

import java.io.*;
import java.util.Iterator;



public class DirectoryCompare {

	public static FileListCompareResultVO<FileVO> compareFileList(
			DirectoryVO dirA, DirectoryVO dirB) {
		FileListCompareResultVO<FileVO> resultList = new FileListCompareResultVO<FileVO>();
		resultList.setPathA(dirA.getPath());
		resultList.setPathB(dirB.getPath());
		SortList<FileVO> listA = dirA.getChildFiles();
		SortList<FileVO> listB = dirB.getChildFiles();
		listA.sort();
		listB.sort();
		int pointA = 0;
		int pointB = 0;

		while (true) {
			FileVO elementA = listA.get(pointA);
			FileVO elementB = listB.get(pointB);
			int compareValue = elementA.getCompareValue(elementB);
			if (compareValue == IComparable.EQUAL) {
				// both sortlist has, compare md5
				if (elementA.isSameByMD5(elementB)) {
					resultList.getSameA().add(elementA);
					resultList.getSameB().add(elementB);
				} else {
					resultList.getDiffA().add(elementA);
					resultList.getDiffB().add(elementB);
				}
				pointA++;
				pointB++;
			} else if (compareValue == IComparable.SMALLER) {
				// only this has
				resultList.getOnlyA().add(elementA);
				pointA++;
			} else if (compareValue == IComparable.BIGGER) {
				// only list has
				resultList.getOnlyB().add(elementB);
				pointB++;
			}
			if (pointA == listA.size() && pointB < listB.size()) {
				// this is end
				while (pointB < listB.size()) {
					resultList.getOnlyB().add(listB.get(pointB));
					pointB++;
				}
				break;
			} else if (pointA < listA.size() && pointB == listB.size()) {
				// list is end
				while (pointA < listA.size()) {
					resultList.getOnlyA().add(listA.get(pointA));
					pointA++;
				}
				break;
			} else if (pointA == listA.size() && pointB == listB.size()) {
				break;
			}
		}
		return resultList;
	}

	public static void separateDirectorys(
			FileListCompareResultVO<FileVO> compareResultVO, String separateDir)
			throws Exception {
		DirectoryCompare.copyListFileToDir(compareResultVO.getOnlyA(),
				compareResultVO.getPathA(), new DirectoryVO(separateDir
						+ File.separator + "onlyA"));
		DirectoryCompare.copyListFileToDir(compareResultVO.getOnlyB(),
				compareResultVO.getPathB(), new DirectoryVO(separateDir
						+ File.separator + "onlyB"));
		DirectoryCompare.copyListFileToDir(compareResultVO.getDiffA(),
				compareResultVO.getPathA(), new DirectoryVO(separateDir
						+ File.separator + "diffA"));
		DirectoryCompare.copyListFileToDir(compareResultVO.getDiffB(),
				compareResultVO.getPathB(), new DirectoryVO(separateDir
						+ File.separator + "diffB"));
		DirectoryCompare.copyListFileToDir(compareResultVO.getSameA(),
				compareResultVO.getPathA(), new DirectoryVO(separateDir
						+ File.separator + "sameA"));
		DirectoryCompare.copyListFileToDir(compareResultVO.getSameB(),
				compareResultVO.getPathB(), new DirectoryVO(separateDir
						+ File.separator + "sameB"));
	}

	public static void copyListFileToDir(SortList<FileVO> filelist,
			String basedir, DirectoryVO separateDir) throws Exception {
		if (separateDir.exists()) {
			if (separateDir.listFiles().length != 0) {
				throw new Exception("Separate Dir is not empty");
			}
		} else {
			separateDir.mkdirs();
		}

		Iterator<FileVO> it = filelist.iterator();
		while (it.hasNext()) {
			FileVO fromFile = it.next();
			File toFile = new File(separateDir.getAbsolutePath()
					+ File.separator
					+ fromFile.getAbsolutePath().substring(basedir.length()));
			fromFile.getParentFile().mkdirs();
			DirectoryCompare.copyFileToFile(fromFile, toFile);
		}
	}

	public static void copyFileToFile(File fromFile, File toFile)
			throws Exception {
		FileInputStream input = new FileInputStream(fromFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}

		FileOutputStream output = new FileOutputStream(toFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		byte[] b = new byte[1024];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outBuff.flush();

		// �ر���
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
}
