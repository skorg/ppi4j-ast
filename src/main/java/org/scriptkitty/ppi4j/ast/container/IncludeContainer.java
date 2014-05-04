package org.scriptkitty.ppi4j.ast.container;

import org.scriptkitty.ppi4j.Statement;

public abstract class IncludeContainer<T> extends AbstractContainer<T>
{
    public static final IncludeContainer<Object> NULL = new IncludeContainer<Object>(null)
    {
        // null implementation
    };

    protected IncludeContainer(T include)
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
