package org.scriptkitty.ppi4j.ast;

import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.ast.state.BlockContainer;
import org.scriptkitty.ppi4j.ast.state.IncludeContainer;
import org.scriptkitty.ppi4j.ast.state.LoopContainer;
import org.scriptkitty.ppi4j.ast.state.ModuleContainer;
import org.scriptkitty.ppi4j.ast.state.PackageContainer;
import org.scriptkitty.ppi4j.ast.state.StatementContainer;
import org.scriptkitty.ppi4j.ast.state.SubContainer;
import org.scriptkitty.ppi4j.statement.CompoundStatement;
import org.scriptkitty.ppi4j.statement.IncludeStatement;
import org.scriptkitty.ppi4j.statement.PackageStatement;
import org.scriptkitty.ppi4j.statement.ScheduledStatement;
import org.scriptkitty.ppi4j.statement.SubStatement;
import org.scriptkitty.ppi4j.structure.BlockStructure;


public interface IASTConverter
{
    BlockContainer convert(BlockStructure struct);
    
    IncludeContainer convert(IncludeStatement stmt);

    PackageContainer convert(PackageStatement stmt);

    SubContainer convert(ScheduledStatement stmt);

    SubContainer convert(SubStatement stmt);

    PackageContainer createMainPackage();

    ModuleContainer startDocument();

    StatementContainer convert(Statement stmt);
    
    LoopContainer convert(CompoundStatement stmt);
}
