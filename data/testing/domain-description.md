Home
Marc Philipp edited this page on Jun 25, 2020 · 60 revisions
Pages 35
Find a page…
Home
JUnit Usage and Idioms
Third-party extensions
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
Welcome to the JUnit wiki!

Download and Install
Getting Started
Release Notes
4.13
4.12
4.11
4.10
4.9.1
4.9
Maintainer Documentation
Deprecation Policy
I want to help!
Latest JUnit Questions on StackOverflow
Latest JUnit API JavaDocs
JUnit Usage and Idioms
Assertions - your bread and butter for unit testing
Test Runners - how tests should be executed
Aggregating tests in Suites - how to combine multiple related tests into a test suite
Test Execution Order - specifying what order to run unit tests
Exception Testing - how to specify expected exceptions in unit tests
Matchers and assertThat - how to use Hamcrest matchers and more descriptive assertions
Ignoring Tests - how to disable test methods or classes
Timeout for Tests - how to specify maximum execution times for tests
Parameterized Tests - writing tests that can be executed multiple times with different parameter values
Assumptions with Assume - similar to assertions, but without making tests fail
Rules - stop extending abstract test classes and start writing test rules
Theories - write tests that are more like scientific experiments using randomly generated data
Test Fixtures - specify set up and clean up methods on a per-method and per-class basis
Categories - group your tests together for easier test filtering
Use with Maven - how to use JUnit with your Maven build
Use with Gradle - how to use JUnit with your Gradle build
Multithreaded code and Concurrency - basic ideas behind testing concurrent code
Java contract test helpers
Continuous Testing
Third-party extensions
Custom Runners
github.com/trajano/commons-testing for UtilityClassTestUtil per #646
System Rules – A collection of JUnit rules for testing code that uses java.lang.System.
JUnit Toolbox – Provides runners for parallel testing, a PoolingWait class to ease asynchronous testing, and a WildcardPatternSuite which allow you to specify wildcard patterns instead of explicitly listing all classes when you create a suite class.
junit-dataprovider – A TestNG like dataprovider (see here) runner for JUnit.
junit-quickcheck – QuickCheck-style parameter suppliers for JUnit theories. Uses junit.contrib's version of the theories machinery, which respects generics on theory parameters.
JGiven - Behavior-Driven Development (BDD) for JUnit
'Enclosed' test runner example
Daan van Berkel edited this page on Jul 31, 2015 · 5 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Enclosed TestRunner usage
Class To Test
Tests
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
Enclosed TestRunner usage
Example of using Enclosed TestRunner.

We want to test this domain class; testing Equals, HashCode, Serializable, Comparable, and the Builders. This can be simply done using the testhelpers from https://bitbucket.org/chas678/testhelpers

Usually we would need a subclass of each abstract test, however with the Enclosed runner, they can all be static inner classes of the same test case class.

Here's the domain class to test.

Class To Test
package abstractions.domain;

import java.io.Serializable;

import com.google.common.collect.ComparisonChain;

public class Address implements Serializable, Comparable<Address> {

    private static final long serialVersionUID = 1L;
    private final String address1;
    private final String city;
    private final String state;
    private final String zip;

    private Address(Builder builder) {
    	this.address1 = builder.address1;
    	this.city = builder.city;
    	this.state = builder.state;
    	this.zip = builder.zip;
    }

    public String getAddress1() {
    	return address1;
    }

    public String getCity() {
    	return city;
    }

    public String getState() {
    	return state;
    }

    public String getZip() {
    	return zip;
    }

    @Override
    public int compareTo(Address that) {
    	return ComparisonChain.start().compare(this.zip, that.zip).compare(this.state, that.state)
    			.compare(this.city, that.city).compare(this.address1, that.address1).result();
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == null) { return false; }
    	if (getClass() != obj.getClass()) { return false; }
    	final Address that = (Address) obj;

    	return com.google.common.base.Objects.equal(this.address1, that.address1)
    			&& com.google.common.base.Objects.equal(this.city, that.city)
    			&& com.google.common.base.Objects.equal(this.state, that.state)
    			&& com.google.common.base.Objects.equal(this.zip, that.zip);
    }

    @Override
    public int hashCode() {
    	return com.google.common.base.Objects.hashCode(getAddress1(), getCity(), getCity(), getState(), getZip());
    }

    @Override
    public String toString() {
    	return com.google.common.base.Objects.toStringHelper(this).addValue(getAddress1()).addValue(getCity()).addValue(getState()).addValue(getZip()).toString();
    }

    public static class Builder {

    	private String address1;
    	private String city;
    	private String state;
    	private String zip;

    	public Builder address1(String address1) {
    		this.address1 = address1;
    		return this;
    	}

    	public Address build() {
    		return new Address(this);
    	}

    	public Builder city(String city) {
    		this.city = city;
    		return this;
    	}

    	public Builder state(String state) {
    		this.state = state;
    		return this;
    	}

    	public Builder zip(String zip) {
    		this.zip = zip;
    		return this;
    	}
    }

}
Tests
And here are the test cases, implemented as inner classes, using the Enclosed runner:

package abstractions.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import testhelpers.ComparabilityTestCase;
import testhelpers.EqualsHashCodeTestCase;
import testhelpers.SerializabilityTestCase;

/\*\*

- The Class AddressTest.
  \*/
  @RunWith(Enclosed.class)
  public class AddressTest {

      /**
       * The Class AddressComparabilityTest.
       */
      public static class AddressComparabilityTest extends ComparabilityTestCase<Address> {

      	@Override
      	protected Address createEqualInstance() throws Exception {
      		return new Address.Builder().address1("2802 South Havana Street").city("Aurora").state("CO").zip("80014").build();
      	}

      	@Override
      	protected Address createGreaterInstance() throws Exception {
      		return new Address.Builder().address1("9839 Carlisle Boulevard NE").city("Albuquerque").state("NM").zip("87110").build();
      	}

      	@Override
      	protected Address createLessInstance() throws Exception {
      		return new Address.Builder().address1("14 Broad St").city("Nashua").state("NH").zip("03064").build();
      	}
      }

      /**
       * The Class AddressEqualsHashCodeTest.
       */
      public static class AddressEqualsHashCodeTest extends EqualsHashCodeTestCase {

      	@Override
      	protected Address createInstance() throws Exception {
      		return new Address.Builder().address1("2802 South Havana Street").city("Aurora").state("CO").zip("80014").build();
      	}

      	@Override
      	protected Address createNotEqualInstance() throws Exception {
      		return new Address.Builder().address1("9839 Carlisle Boulevard NE").city("Albuquerque").state("NM").zip("87110").build();
      	}
      }

      /**
       * The Class AddressSerializabilityTest.
       */
      public static class AddressSerializabilityTest extends SerializabilityTestCase {

      	@Override
      	protected Serializable createInstance() throws Exception {
      		return new Address.Builder().address1("9839 Carlisle Boulevard NE").city("Albuquerque").state("NM").zip("87110").build();
      	}
      }

      public static class AddressMiscTest {

      	private Address address;

      	/**
      	 * Setup.
      	 *
      	 * @throws Exception the exception
      	 */
      	@Before
      	public void setUp() throws Exception {
      		address = new Address.Builder().address1("9839 Carlisle Boulevard NE").city("Albuquerque").state("NM").zip("87110").build();
      	}

      	/**
      	 * Test builder.
      	 */
      	@Test
      	public void testBuilder() {
      		assertThat(address.getAddress1(), is("9839 Carlisle Boulevard NE"));
      		assertThat(address.getCity(), is("Albuquerque"));
      		assertThat(address.getState(), is("NM"));
      		assertThat(address.getZip(), is("87110"));
      	}

      	@Test
      	public void testToString() {
      		assertThat(address.toString(), is("Address{9839 Carlisle Boulevard NE, Albuquerque, NM, 87110}"));
      	}
      }

  }
  Aggregating tests in suites
  jason edited this page on Oct 27, 2017 · 7 revisions
  Pages 35
  Find a page…
  Home
  'Enclosed' test runner example
  Aggregating tests in suites
  Example
  Assertions
  Assertions CN
  Assumptions with assume
  Categories
  Continuous testing
  Custom runners
  Deprecation Policy
  Developing with fast tests
  Download and Install
  Exception testing
  FAQ
  Getting started
  Clone this wiki locally
  https://github.com/junit-team/junit4.wiki.git
  Using Suite as a runner allows you to manually build a suite containing tests from many classes. It is the JUnit 4 equivalent of the JUnit 3.8.x static Test suite() method. To use it, annotate a class with @RunWith(Suite.class) and @SuiteClasses(TestClass1.class, ...). When you run this class, it will run all the tests in all the suite classes.

