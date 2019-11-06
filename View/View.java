package View;

import Controller.ContrellerUltility;
import Controller.DataController;
import Model.Book;
import Model.Management;
import Model.Reader;

import javax.lang.model.type.NullType;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ServiceConfigurationError;

public class View {
    public static void main(String[] args) {
        int choice = 0;
        var booksFileName = "BOOK.DAT";
        var readersFileName = "READER.DAT";
        var brmFileName = "BRM.DAT";

        var controller = new DataController();
        var books = new ArrayList<Book>();
        var readers = new ArrayList<Reader>();
        var brms = new ArrayList<Management>();

        var ischeckBook = false;
        var ischeckReader = false;
        File file1 = new File(booksFileName);
        File file2 = new File(readersFileName);

        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println();
            System.out.println("--------MENU--------");
            System.out.println("1:Them Book vao Book.DAT");
            System.out.println("2:In danh sach Book trong file");
            System.out.println("3:Them Reader vao Reader.DAT");
            System.out.println("4:In danh sach Reader trong file");
            System.out.println("5:Tao thong tin BRM cho tung Reader,luu vao BRM.DAT, in ds");
            System.out.println("6:Sap xep danh sach BRM trong BRM.DAT");
            System.out.println("7:Tim kiem theo ten Reader");
            System.out.println("0:Thoat chuong trinh");
            System.out.println("Lua chon cua ban:");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    if(!ischeckBook && file1.length() > 0) {
                        checkBookID(controller, booksFileName);
                        ischeckBook = true;
                    }
                    String [] specs = {"Science","Art","Economic","IT"};
                    String bookName,author,spec;
                    int year,quan,sp;
                    System.out.println("Nhap ten sach:");
                    bookName = scanner.nextLine();
                    System.out.println("Tac gia:");
                    author = scanner.nextLine();
                    do{
                        System.out.println("The loai:");
                        System.out.println("1:Science\n2:Art\n3:Economic\n4:IT");
                        sp = scanner.nextInt();
                    }while(sp < 1 || sp > 4);
                    spec = specs[sp-1];
                    System.out.println("Nam xuat ban:");
                    year = scanner.nextInt();
                    System.out.println("Nhap so uong:");
                    quan = scanner.nextInt();

                    Book book = new Book(0,bookName,author,spec,year,quan);
                    controller.writeBookToFile(book,booksFileName);
                    break;
                case 2:
                    System.out.println("--------DANH SACH BOOK--------");
                    books = controller.readBooksFromFile(booksFileName);
                    showBookInfo(books);
                    break;
                case 3:
                    if(!ischeckReader && file2.length() > 0){
                        checkReaderID(controller,readersFileName);
                        ischeckReader = true;
                    }
                    String fullName,address,phoneNumber;
                    System.out.println("Ten cua ban:");
                    fullName = scanner.nextLine();
                    System.out.println("Dia chi: ");
                    address = scanner.nextLine();
                    System.out.println("SDT: ");
                    phoneNumber = scanner.nextLine();
                    Reader reader = new Reader(0,fullName,address,phoneNumber);
                    controller.writeReaderToFile(reader,readersFileName);
                    break;
                case 4:
                    System.out.println("--------DANH SACH NGUOI MUON--------");
                    readers = controller.readReadersFromFile(readersFileName);
                    showReaderInfo(readers);
                    break;
                case 5:
                    books = controller.readBooksFromFile(booksFileName);
                    readers = controller.readReadersFromFile(readersFileName);
                    brms = controller.readBRMSFromFile(brmFileName);
                    showReaderInfo(readers);
                    int readerID,bookID;

                    /*
                    Kiểm tra 1 người có mượn tối đa 15 quyển chưa
                     */
                    boolean isBorrowable = false;
                    do{
                        System.out.println("Nhap id ban doc(0 de bo qua):");
                        readerID = scanner.nextInt();
                        if(readerID == 0) break; // Khi ko muon tim id ban doc nua
                        isBorrowable = checkBorrowed(brms,readerID);
                        if(isBorrowable) break;
                        else
                            System.out.println("DA MUON TOI DA SO LUONG CHO PHEP");
                    }while(true);

                    boolean isFull = false;
                    do{
                        showBookInfo(books);
                        System.out.println("----------------------");
                        System.out.println("Nhap id sach(0 de bo qua): ");
                        bookID = scanner.nextInt();
                        if(bookID == 0) break;

                        /*Kiểm tra 1 người có mượn 1 đầu sách tối đa 3 quyển hay chưa
                            Nếu tối đa 3 quyển 1 đầu sách thì phải chọn đầu sách khác
                            Nều chưa tối đa in ra số lượng đã mượn
                         */
                        isFull = checkFull(brms,readerID,bookID);
                        if (isFull)
                            System.out.println("DA MUON TOI DA DAU SACH NAY");
                        else break;
                    }while(true);

                    int total = getTotal(brms,readerID,bookID);
                    do{
                        int x;
                        System.out.println("Nhap so luong muon( da muon " + total + ")");
                        x = scanner.nextInt();
                        if((x+total)>= 1 && (x+total) <= 3){
                            total =  total + x;
                            break;
                        }
                        else
                            System.out.println("NHAP QUA SO LUONG QUY DINH");
                    }while (true);
                    scanner.nextLine();

                    String status = "";
                    System.out.println("Nhap tinh trang:");
                    status = scanner.nextLine();

                    Book currentBook = new Book();
                    for(var r: books){
                        if (r.getBookID() == bookID)
                            currentBook = r;
                    }
                    Reader currentReader = new Reader();
                    for(var r: readers){
                        if(r.getReaderID() == readerID)
                            currentReader = r;
                    }
                    Management b = new Management(currentBook,currentReader,total,status,0);

                    var ultility = new ContrellerUltility();
                    brms = ultility.updateBRM(brms,b); // Cap nhat danh sach
                    controller.updateBRMFile(brms,brmFileName); // Cap nhap fileDAT

                    showBRMInfo(brms);
                    break;

                case 0:
                    System.out.println("Xin chao va hen gap lai");
                    break;
            }
        }while(choice != 0 );
    }

    private static void showBookInfo(ArrayList<Book> books) {
        for(var b: books){
            System.out.println(b);
        }
    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        for (var b : readers) {
            System.out.println(b);
        }
    }

    private static void showBRMInfo(ArrayList<Management> brms){
        for(var b: brms){
            System.out.println(b);
        }
    }

    private static void checkBookID(DataController controller,String fileName){
        var listBook = controller.readBooksFromFile(fileName);
        Book.setId(listBook.get(listBook.size() -1).getBookID() + 1);
    }

    private static void checkReaderID(DataController controller,String fileName){
        var listReader = controller.readReadersFromFile(fileName);
        Reader.setId(listReader.get(listReader.size() - 1).getReaderID() + 1);
    }

    private static boolean checkBorrowed(ArrayList<Management> brms,int readerID){
        int count = 0;
        for(var r : brms){
            if(r.getReader().getReaderID() == readerID)
                count = count + r.getNumOfBorrow();
        }
        if(count <= 15) return  true;
        else  return false;
    }

    private static boolean checkFull(ArrayList<Management> brms, int readerID,int bookID){
        for(var r: brms){
            if(r.getReader().getReaderID() == readerID){
                if(r.getBook().getBookID() == bookID && r.getNumOfBorrow() == 3)
                    return true; // Da muon toi da 3 quyen 1 dau sach
            }
        }
        return false;// Dc phep muon tiep dau sach nay
    }

    private static int getTotal(ArrayList<Management> brms,int readerID,int bookID) {
        for(var r: brms){
            if(r.getReader().getReaderID() == readerID && r.getBook().getBookID() == bookID)
                return r.getNumOfBorrow();
        }
        return 0;
    }

    private static Book getbook(ArrayList<Book> books, int bookID){
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getBookID() == bookID)
                return books.get(i);
        }
        return null;
    }

    private static Reader getReader(ArrayList<Reader> readers,int readerID){
        for(int i = 0; i < readers.size(); i++){
            if(readers.get(i).getReaderID() == readerID)
                return readers.get(i);
        }
        return null;
    }
}
