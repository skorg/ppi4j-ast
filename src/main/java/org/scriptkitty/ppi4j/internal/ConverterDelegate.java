package org.scriptkitty.ppi4j.internal;

import java.util.ServiceLoader;

import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.ast.IASTObjectCreator;
import org.scriptkitty.ppi4j.ast.IStatementConverter;
import org.scriptkitty.ppi4j.ast.container.IASTContainer;

class ConverterDelegate<S extends Statement, C extends IASTContainer<?>>
{
    private IASTObjectCreator creator;

    private IStatementConverter<S, C> converter;

    @SuppressWarnings("rawtypes")
    private ServiceLoader<IStatementConverter> loader = ServiceLoader.load(IStatementConverter.class);

    ConverterDelegate(IASTObjectCreator creator)
    {
        this.creator = creator;
    }

    C convert(S stmt)
    {
        if (!converts(converter, stmt))
        {
            // safety check, should never happen...
            throw new RuntimeException("unable to convert [" + stmt + "] with converter [" + converter + "]");
        }

        C container = converter.convert(stmt, creator);

        // reset the converter
        converter.reset();
        converter = null;

        return container;
    }

    boolean converts(S stmt)
    {
        IStatementConverter<S, C> converter = getHandler(stmt.getClass());

        if (converts(converter, stmt))
        {
            this.converter = converter;
            return true;
        }

        return false;
    }

    private boolean converts(IStatementConverter<S, C> converter, S stmt)
    {
        return ((converter != null) && converter.converts(stmt));
    }

    @SuppressWarnings("unchecked")
    private IStatementConverter<S, C> getHandler(Class<?> clazz)
    {
        for (IStatementConverter<S, C> handler : loader)
        {
            if (handler.canConvert(clazz))
            {
                return handler;
            }
        }

        return null;
    }
}