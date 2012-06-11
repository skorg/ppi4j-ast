package org.scriptkitty.ppi4j.ast.state;

public abstract class StatementContainer extends IASTContainer.AbstractContainer
{
    //~ Static fields/initializers

    public static StatementContainer NULL = new StatementContainer(null)
    {
        @Override public void setEnd(int offset)
        {
            // empty implementation
        }
    };

    //~ Constructors

    protected StatementContainer(Object stmt)
    {
        super(stmt);
    }

    //~ Methods

    @Override public void add(Object e)
    {
        // no-op
    }
}
