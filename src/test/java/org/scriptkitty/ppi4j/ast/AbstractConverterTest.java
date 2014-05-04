package org.scriptkitty.ppi4j.ast;

import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.container.ModuleContainer;
import org.scriptkitty.ppi4j.util.IErrorProxy;
import org.scriptkitty.ppi4j.util.NullErrorProxy;


abstract class AbstractConverterTest
{
    @Mock protected IASTObjectCreator mCreator;

    private final IErrorProxy proxy = NullErrorProxy.getInstance();

    @Before public void setup()
    {
        MockitoAnnotations.initMocks(this);

        // setup the container, all tests will need this
        Mockito.when(mCreator.createModule()).thenReturn(ModuleContainer.NULL);
    }

    protected int anyInt()
    {
        return Matchers.anyInt();
    }

    protected Token anyToken()
    {
        return Matchers.any(Token.class);
    }

    protected void postVisit()
    {
        // empty implementation
    }

    protected final void visit(Document document, IASTObjectCreator mock)
    {
        ASTBuildVisitor visitor = new ASTBuildVisitor(mCreator, proxy);

        document.accept(visitor);
        document.destroy();

        postVisit();
    }
}
