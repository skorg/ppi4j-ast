package org.scriptkitty.ppi4j.ast.state;

public abstract class StatementContainer extends IASTContainer.AbstractContainer
{
    public static StatementContainer NULL = new StatementContainer(null)
    {
        @Override public void setEnd(int offset)
        {
            // empty implementation
        }
    };

    protected StatementContainer(Object stmt)
    {
        super(stmt);
    }

    @Override public void add(Object e)
    {
        // no-op
    }
}
