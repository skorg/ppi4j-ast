package org.scriptkitty.ppi4j.ast.container;

public interface IASTContainer<T>
{
    void add(Object e);

    T get();

    boolean isEmpty();

    void setEnd(int offset);
}
