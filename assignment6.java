import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;

//An IArithVisitor is a function over IArith
interface IArithVisitor<R> extends Function<IArith, R> {
  R visitConst(Const x);
  R visitUnaryFormula(UnaryFormula uf);
  R visitBinaryFormula(BinaryFormula bf);
}

// representing the interface IArith
interface IArith {

  //all things of IArith can accept visitor
  <R> R accept(IArithVisitor<R> visitor); 

  // helper to find all even numbers
  boolean evenHelper();

}

// representing the const class
class Const implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  ... this.num ... -- double
  METHODS:
  ... this.accept(IArithVisitor<R>) ... -- <R> R
  ... this.evenHelper() ... -- Boolean
  METHODS FOR FIELDS:
   */
  double num;
  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }

  public boolean evenHelper() {
    return (num % 2) == 0;
  }
}

// representing the UnaryFormua class
class UnaryFormula implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  ... this.name ... -- String
  ... this.child ... -- IArith
  ... this.func ... -- Function<Double, Double>
  METHODS:
  ... this.accept(IArithVisitor<R>) ... <R> R
  ... this.evenHelper() ... -- Boolean
  METHODS FOR FIELDS:
  ... this.child.accept(IArithVisitor<R>) ... <R> R
  ... this.child.evenHelper() ... -- Boolean
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

  public boolean evenHelper() {
    return this.child.evenHelper();
  }

}

//representing the BinaryFormula class
class BinaryFormula implements IArith {
  /*
  TEMPLATE:
  FIELDS:
  ... this.name ... -- String
  ... this.func ... -- Function<Double, Double>
  ... this.left ... -- IArith
  ... this.right ... -- IArith
  METHODS:
  ... this.accept(IArithVisitor<R>) ... <R> R
  ... this.evenHelper() ... -- Boolean
  METHODS FOR FIELDS:
  ... this.left.accept(IArithVisitor<R>) ... <R> R
  ... this.right.accept(IArithVisitor<R>) ... <R> R
  ... this.left.evenHelper() ... -- Boolean
  ... this.right.evenHelper() ... -- Boolean
   */

  String name;
  BiFunction <Double, Double, Double> func;
  IArith left;
  IArith right;
  BinaryFormula(String name, BiFunction <Double, Double, Double> func, IArith left, IArith right) {
    this.name = name;
    this.left = left;
    this.right = right;
    this.func = func;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }

  public boolean evenHelper() {
    return this.left.evenHelper() && this.right.evenHelper();
  }
}

// EvalVisitor is a function over IArith that visits an IArith and evaluates the tree to a Double answer.
class EvalVisitor implements IArithVisitor<Double> {
  
  // from IArith interface
  public Double apply(IArith iArith) {
    return iArith.accept(this);
  }

  // Const implementation
  public Double visitConst(Const c) {
    return c.num;
  }

  // UnaryFormula implementation
  public Double visitUnaryFormula(UnaryFormula uf) {
    return uf.func.apply(uf.child.accept(this));
  }

  //BinaryFormula implementation
  public Double visitBinaryFormula(BinaryFormula bf) {
    return bf.func.apply(bf.left.accept(this), bf.right.accept(this));
  }
}

//PrintVisitor is a function over IArith that visits an IArith an produces a String showing the 
// fully-parenthesized expression in Racket-like prefix notation
class PrintVisitor implements IArithVisitor<String>, Function<IArith, String> {
  public String apply(IArith iArith) {
    return iArith.accept(this);
  }

  // Const implementation
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  // UnaryFormula implementation
  public String visitUnaryFormula(UnaryFormula uf) {
    return "(" + uf.name + " " + uf.child.accept(this) + ")";
  }

  //BinaryFormula implementation
  public String visitBinaryFormula(BinaryFormula bf) {
    return "(" + bf.name + " " + bf.left.accept(this) + " " + bf.right.accept(this) + ")";
  }
}

class MirrorVisitor implements IArithVisitor<IArith>, Function<IArith, IArith> {
  public IArith apply(IArith iArith) {
    return iArith.accept(this);
  }

  // Const implementation
  public IArith visitConst(Const c) {
    return c;
  }
  
  // UnaryFormula implementation
  public IArith visitUnaryFormula(UnaryFormula uf) {
    return uf;
  }

  //BinaryFormula implementation
  public IArith visitBinaryFormula(BinaryFormula bf) {
    return new BinaryFormula(bf.name, bf.func, bf.right, bf.left);
  }
}

