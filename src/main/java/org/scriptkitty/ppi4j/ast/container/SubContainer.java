package org.scriptkitty.ppi4j.ast.container;

public abstract class SubContainer<P, C> extends AbstractContainer<P, C>
{
    protected SubContainer(P subroutine)
    {
        super(subroutine);
    }

    public void setDeclaringPackage(String currentPackageName)
    {
        // default no-op
    }
}
