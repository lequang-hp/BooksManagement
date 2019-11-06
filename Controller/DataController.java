package Controller;

import Model.Book;
import Model.Management;
import Model.Reader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
/*
public void openFileToWrite(String fileName)
public void closeFileAfterWrite(String fileName)
void writeBookToFile(Book book,String fileName)
void writeReaderToFile(Reader reader,String fileName)
void writeBRMToFile(Management brm,String fileName)

public void openFileToRead(String fileName)
public void closeFileAfterRead(String fileName)
public ArrayList<Book> readBooksFromFile(String fileName)
public Book createBooksFromData(String data)
public ArrayList<Reader> readReadersFromFile(String fileName)
public Reader createReaderFromData(String data)
public ArrayList<Management> readBRMSFromFile(String fileName)
public Management createBRMFromData(String data)
 */
public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    public void openFileToWrite(String fileName){
        try {
            fileWriter = new FileWriter(fileName,true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeFileAfterWrite(String fileName){
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeBookToFile(Book book,String fileName){
        openFileToWrite(fileName);
        printWriter.println(book.getBookID()+"|"+book.getBookName()+"|"+
                book.getAuthor()+"|"+book.getSpecialization()+"|"
                +book.getPublishYear()+"|"+book.getQuantity());
        closeFileAfterWrite(fileName);
    }

    public void writeReaderToFile(Reader reader,String fileName){
        openFileToWrite(fileName);
        printWriter.println(reader.getReaderID()+"|"+reader.getFullName()+"|"+
                reader.getAddress()+"|"+reader.getPhoneNumber());
        closeFileAfterWrite(fileName);
    }

    public void writeBRMToFile(Management brm,String fileName){
        openFileToWrite(fileName);
        printWriter.println(brm.getReader().getReaderID()+"|"+brm.getBook().getBookID()+
                "|"+brm.getNumOfBorrow()+"|"+brm.getState());
        closeFileAfterWrite(fileName);
    }


    public void openFileToRead(String fileName){
        try {
            File file = new File(fileName);
            if(!file.exists())
                file.createNewFile();
            scanner = new Scanner(Paths.get(fileName),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName){
        try {
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Book> readBooksFromFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Book> books = new ArrayList<>();
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Book book = createBooksFromData(data);
            books.add(book);
        }
        closeFileAfterRead(fileName);
        return books;
    }

    public Book createBooksFromData(String data){
        String []datas = data.split("\\|");
        int bookID = Integer.parseInt(datas[0]);
        int publishYear = Integer.parseInt(datas[4]);
        int quantity = Integer.parseInt(datas[5]);
        Book book = new Book(bookID,datas[1],datas[2],datas[3],publishYear,quantity);
        return book;
    }

    public ArrayList<Reader> readReadersFromFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Reader> readers = new ArrayList<>();
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Reader reader = createReaderFromData(data);
            readers.add(reader);
        }
        closeFileAfterRead(fileName);
        return readers;
    }

    public Reader createReaderFromData(String data){
        String []datas = data.split("\\|");
        int readerID = Integer.parseInt(datas[0]);
        Reader reader = new Reader(readerID,datas[1],datas[2],datas[3]);
        return reader;
    }

    public ArrayList<Management> readBRMSFromFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Management> brms = new ArrayList<>();
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Management brm = createBRMFromData(data);
            brms.add(brm);
        }
        closeFileAfterRead(fileName);
        return brms;
    }

    public Management createBRMFromData(String data){
        String []datas = data.split("\\|");
        Book book = new Book(Integer.parseInt(datas[1]));
        Reader reader = new Reader(Integer.parseInt(datas[0]));
        int numOfBrow = Integer.parseInt(datas[2]);
        Management brm = new Management(book,reader,numOfBrow,datas[3],0);
        return brm;
    }

    public void updateBRMFile(ArrayList<Management> list, String fileName){
        //Xoa file cu
        File file = new File(fileName);
        if(file.exists())
            file.delete(); // Neu ton tai thi xoa no di

        //Ghi moi
        openFileToWrite(fileName);
        for(var brm: list){
            printWriter.println(brm.getReader().getReaderID()+"|"+brm.getBook().getBookID()+
                    "|"+brm.getNumOfBorrow()+"|"+brm.getState());
        }
        closeFileAfterWrite(fileName);
    }
}

/*
-Ghi thong tin book vao file
-Ghi thong tin reader vao file
-Ghi thong tin BRM vao file

-Doc thong tin tu file:
    +Doc thong tin tu file kieu String
    +Chuyen doi String sang dang Book,Reader,Managemetn tuy
    +Them vao danh sach
    +Tra ve danh sach
 */