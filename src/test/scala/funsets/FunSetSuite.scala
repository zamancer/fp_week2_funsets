package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = union(s1, s2)
    val s6 = union(s2, s3)
    val s7 = union(s3, s4)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only common elements among sets") {
    new TestSets {
      val s = intersect(s5, s6)
      assert(!contains(s, 1), "Intersect 1")
      assert(contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
    }
  }

  test("diff contains all elements of the set s5 that are not in the set s6") {
    new TestSets {
      val s = diff(s5, s6)
      assert(contains(s, 1), "Diff should contain only 1")
      assert(!contains(s, 2), "Diff should not contain 2")
      assert(!contains(s, 3), "Diff should not contain 3")
    }
  }

  test("filter elements should reduce the set for even numbers") {
    new TestSets {

      val base = union(s5, s7)

      val s = filter(base, (x: Int) => x % 2 == 0)
      assert(contains(s, 2), "Filter should contain 2")
      assert(contains(s, 4), "Filter should contain 4")
      assert(!contains(s, 1), "Filter should not contain 1")
      assert(!contains(s, 3), "Filter should not contain 3")
    }
  }

  test("forall helper function should tell if we satisfy a predicate for all elements of the set") {
    new TestSets {

      val base = union(s5, s7)


      val allNegativesContained = forall(base, (x: Int) => x < 0)
      val allPositivesContained = forall(base, (x: Int) => x > 0 && x < 5)
      val onlyEvenNumbers = forall(base, (x: Int) => x % 2 == 0)
      assert(!allNegativesContained, "Base set shouldn't contain negatives")
      assert(allPositivesContained, "Base set should contain only positives lesser than 5")
      assert(!onlyEvenNumbers, "Base set shouldn't contain only even numbers")
    }
  }

  test("exists helper function should tell if at least one predicate is satisfied by an element of the set") {
    new TestSets {

      val base = union(s5, s7)

      val evenNumbers = exists(base, (x: Int) => x % 2 == 0)
      val numberOneExists = exists(base, (x: Int) => x == 1)
      val numberTenExists = exists(base, (x: Int) => x == 10)

      assert(evenNumbers, "At least one even number exists in the set");
      assert(numberOneExists, "The number 1 should exist in the set");
      assert(!numberTenExists, "The number 10 shouldn't exist in the set");
    }
  }

  test("map helper function should get a new set of elements with the predicate applied to them") {
    new TestSets {
      val base = union(s5, s7)

      val duplicatedElements = map(base, (x: Int) => x * 2)

      assert(
          contains(duplicatedElements, 2) &&
          contains(duplicatedElements, 4) &&
          contains(duplicatedElements, 6) &&
          contains(duplicatedElements, 8),
        "All elements where duplicated")
    }
  }
}
