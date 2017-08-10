package forex.conversion.appln.test;

import forex.conversion.appln.util.ConcurrentHashSet;
import java.util.HashSet;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestConcurrentHashSet extends TestCase {
  @Test
  public void testEquals() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    String s1 = "abc";
    String s2 = "pqr";
    strSet.add(s1);
    strSet.add(s2);
    
    ConcurrentHashSet<String> strSet2 = new ConcurrentHashSet<String>();
    strSet2.add(s1);
    strSet2.add(s2);
    
    assertEquals(strSet.hashCode(),strSet2.hashCode());
    assertEquals(strSet,strSet2);
  }

  @Test
  public void testEquals2() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    String s1 = "abc";
    String s2 = "pqr";
    strSet.add(s1);
    strSet.add(s2);
    
    ConcurrentHashSet<String> strSet2 = new ConcurrentHashSet<String>();
    strSet2.add(s1);
    
    Assert.assertNotEquals(strSet,strSet2);
  }
  
  @Test
  public void testIsEmpty() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    assertTrue(strSet.isEmpty());
  }
  
  @Test
  public void testContains() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    strSet.add("abc");
    assertTrue(strSet.contains("abc"));
  }
  
  @Test
  public void testSize() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    strSet.add("abc");
    strSet.add("pqr");
    assertEquals(2,strSet.size());
  }
  
  @Test
  public void testRemove() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>();
    strSet.add("abc");
    strSet.add("pqr");
    
    assertTrue(strSet.remove("abc"));
  }

  @Test
  public void testInit() {
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>(2);
    strSet.add("abc");
    
    assertEquals(1,strSet.size());
  }

  @Test
  public void testInit2() {
    HashSet<String> anotherSet = new HashSet<String>();
    anotherSet.add("abc");
    anotherSet.add("pqr");
    anotherSet.add("rty");
    
    ConcurrentHashSet<String> strSet = new ConcurrentHashSet<String>(anotherSet);
    
    assertEquals(anotherSet,strSet);
  }
}
