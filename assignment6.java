import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

interface IArithVisitor<R> extends Function<IArith, R> {
  R visitConst(Const x);
  R visitUnaryFormula(UnaryFormula uf);
  R visitBinaryFormula(BinaryFormula bf);
}

interface IArith {
  
  <R> R accept(IArithVisitor<R> visitor); // all things of IFoo can accept visitor

}

class Const implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  METHODS:
  METHODS FOR FIELDS:
   */
  double num;
  Const(double num) {
    this.num = num;
  }
  
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

class UnaryFormula implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  METHODS:
  METHODS FOR FIELDS:
   */
  
  String name;
  IArith child;
  Function<Double, Double> func;
  
  UnaryFormula(String name, IArith child, Function<Double, Double> func) {
    this.name = name;
    this.child = child;
    this.func = func;
  }
  
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnaryFormula(this);
  }
}

class BinaryFormula implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  METHODS:
  METHODS FOR FIELDS:
   */
  
  String name;
  IArith left;
  IArith right;
  BiFunction <Double, Double, Double> func;
  BinaryFormula(String name, IArith left, IArith right, BiFunction <Double, Double, Double> func) {
    this.name = name;
    this.left = left;
    this.right = right;
    this.func = func;
  }
  
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }
}
class EvalVisitor implements IArithVisitor<Double> {
  public Double apply(IArith iArith) {
      return iArith.accept(this);
  }

  public Double visitConst(Const c) {
      return c.num;
  }

  public Double visitUnaryFormula(UnaryFormula uf) {
      return uf.func.apply(uf.child.accept(this));
  }

  public Double visitBinaryFormula(BinaryFormula bf) {
      return bf.func.apply(bf.left.accept(this), bf.right.accept(this));
  }
}

class PrintVisitor implements IArithVisitor<String>, Function<IArith, String> {
  public String apply(IArith iArith) {
    return iArith.accept(this);
  }
  
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }
  
  public String visitUnaryFormula(UnaryFormula uf) {
    return "(" + uf.name + " " + uf.child.accept(this) + ")";
  }
  
  public String visitBinaryFormula(BinaryFormula bf) {
    return "(" + bf.name + " " + bf.left.accept(this) + " " + bf.right.accept(this) + ")";
  }
}