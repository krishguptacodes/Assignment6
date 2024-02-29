import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.*;

// representing the abstract class ABST<T>
abstract class ABST<T> {
  /*
  TEMPLATE:
  FIELDS:
  ... this.order ... -- Comparator<T>
  METHODS:
  ... this.insert(T) ... -- ABST<T>
  ... this.present(T) ... -- boolean
  ... this.getLeftmost() ... -- T
  ... this.isLeaf() ... -- boolean
  ... this.getRight() ... -- ABST<T>
  ... this.sameTree(ABST<T>) ... -- ABST<T>
  ... this.sameData(ABST<T>) ... -- ABST<T>
  METHODS FOR FIELDS:
   */

  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

  //takes an item and produces a new BST with the given item inserted in the correct place.
  //If the value is a duplicate, insert it into the right-side subtree.
  public abstract ABST<T> insert(T item);

  //takes an item and determines whether the item is present in the BST
  public abstract boolean present(T item);

  //returns the left most item in this tree
  public abstract T getLeftmost();

  //helper to determine if the ABST<T> is a leaf
  public abstract boolean isLeaf();

  //returns the tree containing all but the leftmost item of this tree.
  public abstract ABST<T> getRight();
  
  //determines whether this binary search tree is the same as the given one.
  public abstract boolean sameTree(ABST<T> given);

  //determines whether this binary search tree is the same as the given one.
  public abstract boolean sameTreeHelp(T data, ABST<T> left, ABST<T> right);
  
  //determines whether this binary search tree is the same as the given one.
  public abstract boolean sameData(ABST<T> given);
  
  //produces a list of items in the tree in the sorted order
  public abstract IList<T> buildList();
}

//represents the leaf class that extends the ABST<T> class
class Leaf<T> extends ABST<T> {
  /*
  TEMPLATE:
  FIELDS:
  ... this.order ... -- Comparator<T>
  METHODS:
  ... this.insert(T) ... -- ABST<T>
  ... this.present(T) ... -- boolean
  ... this.getLeftmost() ... -- T
  ... this.isLeaf() ... -- boolean
  ... this.getRight() ... -- ABST<T>
  ... this.sameTree(ABST<T>) ... -- ABST<T>
  ... this.sameData(ABST<T>) ... -- ABST<T>
  METHODS FOR FIELDS:
   */

  Leaf(Comparator<T> order) {
    super(order);
  }

  // inserts the item into this leaf
  public ABST<T> insert(T item) {
    return new Node<T>(this.order, item, new Leaf<T>(order), new Leaf<T>(order)); 
  }

  // returns whether this item is in the binary search tree
  public boolean present(T item) {
    return false;
  }

  // Returns whether this is a leaf
  public boolean isLeaf() {
    return true;
  }

  // Get the left most item in the BST
  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // Get the right most item in the BST
  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  //determines whether this binary search tree is the same as this leaf.
  public boolean sameTree(ABST<T> otherTree) {
    return otherTree.isLeaf();
  }

//determines whether this binary search tree is the same as this leaf.
  public boolean sameTreeHelp(T data, ABST<T> left, ABST<T> right) {
    return false;
  }

//determines whether this binary search tree is the same as the given one.
  public boolean sameData(ABST<T> given) {
    return false;
  }

  //produces a list of items in the tree in the sorted order
  public IList<T> buildList() {
    return new MtList<T>();
  }
}

