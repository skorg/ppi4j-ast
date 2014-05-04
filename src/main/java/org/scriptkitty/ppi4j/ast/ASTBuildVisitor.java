package org.scriptkitty.ppi4j.ast;

import java.util.List;

import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Element;
import org.scriptkitty.ppi4j.Node;
import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.container.BlockContainer;
import org.scriptkitty.ppi4j.ast.container.LoopContainer;
import org.scriptkitty.ppi4j.ast.container.PackageContainer;
import org.scriptkitty.ppi4j.internal.ASTConverter;
import org.scriptkitty.ppi4j.internal.StateManager;
import org.scriptkitty.ppi4j.statement.BreakStatement;
import org.scriptkitty.ppi4j.statement.CompoundStatement;
import org.scriptkitty.ppi4j.statement.DataStatement;
import org.scriptkitty.ppi4j.statement.EndStatement;
import org.scriptkitty.ppi4j.statement.ExpressionStatement;
import org.scriptkitty.ppi4j.statement.GivenStatement;
import org.scriptkitty.ppi4j.statement.IncludeStatement;
import org.scriptkitty.ppi4j.statement.NullStatement;
import org.scriptkitty.ppi4j.statement.PackageStatement;
import org.scriptkitty.ppi4j.statement.Perl6IncludeStatement;
import org.scriptkitty.ppi4j.statement.ScheduledStatement;
import org.scriptkitty.ppi4j.statement.SubStatement;
import org.scriptkitty.ppi4j.statement.UnmatchedBrace;
import org.scriptkitty.ppi4j.statement.VariableStatement;
import org.scriptkitty.ppi4j.statement.WhenStatement;
import org.scriptkitty.ppi4j.structure.BlockStructure;
import org.scriptkitty.ppi4j.util.IErrorProxy;
import org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor;


public final class ASTBuildVisitor extends AbstractNodeVisitor
{
    private boolean incTerm;

    private IASTConverter converter;
    private StateManager state;
    
    
    //private IErrorProxy proxy;
    private int lastOffset;

    public ASTBuildVisitor(IASTObjectCreator creator, IErrorProxy proxy)
    {
        this.state = new StateManager();
        this.converter = new ASTConverter(creator, state);
        
    }
    
    public void includeTerminator(boolean incTerm)
    {
        this.incTerm = incTerm;
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.structure.BlockStructure)
     */
    @Override public void visit(BlockStructure struct)
    {
        state.ensurePackage();
        // blocks get added to whomever the parent is
        state.addToParent(converter.convert(struct));

        visitChildren(struct);

        // meh - if the top item on the stack is a package, we need to calculate a different offset
        if (state.isTop(PackageContainer.class))
        {
            Element last = struct.getSigChild(-1);
            state.pop(last.getEndOffset());
        }

        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.BreakStatement)
     */
    @Override public void visit(BreakStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me
        lastOffset = stmt.getEndOffset();
    }

    @Override public void visit(CompoundStatement stmt)
    {
        state.ensurePackage();

        if (stmt.getType() == Statement.Type.CONTINUE)
        {
            BlockContainer<?> container = converter.convert(stmt.getBody());
            state.addToParent(container);

            visit(stmt.getBody());
        }
        else
        {
            LoopContainer<?> container = converter.convert(stmt);
            state.addToParent(container);

            if (stmt.hasConditional())
            {
                container.expectConditional();
                // TODO: visit(stmt.getConditional());

                if (stmt.hasBody())
                {
                    container.expectBody();
                    visit(stmt.getBody());
                }

                if (stmt.hasContinue())
                {
                    container.expectContinue();
                    visit(stmt.getContinue());
                }
            }
        }

        lastOffset = stmt.getEndOffset();
        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.DataStatement)
     */
    @Override public void visit(DataStatement stmt)
    {
        state.ensurePackage();
        /*
         * TODO: add support for data statements
         *
         * stop here so we don't affect the package offset calculation
         */
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.Document)
     */
    @Override public void visit(Document document)
    {
        state.initialize(converter.startDocument());
        visitChildren(document);
        state.terminate(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.EndStatement)
     */
    @Override public void visit(EndStatement stmt)
    {
        state.ensurePackage();
        /*
         * TODO: add support for end statements
         *
         * stop here so we don't affect the package offset calculation
         */
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.ExpressionStatement)
     */
    @Override public void visit(ExpressionStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.GivenStatement)
     */
    @Override public void visit(GivenStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.IncludeStatement)
     */
    @Override public void visit(IncludeStatement stmt)
    {
        state.ensurePackage();
        state.addToPackage(converter.convert(stmt));

        visitChildren(stmt);
        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.NullStatement)
     */
    @Override public void visit(NullStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.PackageStatement)
     */
    @Override public void visit(PackageStatement stmt)
    {
        /*-
         * don't terminate the current package if we find another nested w/in a block, ie:
         *
         * package Foo;
         * {
         *     package Bar;
         * }
         */
        if (state.hasPackage() && !stmt.parentIs(BlockStructure.class))
        {
            state.pop(lastOffset);
        }

        PackageContainer<?> container = converter.convert(stmt);
        state.addToParent(container);

        visitChildren(stmt);
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.Perl6IncludeStatement)
     */
    @Override public void visit(Perl6IncludeStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me!
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.ScheduledStatement)
     */
    @Override public void visit(ScheduledStatement stmt)
    {
        state.ensurePackage();
        state.addToPackage(converter.convert(stmt));

        visitChildren(stmt);
        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.Statement)
     */
    @Override public void visit(Statement stmt)
    {
        state.ensurePackage();
        state.addToParent(converter.convert(stmt));

        if (!stmt.isTerminator() || incTerm)
        {
            lastOffset = stmt.getEndOffset();
        }

        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.SubStatement)
     */
    @Override public void visit(SubStatement stmt)
    {
        state.ensurePackage();
        state.addToPackage(converter.convert(stmt));

        visitChildren(stmt);
        state.pop(lastOffset);
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.Token)
     */
    @Override public void visit(Token token)
    {
        if (token.isSignificant())
        {
            lastOffset = token.getEndOffset();
        }
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.UnmatchedBrace)
     */
    @Override public void visit(UnmatchedBrace stmt)
    {
        state.ensurePackage();

        // TODO: implement me!
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.VariableStatement)
     */
    @Override public void visit(VariableStatement stmt)
    {
        state.ensurePackage();
        // StatementContainer container = converter.convert(stmt);
        // state.addToParent(container);
        //
        // visitChildren(stmt);
        //

        // TODO: implement me!
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.visitor.AbstractNodeVisitor#visit(org.scriptkitty.ppi4j.statement.WhenStatement)
     */
    @Override public void visit(WhenStatement stmt)
    {
        state.ensurePackage();

        // TODO: implement me
        lastOffset = stmt.getEndOffset();
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.AbstractNodeVisitor#getToVisit(org.scriptkitty.ppi4j.Node)
     */
    @Override protected List<Element> getToVisit(Node node)
    {
        return node.getElements();
    }
}
