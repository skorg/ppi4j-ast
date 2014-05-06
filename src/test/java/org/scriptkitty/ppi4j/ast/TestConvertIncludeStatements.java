package org.scriptkitty.ppi4j.ast;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.container.IncludeContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.util.TestCaseProvider;


public class TestConvertIncludeStatements extends AbstractConverterTest
{
    @Mock private IncludeContainer<Object, Object> mContainer;

    @Before @Override public void setup()
    {
        super.setup();

        // only testing 'simple' statements here, so setup as if we were a script
        Mockito.when(mCreator.createMainPackage()).thenReturn(PackageContainer.NULL);
        Mockito.when(mContainer.isEmpty()).thenReturn(false);
    }

    @Test public void testUseBase1()
    {
        Document document = TestCaseProvider.parseSnippet("use base qw(Test);");
        Token[] tokens = document.getTokenArray();

        Mockito.when(mCreator.createInclude(0, tokens[2])).thenReturn(mContainer);

        visit(document, mCreator);

        Mockito.verify(mCreator).createInclude(0, tokens[2]);
        Mockito.verify(mContainer).setEnd(18);
    }

}
