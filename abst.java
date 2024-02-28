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
  Book book1 = new Book("A Book On The Philosopher Stone", "Alexander The Great", 10);
  Book book2 = new Book("Cosmos","Carl Sagan", 20);
  Book book3 = new Book("Daedalus Biogrophy", "Daedalus Ic", 30);
  Book book4 = new Book("Flowers for Agernon", "Finny Love", 40);
  Book book5 = new Book("Gardener Story", "George Sally", 50);
  Book book6 = new Book("Hades", "Harold Patterson", 60);
  Book book7 = new Book("Icarus", "Italian Love", 70);
  Book book8 = new Book("Juliet and Romeo", "Janny List", 80);
  
  // for price
  
  //tree 1
  ABST<Book> priceLeaf = new Leaf<Book>(new BooksByPrice());
  ABST<Book> priceBST1 = new Node<Book>(new BooksByPrice(), book1, priceLeaf, priceLeaf);
  ABST<Book> priceBST2 = new Node<Book>(new BooksByPrice(), book3, priceLeaf, priceLeaf);
  ABST<Book> priceBST3 = new Node<Book>(new BooksByPrice(), book2, priceBST1, priceBST2);
  ABST<Book> priceBST4 = new Node<Book>(new BooksByPrice(), book6, priceLeaf, priceLeaf); 
  ABST<Book> priceBST5 = new Node<Book>(new BooksByPrice(), book8, priceLeaf, priceLeaf); 
  ABST<Book> priceBST6 = new Node<Book>(new BooksByPrice(), book7, priceBST4, priceBST5); 
  ABST<Book> priceBST7 = new Node<Book>(new BooksByPrice(), book5, priceBST3, priceBST6); 
  
  //tree2
  ABST<Book> priceBST8 = new Node<Book>(new BooksByPrice(), book4, priceLeaf, priceLeaf);
  ABST<Book> priceBST9 = new Node<Book>(new BooksByPrice(), book5, priceBST8, priceLeaf);
  ABST<Book> priceBST13 = new Node<Book>(new BooksByPrice(), book2, priceLeaf, priceLeaf);
  ABST<Book> priceBST10 = new Node<Book>(new BooksByPrice(), book3, priceBST13, priceBST9); 
  
  //tree 3
  ABST<Book> priceBST11 = new Node<Book>(new BooksByPrice(), book2, priceBST1, priceLeaf);
  ABST<Book> priceBST12 = new Node<Book>(new BooksByPrice(), book3, priceBST11, priceBST8);
  
  // for author
  
  ABST<Book> authorLeaf = new Leaf<Book>(new BooksByAuthor());
  ABST<Book> authorBST1 = new Node<Book>(new BooksByAuthor(), book1, authorLeaf, authorLeaf);
  ABST<Book> authorBST2 = new Node<Book>(new BooksByAuthor(), book3, authorLeaf, authorLeaf);
  ABST<Book> authorBST3 = new Node<Book>(new BooksByAuthor(), book2, authorBST1, authorBST2);
  ABST<Book> authorBST4 = new Node<Book>(new BooksByAuthor(), book6, authorLeaf, authorLeaf); 
  ABST<Book> authorBST5 = new Node<Book>(new BooksByAuthor(), book8, authorLeaf, authorLeaf); 
  ABST<Book> authorBST6 = new Node<Book>(new BooksByAuthor(), book7, authorBST4, authorBST5); 
  ABST<Book> authorBST7 = new Node<Book>(new BooksByAuthor(), book5, authorBST3, authorBST6); 
  
  //tree2
  ABST<Book> authorBST8 = new Node<Book>(new BooksByAuthor(), book4, authorLeaf, authorLeaf);
  ABST<Book> authorBST9 = new Node<Book>(new BooksByAuthor(), book5, authorBST8, authorLeaf);
  ABST<Book> authorBST13 = new Node<Book>(new BooksByAuthor(), book2, authorLeaf, authorLeaf);
  ABST<Book> authorBST10 = new Node<Book>(new BooksByAuthor(), book3, authorBST13, authorBST9); 
  
  //tree 3
  ABST<Book> authorBST11 = new Node<Book>(new BooksByAuthor(), book2, authorBST1, authorLeaf);
  ABST<Book> authorBST12 = new Node<Book>(new BooksByAuthor(), book3, authorBST11, authorBST8);
  
  // for title
  
  ABST<Book> titleLeaf = new Leaf<Book>(new BooksByTitle());
  ABST<Book> titleBST1 = new Node<Book>(new BooksByTitle(), book1, authorLeaf, authorLeaf);
  ABST<Book> titleBST2 = new Node<Book>(new BooksByTitle(), book3, authorLeaf, authorLeaf);
  ABST<Book> titleBST3 = new Node<Book>(new BooksByTitle(), book2, authorBST1, authorBST2);
  ABST<Book> titleBST4 = new Node<Book>(new BooksByTitle(), book6, authorLeaf, authorLeaf); 
  ABST<Book> titleBST5 = new Node<Book>(new BooksByTitle(), book8, authorLeaf, authorLeaf); 
  ABST<Book> titleBST6 = new Node<Book>(new BooksByTitle(), book7, authorBST4, authorBST5); 
  ABST<Book> titleBST7 = new Node<Book>(new BooksByTitle(), book5, authorBST3, authorBST6); 
  
  //tree2
  ABST<Book> titleBST8 = new Node<Book>(new BooksByTitle(), book4, authorLeaf, authorLeaf);
  ABST<Book> titleBST9 = new Node<Book>(new BooksByTitle(), book5, authorBST8, authorLeaf);
  ABST<Book> titleBST13 = new Node<Book>(new BooksByTitle(), book2, authorLeaf, authorLeaf);
  ABST<Book> titleBST10 = new Node<Book>(new BooksByTitle(), book3, authorBST13, authorBST9); 
  
  //tree 3
  ABST<Book> titleBST11 = new Node<Book>(new BooksByTitle(), book2, authorBST1, authorLeaf);
  ABST<Book> titleBST12 = new Node<Book>(new BooksByTitle(), book3, authorBST11, authorBST8);
  
  
  BooksByTitle titleComparator = new BooksByTitle();
  BooksByAuthor authorComparator = new BooksByAuthor();
  BooksByPrice priceComparator = new BooksByPrice();
}














