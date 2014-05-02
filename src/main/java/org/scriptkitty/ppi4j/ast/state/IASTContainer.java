package org.scriptkitty.ppi4j.ast.state;

public interface IASTContainer<T>
{
    /*
     * note: generics could have been used here, but that would have required setting a type on the visitor on down the implmentation and it
     * just didn't seem worth it.
     */

    void add(T e);

    T get();

    boolean isEmpty();

    void setEnd(int offset);

    static abstract class AbstractContainer<T> implements IASTContainer<T>
    {
        private T contained;

        protected AbstractContainer(T toContain)
        {
            this.contained = toContain;
        }

        /*
         * @see org.scriptkitty.ppi4j.ast.IASTContainer#get()
         */
        @Override public T get()
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
