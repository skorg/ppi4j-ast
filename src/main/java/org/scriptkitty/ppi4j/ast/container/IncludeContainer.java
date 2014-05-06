package org.scriptkitty.ppi4j.ast.container;

import org.scriptkitty.ppi4j.Statement;

public abstract class IncludeContainer<P, C> extends AbstractContainer<P, C>
{
    public static final IncludeContainer<Object, Object> NULL = new IncludeContainer<Object, Object>(null)
    {
        // null implementation
    };

    protected IncludeContainer(P include)
    {
        super(include);
    }

    public void setDeclaringPackage(String currentPackageName)
    {
        // no-op
    }

    public void setModuleVersion(String moduleVersion)
    {
        // no-op
    }

    public void setType(Statement.Type type)
    {
        // no-op
    }
}
