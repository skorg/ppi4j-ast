package org.scriptkitty.ppi4j.ast.state;

public abstract class SubContainer extends IASTContainer.AbstractContainer
{
    protected SubContainer(Object subroutine)
    {
        super(subroutine);
    }

    public void setDeclaringPackage(String currentPackageName)
    {
        // default no-op
    }
}
