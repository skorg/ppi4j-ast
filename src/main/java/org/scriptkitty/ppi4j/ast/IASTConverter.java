package org.scriptkitty.ppi4j.ast;

import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.ast.container.BlockContainer;
import org.scriptkitty.ppi4j.ast.container.IncludeContainer;
import org.scriptkitty.ppi4j.ast.container.LoopContainer;
import org.scriptkitty.ppi4j.ast.container.ModuleContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.ast.container.StatementContainer;
import org.scriptkitty.ppi4j.ast.container.SubContainer;
import org.scriptkitty.ppi4j.statement.CompoundStatement;
import org.scriptkitty.ppi4j.statement.IncludeStatement;
import org.scriptkitty.ppi4j.statement.PackageStatement;
import org.scriptkitty.ppi4j.statement.ScheduledStatement;
import org.scriptkitty.ppi4j.statement.SubStatement;
import org.scriptkitty.ppi4j.structure.BlockStructure;


public interface IASTConverter
{
    BlockContainer<?, ?> convert(BlockStructure struct);

    LoopContainer<?, ?> convert(CompoundStatement stmt);

    IncludeContainer<?, ?> convert(IncludeStatement stmt);

    PackageContainer<?, ?> convert(PackageStatement stmt);

    SubContainer<?, ?> convert(ScheduledStatement stmt);

    StatementContainer<?, ?> convert(Statement stmt);

    SubContainer<?, ?> convert(SubStatement stmt);

    PackageContainer<?, ?> createMainPackage();
   
    ModuleContainer<?, ?> startDocument();
}
