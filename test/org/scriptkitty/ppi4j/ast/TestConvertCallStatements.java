package org.scriptkitty.ppi4j.ast;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;



import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.state.PackageContainer;
import org.scriptkitty.ppi4j.ast.state.StatementContainer;
import org.scriptkitty.ppi4j.util.TestCaseProvider;


public class TestConvertCallStatements extends AbstractConverterTest
{
    //~ Instance fields

    @Mock private StatementContainer mContainer;

    //~ Methods

    @Before @Override public void setup()
    {
        super.setup();

        // only testing 'simple' statements here, so setup as if we were a script
        Mockito.when(mCreator.createMainPackage()).thenReturn(PackageContainer.NULL);
        Mockito.when(mContainer.isEmpty()).thenReturn(false);
    }

    @Test public void testBuiltin1()
    {
        Document document = TestCaseProvider.parseSnippet("print");
        Token[] tokens = document.getTokenArray();

        Mockito.when(mCreator.createBuiltinCall(0, tokens[0])).thenReturn(mContainer);

        visit(document, mCreator);

        Mockito.verify(mCreator).createBuiltinCall(0, tokens[0]);
        Mockito.verify(mContainer).setEnd(5);
    }

    @Test public void testMethodCall1()
    {
        Document document = TestCaseProvider.parseSnippet("Foo->a();");
        Token[] tokens = document.getTokenArray();

        Mockito.when(mCreator.createMethodCall(0, tokens[0], tokens[2])).thenReturn(mContainer);

        visit(document, mCreator);

        Mockito.verify(mCreator).createMethodCall(0, tokens[0], tokens[2]);
        Mockito.verify(mContainer).setEnd(9);
    }

    @Test public void testMethodCall2()
    {
        Document document = TestCaseProvider.parseSnippet("Foo->");
        Token[] tokens = document.getTokenArray();

        Mockito.when(mCreator.createMethodCall(0, tokens[0], Token.NULL)).thenReturn(mContainer);

        visit(document, mCreator);

        Mockito.verify(mCreator).createMethodCall(0, tokens[0], Token.NULL);
        Mockito.verify(mContainer).setEnd(5);
    }

    @Override protected void postVisit()
    {
        // make sure we were set up correctly...
        Mockito.verify(mCreator).createModule();
        Mockito.verify(mCreator).createMainPackage();
    }
}
