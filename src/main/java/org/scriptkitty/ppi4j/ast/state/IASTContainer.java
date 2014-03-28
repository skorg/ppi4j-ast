package org.scriptkitty.ppi4j.ast.state;

public interface IASTContainer
{
    /*
     * note: generics could have been used here, but that would have required setting a type on the visitor on down the implmentation and it
     * just didn't seem worth it.
     */

    void add(Object e);

    Object get();

    boolean isEmpty();

    void setEnd(int offset);

    abstract class AbstractContainer implements IASTContainer
    {
        private Object contained;

        protected AbstractContainer(Object toContain)
        {
            this.contained = toContain;
        }

        /*
         * @see org.scriptkitty.ppi4j.ast.IASTContainer#get()
         */
        @Override public Object get()
        {
            return contained;
        }

        /*
         * @see org.scriptkitty.ppi4j.ast.state.IASTContainer#isEmpty()
         */
        @Override public boolean isEmpty()
        {
            return (contained == null);
        }
    }
}
