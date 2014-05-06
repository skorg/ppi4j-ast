package org.scriptkitty.ppi4j.ast.container;

public abstract class AbstractContainer<P, C> implements IASTContainer<P, C>
{
    private P contained;

    protected AbstractContainer(P toContain)
    {
        this.contained = toContain;
    }
    
    /*
     * @see org.scriptkitty.ppi4j.ast.IASTContainer#get()
     */
    @Override public P get()
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

    @Override public void add(C e)
    {
        // no-op
    }
}