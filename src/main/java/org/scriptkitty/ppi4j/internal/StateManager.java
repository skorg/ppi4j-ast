package org.scriptkitty.ppi4j.internal;

import java.util.LinkedList;
import java.util.ListIterator;

import org.scriptkitty.ppi4j.ast.IASTConverter;
import org.scriptkitty.ppi4j.ast.container.IASTContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.ast.container.TerminatorContainer;

public class StateManager
{
    private boolean initialized;

    private final LinkedList<IASTContainer<?>> stack = new LinkedList<>();

    private IASTConverter converter;
    
    StateManager setConverter(IASTConverter converter)
    {
        this.converter = converter;
        return this;
    }
    
    public void addToPackage(IASTContainer<?> container)
    {
        if (!hasPackage())
        {
            addToParent(converter.createMainPackage());
        }

        add(getPackage(), container);
    }

    public void addToParent(IASTContainer<?> container)
    {
        if (stack.isEmpty())
        {
            throw new RuntimeException("unable to add container, perhaps it was not initialized");
        }

        add(stack.getLast(), container);
    }

    public void ensurePackage()
    {
        if (!hasPackage())
        {
            addToParent(converter.createMainPackage());
        }
    }

    public PackageContainer<?> getPackage()
    {
        IASTContainer<?> container = stack.getLast();

        if (container instanceof PackageContainer)
        {
            return (PackageContainer<?>) container;
        }

        ListIterator<IASTContainer<?>> iterator = stack.listIterator(stack.size());
        while (iterator.hasPrevious())
        {
            if ((container = iterator.previous()) instanceof PackageContainer)
            {
                return (PackageContainer<?>) container;
            }
        }

        return null;
    }

    public boolean hasPackage()
    {
        return (getPackage() != null);
    }

    public void initialize(IASTContainer<?> container)
    {
        if (initialized)
        {
            throw new RuntimeException("state manager already initialized");
        }

        stack.add(container);
        initialized = true;
    }

    public boolean isTop(@SuppressWarnings("rawtypes") Class<? extends IASTContainer> clazz)
    {
        if (stack.isEmpty())
        {
            return false;
        }

        return (stack.getLast().getClass().getSuperclass() == clazz);
    }

    public void pop(int offset)
    {
        IASTContainer<?> container = stack.removeLast();

        // if the container's empty, don't set an offset
        if (!container.isEmpty())
        {
            container.setEnd(offset);
        }
    }

    public void terminate(int offset)
    {
        while (!stack.isEmpty())
        {
            pop(offset);
        }
    }

    private void add(IASTContainer<?> parent, IASTContainer<?> container)
    {
        /*
         * don't add the container to the parent if it's empty...
         *
         * this is done as a convienece so an IASTConverter can safely return 'null' if it doesn't know how to process a statement and not
         * have to worry about handling that case in their implementation.
         *
         * it is still added to the stack however so it can be 'popped' off when processing is done
         */
        if (!container.isEmpty())
        {
            if (container instanceof TerminatorContainer)
            {
                stack.getFirst().add(container.get());
            }
            else
            {
                parent.add(container.get());
            }
        }

        stack.add(container);
    }
}