import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Input lines = new Input();
        ArrayList<ArrayList<String>> each_line = lines.getInput();
        BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
        ArrayList<Books> bookarraylist = new ArrayList<>();
        ArrayList<Person> personarraylist = new ArrayList<>();


        int clockforbooks = 1;
        int clockforpeople = 1;

        for (ArrayList<String> line : each_line) {
            switch (line.get(0)) {
                case "addBook": // Adding books to bookarraylist by using books class.
                    if (line.get(1).equals("P")) { // Adding printed books.
                        Books book = new Books("Printed", clockforbooks);
                        bookarraylist.add(book);
                        output.write("Created new book: Printed [id: " + clockforbooks + "]\n");
                        clockforbooks += 1;
                    } else if (line.get(1).equals("H")) { // Adding handwritten books.
                        Books book = new Books("Handwritten", clockforbooks);
                        bookarraylist.add(book);
                        output.write("Created new book: Handwritten [id: " + clockforbooks + "]\n");
                        clockforbooks += 1;
                    }
                    break;
                case "addMember": // Adding people to personarraylist by using person class.
                    if (line.get(1).equals("S")) { // Adding students.
                        Person person = new Person("Student", clockforpeople);
                        personarraylist.add(person);
                        output.write("Created new member: Student [id: " + clockforpeople + "]\n");
                        clockforpeople += 1;
                    } else if (line.get(1).equals("A")) { // Adding academicians.
                        Person person = new Person("Academic", clockforpeople);
                        personarraylist.add(person);
                        output.write("Created new member: Academic [id: " + clockforpeople + "]\n");
                        clockforpeople += 1;
                    }
                    break;
                case "borrowBook": // Borrowing books by using person and books classes.
                    for (Books book : bookarraylist) {
                        if (book.getId() == Integer.parseInt(line.get(1))) {
                            if (book.isIstaken()) { // If book is taken it gives an error message.
                                output.write("This book is taken!\n");
                            } else if (book.getName().equals("Handwritten")) { // If book type is handwritten it gives an error message.
                                output.write("You cannot borrow this book!\n");
                            } else {
                                for (Person person : personarraylist) {
                                    if (person.getId() == Integer.parseInt(line.get(2))) { // Checks the ID's for people.
                                        if (person.getName().equals("Academic") && person.getBorrowedbooks() < 4) { // If person name is Academic and the person didn't take 4 books, it will take the book.
                                            LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            book.setBorrowdate(time);
                                            output.write("The book [" + book.getId() + "] was borrowed by member [" + person.getId() + "] at " + time + "\n");
                                            book.setReturndate(time.plus(2, ChronoUnit.WEEKS));
                                            book.setIdforperson(person.getId());
                                            book.setIstaken(true);
                                            person.setBorrowedbooks(person.getBorrowedbooks() + 1);
                                        } else if (person.getName().equals("Student") && person.getBorrowedbooks() < 2) {// If person name is Student and the person didn't take 2 books, it will take the book.
                                            LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            book.setBorrowdate(time);
                                            output.write("The book [" + book.getId() + "] was borrowed by member [" + person.getId() + "] at " + time + "\n");
                                            book.setReturndate(time.plus(1, ChronoUnit.WEEKS));
                                            book.setIstaken(true);
                                            book.setIdforperson(person.getId());
                                            person.setBorrowedbooks(person.getBorrowedbooks() + 1);
                                        } else { // If the person who takes more than 4 or 2 books depending on the person, it will give an error message.
                                            output.write("You have exceeded the borrowing limit!\n");
                                        }
                                    }

                                }
                            }
                        }

                    }
                    break;
                case "returnBook":
                    for (Books book : bookarraylist) {
                        if (book.getId() == Integer.parseInt(line.get(1))) {
                            if (!book.isIstaken()) { // If book is not taken it gives an error message.
                                output.write("This book is not taken!\n");
                            } else {
                                for (Person person : personarraylist) {
                                    if (person.getId() == Integer.parseInt(line.get(2)) && book.getIdforperson() == person.getId()) {
                                        if (book.getReturndate() == null) { // If there is no returnDate for the book(if the person read the book in library), it will return the book by these commands below.
                                            output.write("The book [" + book.getId() + "] was returned by member [" + person.getId() + "] at " + line.get(3) + " Fee: 0\n");
                                            book.setIstaken(false);
                                            book.setBorrowdate(null);
                                            book.setIdforperson(0);
                                            book.setBookextend(0);
                                        } else { // If there is returnDate for the book(if the person took the book), it will return the book by these commands below.
                                            LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            long diff = book.getReturndate().until(time, ChronoUnit.DAYS);
                                            if (diff <= 0) { // If the person brought the book until time.
                                                output.write("The book [" + book.getId() + "] was returned by member [" + person.getId() + "] at " + time + " Fee: 0\n");
                                                book.setReturndate(null);
                                                book.setBorrowdate(null);
                                                book.setIdforperson(0);
                                                book.setIstaken(false);
                                                person.setBorrowedbooks(person.getBorrowedbooks() - 1);
                                                book.setBookextend(0);
                                            } else { // If the person didn't bring the book until deadline.
                                                output.write("The book [" + book.getId() + "] was returned by member [" + person.getId() + "] at " + time + " Fee: " + diff + "\n");
                                                book.setReturndate(null);
                                                book.setBorrowdate(null);
                                                book.setIdforperson(0);
                                                book.setIstaken(false);
                                                person.setBorrowedbooks(person.getBorrowedbooks() - 1);
                                                book.setBookextend(0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "readInLibrary":
                    for (Books book : bookarraylist) {
                        if (book.getId() == Integer.parseInt(line.get(1))) {
                            if (book.isIstaken()) { // If the book is taken, then person can't read the book.
                                output.write("You can not read this book!\n");
                            } else {
                                for (Person person : personarraylist) {
                                    if (person.getId() == Integer.parseInt(line.get(2))) { // readInLibrary commands for academicians and students.
                                        if (person.getName().equals("Academic") && book.getName().equals("Handwritten")) {
                                            LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            output.write("The book [" + book.getId() + "] was read in library by member [" + person.getId() + "] at " + time + "\n");
                                            book.setIstaken(true);
                                            book.setBorrowdate(time);
                                            book.setIdforperson(person.getId());
                                        } else if (person.getName().equals("Student") && book.getName().equals("Handwritten")) { // Students can't read handwritten books.
                                            output.write("Students can not read handwritten books!\n");
                                        } else {
                                            LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                            output.write("The book [" + book.getId() + "] was read in library by member [" + person.getId() + "] at " + time + "\n");
                                            book.setIstaken(true);
                                            book.setBorrowdate(time);
                                            book.setIdforperson(person.getId());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "extendBook":
                    for (Books book : bookarraylist) {
                        if (book.getId() == Integer.parseInt(line.get(1))) {
                            for (Person person : personarraylist) {
                                if (book.getReturndate() != null && person.getName().equals("Academic") && person.getId() == Integer.parseInt(line.get(2))) {
                                    LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    long diff = time.until(book.getReturndate(), ChronoUnit.DAYS);
                                    if (diff <= 14 && book.getBookextend() == 0) { // If there is no mistake, extends the returndate by two weeks.
                                        output.write("The deadline of book [" + book.getId() + "] was extended by member [" + person.getId() + "] at " + time + "\n");
                                        book.setReturndate(time.plus(2, ChronoUnit.WEEKS));
                                        output.write("New deadline of book [" + book.getId() + "] is " + book.getReturndate() + "\n");
                                        book.setBookextend(1);
                                    } else if (diff > 14 || book.getBookextend() != 0) { // If the academician try to extend the book more than once or extend the deadline after passing.
                                        output.write("You cannot extend the deadline!\n");
                                    }
                                } else if (book.getReturndate() != null && person.getName().equals("Student") && person.getId() == Integer.parseInt(line.get(2))) {
                                    LocalDate time = LocalDate.parse(line.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    long diff = time.until(book.getReturndate(), ChronoUnit.DAYS);
                                    if (diff <= 7 && book.getBookextend() == 0) { // If there is no mistake, extends the returndate by one weeks.
                                        output.write("The deadline of book [" + book.getId() + "] was extended by member [" + person.getId() + "] at " + time + "\n");
                                        book.setReturndate(time.plus(1, ChronoUnit.WEEKS));
                                        output.write("New deadline of book [" + book.getId() + "] is " + book.getReturndate() + "\n");
                                        book.setBookextend(1);
                                    } else if (diff > 7 || book.getBookextend() != 0) { // If the student try to extend the book more than once or extend the deadline after passing.
                                        output.write("You cannot extend the deadline!\n");
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "getTheHistory": // Writing the information of library.
                    output.write("History of library:\n");
                    output.write("\n");
                    long countStudent = personarraylist.stream().filter(b -> b.getName().equals("Student")).count(); // Student count in personarraylist.
                    output.write("Number of students: " + countStudent + "\n");
                    for (Person person : personarraylist) {
                        if (person.getName().equals("Student")) {
                            output.write("Student [id: " + person.getId() + "]\n");
                        }
                    }
                    output.write("\n");
                    long countAcademic = personarraylist.stream().filter(b -> b.getName().equals("Academic")).count(); // Academician count in personarraylist.
                    output.write("Number of academics: " + countAcademic + "\n");
                    for (Person person : personarraylist) {
                        if (person.getName().equals("Academic")) {
                            output.write("Academic [id: " + person.getId() + "]\n");
                        }
                    }
                    output.write("\n");
                    long countPrinted = bookarraylist.stream().filter(b -> b.getName().equals("Printed")).count(); // Printed book count in bookarraylist.
                    output.write("Number of printed books: " + countPrinted + "\n");
                    for (Books book : bookarraylist) {
                        if (book.getName().equals("Printed")) {
                            output.write("Printed [id: " + book.getId() + "]\n");
                        }
                    }
                    output.write("\n");
                    long countHandwritten = bookarraylist.stream().filter(b -> b.getName().equals("Handwritten")).count(); // Handwritten book count in bookarraylist.
                    output.write("Number of handwritten books: " + countHandwritten + "\n");
                    for (Books book : bookarraylist) {
                        if (book.getName().equals("Handwritten")) {
                            output.write("Handwritten [id: " + book.getId() + "]\n");
                        }
                    }
                    output.write("\n");
                    long countBorrowed = bookarraylist.stream().filter(b -> b.getReturndate() != null).count(); // Borrowed book count in bookarraylist.
                    output.write("Number of borrowed books: " + countBorrowed + "\n");
                    for (Books book : bookarraylist) {
                        if (book.getReturndate() != null) {
                            output.write("The book [" + book.getId() + "] was borrowed by member [" + book.getIdforperson() + "] at " + book.getBorrowdate() + "\n");
                        }
                    }
                    output.write("\n");
                    long countReadInLibrary = bookarraylist.stream().filter(b -> b.getReturndate() == null && b.isIstaken()).count(); // Books that reading in library at the moment count in bookarraylist.
                    output.write("Number of books read in library: " + countReadInLibrary + "\n");
                    for (Books book : bookarraylist) {
                        if (book.getReturndate() == null && book.isIstaken()) {
                            output.write("The book [" + book.getId() + "] was read in library by member [" + book.getIdforperson() + "] at " + book.getBorrowdate() + "\n");
                        }
                    }
                    break;
            }
        }
        output.close();
}}