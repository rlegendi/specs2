package org.specs2
package mock

import org.hamcrest.{ Description, TypeSafeMatcher }
import matcher._

/** 
 * Adapter class to use specs2 matchers as Hamcrest matchers 
 */
case class HamcrestMatcherAdapter[T](m: Matcher[T]) extends TypeSafeMatcher[T] {
  /** this variable is necessary to store the result of a match */ 
  private var message = ""
    
  def matchesSafely(item: T): Boolean = {
    // special case for by-name arguments
    // in that case we apply the Function0 to get the value
    val i = if (item.isInstanceOf[Function0[_]]) item.asInstanceOf[Function0[_]].apply().asInstanceOf[T] else item
    m.apply(Expectable(i)) match {
      case MatchFailure(_, m, _, _) => message = m; false
      case _ => true
    }
    
  }
  def describeTo(description: Description) = {
    description.appendText(message)
  }
}



