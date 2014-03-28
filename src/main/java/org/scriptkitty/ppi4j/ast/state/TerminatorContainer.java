package org.scriptkitty.ppi4j.ast.state;

public abstract class TerminatorContainer extends StatementContainer
{
    protected TerminatorContainer(Object terminator)
    {
        super(terminator);
    }

    @Override public final void add(Object e)
    {
        throw new RuntimeException("attemted to add [" + e + "] to the terminator container");
    }
}
