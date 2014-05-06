package org.scriptkitty.ppi4j.ast.container;

public interface IASTContainer<P, C>
{
    void add(C e);

    P get();

    boolean isEmpty();

    void setEnd(int offset);
    
    
}
