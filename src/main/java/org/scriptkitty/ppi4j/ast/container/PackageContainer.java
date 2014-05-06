package org.scriptkitty.ppi4j.ast.container;

public abstract class PackageContainer<P, C> extends AbstractContainer<P, C>
{
    public static final PackageContainer<Object, Object> NULL = new PackageContainer<Object, Object>(null)
    {
        @Override public String getPackageName()
        {
            return null;
        }
    };

    protected PackageContainer(P pkg)
    {
        super(pkg);
    }

    public abstract String getPackageName();

    public void addSuperClass(String clazz)
    {
        // no-op
    }
}
