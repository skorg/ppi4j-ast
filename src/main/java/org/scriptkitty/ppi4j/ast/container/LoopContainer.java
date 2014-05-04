package org.scriptkitty.ppi4j.ast.container;

public abstract class LoopContainer<T> extends AbstractContainer<T>
{
    public static LoopContainer<Object> NULL = new LoopContainer<Object>(null)
    {
        @Override public void setEnd(int offset)
        {
            // no-op
        }

        @Override protected void addBody(Object stmt)
        {
            // no-op
        }

        @Override protected void addConditional(Object stmt)
        {
            // no-op
        }

        @Override protected void addContinue(Object stmt)
        {
            // no-op
        }
    };

    private boolean eBody;

    private boolean eCond;

    private boolean eCont;

    protected LoopContainer(T stmt)
    {
        super(stmt);
    }

    @Override public final void add(Object stmt)
    {
        if (eCond)
        {
            addConditional(stmt);
        }
        else if (eBody)
        {
            addBody(stmt);
        }
        else if (eCont)
        {
            addContinue(stmt);
        }
        else
        {
            // TODO: replace w/ custom exception
            // this should never happen...
            throw new RuntimeException("unable to add statement to contained object");
        }
    }

    public final void expectBody()
    {
        eBody = true;
        eCond = eCont = false;
    }

    public final void expectConditional()
    {
        eCond = true;
        eBody = eCont = false;
    }

    public final void expectContinue()
    {
        eCont = true;
        eCond = eBody = false;
    }

    protected abstract void addBody(Object stmt);

    protected abstract void addConditional(Object stmt);

    protected abstract void addContinue(Object stmt);
}
