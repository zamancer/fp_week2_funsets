val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x.numeral
x.denominator
x.sub(y).sub(z)
y.add(y)
x.less(y)
x.maximum(y)
new Rational(2)

class Rational(x: Int, y: Int) {
  require(y != 0, "denominator must be nonzero")

  def this(x: Int) = this(x, 1)

  def numeral = x

  def denominator = y

  def less(that: Rational) =
    numeral * that.denominator < that.numeral * denominator

  def maximum(that: Rational) =
    if (this.less(that)) that else this

  def add(that: Rational) =
    new Rational(
      numeral * that.denominator + that.numeral * denominator,
      denominator * that.denominator
    )

  def neg: Rational = new Rational(-numeral, denominator)

  def sub(that: Rational) = add(that.neg)

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  override def toString = {
    val g = gcd(numeral, denominator)
    numeral / g + "/" + denominator / g
  }
}
