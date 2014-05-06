package org.scriptkitty.ppi4j.ast.container;

public abstract class StatementContainer<P, C> extends AbstractContainer<P, C>
{
    public static final StatementContainer<Object, Object> NULL = new StatementContainer<Object, Object>(null)
    {
        // empty implementation
    };
    
    protected StatementContainer(P stmt)
    {
        super(stmt);
    }
}