Example
The class below is a placeholder for the suite annotations, no other implementation is required. Note the @RunWith annotation, which specifies that the JUnit 4 test runner to use is org.junit.runners.Suite for running this particular test class. This works in conjunction with the @Suite.SuiteClasses annotation, which tells the Suite runner which test classes to include in this suite and in which order.

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
TestFeatureLogin.class,
TestFeatureLogout.class,
TestFeatureNavigate.class,
TestFeatureUpdate.class
})

public class FeatureTestSuite {
// the class remains empty,
// used only as a holder for the above annotations
}
Assertions
Dariusz Andrzej Stefański edited this page on Mar 19, 2016 · 7 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Examples
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
JUnit provides overloaded assertion methods for all primitive types and Objects and arrays (of primitives or Objects). The parameter order is expected value followed by actual value. Optionally the first parameter can be a String message that is output on failure. There is a slightly different assertion, assertThat that has parameters of the optional failure message, the actual value, and a Matcher object. Note that expected and actual are reversed compared to the other assert methods.

Examples
A representative of each assert method is shown.

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.Test;

public class AssertTests {
@Test
public void testAssertArrayEquals() {
byte[] expected = "trial".getBytes();
byte[] actual = "trial".getBytes();
assertArrayEquals("failure - byte arrays not same", expected, actual);
}

@Test
public void testAssertEquals() {
assertEquals("failure - strings are not equal", "text", "text");
}

@Test
public void testAssertFalse() {
assertFalse("failure - should be false", false);
}

@Test
public void testAssertNotNull() {
assertNotNull("should not be null", new Object());
}

@Test
public void testAssertNotSame() {
assertNotSame("should not be same Object", new Object(), new Object());
}

@Test
public void testAssertNull() {
assertNull("should be null", null);
}

@Test
public void testAssertSame() {
Integer aNumber = Integer.valueOf(768);
assertSame("should be same", aNumber, aNumber);
}

// JUnit Matchers assertThat
@Test
public void testAssertThatBothContainsString() {
assertThat("albumen", both(containsString("a")).and(containsString("b")));
}

@Test
public void testAssertThatHasItems() {
assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
}

@Test
public void testAssertThatEveryItemContainsString() {
assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
}

// Core Hamcrest Matchers with assertThat
@Test
public void testAssertThatHamcrestCoreMatchers() {
assertThat("good", allOf(equalTo("good"), startsWith("good")));
assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
assertThat(new Object(), not(sameInstance(new Object())));
}

@Test
public void testAssertTrue() {
assertTrue("failure - should be true", true);
}
}
Assertions CN
贾康 edited this page on Jul 22, 2016 · 2 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
例子
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
JUnit 为基本类型和对象以及数组(基本类型或对象)提供了重载的断言方法。参数的顺序是期望值和实际值。可选的第一个值是错误情况的消息。有一个略微不同的断言是 assertThat 它需要的参数是一个可选的失败消息，实际返回值和一个 Matcher 对象。值得注意的是，相比其他断言方法预期和实际是相反的。

例子
所有有代表性的断言方法。

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Arrays;

import org.hamcrest.core.CombinableMatcher;
import org.junit.Test;

public class AssertTests {
@Test
public void testAssertArrayEquals() {
byte[] expected = "trial".getBytes();
byte[] actual = "trial".getBytes();
org.junit.Assert.assertArrayEquals("failure - byte arrays not same", expected, actual);
}

@Test
public void testAssertEquals() {
org.junit.Assert.assertEquals("failure - strings are not equal", "text", "text");
}

@Test
public void testAssertFalse() {
org.junit.Assert.assertFalse("failure - should be false", false);
}

@Test
public void testAssertNotNull() {
org.junit.Assert.assertNotNull("should not be null", new Object());
}

@Test
public void testAssertNotSame() {
org.junit.Assert.assertNotSame("should not be same Object", new Object(), new Object());
}

@Test
public void testAssertNull() {
org.junit.Assert.assertNull("should be null", null);
}

@Test
public void testAssertSame() {
Integer aNumber = Integer.valueOf(768);
org.junit.Assert.assertSame("should be same", aNumber, aNumber);
}

// JUnit Matchers assertThat
@Test
public void testAssertThatBothContainsString() {
org.junit.Assert.assertThat("albumen", both(containsString("a")).and(containsString("b")));
}

@Test
public void testAssertThathasItemsContainsString() {
org.junit.Assert.assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
}

@Test
public void testAssertThatEveryItemContainsString() {
org.junit.Assert.assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
}

// Core Hamcrest Matchers with assertThat
@Test
public void testAssertThatHamcrestCoreMatchers() {
assertThat("good", allOf(equalTo("good"), startsWith("good")));
assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
assertThat(new Object(), not(sameInstance(new Object())));
}

@Test
public void testAssertTrue() {
org.junit.Assert.assertTrue("failure - should be true", true);
}
}
Assumptions with assume
Daan van Berkel edited this page on Jul 30, 2015 · 4 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Assumptions
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
Assumptions
Ideally, the developer writing a test has control of all of the forces that might cause a test to fail. If this isn't immediately possible, making dependencies explicit can often improve a design. For example, if a test fails when run in a different locale than the developer intended, it can be fixed by explicitly passing a locale to the domain code.

However, sometimes this is not desirable or possible. It's good to be able to run a test against the code as it is currently written, implicit assumptions and all, or to write a test that exposes a known bug. For these situations, JUnit now includes the ability to express "assumptions":

    import static org.junit.Assume.*
    @Test public void filenameIncludesUsername() {
        assumeThat(File.separatorChar, is('/'));
        assertThat(new User("optimus").configFileName(), is("configfiles/optimus.cfg"));
    }

    @Test public void correctBehaviorWhenFilenameIsNull() {
       assumeTrue(bugFixed("13356"));  // bugFixed is not included in JUnit
       assertThat(parse(null), is(new NullDocument()));
    }

The default JUnit runner treats tests with failing assumptions as ignored. Custom runners may behave differently.

We have included assumeTrue for convenience, but thanks to the inclusion of Hamcrest, we do not need to create assumeEquals, assumeSame, and other analogues to the assert\* methods. All of those functionalities are subsumed in assumeThat, with the appropriate matcher.

A failing assumption in a @Before or @BeforeClass method will have the same effect as a failing assumption in each @Test method of the class.
Categories
Jeremy W. Sherman edited this page on Aug 17, 2018 · 12 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Using categories with Maven
Using categories with Gradle
Using categories with SBT
Typical usages for categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
From a given set of test classes, the Categories runner runs only the classes and methods that are annotated with either the category given with the @IncludeCategory annotation, or a subtype of that category. Either classes or interfaces can be used as categories. Subtyping works, so if you say @IncludeCategory(SuperClass.class), a test marked @Category({SubClass.class}) will be run.

You can also exclude categories by using the @ExcludeCategory annotation

Example:

public interface FastTests { /_ category marker _/ }
public interface SlowTests { /_ category marker _/ }

public class A {
@Test
public void a() {
fail();
}

@Category(SlowTests.class)
@Test
public void b() {
}
}

@Category({SlowTests.class, FastTests.class})
public class B {
@Test
public void c() {

}
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
// Will run A.b and B.c, but not A.a
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
// Will run A.b, but not A.a or B.c
}
Using categories with Maven
You can use categories with either maven-surefire-plugin (for unit tests) or maven-failsafe-plugin (for integration tests). Using either plugin, you can configure a list of categories to include or exclude. Without using either option, all tests will be executed by default.

