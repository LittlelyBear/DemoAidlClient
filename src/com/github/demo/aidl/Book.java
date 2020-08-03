package com.github.demo.aidl;

import android.os.Parcelable;

import javax.security.auth.Destroyable;

import android.os.Parcel;

public class Book implements Parcelable{
	private String bookName;
	
	public Book() {
		bookName = null;
	}
	
	public Book(String name) {
		bookName = name;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String name) {
		bookName = name;
	}
	
	public static final Creator<Book> CREATOR = new Creator<Book>() {
		
		@Override
		public Book[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Book[size];
		}
		
		@Override
		public Book createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Book book = new Book();
			book.setBookName(source.readString());
			return book;
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(bookName);
	}
	
	public void readFromParcel(Parcel desc) {
		// TODO Auto-generated method stub
		bookName = desc.readString();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "bookName = " + bookName;
	}
}
