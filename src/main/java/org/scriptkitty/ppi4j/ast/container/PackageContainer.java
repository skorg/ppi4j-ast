package org.scriptkitty.ppi4j.ast.container;

public abstract class PackageContainer<T> extends AbstractContainer<T>
{
    public static final PackageContainer<Object> NULL = new PackageContainer<Object>(null)
    {
        @Override public String getPackageName()
        {
            return null;
        }
    };

    protected PackageContainer(T pkg)
    {
        super(pkg);
    }

    public abstract String getPackageName();

    public void addSuperClass(String clazz)
    {
        // no-op
    }
}
