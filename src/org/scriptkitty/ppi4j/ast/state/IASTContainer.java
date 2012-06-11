package org.scriptkitty.ppi4j.ast.state;

public interface IASTContainer
{
    /*
     * note: generics could have been used here, but that would have required setting a type on the visitor on
     * down the implmentation and it just didn't seem worth it.
     */
   
    public Object get();
    
    public void add(Object e);
    
    public void setEnd(int offset);
    
    public boolean isEmpty();

    abstract class AbstractContainer implements IASTContainer
    {
        //~ Instance fields

        private Object contained;

        //~ Constructors
        
        protected AbstractContainer(Object toContain)
        {
            this.contained = toContain;
        }

        //~ Methods

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
