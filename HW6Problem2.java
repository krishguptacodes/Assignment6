import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.*;

abstract class ABST<T> {
  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

}

class Leaf<T> extends ABST {
  Leaf(Comparator<T> order) {
    super(order);
  }
}

class Node<T> extends ABST {
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

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

class BooksByTitle implements Comparator<Book> {

  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

class BooksByAuthor implements Comparator<Book> {

  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

class BooksByPrice implements Comparator<Book> {

  public int compare(Book b1, Book b2) {
    if (b1.price < b2.price) {
      return -1;
    }
    else if (b1.price == b2.price) {
      return 0;
    }
    else {
      return 1;
    }
  }
}















