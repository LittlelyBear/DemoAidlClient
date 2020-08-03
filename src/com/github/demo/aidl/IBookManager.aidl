package com.github.demo.aidl;
import com.github.demo.aidl.Book;

interface IBookManager {
	List<Book> getBookList();
	void addBook(inout Book book);
}