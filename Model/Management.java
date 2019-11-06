package Model;

import java.util.ArrayList;

public class Management {
    private Book book;
    private Reader reader;
    private int numOfBorrow;
    private String state;
    private int total;

    public Management() {
    }

    public Management(Book book) {
        this.book = book;
    }

    public Management(Book book, Reader reader, int numOfBorrow, String state, int total) {
        this.book = book;
        this.reader = reader;
        this.numOfBorrow = numOfBorrow;
        this.state = state;
        this.total = total;
    }

    public Book getBook(){
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getNumOfBorrow() {
        return numOfBorrow;
    }

    public void setNumOfBorrow(int numOfBorrow) {
        this.numOfBorrow = numOfBorrow;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Management{" +
                "readerID=" + reader.getReaderID() +
                ",bookID=" + book.getBookID() +
                ", numOfBorrow=" + numOfBorrow +
                ", state='" + state + '\'' +
                ", total=" + total +
                '}';
    }
}
