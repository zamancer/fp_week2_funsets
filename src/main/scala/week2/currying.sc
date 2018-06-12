object Week2Lesson {
  def sumOriginal(f: Int => Int, a: Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sumOriginal(f, a + 1, b)

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }

    loop(a, 0)
  }

  // Currying first approach
  def sum1(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)

    sumF
  }

  // Currying second approach
  def sum2(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sum2(f)(a + 1, b)

  def sumCubes(a: Int, b: Int) = sum((x: Int) => x * x * x)(a, b)
}