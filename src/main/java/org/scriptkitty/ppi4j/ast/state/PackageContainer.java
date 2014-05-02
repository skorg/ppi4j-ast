package org.scriptkitty.ppi4j.ast.state;

public abstract class PackageContainer<T> extends IASTContainer.AbstractContainer<T>
{
    public static final PackageContainer<Void> NULL = new PackageContainer<Void>(null)
    {
        @Override public void add(Void e)
        {
            // empty implementation
        }

        @Override public String getPackageName()
        {
            return null;
        }

        @Override public void setEnd(int offset)
        {
            // empty implementation
        }
    };

    protected PackageContainer(T pkg)
    {
        super(pkg);
    }

    public void addSuperClass(String clazz)
    {
        // no-op
    }

    public abstract String getPackageName();
}
