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

class MirrorVisitor implements IArithVisitor<IArith>, Function<IArith, IArith> {
  public IArith apply(IArith iArith) {
    return iArith.accept(this);
  }

  public IArith visitConst(Const c) {
    return c;
  }

  public IArith visitUnaryFormula(UnaryFormula uf) {
    return uf;
  }

  public IArith visitBinaryFormula(BinaryFormula bf) {
    return new BinaryFormula(bf.name, bf.func, bf.right, bf.left);
  }
}

class AllEvenVisitor implements IArithVisitor<Boolean>, Function<IArith, Boolean> {
  public Boolean apply(IArith iArith) {
    return iArith.accept(this);
  }

  public Boolean visitConst(Const c) {
    return c.num % 2 == 0;
  }

  public Boolean visitUnaryFormula(UnaryFormula uf) {
    return uf.child.accept(this);
  }

  public Boolean visitBinaryFormula(BinaryFormula bf) {
    return bf.left.accept(this) & bf.right.accept(this);
  }
}

class Plus implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 + d2;
  }
}

class Minus implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 - d2;
  }  
}

class Mul implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 * d2;
  }  
}

class Div implements BiFunction<Double, Double, Double> {
  public Double apply(Double d1, Double d2) {
    return d1 / d2;
  } 
}

class Neg implements Function<Double, Double> {
  public Double apply(Double d) {
    return d * -1;
  }  
}

class Srq implements Function<Double, Double> {
    public Double apply(Double d) {
      return d * d;
    }    
}

return t.checkExpect(this.exp1.apply(), 9);













return t.checkExpect(plus1.accept(new EvalVisitor()), 9.0) 
    && t.checkExpect(const1.accept(new EvalVisitor()), 5)
    && t.checkExpect(mull.accept(new EvalVisitor()), -18)
    && t.checkExpect(div1.accept(new EvalVisitor()), 2)
    && t.checkExpect(minus1.accept(new EvalVisitor()), 1)
    && t.checkExpect(minus2.accept(new EvalVisitor()), -4)
    && t.checkExpect(neg1.accept(new EvalVisitor()), -5)
    && t.checkExpect(sqr1.accept(new EvalVisitor()), 20.25);
    
//
return t.checkExpect(plus1.accept(new PrintVisitor()), "(plus 5.0 4.0)") 
    && t.checkExpect(const1.accept(new PrintVisitor()), "4")
    && t.checkExpect(mul1.accept(new PrintVisitor()), "(mul (plus 5.0 4.0) 3.0)")
    && t.checkExpect(div1.accept(new PrintVisitor()), "(div (plus 5.0 4.0) 4.5)")
    && t.checkExpect(minus1.accept(new PrintVisitor()), "(minus 5.0 4.0)")
    && t.checkExpect(minus2.accept(new PrintVisitor()), "(minus -2.0 -2.0)")
    && t.checkExpect(neg1.accept(new PrintVisitor()), "(neg 5.0)")
    && t.checkExpect(sqr1.accept(new PrintVisitor()), "(sqr 4.5)");

// mirror tests

//
return t.checkExpect(plus1.accept(new )
    
 

