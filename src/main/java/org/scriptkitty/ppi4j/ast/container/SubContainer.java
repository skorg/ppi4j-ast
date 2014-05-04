package org.scriptkitty.ppi4j.ast.container;

public abstract class SubContainer<T> extends AbstractContainer<T>
{
    protected SubContainer(T subroutine)
    {
        super(subroutine);
    }

    public void setDeclaringPackage(String currentPackageName)
    {
        // default no-op
    }
}
