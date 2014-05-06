package org.scriptkitty.ppi4j.ast;

import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.container.AbstractContainer;
import org.scriptkitty.ppi4j.ast.container.ModuleContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.util.IErrorProxy;
import org.scriptkitty.ppi4j.util.NullErrorProxy;


@SuppressWarnings({ "rawtypes", "unchecked" })
abstract class AbstractConverterTest
{
    @Mock protected IASTObjectCreator mCreator;

    private final IErrorProxy proxy = NullErrorProxy.getInstance();
    
    @Before public void setup()
    {
        MockitoAnnotations.initMocks(this);

        // setup the container, all tests will need this
        ModuleContainer container = ModuleContainer.NULL;        
        Mockito.when(mCreator.createModule()).thenReturn(container);
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

    protected void setupAsScript(AbstractContainer container)
    {
        PackageContainer pkgContainer = PackageContainer.NULL;
        
        Mockito.when(mCreator.createMainPackage()).thenReturn(pkgContainer);
        Mockito.when(container.isEmpty()).thenReturn(false);
    }
    
    protected final void visit(Document document, IASTObjectCreator mock)
    {
        ASTBuildVisitor visitor = new ASTBuildVisitor(mCreator, proxy);

        document.accept(visitor);
        document.destroy();

        postVisit();
    }
}
