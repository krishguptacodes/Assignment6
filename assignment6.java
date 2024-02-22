interface IArithVisitor<R> extends IFunc<IArith R> {
  R visitConst(Const x);
  R visitUnaryFormula(UnaryFormula y);
  R visitBinaryFormula(BinaryFormula z);
}

interface IArith {
  
  <R> R accept(IArithVisitor<R> visitor); // all things of IFoo can accept visitor

}

class Const implements IArith {
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

class UnaryFormula implements IArith {
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnaryFormula(this);
  }
}

class BinaryFormula implements IArith {
  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }
}
