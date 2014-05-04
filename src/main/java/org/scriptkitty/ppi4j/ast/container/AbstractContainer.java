package org.scriptkitty.ppi4j.ast.container;

public abstract class AbstractContainer<T> implements IASTContainer<T>
{
    private T contained;

    protected AbstractContainer(T toContain)
    {
        this.contained = toContain;
    }
    
    /*
     * @see org.scriptkitty.ppi4j.ast.IASTContainer#get()
     */
    @Override public T get()
    {
        return contained;
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.state.IASTContainer#isEmpty()
     */
    @Override public boolean isEmpty()
    {
        return (contained == null);
    }

    @Override public void setEnd(int offset)
    {
        // no-op
    }

    @Override public void add(Object e)
    {
        // no-op
    }
}