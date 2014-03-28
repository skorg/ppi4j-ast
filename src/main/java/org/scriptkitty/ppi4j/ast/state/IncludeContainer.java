package org.scriptkitty.ppi4j.ast.state;

import org.scriptkitty.ppi4j.Statement;


public abstract class IncludeContainer extends IASTContainer.AbstractContainer
{
    public static final IncludeContainer NULL = new IncludeContainer(null)
    {
        @Override public void add(Object e)
        {
            // no-op
        }

        @Override public void setEnd(int offset)
        {
            // no-op
        }
    };

    protected IncludeContainer(Object include)
    {
        super(include);
    }

    public void setDeclaringPackage(String currentPackageName)
    {
        // default no-op
    }

    public void setModuleVersion(String moduleVersion)
    {
        // default no-op
    }

    public void setType(Statement.Type type)
    {
        // default no-op
    }
}
