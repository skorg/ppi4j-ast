package org.scriptkitty.ppi4j.ast.container;

public abstract class StatementContainer<T> extends AbstractContainer<T>
{
    public static final StatementContainer<Object> NULL = new StatementContainer<Object>(null)
    {
        // empty implementation
    };
    
    protected StatementContainer(T stmt)
    {
        super(stmt);
    }
}