class AllEvenVisitor implements IArithVisitor<Boolean>, Function<IArith, Boolean> {
  public Boolean apply(IArith iArith) {
    return iArith.accept(this);
  }

  // Const implementation
  public Boolean visitConst(Const c) {
    return c.num % 2 == 0;
  }

  // UnaryFormula implementation
  public Boolean visitUnaryFormula(UnaryFormula uf) {
    return uf.evenHelper();
  }

  //BinaryFormula implementation
  public Boolean visitBinaryFormula(BinaryFormula bf) {
    return bf.evenHelper();
  }
}

// representing the 4 binary formulas

// implementing addition
class Plus implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 + d2;
  }
}

// implementing subtraction
class Minus implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 - d2;
  }  
}

// implementing multiplication
class Mul implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 * d2;
  }  
}

// implementing division
class Div implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 / d2;
  } 
}

//repesenting the 2 unary formulas

// implementing negation
class Neg implements Function<Double, Double> {
  public Double apply(Double d) {
    return d * -1;
  }  
}

// implementing squaring
class sqr implements Function<Double, Double> {
  public Double apply(Double d) {
    return d * d;
  }    
}

class ExamplesArith {
  IArith const1 = new Const(5);
  IArith const2 = new Const(4);
  IArith const3 = new Const(-2);
  IArith const4 = new Const(4.5);

  IArith plus1 = new BinaryFormula("plus", new Plus(), const1, const2); // 9
  IArith plus2 = new BinaryFormula("plus", new Plus(), const4, const4); // 9

  IArith minus1 = new BinaryFormula("minus", new Minus(), const1, const2); // 1
  IArith minus2 = new BinaryFormula("minus", new Minus(), const3, const3); // -4

  IArith mul1 = new BinaryFormula("mul", new Mul(), plus1, const3); // 27
  IArith mul2 = new BinaryFormula("mul", new Mul(), const3, const4); //-9

  IArith div1 = new BinaryFormula("div", new Div(), plus1, const4); // 2
  IArith div2 = new BinaryFormula("div", new Div(), const1, const1); // 1
  IArith div3 = new BinaryFormula("div", new Div(), const2, const3); // 1
  
  IArith neg1 = new UnaryFormula("neg", const1, new Neg()); // -5
  IArith neg2 = new UnaryFormula("neg", const3, new Neg()); // 2

  IArith sqr1 = new UnaryFormula("sqr", const4, new sqr()); // 20.25
  IArith sqr2 = new UnaryFormula("sqr", const3, new sqr()); // 4

  IArith exp1 = new BinaryFormula("mul", new Mul(), plus1, minus2); // -36
  IArith exp2 = new BinaryFormula("div", new Div(), exp1, sqr2); // -9
  IArith exp3 = new UnaryFormula("neg", exp2, new Neg()); // 9
  IArith exp4 = new BinaryFormula("plus", new Plus(), exp3, neg1); // 4
  IArith exp5 = new BinaryFormula("div", new Div(), exp4, div1); // 2
  IArith exp6 = new UnaryFormula("sqr", exp5, new sqr()); // 4
  IArith exp7 = new BinaryFormula("minus", new Minus(), exp6, const3); // 6
  
  IArith exp8 = new BinaryFormula("minus", new Minus(), minus2, div3); // 6
  
  // testing the EvalVisitor class
  boolean testEvalVisitor(Tester t) {  
    return t.checkExpect(plus1.accept(new EvalVisitor()), 9.0) 
        && t.checkExpect(const1.accept(new EvalVisitor()), 5.0)
        && t.checkExpect(mul1.accept(new EvalVisitor()), -18.0)
        && t.checkExpect(div1.accept(new EvalVisitor()), 2.0)
        && t.checkExpect(minus1.accept(new EvalVisitor()), 1.0)
        && t.checkExpect(minus2.accept(new EvalVisitor()), 0.0)
        && t.checkExpect(neg1.accept(new EvalVisitor()), -5.0)
        && t.checkExpect(sqr1.accept(new EvalVisitor()), 20.25)
        && t.checkExpect(exp1.accept(new EvalVisitor()), -36)
        && t.checkExpect(exp2.accept(new EvalVisitor()), -9)
        && t.checkExpect(exp3.accept(new EvalVisitor()), 9)
        && t.checkExpect(exp4.accept(new EvalVisitor()), 4)
        && t.checkExpect(exp5.accept(new EvalVisitor()), 2)
        && t.checkExpect(exp6.accept(new EvalVisitor()), 4)
        && t.checkExpect(exp7.accept(new EvalVisitor()), 6);
        
  }

