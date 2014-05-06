package org.scriptkitty.ppi4j.ast;

import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.container.BlockContainer;
import org.scriptkitty.ppi4j.ast.container.IncludeContainer;
import org.scriptkitty.ppi4j.ast.container.LoopContainer;
import org.scriptkitty.ppi4j.ast.container.ModuleContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.ast.container.StatementContainer;
import org.scriptkitty.ppi4j.ast.container.SubContainer;
import org.scriptkitty.ppi4j.ast.container.TerminatorContainer;


public interface IASTObjectCreator
{
    BlockContainer<?, ?> createBlock(int start, boolean isBody);

    StatementContainer<?, ?> createBuiltinCall(int start, Token bToken);

    LoopContainer<?, ?> createFor(int start);

    LoopContainer<?, ?> createForeach(int start);

    IncludeContainer<?, ?> createInclude(int startOffset, Token mToken);

    PackageContainer<?, ?> createMainPackage();

    StatementContainer<?, ?> createMethodCall(int start, Token cToken, Token mToken);

    ModuleContainer<?, ?> createModule();

    PackageContainer<?, ?> createPackage(int start, Token pName);

    SubContainer<?, ?> createScheduled(int start, Token sName);

    SubContainer<?, ?> createSubroutine(int start, Token sName);

    //LoopContainer<?, ?> createIf(int start);

    TerminatorContainer<?, ?> createTerminator(int start);

    LoopContainer<?, ?> createUntil(int start);

    LoopContainer<?, ?> createWhile(int start);
}