<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <configuration>
        <groups>com.example.FastTests,com.example.RegressionTests</groups>
      </configuration>
    </plugin>
  </plugins>
</build>
Similarly, to exclude a certain list of categories, you would use the <excludedGroups/> configuration element.

Using categories with Gradle
Gradle's test task allows the specification of the JUnit categories you want to include and exclude.

test {
useJUnit {
includeCategories 'org.gradle.junit.CategoryA'
excludeCategories 'org.gradle.junit.CategoryB'
}
}
Using categories with SBT
SBT's junit-interface allows the specification of JUnit categories via --include-categories=<CLASSES> and --exclude-categories=<CLASSES>.

Typical usages for categories
Categories are used to add metadata on the tests.

The frequently encountered categories usages are about:

The type of automated tests: UnitTests, IntegrationTests, SmokeTests, RegressionTests, PerformanceTests ...
How quick the tests execute: SlowTests, QuickTests
In which part of the ci build the tests should be executed: NightlyBuildTests
The state of the test: UnstableTests, InProgressTests
This is also used to add project specific metadata like which feature of a project is covered by the test.

See usages of Junit Categories on github hosted projects

Continuous testing
mattj256 edited this page on Sep 12, 2013 · 3 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Article
IDE plugins
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
Continuous Testing allows for immediate feedback on JUnit test results within an IDE. The tests are run automatically after every change, usually in an intelligent order so that newest tests or most recently failed tests are run first.

Article
This article explains the concepts further:

http://groups.csail.mit.edu/pag/continuoustesting/what.html
IDE plugins
Infinitest is an open source plugin for Eclipse or IntelliJ IDEA that provides continuous testing functionality:

http://infinitest.github.com/

Custom runners
Kevin Cooney edited this page on Mar 19, 2021 · 31 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Third Party Runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
Third Party Runners
Some popular third party implementations of runners for use with @RunWith include:

Spring Framework
Spring TestContext Framework reference manual
SpringJUnit4ClassRunner Javadoc API
Mockito
MockitoJUnitRunner
JMockit
http://jmockit.org/api1x/mockit/integration/junit4/JMockit.html
Concurrency / Test Helpers
Parallel Test Runner
ConcurrentTestRunner (Run tests in parallel)
JUnit Toolbox (Run tests in parallel; also Suite and Categories with Patterns, async)
IntermittentTestRunner (run repeatedly to expose intermittent failures)
Hierarchical Context Runner – A runner implementation that supports context hierarchies in JUnit.
Parameterised and data-driven testing
junit-dataprovider – A TestNG like dataprovider (see here) runner for JUnit.
JUnitParams - an alternative to JUnit's Parameterized runner, which support test data on a per method basis
ParameterizedSuite - Allows to define parameters for test suites.
TestParameterInjector - A simple yet powerful parameterized test runner developed by Google
HookInstallingRunner - This JUnit test runner implements four significant features:
Invocation hooks for test and configuration methods
Test method timeout management
Automatic retry of failed tests
Shutdown hook installation
BDD testing - these integrate popular BDD/ATDD methodologies
Cucumber Runner - Cucumber JVM integration for JUnit, based on Gherkin specifications written in feature files
JBehave - JBehave JUnit runner - a Gherkin-based specification framework
FitNesse - FitNesse Runner
ConcordionRunner - the Concordion BDD framework based on Gherkin specifications written in markdown
Spectrum - supports Specs in Jasmine/Mocha/Gherkin syntax (Java 8 required)
Oleaster - supports Specs in Jasmine/Mocha syntax
Ginkgo4jRunner - supports Specs in Jasmine/Mocha syntax
Cola tests - technically a precompiler for tests - Cola Documentation
JICUnit - A JUnit runner in the JEE container for in-container testing similar to JUnitEE and Jakarta Cactus, both which are not developed any more.
JUnit Browser Runner - A JUnit runner that executes your tests in a Browser using bck2brwsr.
Deprecation Policy
Marc Philipp edited this page on Jan 20, 2015 · 1 revision
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Guidelines
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
From version 4.12 on JUnit follows the principles of semantic versioning.

Guidelines
An existing API can be deprecated in both major and minor releases.
Deprecated APIs will only be removed in major releases and not before one year has passed since the release in which they were first deprecated.

Developing with fast tests
Kevin Cooney edited this page on Oct 14, 2018 · 2 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
This page is deprecated.
Developing with fast tests
Kevin Cooney edited this page on Oct 14, 2018 · 2 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
This page is deprecated
Download and Install
Marc Philipp edited this page on Jan 2, 2020 · 34 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Plain-old JAR
Maven
Gradle
Exception testing
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
To download and install JUnit you currently have the following options.

Plain-old JAR
Download the following JARs and add them to your test classpath:

junit.jar
hamcrest-core.jar
Maven
Add a dependency to junit:junit in test scope. (Note: 4.13 is the latest stable version as of the latest edit on this page.)

<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.13</version>
  <scope>test</scope>
</dependency>
Gradle
See Use-with-Gradle

Exception testing
Kevin Cooney edited this page on Feb 10, 2020 · 24 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
Using assertThrows Method
Try/Catch Idiom
Specifying the expected annotation via the @Test annotation.
ExpectedException Rule
FAQ
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
How do you verify that code throws exceptions as expected? Verifying that code completes normally is important, but making sure the code behaves as expected in exceptional situations is vital too. For example:

new ArrayList<Object>().get(0);
This code should throw an IndexOutOfBoundsException. There are multiple ways in JUnit to write a test to verify this behavior.

Using assertThrows Method
The method assertThrows has been added to the Assert class in version 4.13. With this method you can assert that a given function call (specified, for instance, as a lambda expression or method reference) results in a particular type of exception being thrown. In addition it returns the exception that was thrown, so that further assertions can be made (e.g. to verify that the message and cause are correct). Furthermore, you can make assertions on the state of a domain object after the exception has been thrown:

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

@Test
public void testExceptionAndState() {
List<Object> list = new ArrayList<>();

IndexOutOfBoundsException thrown = assertThrows(
IndexOutOfBoundsException.class,
() -> list.add(1, new Object()));

// assertions on the thrown exception
assertEquals("Index: 1, Size: 0", thrown.getMessage());
// assertions on the state of a domain object after the exception has been thrown
assertTrue(list.isEmpty());
}
Try/Catch Idiom
If you project is not yet using JUnit 4.13 or your code base does not support lambdas, you can use the try/catch idiom which prevailed in JUnit 3.x:

@Test
public void testExceptionMessage() {
List<Object> list = new ArrayList<>();

try {
list.get(0);
fail("Expected an IndexOutOfBoundsException to be thrown");
} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
}
}
Be aware that fail() throws an AssertionError, so you cannot use the above idiom to verify that a method call should throw an AssertionError.

Specifying the expected annotation via the @Test annotation.
The @Test annotation has an optional parameter "expected" that takes as values subclasses of Throwable. If we wanted to verify that ArrayList throws the correct exception, we could write:

@Test(expected = IndexOutOfBoundsException.class)
public void empty() {
new ArrayList<Object>().get(0);
}
The expected parameter should be used with care. The above test will pass if any code in the method throws IndexOutOfBoundsException. Using the method you also cannot test the value of the message in the exception, or the state of a domain object after the exception has been thrown.

For these reasons, the previous approaches are recommended.

ExpectedException Rule
Another way to test exceptions is the ExpectedException rule, but that approach has been deprecated in JUnit 4.13. This rule let you indicate not only what exception you are expecting, but also the exception message you are expecting:

@Rule
public ExpectedException thrown = ExpectedException.none();

@Test
public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
List<Object> list = new ArrayList<Object>();

thrown.expect(IndexOutOfBoundsException.class);
thrown.expectMessage("Index: 0, Size: 0");
list.get(0); // execution will never get past this line
}
The expectMessage also lets you use Matchers, which gives you a bit more flexibility in your tests. An example:

