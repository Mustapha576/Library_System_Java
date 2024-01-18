public class Person {
    private String name;
    private int id;
    private int borrowedbooks;

    public int getBorrowedbooks() {
        return borrowedbooks;
    }

    public void setBorrowedbooks(int borrowedbooks) {
        this.borrowedbooks = borrowedbooks;
    }

    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }

    public Person(String name, int id){
        this.name = name;
        this.id = id;
    }

}

