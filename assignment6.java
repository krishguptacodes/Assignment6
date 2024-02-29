import java.util.function.BiFunction;
import java.util.function.Function;

import tester.Tester;

//An IArithVisitor is a function over IArith
interface IArithVisitor<R> extends Function<IArith, R> {
  R visitConst(Const x);

  R visitUnaryFormula(UnaryFormula uf);

  R visitBinaryFormula(BinaryFormula bf);
}

// representing the interface IArith
interface IArith {

  // all things of IArith can accept visitor
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

  // Accepts a visitor and points to const
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }

  // Returns true if this number is even
  public boolean evenHelper() {
    return (num % 2) == 0;
  }
}

// representing the UnaryFormua class
class UnaryFormula implements IArith {
  Function<Double, Double> func;
  String name;
  IArith child;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.name = name;
    this.child = child;
    this.func = func;
  }

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

  // Accepts a visitor and points to UnaryFormula
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnaryFormula(this);
  }

  //Returns true if this number is even
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

  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;

  BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right;

  }

  // Accepts a IArithVisitor and points to BinaryFormula
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }

  // Returns true if this number is even
  public boolean evenHelper() {
    return this.left.evenHelper() && this.right.evenHelper();
  }
}

// EvalVisitor is a function over IArith that visits 
// an IArith and evaluates the tree to a Double answer.
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
    return new UnaryFormula(uf.func, uf.name, uf.child.accept(this));
  }

  //BinaryFormula implementation
  public IArith visitBinaryFormula(BinaryFormula bf) {
    return new BinaryFormula(bf.func, bf.name, bf.right.accept(this), bf.left.accept(this));
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
    if (d2 == 0) {
      throw new RuntimeException("you can not divide a number by zero");
    } else {
      return d1/d2;
    }
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
class Sqr implements Function<Double, Double> {
  public Double apply(Double d) {
    return d * d;
  }    
}

class ExamplesArith {
  IArith const1 = new Const(5);
  IArith const2 = new Const(4);
  IArith const3 = new Const(-2);
  IArith const4 = new Const(4.5);
  IArith const5 = new Const(0);

  IArith plus1 = new BinaryFormula(new Plus(),"plus" , const1, const2); // 9
  IArith plus2 = new BinaryFormula(new Plus(), "plus", const4, const4); // 9

  IArith minus1 = new BinaryFormula(new Minus(), "minus", const1, const2); // 1
  IArith minus2 = new BinaryFormula(new Minus(),"minus", const5, const2); // -4
  IArith minus2Help = new BinaryFormula(new Minus(), "minus", const2, const5); // 

  IArith mul1 = new BinaryFormula(new Mul(), "mul", plus1, const3); // 27
  IArith mul2 = new BinaryFormula(new Mul(), "mul", const3, const4); //-9

  IArith div1 = new BinaryFormula(new Div(), "div", plus1, const4); // 2
  IArith div2 = new BinaryFormula(new Div(), "div", const1, const1); // 1
  IArith div3 = new BinaryFormula(new Div(), "div", const2, const3); // 1
  IArith div4 = new BinaryFormula(new Div(), "div", const2, const5); // divide by 0

  IArith neg1 = new UnaryFormula(new Neg(), "neg", const1); // -5
  IArith neg2 = new UnaryFormula(new Neg(), "neg", const3); // 2

  IArith sqr1 = new UnaryFormula(new Sqr(), "sqr", const4); // 20.25
  IArith sqr2 = new UnaryFormula(new Sqr(), "sqr", const3); // 4

  IArith exp1 = new BinaryFormula(new Mul(), "mul", plus1, minus2); // -36
  IArith exp1help = new BinaryFormula(new Mul(), "mul", minus2, plus1);
  IArith exp2 = new BinaryFormula(new Div(), "div", exp1, sqr2); // -9
  IArith exp3 = new UnaryFormula( new Neg(), "neg", exp2); // 9
  IArith exp4 = new BinaryFormula(new Plus(), "plus", exp3, neg1); // 4
  IArith exp5 = new BinaryFormula(new Div(), "div", exp4, div1); // 2
  IArith exp6 = new UnaryFormula(new Sqr(), "sqr", exp5); // 4
  IArith exp7 = new BinaryFormula(new Minus(), "minus", exp6, const3); // 6

  IArith exp8 = new BinaryFormula(new Minus(),"minus", minus2, div3); // 6

  //   testing the EvalVisitor class
  boolean testEvalVisitor(Tester t) {  
    return t.checkExpect(plus1.accept(new EvalVisitor()), 9.0) 
        && t.checkExpect(const1.accept(new EvalVisitor()), 5.0)
        && t.checkExpect(mul1.accept(new EvalVisitor()), -18.0)
        && t.checkExpect(div1.accept(new EvalVisitor()), 2.0)
        && t.checkExpect(minus1.accept(new EvalVisitor()), 1.0)
        && t.checkExpect(minus2.accept(new EvalVisitor()), -4.0)
        && t.checkExpect(neg1.accept(new EvalVisitor()), -5.0)
        && t.checkExpect(sqr1.accept(new EvalVisitor()), 20.25)

        && t.checkExpect(exp1.accept(new EvalVisitor()), -36.0)
        && t.checkExpect(exp2.accept(new EvalVisitor()), -9.0)
        && t.checkExpect(exp3.accept(new EvalVisitor()), 9.0)
        && t.checkExpect(exp4.accept(new EvalVisitor()), 4.0)
        && t.checkExpect(exp5.accept(new EvalVisitor()), 2.0)
        && t.checkExpect(exp6.accept(new EvalVisitor()), 4.0)
        && t.checkExpect(exp7.accept(new EvalVisitor()), 6.0);

  }

  // testing the PrintVisitor class
  boolean testPrintVisitor(Tester t) {  
    return t.checkExpect(plus1.accept(new PrintVisitor()), "(plus 5.0 4.0)") 
        && t.checkExpect(const1.accept(new PrintVisitor()), "5.0")
        && t.checkExpect(mul1.accept(new PrintVisitor()), "(mul (plus 5.0 4.0) -2.0)")
        && t.checkExpect(div1.accept(new PrintVisitor()), "(div (plus 5.0 4.0) 4.5)")
        && t.checkExpect(minus1.accept(new PrintVisitor()), "(minus 5.0 4.0)")
        && t.checkExpect(minus2.accept(new PrintVisitor()), "(minus 0.0 4.0)")
        && t.checkExpect(neg1.accept(new PrintVisitor()), "(neg 5.0)")
        && t.checkExpect(sqr1.accept(new PrintVisitor()), "(sqr 4.5)")
        && t.checkExpect(exp1.accept(new PrintVisitor()), "(mul (plus 5.0 4.0) "
            + "(minus 0.0 4.0))")
        && t.checkExpect(exp2.accept(new PrintVisitor()), "(div (mul (plus 5.0 4.0) "
            + "(minus 0.0 4.0)) (sqr -2.0))")
        && t.checkExpect(exp3.accept(new PrintVisitor()), "(neg (div (mul (plus 5.0 4.0) "
            + "(minus 0.0 4.0)) (sqr -2.0)))")
        && t.checkExpect(exp4.accept(new PrintVisitor()), "(plus (neg (div (mul (plus 5.0 4.0) "
            + "(minus 0.0 4.0)) (sqr -2.0))) (neg 5.0))")
        && t.checkExpect(exp5.accept(new PrintVisitor()), "(div (plus (neg (div (mul "
            + "(plus 5.0 4.0) (minus 0.0 4.0)) (sqr -2.0))) "
            + "(neg 5.0)) (div (plus 5.0 4.0) " + "4.5))")
        && t.checkExpect(exp6.accept(new PrintVisitor()), "(sqr (div (plus (neg "
            + "(div (mul (plus 5.0 4.0) (minus 0.0 4.0)) (sqr -2.0))) (neg 5.0)) "
            + "(div (plus 5.0 4.0) " + "4.5)))")
        && t.checkExpect(exp7.accept(new PrintVisitor()), "(minus (sqr (div (plus "
            + "(neg (div (mul (plus 5.0 4.0)"
            + " (minus 0.0 4.0)) (sqr -2.0))) (neg 5.0)) (div (plus 5.0 4.0) 4.5))) -2.0)");

  }

  // testing the MirrorVisitor class
  boolean testMirrorVisitor(Tester t) {
    // mirror tests
    return t.checkExpect(plus1.accept(new MirrorVisitor()), new BinaryFormula(new Plus(), 
        "plus", const2, const1))
        && t.checkExpect(const1.accept(new MirrorVisitor()), const1)
        && t.checkExpect(mul1.accept(new MirrorVisitor()), 
            new BinaryFormula(new Mul(), "mul", const3, plus1))
        && t.checkExpect(div1.accept(new MirrorVisitor()), 
            new BinaryFormula(new Div(), "div", const4, plus1))        
        && t.checkExpect(minus1.accept(new MirrorVisitor()),  
            new BinaryFormula(new Minus(), "minus", const2, const1))
        && t.checkExpect(minus2.accept(new MirrorVisitor()), 
            new BinaryFormula(new Minus(), "minus", const2, const5))
        && t.checkExpect(neg1.accept(new MirrorVisitor()), neg1)
        && t.checkExpect(sqr1.accept(new MirrorVisitor()), sqr1)
        && t.checkExpect(exp1.accept(new MirrorVisitor()), exp1help)
        && t.checkExpect(exp2.accept(new MirrorVisitor()),
            new BinaryFormula(new Div(), "div", sqr2, exp1help));
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
  boolean testEvenHelper(Tester t) {
    return t.checkExpect(plus1.evenHelper(), false)
        && t.checkExpect(const1.evenHelper(), false)
        && t.checkExpect(minus2.evenHelper(), true)
        && t.checkExpect(div1.evenHelper(), false)
        && t.checkExpect(sqr1.evenHelper(), false)
        && t.checkExpect(neg1.evenHelper(), false)
        && t.checkExpect(exp2.evenHelper(), false)
        && t.checkExpect(exp8.evenHelper(), true);

  }
  // exception testing for the divide case
  boolean testRuntimeException(Tester t) {
    return t.checkException(new RuntimeException("you can not divide a number by zero"),
            this.div1, "accept", new EvalVisitor());

  }
}




