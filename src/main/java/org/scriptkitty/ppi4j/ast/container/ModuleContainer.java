package org.scriptkitty.ppi4j.ast.container;

public abstract class ModuleContainer<T> extends AbstractContainer<T>
{
    public static final ModuleContainer<Object> NULL = new ModuleContainer<Object>(null)
    {
        // empty implementation
    };

    protected ModuleContainer(T module)
    {
        super(module);
    }
}
