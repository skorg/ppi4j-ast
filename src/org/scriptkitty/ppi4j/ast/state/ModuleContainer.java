package org.scriptkitty.ppi4j.ast.state;

public abstract class ModuleContainer extends IASTContainer.AbstractContainer
{
    public static final ModuleContainer NULL = new ModuleContainer(null) 
    {
        @Override public void add(Object e)
        {
            // empty implementation
        }
    };
    
    //~ Constructors

    protected ModuleContainer(Object module)
    {
        super(module);
    }

    //~ Methods

    /*
     * @see org.scriptkitty.ppi4j.ast.IASTContainer#setEnd(int)
     */
    @Override public void setEnd(int offset)
    {
        // default no-op
    }
}
