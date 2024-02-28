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
  
  public abstract ABST<T> insert(T item);

}

class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    super(order);
  }
  
  public ABST<T> insert(T item) {
    return new Node<T>(order, item, new Leaf<T>(order), new Leaf<T>(order)); 
  }
}

class Node<T> extends ABST<T> {
  T data;
  ABST left;
  ABST right;

  Node(Comparator<T> order, T data, ABST left, ABST right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  
  public ABST<T> insert(T item) {
    if (new BooksByPrice().compare(this.data, item) < 1)
      
      
    }
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

class ExampleBooks {
  Book book1 = new Book("The Philosopher Stone", "J.K Rowling", 10);
  Book book2 = new Book("Cosmos","Carl Sagan", 20);
  Book book3 = new Book("Our Place in the Cosmos", "Krish Gupta", 30);
  Book book4 = new Book("Flowers for Agernon", "Daniel Keyes", 40);
  Book book5 = new Book("Pele", "Anonymous", 50);
  Book book6 = new Book("The Killer Who Lived Among Us", "Daniel Nightmare", 60);
  Book book7 = new Book("Zero", "Math Person", 70);
  
  ABST<Book> priceLeaf = new Leaf<Book>(new BooksByPrice());
  ABST<Book> priceBST2 = new Node<Book>(new BooksByPrice(), book1, priceLeaf, priceLeaf);
  ABST<Book> priceBST5 = new Node<Book>(new BooksByPrice(), book4, priceLeaf, priceLeaf);
  ABST<Book> priceBST3 = new Node<Book>(new BooksByPrice(), book2, priceBST2, priceLeaf);
  ABST<Book> priceBST4 = new Node<Book>(new BooksByPrice(), book3, priceBST3, priceBST5); // bstA
  
  ABST<Book> authorLeaf = new Leaf<Book>(new BooksByAuthor());
  ABST<Book> authorBST1 = new Node<Book>(new BooksByAuthor(), book1, authorLeaf, authorLeaf);
  ABST<Book> authorBST2 = new Node<Book>(new BooksByAuthor(), book2, authorBST1, authorLeaf);
  ABST<Book> authorBST3 = new Node<Book>(new BooksByAuthor(), book3, authorBST2, authorBST1);
  ABST<Book> authorBST4 = new Node<Book>(new BooksByAuthor(), book4, authorBST3, authorBST2);
  ABST<Book> authorBST5 = new Node<Book>(new BooksByAuthor(), book5, authorBST4, authorLeaf);
  
  ABST<Book> titleLeaf = new Leaf<Book>(new BooksByTitle( ));
  ABST<Book> titleBST1 = new Node<Book>(new BooksByTitle(), book1, titleLeaf, titleLeaf);
  ABST<Book> titleBST2 = new Node<Book>(new BooksByTitle(), book2, titleLeaf, titleLeaf);
  ABST<Book> titleBST3 = new Node<Book>(new BooksByTitle(), book3, titleBST2, titleBST1);
  ABST<Book> titleBST4 = new Node<Book>(new BooksByTitle(), book4, titleBST3, titleBST2);
  ABST<Book> titleBST5 = new Node<Book>(new BooksByTitle(), book5, titleBST4, titleBST3);
  
  BooksByTitle titleComparator = new BooksByTitle();
  BooksByAuthor authorComparator = new BooksByAuthor();
  BooksByPrice priceComparator = new BooksByPrice();
}














