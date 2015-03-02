# junit-doc

A small helper-classes to add easily junit-documenation, inspired by the work
of [Mario Gleichmann](https://github.com/mariogleichmann/JUnitExtensions).

If you want to use it add the following dependency to your pom.

    <dependency>
        <groupId>de.ppi.oss</groupId>
        <artifactId>junit-doc</artifactId>
        <version>0.1</version>
    </dependency>

and add the following repository

    <repositories>
        <repository>
            <id>opensource21</id>
            <url> http://opensource21.github.com/releases</url>
        </repository>
    </repositories>

The idea is that you use methods instead of comments. So normally you would write

    //ARRANGE create an an empty stack
    Stack<Integer> stack = new Stack<Integer>();
    //ACT pushing an element onto the stack
    stack.push(Integer.valueOf(42));
    //ASSUME the stack shouldn't be empty anymore
    assertFalse(stack.isEmpty());
now you wrote

    ARRANGE("an empty stack");
    Stack<Integer> stack = new Stack<Integer>();
    ACT("pushing an element onto the stack");
    stack.push(Integer.valueOf(42));
    ASSUME("the stack shouldn't be empty anymore");
    assertFalse(stack.isEmpty());

and can get a simple console report

    ARRANGE - an empty set
    ACT - adding an element to the set
    ASSUME - the set should'nt be empty anymore
     => Test successful

You can easily write more sophisticated reporter. Feel free to send pull-request
of your implementation.

See `/junit-doc/src/test/java/aaa/demo/StackTest.java` for an example.
