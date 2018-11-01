package com.compareFile.tool.component;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class DigestMD5 {
	private static DigestMD5 instance = null;
	public static DigestMD5 getInstance()
	{
		if (instance == null)
		{
			instance = new DigestMD5();
		}
		return instance;
	}
	
	private DigestMD5()
	{
		
	}
	
	public String getMD5Str(String str) throws Exception
	{
		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(str.getBytes("UTF-8"));
		byte[] byteArray = md.digest();
		return this.bytesToHexString(byteArray);
	}
	
	public String getMD5File(File file) throws Exception
	{
		if (!file.exists())
		{
			throw new Exception("file not found");
		}
		else if (file.isDirectory())
		{
			throw new Exception("file is directory");
		}
		DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		try {
			byte[] b = new byte[64];
			int len;
			while((len = in.read(b)) == 64)
			{
				md.update(b);
			}
			if (len > 0)
			{
				byte[] tail = new byte[len];
				for (int i=0;i<len;i++)
				{
					tail[i] = b[i];
				}
				md.update(tail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
			in.close();
		}
		byte[] byteArray = md.digest();
		return this.bytesToHexString(byteArray);
	}
	
	private String bytesToHexString(byte[] byteArray)
	{
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
		{
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
			{
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			}
			else
			{
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
}
