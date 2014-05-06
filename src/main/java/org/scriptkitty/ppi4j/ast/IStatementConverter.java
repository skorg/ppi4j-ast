package org.scriptkitty.ppi4j.ast;

import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.ast.container.IASTContainer;

/**
 * 
 *
 * @param <S> statement type being converted
 * @param <C> container type that will hold the converted value
 */
public interface IStatementConverter<S extends Statement, C extends IASTContainer<?, ?>>
{
    boolean canConvert(Class<?> clazz);

    C convert(S stmt, IASTObjectCreator creator);

    boolean converts(S stmt);

    void reset();
}