// represents the node class that extends the ABST<T> class
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  
  /*
  TEMPLATE:
  FIELDS:
  ... this.order ... -- Comparator<T>
  ... this.data ... -- T
  ... this.left ... -- ABST<T>
  ... this.right ... -- ABST<T>
  METHODS:
  ... this.insert(T) ... -- ABST<T>
  ... this.present(T) ... -- boolean
  ... this.getLeftmost() ... -- T
  ... this.isLeaf() ... -- boolean
  ... this.getRight() ... -- ABST<T>
  ... this.sameTree(ABST<T>) ... -- ABST<T>
  ... this.sameData(ABST<T>) ... -- ABST<T>
  METHODS FOR FIELDS:
  ... this.left.insert(T) ... -- ABST<T>
  ... this.left.present(T) ... -- boolean
  ... this.left.getLeftmost() ... -- T
  ... this.left.isLeaf() ... -- boolean
  ... this.left.getRight() ... -- ABST<T>
  ... this.left.sameTree(ABST<T>) ... -- ABST<T>
  ... this.left.sameTreeHelp(T, ABST<T>, ABST<T>) ... -- ABST<T>
  ... this.left.sameData(ABST<T>) ... -- ABST<T>
  ... this.right.insert(T) ... -- ABST<T>
  ... this.right.present(T) ... -- boolean
  ... this.right.getLeftmost() ... -- T
  ... this.right.isLeaf() ... -- boolean
  ... this.right.getRight() ... -- ABST<T>
  ... this.right.sameTree(ABST<T>) ... -- ABST<T>
  ... this.right.sameTreeHelp(T, ABST<T>, ABST<T>) ... -- ABST<T>
  ... this.right.sameData(ABST<T>) ... -- ABST<T>
   */

  //takes an item and produces a new BST with the given item inserted in the correct place.
  //If the value is a duplicate, insert it into the right-side subtree.
  public ABST<T> insert(T item) {
    if (order.compare(item, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(item), this.right);
    }
    else if (order.compare(this.data, item) == 0) {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
    else {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }

  }

  //takes an item and determines whether the item is present in the BST
  public boolean present(T item) {
    return order.compare(this.data, item) == 0
        || this.left.present(item)
        || this.right.present(item);
  }

  // returns whether the ABST<T> is a leaf
  public boolean isLeaf() {
    return false;
  }

  //returns the left most item in this tree
  public T getLeftmost() {
    if (this.left.isLeaf()) {
      return this.data;
    }
    else {
      return this.left.getLeftmost();
    }
  }

  //returns the tree containing all but the leftmost item of this tree.
  public ABST<T> getRight() {
    if (this.left.isLeaf()) {
      return this.right;
    }
    else {
      return new Node<T>(this.order, this.data, this.left.getRight(), this.right);
    }
  }

  //determines whether this binary search tree is the same as the given one.
  public boolean sameTree(ABST<T> given) {
    return given.sameTreeHelp(this.data, this.left, this.right);
  }

  //determines whether this binary search tree is the same as the given one.
  public boolean sameTreeHelp(T otherData, ABST<T> otherLeft, ABST<T> otherRight) {
    return (this.order.compare(this.data, otherData) == 0)
        && (this.left.sameTree(otherLeft) && (this.right.sameTree(otherRight)));
  }
  
  //determines whether this binary search tree is the same as the given one.
  public boolean sameData(ABST<T> given) {
    return false;
  }

  //produces a list of items in the tree in the sorted order
  public IList<T> buildList() {
    if (this.left.isLeaf()) {
      return this.right.buildList
    }
    else {
      return new ConsList<T>(this.getLeftmost(), this.getRight().buildList());
    }
  }

}

interface IList<T> {
  
}

class MtList<T> implements IList<T> {
  
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
}

// represents the book class
class Book {
  String title;
  String author;
  int price;
  /*
  TEMPLATE:
  FIELDS:
  ... this.title ... -- String
  ... this.author ... -- String
  ... this.price ... -- int
  METHODS:
  METHODS FOR FIELDS:
   */
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

// represents the BooksByTitle class that implements Comparator<Book>
class BooksByTitle implements Comparator<Book> {

  // to compare the titles of both books
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

//represents the BooksByAuthor class that implements Comparator<Book>
class BooksByAuthor implements Comparator<Book> {

  // to compare the authors of both books
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

//represents the BooksByPrice class that implements Comparator<Book>
class BooksByPrice implements Comparator<Book> {

  // to compare the prices of both books
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

//representing the ExampleBooks class that contains all examples and testing
class ExampleBooks{

