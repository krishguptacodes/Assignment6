import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

// representing the Book class
public class Book() {
  String title;
  String author;
  int price;
  
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

public class BooksByTitle implements Comparator<Book> {
  public int
}

public class ABST<T> {
  Comparator<T> order;
  ABST(Comparator<t> order) {
    this.order = order;
  }
}

public class Leaf<T> extends ABST<T> {
  public Leaf(Comparator<T> order) {
    super(order);
  }
}

public class Node<T> extends ABST<T> {
  T data;
  ABST left;
  ABST right;
  Node(Comparator<T> order, T data, ABST left, ABST right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
}