package org.scriptkitty.ppi4j.ast.container;

public abstract class TerminatorContainer<P, C> extends StatementContainer<P, C>
{
    protected TerminatorContainer(P terminator)
    {
        super(terminator);
    }

    @Override public final void add(C stmt)
    {
        throw new RuntimeException("attemted to add [" + stmt + "] to the terminator container");
    }
}
