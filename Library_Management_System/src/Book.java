import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Book {
    private String id;//图书编号
    private String name;//图书名称
    private String type;//图书类别
    private String author;//作者
    private String publishing;//出版社
    private Date date;//出版时间
    private double price;//定价
    private int num;//库存数量

    public Book(String id, String name, String type, String author, String publishing, String dateStr, String priceStr, String numStr) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.author = author;
        this.publishing = publishing;

        try {
            this.date = new SimpleDateFormat("yyyy-MM").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = new Date();
        }

        try {
            this.price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.price = 0.0;
        }

        try {
            this.num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.num = 0;
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getAuthor() { return author; }
    public String getPublishing() { return publishing; }
    public Date getDate() { return date; }
    public double getPrice() { return price; }
    public int getNum() { return num; }


    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", date=" + sdf.format(date) +
                ", price=" + price +
                ", num=" + num +
                '}';
    }

    public void setN(String s) {
        try {
            this.num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.num = 0;
        }
    }

    public void setNum(int i) {
        this.num = i;
    }

}