  // examples of books
  Book book1 = new Book("A Book On The Philosopher Stone", "Alexander The Great", 10);
  Book book2 = new Book("Cosmos","Carl Sagan", 20);
  Book book3 = new Book("Daedalus Biogrophy", "Daedalus Ic", 30);
  Book book4 = new Book("Flowers for Agernon", "Finny Love", 40);
  Book book5 = new Book("Gardener Story", "George Sally", 50);
  Book book6 = new Book("Hades", "Harold Patterson", 60);
  Book book7 = new Book("Icarus", "Italian Love", 70);
  Book book8 = new Book("Juliet and Romeo", "Janny List", 80);
  Book book9 = new Book("Kant: Version 1", "Kant Immanuel", 90);
  Book book10 = new Book("Lenny's Adventures", "Linel Mike", 100);

  // examples of the trees

  // for price

  //tree 1
  ABST<Book> priceLeaf = new Leaf<Book>(new BooksByPrice());
  ABST<Book> priceBST1 = new Node<Book>(new BooksByPrice(), book1, priceLeaf, priceLeaf);
  ABST<Book> priceBST2 = new Node<Book>(new BooksByPrice(), book3, priceLeaf, priceLeaf);
  ABST<Book> priceBST3 = new Node<Book>(new BooksByPrice(), book2, priceBST1, priceBST2);
  ABST<Book> priceBST4 = new Node<Book>(new BooksByPrice(), book6, priceLeaf, priceLeaf); 
  ABST<Book> priceBST5 = new Node<Book>(new BooksByPrice(), book8, priceLeaf, priceLeaf); 
  ABST<Book> priceBST5HelpInsert = new Node<Book>(new BooksByPrice(), book8, priceLeaf, priceBST5);
  ABST<Book> priceBST6 = new Node<Book>(new BooksByPrice(), book7, priceBST4, priceBST5); 
  ABST<Book> priceBST6HelpInsert = new Node<Book>(new BooksByPrice(), book7, priceBST4, priceBST5HelpInsert); 
  ABST<Book> priceBST7 = new Node<Book>(new BooksByPrice(), book5, priceBST3, priceBST6);
  ABST<Book> priceBST7HelpInsert = new Node<Book>(new BooksByPrice(), book5, priceBST3, priceBST6HelpInsert); 
  
  ABST<Book> priceTreeHelp = new Node<Book>(new BooksByPrice(), book3, priceBST1, priceLeaf); 

  ABST<Book> priceBST3R = new Node<Book>(new BooksByPrice(), book2, priceLeaf, priceBST2);
  ABST<Book> priceBST7R = new Node<Book>(new BooksByPrice(), book5, priceBST3R, priceBST6);
  
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

  //tree 1
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

  IList<Book> priceTree2 = new ConsList<Book>(book2, new ConsList<Book>(book3, 
      new ConsList<Book>(book4, new ConsList<Book>(book5, new MtList<Book>()))));
  
  BooksByTitle titleComparator = new BooksByTitle();
  BooksByAuthor authorComparator = new BooksByAuthor();
  BooksByPrice priceComparator = new BooksByPrice();

  // testing for insert method 
  public boolean testInsert(Tester t) {
    return // for price 
        t.checkExpect(this.priceLeaf.insert(book1), this.priceBST1)
        && t.checkExpect(this.priceBST2.insert(book1), this.priceTreeHelp)
        && t.checkExpect(this.priceBST7.insert(book8), priceBST7HelpInsert); 
    // if duplicate and leaf at end
    // if duplicate but with no leaf
    // not duplicate case

    // for author

    // for title
  }

  // testing for the present method
  public boolean testPresent(Tester t) {
    return // for price 
        t.checkExpect(this.priceLeaf.present(book2), false)
        && t.checkExpect(this.priceBST1.present(book1), true)
        && t.checkExpect(this.priceBST7.present(book6), true)
        && t.checkExpect(this.priceBST7.present(book10), false) 
        // for author
        && t.checkExpect(this.authorLeaf.present(book2), false)
        && t.checkExpect(this.authorBST1.present(book1), true)
        && t.checkExpect(this.authorBST7.present(book6), true)
        && t.checkExpect(this.authorBST7.present(book10), false) 
        // for title
        && t.checkExpect(this.titleLeaf.present(book2), false)
        && t.checkExpect(this.titleBST11.present(book2), true)
        && t.checkExpect(this.titleBST12.present(book6), false)
        && t.checkExpect(this.titleBST12.present(book3), true); 
  }