thrown.expectMessage(CoreMatchers.containsString("Size: 0"));
Moreover, you can use Matchers to inspect the Exception, useful if it has embedded state you wish to verify. For example

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestExy {
@Rule
public ExpectedException thrown = ExpectedException.none();

@Test
public void shouldThrow() {
TestThing testThing = new TestThing();
thrown.expect(NotFoundException.class);
thrown.expectMessage(startsWith("some Message"));
thrown.expect(hasProperty("response", hasProperty("status", is(404))));
testThing.chuck();
}

private class TestThing {
public void chuck() {
Response response = Response.status(Status.NOT_FOUND).entity("Resource not found").build();
throw new NotFoundException("some Message", response);
}
}
}
For an expanded discussion of the ExpectedException rule, see this blog post.

Do note that when the test calls the method under test that throws the exception, no code in the test after the method will execute (because the method under test is throwing the exception). This can lead to confusion, which is one of the reasons why ExpectedException.none() is deprecated.

FAQ
Marc Philipp edited this page on Jun 25, 2020 · 10 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Table of Contents
FAQ Info
Overview
Getting Started
Writing Tests
Organizing Tests
Running Tests
Best Practices
Miscellaneous
Getting started
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
JUnit is a simple, open source framework to write and run repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.

Edited by Mike Clark (http://clarkware.com)

Last modified on February 20, 2006

Table of Contents
FAQ Info

Who is responsible for this FAQ?

How can I contribute to this FAQ?

Where do I get the latest version of this FAQ?

Overview

What is JUnit?

Where is the JUnit home page?

Where are the JUnit mailing lists and forums?

Where is the JUnit documentation?

Where can I find articles on JUnit?

What's the latest news on JUnit?

How is JUnit licensed?

What awards has JUnit won?

Getting Started

Where do I download JUnit?

How do I install JUnit?

How do I uninstall JUnit?

How do I ask questions?

How do I submit bugs, patches, or feature requests?

Writing Tests

How do I write and run a simple test?

How do I use a test fixture?

How do I test a method that doesn't return anything?

Under what conditions should I test get() and set() methods?

Under what conditions should I not test get() and set() methods?

How do I write a test that passes when an expected exception is thrown?

How do I write a test that fails when an unexpected exception is thrown?

How do I test protected methods?

How do I test private methods?

Why does JUnit only report the first failure in a single test?

In Java 1.4, 'assert' is a keyword. Won't this conflict with JUnit's assert() method?

How do I test things that must be run in a J2EE container (e.g. servlets, EJBs)?

Do I need to write a test class for every class I need to test?

Is there a basic template I can use to create a test?

How do I write a test for an abstract class?

When are tests garbage collected?

Organizing Tests

Where should I put my test files?

How can I run setUp() and tearDown() code once for all of my tests?

Running Tests

What CLASSPATH settings are needed to run JUnit?

Why do I get a NoClassDefFoundError when trying to test JUnit or run the samples?

How do I run JUnit from my command window?

How do I run JUnit using Ant?

How do I use Ant to create HTML test reports?

How do I pass command-line arguments to a test execution?

Why do I get a LinkageError when using XML interfaces in my test class?

Why do I get the warning "AssertionFailedError: No tests found in XXX" when I run my test?

Why do I see "Unknown Source" in the stack trace of a test failure, rather than the source file's line number?

How do I organize all test classes in a TestSuite automatically and not use or manage a TestSuite explicitly?

Best Practices

When should tests be written?

Do I have to write a test for everything?

How simple is 'too simple to break'?

How often should I run my tests?

What do I do when a defect is reported?

Why not just use System.out.println()?

Why not just use a debugger?

Miscellaneous

How do I integrate JUnit with my IDE?

How do I launch a debugger when a test fails?

Where can I find unit testing frameworks similar to JUnit for other languages?

FAQ Info
Who is responsible for this FAQ?

The current version of this FAQ is maintained by Mike Clark.

Most of the wisdom contained in this FAQ comes from the collective insights and hard-won experiences of the many good folks who participate on the JUnit mailing list and the JUnit community at large.

If you see your genius represented anywhere in this FAQ without due credit to you, please send me an email and I'll make things right.

How can I contribute to this FAQ?

Your contributions to this FAQ are greatly appreciated! The JUnit community thanks you in advance.

To contribute to this FAQ, simply write a JUnit-related question and answer, then send the unformatted text to Mike Clark. Corrections to this FAQ are always appreciated, as well.

No reasonable contribution will be denied. Your name will always appear along with any contribution you make.

Where do I get the latest version of this FAQ?

The master copy of this FAQ is available at http://junit.sourceforge.net/doc/faq/faq.htm.

The JUnit distribution also includes this FAQ in the doc directory.

Overview
What is JUnit?

JUnit is a simple, open source framework to write and run repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks. JUnit features include: Assertions for testing expected results Test fixtures for sharing common test data Test runners for running tests

JUnit was originally written by Erich Gamma and Kent Beck.

Where is the JUnit home page?

The official JUnit home page is http://junit.org.

Where are the JUnit mailing lists and forums?

There are 3 mailing lists dedicated to everything JUnit:

JUnit user list. (Search it for answers to frequently asked questions not included here.)
JUnit announcements (Link was to: http://lists.sourceforge.net/lists/listinfo/junit-announce)
JUnit developer list (Link was to: http://lists.sourceforge.net/lists/listinfo/junit-devel)
Where is the JUnit documentation?

The following documents are included in the JUnit distribution in the doc directory:

JUnit Test Infected: Programmers Love Writing Tests (Old link: http://junit.sourceforge.net/doc/testinfected/testing.htm)
JUnit Cookbook (Old link: http://junit.sourceforge.net/doc/cookbook/cookbook.htm)
JUnit - A Cook's Tour (Old link: http://junit.sourceforge.net/doc/cookstour/cookstour.htm)
JUnit FAQ (Old link: http://junit.sourceforge.net/doc/faq/faq.htm)
Where can I find articles on JUnit?

The JUnit home page maintains a list of JUnit articles. (Dead link: http://www.junit.org/news/article/index.htm)

What's the latest news on JUnit?

The JUnit home page publishes the latest JUnit news. (Dead link: http://www.junit.org/news/index.htm)

How is JUnit licensed?

JUnit is Open Source Software, released under the Eclipse Public License Version 1.0 and hosted on GitHub.

What awards has JUnit won?

2002 JavaWorld Editors' Choice Awards (ECA)
Best Java Performance Monitoring/Testing Tool

2001 JavaWorld Editors' Choice Awards (ECA)
Best Java Performance Monitoring/Testing Tool

Getting Started
Where do I download JUnit?

The latest version of JUnit is available on SourceForge. (Old link: http://sourceforge.net/project/showfiles.php?group_id=15278) (Needs link: https://github.com/junit-team/junit4/wiki/Download-and-Install)

How do I install JUnit?

First, _download_(needs link: <https://github.com/junit-team/junit4/wiki/Download-and-Install>) the latest version of JUnit, referred to below as 'junit.zip'.

Then install JUnit on your platform of choice:

Windows

To install JUnit on Windows, follow these steps:

     Unzip the junit.zip distribution file to a directory referred to as %JUNIT_HOME%.
     Add JUnit to the classpath:

     set CLASSPATH=%CLASSPATH%;%JUNIT_HOME%\junit.jar

Unix (bash)

To install JUnit on Unix, follow these steps:

     Unzip the junit.zip distribution file to a directory referred to as $JUNIT_HOME.

     Add JUnit to the classpath:

     export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit.jar

(Optional) Unzip the $JUNIT_HOME/src.jar file.

Test the installation by running the sample tests distributed with JUnit. Note that the sample tests are located in the installation directory directly, not the junit.jar file. Therefore, make sure that the JUnit installation directory is on your CLASSPATH. Then simply type:

     java org.junit.runner.JUnitCore org.junit.tests.AllTests

All the tests should pass with an "OK" message.

If the tests don't pass, verify that junit.jar is in the CLASSPATH.

Finally, read the documentation.
How do I uninstall JUnit?

Delete the directory structure where you unzipped the JUnit distribution.

Remove junit.jar from the CLASSPATH.
JUnit does not modify the registry so simply removing all the files will fully uninstall it.

How do I ask questions?

Questions that are not answered in the FAQ or in the documentation should be posted to the jGuru discussion forum or the JUnit user mailing list.

Please stick to technical issues on the discussion forum and mailing lists. Keep in mind that these are public, so do not include any confidental information in your questions!

You should also read "How to ask questions the smart way" by Eric Raymond before participating in the discussion forum and mailing lists.

NOTE: Please do NOT submit bugs, patches, or feature requests to the discussion forum or mailing lists. Refer instead to "How do I submit bugs, patches, or feature requests?".

How do I submit bugs, patches, or feature requests?

JUnit celebrates programmers testing their own software. In this spirit, bugs, patches, and feature requests that include JUnit tests have a better chance of being addressed than those without.

JUnit is hosted on SourceForge. Please use the tools provided by SourceForge for your submissions.

Writing Tests
How do I write and run a simple test?

Create a class:

package junitfaq;

import org.junit._;
import static org.junit.Assert._;

import java.util.\*;

public class SimpleTest {
Write a test method (annotated with @Test) that asserts expected results on the object under test:

@Test
public void testEmptyCollection() {
Collection collection = new ArrayList();
assertTrue(collection.isEmpty());
}
If you are running your JUnit 4 tests with a JUnit 3.x runner, write a suite() method that uses the JUnit4TestAdapter class to create a suite containing all of your test methods:

public static junit.framework.Test suite() {
return new junit.framework.JUnit4TestAdapter(SimpleTest.class);
}
Although writing a main() method to run the test is much less important with the advent of IDE runners, it's still possible:

public static void main(String args[]) {
org.junit.runner.JUnitCore.main("junitfaq.SimpleTest");
}
}

Run the test:

To run the test from the console, type:

java org.junit.runner.JUnitCore junitfaq.SimpleTest
To run the test with the test runner used in main(), type:

java junitfaq.SimpleTest
The passing test results in the following textual output:

.
Time: 0

OK (1 tests)
How do I use a test fixture?

(Submitted by: Jeff Nielsen)

A test fixture is useful if you have two or more tests for a common set of objects. Using a test fixture avoids duplicating the code necessary to initialize (and cleanup) the common objects.

Tests can use the objects (variables) in a test fixture, with each test invoking different methods on objects in the fixture and asserting different expected results. Each test runs in its own test fixture to isolate tests from the changes made by other tests. That is, tests don't share the state of objects in the test fixture. Because the tests are isolated, they can be run in any order.

To create a test fixture, declare instance variables for the common objects. Initialize these objects in a public void method annotated with @Before. The JUnit framework automatically invokes any @Before methods before each test is run.

The following example shows a test fixture with a common Collection object.

package junitfaq;

import org.junit._;
import static org.junit.Assert._;
import java.util.\*;

public class SimpleTest {

     private Collection<Object> collection;

     @Before
     public void setUp() {
         collection = new ArrayList<Object>();
     }

     @Test
     public void testEmptyCollection() {
         assertTrue(collection.isEmpty());
     }


     @Test
     public void testOneItemCollection() {
         collection.add("itemA");
         assertEquals(1, collection.size());
     }

}
Given this test, the methods might execute in the following order:

setUp()
testEmptyCollection()
setUp()
testOneItemCollection()
The ordering of test-method invocations is not guaranteed, so testOneItemCollection() might be executed before testEmptyCollection(). But it doesn't matter, because each method gets its own instance of the collection.

Although JUnit provides a new instance of the fixture objects for each test method, if you allocate any external resources in a @Before method, you should release them after the test runs by annotating a method with @After. The JUnit framework automatically invokes any @After methods after each test is run. For example:

package junitfaq;

import org.junit._;
import static org.junit.Assert._;
import java.io.\*;

public class OutputTest {

     private File output;

     @Before
     public void createOutputFile() {
         output = new File(...);
     }

     @After
     public void deleteOutputFile() {
         output.delete();
     }

     @Test
     public void testSomethingWithFile() {
         ...
     }

}
With this test, the methods will execute in the following order:

createOutputFile()
testSomethingWithFile()
deleteOutputFile()
How do I test a method that doesn't return anything?

(Submitted by: Dave Astels)

Often if a method doesn't return a value, it will have some side effect. Actually, if it doesn't return a value AND doesn't have a side effect, it isn't doing anything.

There may be a way to verify that the side effect actually occurred as expected. For example, consider the add() method in the Collection classes. There are ways of verifying that the side effect happened (i.e. the object was added). You can check the size and assert that it is what is expected:

@Test
public void testCollectionAdd() {
Collection collection = new ArrayList();
assertEquals(0, collection.size());
collection.add("itemA");
assertEquals(1, collection.size());
collection.add("itemB");
assertEquals(2, collection.size());
}
Another approach is to make use of MockObjects.

A related issue is to design for testing. For example, if you have a method that is meant to output to a file, don't pass in a filename, or even a FileWriter. Instead, pass in a Writer. That way you can pass in a StringWriter to capture the output for testing purposes. Then you can add a method (e.g. writeToFileNamed(String filename)) to encapsulate the FileWriter creation.

Under what conditions should I test get() and set() methods?

Unit tests are intended to alleviate fear that something might break. If you think a get() or set() method could reasonably break, or has in fact contributed to a defect, then by all means write a test.

In short, test until you're confident. What you choose to test is subjective, based on your experiences and confidence level. Remember to be practical and maximize your testing investment.

Refer also to "How simple is 'too simple to break'?".

Under what conditions should I not test get() and set() methods?

(Submitted by: J. B. Rainsberger)

Most of the time, get/set methods just can't break, and if they can't break, then why test them? While it is usually better to test more, there is a definite curve of diminishing returns on test effort versus "code coverage". Remember the maxim: "Test until fear turns to boredom."

Assume that the getX() method only does "return x;" and that the setX() method only does "this.x = x;". If you write this test:

@Test
public void testGetSetX() {
setX(23);
assertEquals(23, getX());
}
then you are testing the equivalent of the following:

@Test
public void testGetSetX() {
x = 23;
assertEquals(23, x);
}
or, if you prefer,

@Test
public void testGetSetX() {
assertEquals(23, 23);
}
At this point, you are testing the Java compiler, or possibly the interpreter, and not your component or application. There is generally no need for you to do Java's testing for them.

If you are concerned about whether a property has already been set at the point you wish to call getX(), then you want to test the constructor, and not the getX() method. This kind of test is especially useful if you have multiple constructors:

@Test
public void testCreate() {
assertEquals(23, new MyClass(23).getX());
}
How do I write a test that passes when an expected exception is thrown?

Add the optional expected attribute to the @Test annotation. The following is an example test that passes when the expected IndexOutOfBoundsException is raised:

@Test(expected=IndexOutOfBoundsException.class)
public void testIndexOutOfBoundsException() {
ArrayList emptyList = new ArrayList();
Object o = emptyList.get(0);
}
How do I write a test that fails when an unexpected exception is thrown?

Declare the exception in the throws clause of the test method and don't catch the exception within the test method. Uncaught exceptions will cause the test to fail with an error.

The following is an example test that fails when the IndexOutOfBoundsException is raised:

@Test
public void testIndexOutOfBoundsExceptionNotRaised()
throws IndexOutOfBoundsException {

     ArrayList emptyList = new ArrayList();
     Object o = emptyList.get(0);

}
How do I test protected methods?

Place your tests in the same package as the classes under test.

Refer to "Where should I put my test files?" for examples of how to organize tests for protected method access.

How do I test private methods?

Testing private methods may be an indication that those methods should be moved into another class to promote reusability.

But if you must...

If you are using JDK 1.3 or higher, you can use reflection to subvert the access control mechanism with the aid of the PrivilegedAccessor. For details on how to use it, read this article.

Why does JUnit only report the first failure in a single test?

(Submitted by: J. B. Rainsberger)

Reporting multiple failures in a single test is generally a sign that the test does too much, compared to what a unit test ought to do. Usually this means either that the test is really a functional/acceptance/customer test or, if it is a unit test, then it is too big a unit test.

JUnit is designed to work best with a number of small tests. It executes each test within a separate instance of the test class. It reports failure on each test. Shared setup code is most natural when sharing between tests. This is a design decision that permeates JUnit, and when you decide to report multiple failures per test, you begin to fight against JUnit. This is not recommended.

Long tests are a design smell and indicate the likelihood of a design problem. Kent Beck is fond of saying in this case that "there is an opportunity to learn something about your design." We would like to see a pattern language develop around these problems, but it has not yet been written down.

Finally, note that a single test with multiple assertions is isomorphic to a test case with multiple tests:

One test method, three assertions:

public class MyTestCase {
@Test
public void testSomething() {
// Set up for the test, manipulating local variables
assertTrue(condition1);
assertTrue(condition2);
assertTrue(condition3);
}
}
Three test methods, one assertion each:

public class MyTestCase {
// Local variables become instance variables

    @Before
    public void setUp() {
        // Set up for the test, manipulating instance variables
    }

    @Test
    public void testCondition1() {
        assertTrue(condition1);
    }

    @Test
    public void testCondition2() {
        assertTrue(condition2);
    }

    @Test
    public void testCondition3() {
        assertTrue(condition3);
    }

}
The resulting tests use JUnit's natural execution and reporting mechanism and, failure in one test does not affect the execution of the other tests. You generally want exactly one test to fail for any given bug, if you can manage it.

In Java 1.4, assert is a keyword. Won't this conflict with JUnit's assert() method?

JUnit 3.7 deprecated assert() and replaced it with assertTrue(), which works exactly the same way.

JUnit 4 is compatible with the assert keyword. If you run with the -ea JVM switch, assertions that fail will be reported by JUnit.

How do I test things that must be run in a J2EE container (e.g. servlets, EJBs)?

Refactoring J2EE components to delegate functionality to other objects that don't have to be run in a J2EE container will improve the design and testability of the software.

Cactus is an open source JUnit extension that can be used to test J2EE components in their natural environment.

Do I need to write a test class for every class I need to test?

(Submitted by: J. B. Rainsberger)

No. It is a convention to start with one test class per class under test, but it is not necessary.

Test classes only provide a way to organize tests, nothing more. Generally you will start with one test class per class under test, but then you may find that a small group of tests belong together with their own common test fixture.[1] In this case, you may move those tests to a new test class. This is a simple object-oriented refactoring: separating responsibilities of an object that does too much.

Another point to consider is that the TestSuite is the smallest execution unit in JUnit: you cannot execute anything smaller than a TestSuite at one time without changing source code. In this case, you probably do not want to put tests in the same test class unless they somehow "belong together". If you have two groups of tests that you think you'd like to execute separately from one another, it is wise to place them in separate test classes.

[1] A test fixture is a common set of test data and collaborating objects shared by many tests. Generally they are implemented as instance variables in the test class.

Is there a basic template I can use to create a test?

(Submitted by: Eric Armstrong)

The following templates are a good starting point. Copy/paste and edit these templates to suit your coding style.

SampleTest is a basic test template:

import org.junit._;
import static org.junit.Assert._;

public class SampleTest {

    private java.util.List emptyList;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        emptyList = new java.util.ArrayList();
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        emptyList = null;
    }

    @Test
    public void testSomeBehavior() {
        assertEquals("Empty list should have 0 elements", 0, emptyList.size());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testForException() {
        Object o = emptyList.get(0);
    }

}
How do I write a test for an abstract class?

Refer to http://c2.com/cgi/wiki?AbstractTestCases.

When are tests garbage collected?

(Submitted by: Timothy Wall and Kent Beck)

By design, the tree of Test instances is built in one pass, then the tests are executed in a second pass. The test runner holds strong references to all Test instances for the duration of the test execution. This means that for a very long test run with many Test instances, none of the tests may be garbage collected until the end of the entire test run.

Therefore, if you allocate external or limited resources in a test, you are responsible for freeing those resources. Explicitly setting an object to null in the tearDown() method, for example, allows it to be garbage collected before the end of the entire test run.

Organizing Tests
Where should I put my test files?

You can place your tests in the same package and directory as the classes under test.

For example:

src
com
xyz
SomeClass.java
SomeClassTest.java
While adequate for small projects, many developers feel that this approach clutters the source directory, and makes it hard to package up client deliverables without also including unwanted test code, or writing unnecessarily complex packaging tasks.

An arguably better way is to place the tests in a separate parallel directory structure with package alignment.

For example:

src
com
xyz
SomeClass.java
test
com
xyz
SomeClassTest.java
These approaches allow the tests to access to all the public and package visible methods of the classes under test.

Some developers have argued in favor of putting the tests in a sub-package of the classes under test (e.g. com.xyz.test). The author of this FAQ sees no clear advantage to adopting this approach and believes that said developers also put their curly braces on the wrong line. :-)

How can I run setUp() and tearDown() code once for all of my tests?

The desire to do this is usually a symptom of excessive coupling in your design. If two or more tests must share the same test fixture state, then the tests may be trying to tell you that the classes under test have some undesirable dependencies.

Refactoring the design to further decouple the classes under test and eliminate code duplication is usually a better investment than setting up a shared test fixture.

But if you must...

You can add a @BeforeClass annotation to a method to be run before all the tests in a class, and a @AfterClass annotation to a method to be run after all the tests in a class. Here's an example:

package junitfaq;

import org.junit._;
import static org.junit.Assert._;
import java.util.\*;

public class SimpleTest {

     private Collection collection;

     @BeforeClass
     public static void oneTimeSetUp() {
         // one-time initialization code
     }

     @AfterClass
     public static void oneTimeTearDown() {
         // one-time cleanup code
     }

     @Before
     public void setUp() {
         collection = new ArrayList();
     }

     @After
     public void tearDown() {
         collection.clear();
     }

     @Test
     public void testEmptyCollection() {
         assertTrue(collection.isEmpty());
     }

     @Test
     public void testOneItemCollection() {
         collection.add("itemA");
         assertEquals(1, collection.size());
     }

}
Given this test, the methods will execute in the following order:

oneTimeSetUp()
setUp()
testEmptyCollection()
tearDown()
setUp()
testOneItemCollection()
tearDown()
oneTimeTearDown()
Running Tests
What CLASSPATH settings are needed to run JUnit?

(Submitted by: Eric Armstrong)

To run your JUnit tests, you'll need the following elemements in your CLASSPATH: JUnit class files Your class files, including your JUnit test classes Libraries your class files depend on

If attempting to run your tests results in a NoClassDefFoundError, then something is missing from your CLASSPATH.

Windows Example:

set CLASSPATH=%JUNIT_HOME%\junit.jar;c:\myproject\classes;c:\myproject\lib\something.jar

Unix (bash) Example:

export CLASSPATH=$JUNIT_HOME/junit.jar:/myproject/classes:/myproject/lib/something.jar

Why do I get a NoClassDefFoundError when trying to test JUnit or run the samples?

(Submitted by: J.B. Rainsberger and Jason Rogers)

Most likely your CLASSPATH doesn't include the JUnit installation directory.

Refer to "What CLASSPATH settings are needed to run JUnit?" for more guidance.

Also consider running WhichJunit to print the absolute location of the JUnit class files required to run and test JUnit and its samples.

If the CLASSPATH seems mysterious, read this!

How do I run JUnit from my command window?

(Submitted by: Eric Armstrong)

Set your CLASSPATH

Invoke the runner:

java org.junit.runner.JUnitCore <test class name>
How do I run JUnit using Ant?

(Submitted by: Eric Armstrong)

Define any necessary Ant properties:

 <property name="src" value="./src" />
 <property name="lib" value="./lib" />
 <property name="classes" value="./classes" />
 <property name="test.class.name" value="com.xyz.MyTestSuite" />


Set up the CLASSPATH to be used by JUnit:

 <path id="test.classpath">
   <pathelement location="${classes}" />
   <pathelement location="/path/to/junit.jar" />
   <fileset dir="${lib}">
     <include name="**/*.jar"/>
   </fileset>
 </path>


Define the Ant task for running JUnit:

 <target name="test">
   <junit fork="yes" haltonfailure="yes">
     <test name="${test.class.name}" />
     <formatter type="plain" usefile="false" />
     <classpath refid="test.classpath" />
   </junit>
 </target>


Run the test:
ant test
Refer to the JUnit Ant Task for more information.

How do I use Ant to create HTML test reports?

(Submitted by: Eric Armstrong and Steffen Gemkow)

Ensure that Ant's optional.jar file is either in your CLASSPATH or exists in your $ANT_HOME/lib directory.

Add an ANT property for the directory containing the HTML reports:
<property name="test.reports" value="./reports" />

Define the Ant task for running JUnit and generating reports:

 <target name="test-html">
   <junit fork="yes" printsummary="no" haltonfailure="no">
     <batchtest fork="yes" todir="${test.reports}" >
       <fileset dir="${classes}">
         <include name="**/*Test.class" />
       </fileset>
     </batchtest>
     <formatter type="xml" />
     <classpath refid="test.classpath" />
   </junit>

   <junitreport todir="${test.reports}">
     <fileset dir="${test.reports}">
       <include name="TEST-*.xml" />
     </fileset>
     <report todir="${test.reports}" />
   </junitreport>
 </target>


Run the test:
ant test-html
Refer to the JUnit Ant Task for more information.

How do I pass command-line arguments to a test execution?

Use the -D JVM command-line options, as in:

-DparameterName=parameterValue
If the number of parameters on the command line gets unweildy, pass in the location of a property file that defines a set of parameters. Alternatively, the JUnit-addons package contains the XMLPropertyManager and PropertyManager classes that allow you to define a property file (or XML file) containing test parameters.

Why do I get a LinkageError when using XML interfaces in my test?

(Submitted by: Scott Stirling)

The workaround as of JUnit 3.7 is to add org.w3c.dom._ and org.xml.sax._ to your excluded.properties.

It's just a matter of time before this fix becomes incorporated into the released version of JUnit's excluded.properties, since JAXP is a standard part of JDK 1.4. It will be just like excluding org.omg.\*. By the way, if you download the JUnit source from its Sourceforge CVS, you will find that these patterns have already been added to the default excluded.properties and so has a pattern for JINI. In fact, here is the current version in CVS, which demonstrates how to add exclusions to the list too:

#

# The list of excluded package paths for the TestCaseClassLoader

#

excluded.0=sun._
excluded.1=com.sun._
excluded.2=org.omg._
excluded.3=javax._
excluded.4=sunw._
excluded.5=java._
excluded.6=org.w3c.dom._
excluded.7=org.xml.sax._
excluded.8=net.jini._
This is the most common case where the default excluded.properties list needs modification. The cause of the LinkageError is related to using JAXP in your test cases. By JAXP I mean the whole set of javax.xml._ classes and the supporting org.w3c.dom._ and org.xml.sax._ classes.

As stated above, the JUnit GUI TestRunners' classloader relies on the excluded.properties for classes it should delegate to the system classloader. JAXP is an unusual case because it is a standard Java extension library dependent on classes whose package names (org.w3c.dom._ and org.xml.sax.) do not begin with a standard Java or Sun prefix. This is similar to the relationship between javax.rmi. and the org.omg._ classes, which have been excluded by default in JUnit'ss excluded.properties for a while.

What can happen, and frequently does when using the JUnit Swing or AWT UI with test cases that reference, use or depend on JAXP classes, such as Log4J, Apache SOAP, Axis, Cocoon, etc., is that the JUnit class loader (properly) delegates javax.xml.\* classes it "sees" to the system loader. But then the system loader, in the process of initializing and loading that JAXP class, links and loads up a bunch of org.w3c.dom/org.xml.sax classes. When it does so, the JUnit custom classloader is not involved at all because the system classloader never delegates "down" or checks with custom classloaders to see if a class is already loaded. At any point after this, if the JUnit loader is asked to load an org.w3c.dom/org.xml.sax class that it's never seen before, it will try to load it because the class' name doesn't match any of the patterns in the default exclude list. That's when a LinkageError occurs. This is really a flaw in the JUnit classloader design, but there is the workaround given above.

Java 2 JVMs keep classes (remember, classes and objects, though related, are different entities to the JVM - I'm talking about classes here, not object instances) in namespaces, identifying them by their fully qualified classname plus the instance of their defining (not initiating) loader. The JVM will attempt to assign all unloaded classes referenced by an already defined and loaded class to that class's defining loader. The JVM's classresolver routine (implemented as a C function in the JVM source code) keeps track of all these class loading events and "sees" if another classloader (such as the JUnit custom loader) attempts to define a class that has already been defined by the system loader. According to the rules of Java 2 loader constraints, in case a class has already been defined by the system loader, any attempts to load a class should first be delegated to the system loader. A "proper" way for JUnit to handle this feature would be to load classes from a repository other than the CLASSPATH that the system classloader knows nothing about. And then the JUnit custom classloader could follow the standard Java 2 delegation model, which is to always delegate class loading to the system loader, and only attempt to load if that fails. Since they both load from the CLASSPATH in the current model, if the JUnit loader delegated like it's supposed to, it would never get to load any classes since the system loader would always find them.

You could try to hack around this in the JUnit source by catching the LinkageError in TestCaseClassLoader's loadClass() method and then making a recovery call to findSystemClass() -- thereby delegating to the system loader after the violation has been caught. But this hack only works some of the time, because now you can have the reverse problem where the JUnit loader will load a host of org.w3c.dom/org.xml.sax classes, and then the system loader violates the loader contraints at some point when it tries to do exactly what I described above with JAXP because it doesn't ever delegate to its logical child (the JUnit loader). Inevitably, if your test cases use many JAXP and related XML classes, one or the other classloader will end up violating the constraints whatever you do.

Why do I get the warning "AssertionFailedError: No tests found in XXX" when I run my test?

Make sure you have more or more method annotated with @Test.

For example:

@Test
public void testSomething() {
}
Why do I see "Unknown Source" in the stack trace of a test failure, rather than the source file's line number?

The debug option for the Java compiler must be enabled in order to see source file and line number information in a stack trace.

When invoking the Java compiler from the command line, use the -g option to generate all debugging info.

When invoking the Java compiler from an Ant task, use the debug="on" attribute. For example:

 <javac srcdir="${src}" destdir="${build}" debug="on" /> 
When using older JVMs pre-Hotspot (JDK 1.1 and most/all 1.2), run JUnit with the -DJAVA_COMPILER=none JMV command line argument to prevent runtime JIT compilation from obscuring line number info.

Compiling the test source with debug enabled will show the line where the assertion failed. Compiling the non-test source with debug enabled will show the line where an exception was raised in the class under test.

How do I organize all test classes in a TestSuite automatically and not use or manage a TestSuite explicitly?

There are a number of ways to do this. In Ant, use the junit task and the batchtest element:

<junit printsummary="yes" haltonfailure="yes">
  ...
  <batchtest fork="yes">
    <fileset dir="${src.dir}">
       <include name="**/*Test.java" />
       <include name="**/Test*.java" />
    </fileset>
  </batchtest>
</junit> 
Idiomatic naming patterns for unit tests are Test*.java and *Test.java. Documentation and examples are at http://ant.apache.org/manual/Tasks/junit.html.

Use the DirectorySuiteBuilder and ArchiveSuiteBuilder (for jar/zip files) classes provided by JUnit-addons project:

    DirectorySuiteBuilder builder = new DirectorySuiteBuilder();
    builder.setSuffix("Test");
    Test suite = builer.suite("/home/project/myproject/tests");

Documentation and examples are at http://junit-addons.sourceforge.net.

Write your own custom suite builder.

Have your test classes implement an interface and write a treewalker to load each class in a directory, inspect the class, and add any classes that implement the interface to a TestSuite.

You might only want to do this if you are very uncomfortable with using a naming convention for test classes. Aside from being slow for larger suites, ultimately it's arguable whether it's more effort to follow a naming convention that have test classes implement an interface!

An example of this approach is at http://www.javaworld.com/javaworld/jw-12-2000/jw-1221-junit_p.html.

Best Practices
When should tests be written?

Tests should be written before the code. Test-first programming is practiced by only writing new code when an automated test is failing.

Good tests tell you how to best design the system for its intended use. They effectively communicate in an executable format how to use the software. They also prevent tendencies to over-build the system based on speculation. When all the tests pass, you know you're done!

Whenever a customer test fails or a bug is reported, first write the necessary unit test(s) to expose the bug(s), then fix them. This makes it almost impossible for that particular bug to resurface later.

Test-driven development is a lot more fun than writing tests after the code seems to be working. Give it a try!

Do I have to write a test for everything?

No, just test everything that could reasonably break.

Be practical and maximize your testing investment. Remember that investments in testing are equal investments in design. If defects aren't being reported and your design responds well to change, then you're probably testing enough. If you're spending a lot of time fixing defects and your design is difficult to grow, you should write more tests.

If something is difficult to test, it's usually an opportunity for a design improvement. Look to improve the design so that it's easier to test, and by doing so a better design will usually emerge.

How simple is 'too simple to break'?

(Submitted by: J. B. Rainsberger)

The general philosophy is this: if it can't break on its own, it's too simple to break.

First example is the getX() method. Suppose the getX() method only answers the value of an instance variable. In that case, getX() cannot break unless either the compiler or the interpreter is also broken. For that reason, don't test getX(); there is no benefit. The same is true of the setX() method, although if your setX() method does any parameter validation or has any side effects, you likely need to test it.

Next example: suppose you have written a method that does nothing but forward parameters into a method called on another object. That method is too simple to break.

public void myMethod(final int a, final String b) {
myCollaborator.anotherMethod(a, b);
}
myMethod cannot possibly break because it does nothing: it forwards its input to another object and that's all.

The only precondition for this method is "myCollaborator != null", but that is generally the responsibility of the constructor, and not of myMethod. If you are concerned, add a test to verify that myCollaborator is always set to something non-null by every constructor.

The only way myMethod could break would be if myCollaborator.anotherMethod() were broken. In that case, test myCollaborator, and not the current class.

It is true that adding tests for even these simple methods guards against the possibility that someone refactors and makes the methods "not-so-simple" anymore. In that case, though, the refactorer needs to be aware that the method is now complex enough to break, and should write tests for it -- and preferably before the refactoring.

Another example: suppose you have a JSP and, like a good programmer, you have removed all business logic from it. All it does is provide a layout for a number of JavaBeans and never does anything that could change the value of any object. That JSP is too simple to break, and since JSPs are notoriously annoying to test, you should strive to make all your JSPs too simple to break.

Here's the way testing goes:

becomeTimidAndTestEverything
while writingTheSameThingOverAndOverAgain
becomeMoreAggressive
writeFewerTests
writeTestsForMoreInterestingCases
if getBurnedByStupidDefect
feelStupid
becomeTimidAndTestEverything
end
end
The loop, as you can see, never terminates.

How often should I run my tests?

Run all your unit tests as often as possible, ideally every time the code is changed. Make sure all your unit tests always run at 100%. Frequent testing gives you confidence that your changes didn't break anything and generally lowers the stress of programming in the dark.

For larger systems, you may just run specific test suites that are relevant to the code you're working on.

Run all your acceptance, integration, stress, and unit tests at least once per day (or night).

If you're using Eclipse, be sure to check out David Saff's continuous testing plug-in.

What do I do when a defect is reported?

Test-driven development generally lowers the defect density of software. But we're all fallible, so sometimes a defect will slip through. When this happens, write a failing test that exposes the defect. When the test passes, you know the defect is fixed!

Don't forget to use this as a learning opportunity. Perhaps the defect could have been prevented by being more aggressive about testing everything that could reasonably break.

Why not just use System.out.println()?

Inserting debug statements into code is a low-tech method for debugging it. It usually requires that output be scanned manually every time the program is run to ensure that the code is doing what's expected.

It generally takes less time in the long run to codify expectations in the form of an automated JUnit test that retains its value over time. If it's difficult to write a test to assert expectations, the tests may be telling you that shorter and more cohesive methods would improve your design.

Why not just use a debugger?

Debuggers are commonly used to step through code and inspect that the variables along the way contain the expected values. But stepping through a program in a debugger is a manual process that requires tedious visual inspections. In essence, the debugging session is nothing more than a manual check of expected vs. actual results. Moreover, every time the program changes we must manually step back through the program in the debugger to ensure that nothing broke.

It generally takes less time to codify expectations in the form of an automated JUnit test that retains its value over time. If it's difficult to write a test to assert expected values, the tests may be telling you that shorter and more cohesive methods would improve your design.

Miscellaneous
How do I integrate JUnit with my IDE?

The JUnit home page maintains a list of IDE integration instructions.

How do I launch a debugger when a test fails?

Start the TestRunner under the debugger and configure the debugger so that it catches the junit.framework.AssertionFailedError.

How you configure this depends on the debugger you prefer to use. Most Java debuggers provide support to stop the program when a specific exception is raised.

Notice that this will only launch the debugger when an expected failure occurs.

Where can I find unit testing frameworks similar to JUnit for other languages?

XProgramming.com maintains a complete list of available xUnit testing frameworks.

Getting started
Marc Philipp edited this page on Jun 25, 2020 · 22 revisions
Pages 35
Find a page…
Home
'Enclosed' test runner example
Aggregating tests in suites
Assertions
Assertions CN
Assumptions with assume
Categories
Continuous testing
Custom runners
Deprecation Policy
Developing with fast tests
Download and Install
Exception testing
FAQ
Getting started
Preparation
Create the class under test
Create a test
Run the test
Let the test fail
Clone this wiki locally
https://github.com/junit-team/junit4.wiki.git
This small example shows you how to write a unit test. You need to have a JDK installed and a text editor. (In general it is recommended to use a build tool for building your software and running the tests.)

Preparation
Create a new folder junit-example and download the current junit-4.XX.jar from JUnit's release page and Hamcrest to this folder. Change to the folder junit-example. All files are created within this folder and all commands are executed there, too.

Create the class under test
Create a new file Calculator.java and copy the following code to this file.

public class Calculator {
public int evaluate(String expression) {
int sum = 0;
for (String summand: expression.split("\\+"))
sum += Integer.valueOf(summand);
return sum;
}
}
Now compile this class:

javac Calculator.java
The Java compiler creates a file Calculator.class.

Create a test
Create the file CalculatorTest.java and copy the following code to this file.

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalculatorTest {
@Test
public void evaluatesExpression() {
Calculator calculator = new Calculator();
int sum = calculator.evaluate("1+2+3");
assertEquals(6, sum);
}
}
Compile the test. On Linux or MacOS

javac -cp .:junit-4.XX.jar:hamcrest-core-1.3.jar CalculatorTest.java
and on Windows

javac -cp .;junit-4.XX.jar;hamcrest-core-1.3.jar CalculatorTest.java
The Java compiler creates a file CalculatorTest.class.

Run the test
Run the test from the command line. On Linux or MacOS

java -cp .:junit-4.XX.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CalculatorTest
and on Windows

java -cp .;junit-4.XX.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore CalculatorTest
The output is

JUnit version 4.12
.
Time: 0,006

OK (1 test)
The single . means that one test has been run and the OK in the last line tells you that your test is successful.

Let the test fail
Modify Calculator.java in order to get a failing test. Replace the line

sum += Integer.valueOf(summand);
with

sum -= Integer.valueOf(summand);
and recompile the class.

javac Calculator.java
Run the test again. On Linux or MacOS

java -cp .:junit-4.XX.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CalculatorTest
and on Windows

java -cp .;junit-4.XX.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore CalculatorTest
Now the test fails and the output is

JUnit version 4.12
.E
Time: 0,007
There was 1 failure:

1. evaluatesExpression(CalculatorTest)
   java.lang.AssertionError: expected:<6> but was:<-6>
   at org.junit.Assert.fail(Assert.java:88)
   ...

FAILURES!!!
Tests run: 1, Failures: 1
JUnit tells you which test failed (evaluatesExpression(CalculatorTest)) and what went wrong:

java.lang.AssertionError: expected:<6> but was:<-6>