  // testing the PrintVisitor class
  boolean testPrintVisitor(Tester t) {  
    return t.checkExpect(plus1.accept(new PrintVisitor()), "(plus 5.0 4.0)") 
        && t.checkExpect(const1.accept(new PrintVisitor()), "5.0")
        && t.checkExpect(mul1.accept(new PrintVisitor()), "(mul (plus 5.0 4.0) -2.0)")
        && t.checkExpect(div1.accept(new PrintVisitor()), "(div (plus 5.0 4.0) 4.5)")
        && t.checkExpect(minus1.accept(new PrintVisitor()), "(minus 5.0 4.0)")
        && t.checkExpect(minus2.accept(new PrintVisitor()), "(minus -2.0 -2.0)")
        && t.checkExpect(neg1.accept(new PrintVisitor()), "(neg 5.0)")
        && t.checkExpect(sqr1.accept(new PrintVisitor()), "(sqr 4.5)")
        && t.checkExpect(exp1.accept(new PrintVisitor()), "(mul (plus 5.0 4.0) "
            + "(minus -2.0 -2.0))")
        && t.checkExpect(exp2.accept(new PrintVisitor()), "(div (mul (plus 5.0 4.0) "
            + "(minus -2.0 -2.0)) (sqr 2.0))")
        && t.checkExpect(exp3.accept(new PrintVisitor()), "(neg (div (mul (plus 5.0 4.0) "
            + "(minus -2.0 -2.0)) (sqr 2.0)))")
        && t.checkExpect(exp4.accept(new PrintVisitor()), "(plus (neg (div (mul "
            + "(plus 5.0 4.0) (minus -2.0 -2.0)) (sqr 2.0))) (neg 5.0))")
        && t.checkExpect(exp5.accept(new PrintVisitor()), "(div (plus (neg (div (mul "
            + "(plus 5.0 4.0) (minus -2.0 -2.0)) (sqr 2.0))) (neg 5.0)) (div (plus 5.0 4.0) " + "4.5))")
        && t.checkExpect(exp6.accept(new PrintVisitor()), "(sqr (div (plus (neg "
            + "(div (mul (plus 5.0 4.0) (minus -2.0 -2.0)) (sqr 2.0))) (neg 5.0)) "
            + "(div (plus 5.0 4.0) " + "4.5)))")
        && t.checkExpect(exp7.accept(new PrintVisitor()), "(minus (sqr (div (plus (neg "
            + "(div (mul (plus 5.0 4.0) (minus -2.0 -2.0)) (sqr 2.0))) (neg 5.0)) "
            + "(div (plus 5.0 4.0) " + "4.5" + ")))"  + "3.0)");
    
  }
  
  // testing the MirrorVisitor class
  boolean testMirrorVisitor(Tester t) {
 // mirror tests
    return t.checkExpect(plus1.accept(new MirrorVisitor()), new BinaryFormula("plus", 
        new Plus(), const2, const1))
        && t.checkExpect(const1.accept(new MirrorVisitor()), const1)
        && t.checkExpect(mul1.accept(new MirrorVisitor()), 
            new BinaryFormula("mul", new Mul(), const3, plus1))
        && t.checkExpect(div1.accept(new MirrorVisitor()), 
            new BinaryFormula("div", new Div(), const4, plus1))
        && t.checkExpect(minus1.accept(new MirrorVisitor()), 
            new BinaryFormula("minus", new Minus(), const2, const1))
        && t.checkExpect(minus2.accept(new MirrorVisitor()), 
            new BinaryFormula("minus", new Minus(), const3, const3))
        && t.checkExpect(neg1.accept(new MirrorVisitor()), neg1)
        && t.checkExpect(sqr1.accept(new MirrorVisitor()), sqr1);
  }
 
  // testing the AllEvenVisitor class
  boolean testAllEvenVisitor(Tester t) {
    return t.checkExpect(plus1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(const1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(mul1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(div1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(minus1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(minus2.accept(new AllEvenVisitor()), true)
        && t.checkExpect(neg1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(sqr1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(exp1.accept(new AllEvenVisitor()), false)
        && t.checkExpect(exp2.accept(new AllEvenVisitor()), false)
        && t.checkExpect(exp3.accept(new AllEvenVisitor()), false)
        && t.checkExpect(exp4.accept(new AllEvenVisitor()), false)
        && t.checkExpect(exp8.accept(new AllEvenVisitor()), true);   
  }
  
  // testing the evenHelper method in the IArith interface
  // boolean testEvenHelper(Tester t) {

}
    
    
    
 