  //testing for the exceptions for getLeftmost & getRight
  boolean testRuntimeException(Tester t) {
    return // for getLeftMost 
        t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.priceLeaf, "getLeftmost")
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.authorLeaf, "getLeftmost")
        && t.checkException(new RuntimeException("No right of an empty tree"),
            this.authorLeaf, "getRight")
        && t.checkException(new RuntimeException("No right of an empty tree"),
            this.priceLeaf, "getRight");
  }

  // testing the getLeftmost method
  public boolean testGetLeftmost(Tester t) {
    return // for price
        t.checkExpect(this.priceBST7.getLeftmost(), this.book1)
        && t.checkExpect(this.priceBST10.getLeftmost(), this.book2)
        && t.checkExpect(this.priceBST12.getLeftmost(), this.book1)
        && t.checkExpect(this.priceBST1.getLeftmost(), this.book1)
        // for author
        && t.checkExpect(this.authorBST7.getLeftmost(), this.book1)
        && t.checkExpect(this.authorBST10.getLeftmost(), this.book2)
        && t.checkExpect(this.authorBST12.getLeftmost(), this.book1)
        // for price
        && t.checkExpect(this.authorBST7.getLeftmost(), this.book1)
        && t.checkExpect(this.authorBST10.getLeftmost(), this.book2)
        && t.checkExpect(this.authorBST12.getLeftmost(), this.book1); 
  }

  // testing the isLeaf method
  public boolean testIsLeaf(Tester t) {
    return 
        t.checkExpect(this.priceBST7.isLeaf(), false)
        && t.checkExpect(this.priceBST7.isLeaf(), false)
        && t.checkExpect(this.authorBST12.isLeaf(), false)
        && t.checkExpect(this.authorBST5.isLeaf(), false)
        && t.checkExpect(this.titleBST13.isLeaf(), false)
        && t.checkExpect(this.authorBST2.isLeaf(), false)
        && t.checkExpect(this.authorLeaf.isLeaf(), true)
        && t.checkExpect(this.titleLeaf.isLeaf(), true)
        && t.checkExpect(this.priceLeaf.isLeaf(), true);
  }

  // testing the getRight method
  public boolean testGetRight(Tester t) {
    return 
        t.checkExpect(this.priceBST6.getRight(), new Node<Book>(new BooksByPrice(), book7, priceLeaf, priceBST5))
        && t.checkExpect(this.priceBST3.getRight(), new Node<Book>(new BooksByPrice(), book2, priceLeaf, priceBST2))
        && t.checkExpect(this.priceBST7.getRight(), this.priceBST7R)
        && t.checkExpect(this.priceBST10.getRight(),
            new Node<Book>(new BooksByPrice(), book3, priceLeaf, priceBST9));
  }
  
  // testing the getRight method
  public boolean testSameTree(Tester t) {
    return 
        t.checkExpect(this.priceBST6.sameTree(this.priceBST6), true)
        && t.checkExpect(this.priceBST3.sameTree(this.priceBST6), false)
        && t.checkExpect(this.priceBST7.sameTree(this.priceLeaf), false)
        && t.checkExpect(this.priceBST7.sameTree(this.priceBST7), true);
  }
  
  
  // testing the getRight method
  public boolean atestBuildList(Tester t) {
    return 
        t.checkExpect(this.priceBST6.sameTree(this.priceBST6), true)
        && t.checkExpect(this.priceBST3.sameTree(this.priceBST6), false)
        && t.checkExpect(this.priceBST7.sameTree(this.priceLeaf), false)
        && t.checkExpect(this.priceBST7.sameTree(this.priceBST7), true);
  }

}













