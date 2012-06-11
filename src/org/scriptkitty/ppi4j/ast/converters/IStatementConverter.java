package org.scriptkitty.ppi4j.ast.converters;

import org.scriptkitty.ppi4j.ast.IASTObjectCreator;
import org.scriptkitty.ppi4j.ast.state.IASTContainer;

public interface IStatementConverter<S, C extends IASTContainer>
{
    boolean canConvert(Class<?> clazz);
    
    void reset();
    
    boolean converts(S stmt);
    
    C convert(S stmt, IASTObjectCreator creator);
}
