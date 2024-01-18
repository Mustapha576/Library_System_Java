import java.time.LocalDate;

public class Books {
    private String name;
    private int id;
    private int idforperson;
    private LocalDate borrowdate;
    private LocalDate returndate;
    private boolean istaken;
    private int bookextend;

    public int getBookextend() {
        return bookextend;
    }

    public void setBookextend(int bookextend) {
        this.bookextend = bookextend;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getIdforperson() {
        return idforperson;
    }

    public void setIdforperson(int idforperson) {
        this.idforperson = idforperson;
    }

    public LocalDate getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(LocalDate borrowdate) {
        this.borrowdate = borrowdate;
    }

    public LocalDate getReturndate() {
        return returndate;
    }

    public void setReturndate(LocalDate returndate) {
        this.returndate = returndate;
    }

    public boolean isIstaken() {
        return istaken;
    }

    public void setIstaken(boolean istaken) {
        this.istaken = istaken;
    }

    public Books(String name, int id){
        this.name = name;
        this.id = id;
    }

}
