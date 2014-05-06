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
    <P, C> BlockContainer<P, C> createBlock(int start, boolean isBody);

    <P, C> StatementContainer<P, C> createBuiltinCall(int start, Token bToken);

    <P, C> LoopContainer<P, C> createFor(int start);

    <P, C> LoopContainer<P, C> createForeach(int start);

    <P, C> IncludeContainer<P, C> createInclude(int startOffset, Token mToken);

    <P, C> PackageContainer<P, C> createMainPackage();

    <P, C>StatementContainer<P, C> createMethodCall(int start, Token cToken, Token mToken);

    <P, C> ModuleContainer<P, C> createModule();

    <P, C>PackageContainer<P, C> createPackage(int start, Token pName);

    <P, C>SubContainer<P, C> createScheduled(int start, Token sName);

    <P, C>SubContainer<P, C> createSubroutine(int start, Token sName);

    //<P, C> LoopContainer<P, C> createIf(int start);

    <P, C>TerminatorContainer<P, C> createTerminator(int start);

    <P, C>LoopContainer<P, C> createUntil(int start);

    <P, C>LoopContainer<P, C> createWhile(int start);
}
