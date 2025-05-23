import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Reader{
    private String readerId;//读者编号
    private String readerName;//读者姓名
    private String gender;//读者性别
    private String type;//读者类型
    private List<String> borrowedBooks;//借阅的图书（改为集合类型）

    private Date borrowDate;//借阅日期
    private Date returnDate;//归还日期
    private int maxBorrowCount;//最大借阅数量
    private int maxBorrowDays;//最大借阅天数

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Reader(String readerId, String readerName, String gender, String type,
                  List<String> borrowedBooks, String borrowDateStr, String returnDateStr,
                  int maxBorrowCount, int maxBorrowDays) {
        this.readerId = readerId;
        this.readerName = readerName;
        this.gender = gender;
        this.type = type;
        this.borrowedBooks = borrowedBooks;
        this.maxBorrowCount = maxBorrowCount;
        this.maxBorrowDays = maxBorrowDays;

        // 处理借阅日期
        if (borrowDateStr != null && !borrowDateStr.isEmpty()) {
            try {
                this.borrowDate = dateFormat.parse(borrowDateStr);
            } catch (ParseException e) {
                System.err.println("解析借阅日期失败: " + borrowDateStr);
                e.printStackTrace();
                this.borrowDate = null; // 解析失败设为null
            }
        } else {
            this.borrowDate = null; // 空字符串设为null
        }

        // 处理归还日期
        if (returnDateStr != null && !returnDateStr.isEmpty()) {
            try {
                this.returnDate = dateFormat.parse(returnDateStr);
            } catch (ParseException e) {
                System.err.println("解析归还日期失败: " + returnDateStr);
                e.printStackTrace();
                this.returnDate = null; // 解析失败设为null
            }
        } else {
            this.returnDate = null; // 空字符串设为null
        }
    }

    public Reader(String readerId, String text, String selectedItem, String selectedItem1, List<String> borrowedBooks, Date borrowDate, Date returnDate, int maxBorrowCountInt, int maxBorrowDaysInt) {
        this.readerId = readerId;
        this.readerName = text;
        this.gender = selectedItem;
        this.type = selectedItem1;
        this.borrowedBooks = borrowedBooks;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.maxBorrowCount = maxBorrowCountInt;
        this.maxBorrowDays = maxBorrowDaysInt;

    }


    public String getReaderId() {
        return readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getReaderGender() {
        return gender;
    }

    public String getReaderType() {
        return type;
    }

    public List<String> getBorrowedBooks() { // getter返回类型修改为List<String>
        return borrowedBooks;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getMaxBorrowCount() {
        return maxBorrowCount;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }


    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Reader{" +
                "readerId='" + readerId + '\'' +
                ", readerName='" + readerName + '\'' +
                ", readerGender='" + gender + '\'' +
                ", readerType='" + type + '\'' +
                ", borrowedBooks=" + borrowedBooks + // 集合自动调用toString()
                ", borrowDate=" + sdf.format(borrowDate) +
                ", returnDate=" + sdf.format(returnDate) +
                ", maxBorrowCount=" + maxBorrowCount +
                ", maxBorrowDays=" + maxBorrowDays +
                '}';
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public void setReaderGender(String readerGender) {
        this.gender = readerGender;
    }

    public void setReaderType(String readerType) {
        this.type = readerType;
    }

    public void setBorrowedBooks(List<String> borrowedBooks) { // setter参数类型修改为List<String>
        this.borrowedBooks = borrowedBooks;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setMaxBorrowCount(int maxBorrowCount) {
        this.maxBorrowCount = maxBorrowCount;
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }


    public void addBorrowRecord(String bookId, Date borrowDate) {
        if (borrowedBooks.size() < maxBorrowCount) {
            borrowedBooks.add(bookId);
            this.borrowDate = borrowDate;
        } else {
            System.out.println("借阅数量已达上限，无法借阅更多图书。");
        }
    }
}

