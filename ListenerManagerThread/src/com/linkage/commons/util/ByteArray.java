package com.linkage.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * �䳤byte����
 * 
 * @author zhaoxin
 * 
 */
public class ByteArray {
	private int size;
	private byte[] buf;
	private int length = 0;

	public ByteArray() {
		size = 20;
		buf = new byte[size];
	}

	public ByteArray(int initSize) {
		size = initSize;
		buf = new byte[size];
	}

	public void put(byte[] buffer) {
		for (int i = 0; i < buffer.length; ++i) {
			put(buffer[i]);
		}
	}

	public void put(byte b) {
		if (length >= size) {
			size *= 2;
			byte[] tmp = new byte[size];
			System.arraycopy(buf, 0, tmp, 0, length);
			buf = tmp;
		}
		buf[length++] = b;
	}

	public byte get(int index) throws ArrayIndexOutOfBoundsException {
		if (index >= length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return buf[index];
	}

	public void set(int index, byte b) throws ArrayIndexOutOfBoundsException {
		if (index >= length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		buf[index] = b;
	}

	public void clear() {
		length = 0;
		buf = null;
		size = 0;
	}

	public String getString(String charset) throws UnsupportedEncodingException {
		return new String(buf, charset).trim();
	}
	@Override
	public String toString(){
		return new String(buf, 0, length).trim();
	}
	public byte[] getArray() {
		/*
		byte[] tmp = new byte[length];
		System.arraycopy(buf, 0, tmp, 0, length);
		return tmp;
		*/
		return buf;
	}

	public int length() {
		return length;
	}

	public int size() {
		return size;
	}

	public static void main(String[] args) throws Exception {
		ByteArray ba = new ByteArray();
		String src = "���Ǹ����ˣ��Ҳ��ǣ�38��34343434343����ʵ��Ҳ��kdfjowejif��!";
		byte[] srcBytes = src.getBytes();
		for (int i = 0; i < srcBytes.length; ++i) {
			ba.put(srcBytes[i]);
		}
		System.out.println(ba);
		System.out.println("length=" + ba.getString("UTF-8").getBytes().length);
		System.out.println("size=" + ba.size());

		ByteArrayInputStream in = new ByteArrayInputStream("aa�ܹ��󿪷�aaiiaaa"
				.getBytes("UTF-8"));
		byte[] buffer = new byte[256];
		ByteArray byteArray = new ByteArray();
		while (in.read(buffer) >= 0) {
			System.out.println(buffer.length);
			byteArray.put(buffer);
		}
		System.out.println("ba:" + byteArray.getString("UTF-8").trim());
		
	test();
	}
	public static void test()throws Exception{
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(new Date());
        
        ByteArrayInputStream in = new ByteArrayInputStream(bo.toByteArray());
		byte[] buffer = new byte[256];
		ByteArray byteArray = new ByteArray();

		while (in.read(buffer)>= 0) {
			System.out.println(buffer.length);
			byteArray.put(buffer);
		}
        
		Object obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(byteArray.getArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        obj = oi.readObject();
        Date obj2 = (Date)obj;
		System.out.println("date:"+obj2);

	}
}