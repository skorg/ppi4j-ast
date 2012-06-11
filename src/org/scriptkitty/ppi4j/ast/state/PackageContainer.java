package org.scriptkitty.ppi4j.ast.state;

public abstract class PackageContainer extends IASTContainer.AbstractContainer
{
    public static final PackageContainer NULL = new PackageContainer(null)
    {
        @Override public void add(Object e)
        {
            // empty implementation
        }

        @Override public void setEnd(int offset)
        {
            // empty implementation                     
        }

        @Override public String getPackageName()
        {            
            return null;
        }
    };
    
    protected PackageContainer(Object pkg)
    {
        super(pkg);
    }

    public void addSuperClass(String clazz)
    {
        // no-op
    }
    
    public abstract String getPackageName();
}
