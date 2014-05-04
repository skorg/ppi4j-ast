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
    <T> BlockContainer<T> createBlock(int start, boolean isBody);

    <T> StatementContainer<T> createBuiltinCall(int start, Token bToken);

    <T> LoopContainer<T> createFor(int start);

    <T> LoopContainer<T> createForeach(int start);

    <T> IncludeContainer<T> createInclude(int startOffset, Token mToken);

    <T> PackageContainer<T> createMainPackage();

    <T>StatementContainer<T> createMethodCall(int start, Token cToken, Token mToken);

    <T> ModuleContainer<T> createModule();

    <T>PackageContainer<T> createPackage(int start, Token pName);

    <T>SubContainer<T> createScheduled(int start, Token sName);

    <T>SubContainer<T> createSubroutine(int start, Token sName);

    //<T> LoopContainer<T> createIf(int start);

    <T>TerminatorContainer<T> createTerminator(int start);

    <T>LoopContainer<T> createUntil(int start);

    <T>LoopContainer<T> createWhile(int start);
}
