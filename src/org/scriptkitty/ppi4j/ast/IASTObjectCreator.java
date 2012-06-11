package org.scriptkitty.ppi4j.ast;

import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.state.BlockContainer;
import org.scriptkitty.ppi4j.ast.state.IncludeContainer;
import org.scriptkitty.ppi4j.ast.state.LoopContainer;
import org.scriptkitty.ppi4j.ast.state.ModuleContainer;
import org.scriptkitty.ppi4j.ast.state.PackageContainer;
import org.scriptkitty.ppi4j.ast.state.StatementContainer;
import org.scriptkitty.ppi4j.ast.state.SubContainer;
import org.scriptkitty.ppi4j.ast.state.TerminatorContainer;

public interface IASTObjectCreator
{
    BlockContainer createBlock(int start, boolean isBody);

    PackageContainer createPackage(int start, Token pName);

    SubContainer createScheduled(int start, Token sName);

    SubContainer createSubroutine(int start, Token sName);

    StatementContainer createMethodCall(int start, Token cToken, Token mToken);
    
    TerminatorContainer createTerminator(int start);
    
    StatementContainer createBuiltinCall(int start, Token bToken);
    
    ModuleContainer createModule();
    
    PackageContainer createMainPackage();

    LoopContainer createFor(int start);

    LoopContainer createForeach(int start);

    //LoopContainer createIf(int start);

    LoopContainer createWhile(int start);

    LoopContainer createUntil(int start);

    IncludeContainer createInclude(int startOffset, Token mToken);
}
