package org.scriptkitty.ppi4j.ast.container;

public abstract class ModuleContainer<P, C> extends AbstractContainer<P, C>
{
    public static final ModuleContainer<Object, Object> NULL = new ModuleContainer<Object, Object>(null)
    {
        // empty implementation
    };

    protected ModuleContainer(P module)
    {
        super(module);
    }
}
