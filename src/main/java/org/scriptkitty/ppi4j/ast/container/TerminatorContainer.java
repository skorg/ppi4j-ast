package org.scriptkitty.ppi4j.ast.container;

public abstract class TerminatorContainer<T> extends StatementContainer<T>
{
    protected TerminatorContainer(T terminator)
    {
        super(terminator);
    }

    @Override public final void add(Object e)
    {
        throw new RuntimeException("attemted to add [" + e + "] to the terminator container");
    }
}
