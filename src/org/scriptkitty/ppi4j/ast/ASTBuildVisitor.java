package org.scriptkitty.ppi4j.ast;






import org.scriptkitty.ppi4j.Document;
import org.scriptkitty.ppi4j.Element;
import org.scriptkitty.ppi4j.Node;
import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.state.BlockContainer;
import org.scriptkitty.ppi4j.ast.state.IASTContainer;
import org.scriptkitty.ppi4j.ast.state.LoopContainer;
import org.scriptkitty.ppi4j.ast.state.PackageContainer;
import org.scriptkitty.ppi4j.ast.state.TerminatorContainer;
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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public final class ASTBuildVisitor extends AbstractNodeVisitor // AbstractExtendedNodeVisitor
{
    //~ Instance fields

    private IASTConverter converter;

    private boolean incTerm;
    private int lastOffset;

    private IErrorProxy proxy;
    private StateManager state = new StateManager();

    //~ Constructors

    public ASTBuildVisitor(IASTConverter converter, IErrorProxy proxy)
    {
        this.proxy = proxy;

        if (converter instanceof ASTConverter)
        {
            ((ASTConverter) converter).setStateManager(state);
        }

        this.converter = converter;
    }

    //~ Methods

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
            BlockContainer container = converter.convert(stmt.getBody());
            state.addToParent(container);

            visit(stmt.getBody());
        }
        else
        {
            LoopContainer container = converter.convert(stmt);
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

        PackageContainer container = converter.convert(stmt);
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

    //~ Inner Classes

    class StateManager
    {
        private boolean initialized;

        private LinkedList<IASTContainer> stack = new LinkedList<IASTContainer>();

        void addToPackage(IASTContainer container)
        {
            if (!state.hasPackage())
            {
                addToParent(converter.createMainPackage());
            }

            add(getPackage(), container);
        }

        void addToParent(IASTContainer container)
        {
            if (stack.isEmpty())
            {
                throw new RuntimeException("unable to add container, perhaps it was not initialized");
            }

            add(stack.getLast(), container);
        }

        void ensurePackage()
        {
            if (!hasPackage())
            {
                addToParent(converter.createMainPackage());
            }
        }

        PackageContainer getPackage()
        {
            IASTContainer container = stack.getLast();

            if (container instanceof PackageContainer)
            {
                return (PackageContainer) container;
            }

            ListIterator<IASTContainer> iterator = stack.listIterator(stack.size());
            while (iterator.hasPrevious())
            {
                if ((container = iterator.previous()) instanceof PackageContainer)
                {
                    return (PackageContainer) container;
                }
            }

            return null;
        }

        boolean hasPackage()
        {
            return (getPackage() != null);
        }

        void initialize(IASTContainer container)
        {
            if (initialized)
            {
                throw new RuntimeException("state manager already initialized");
            }

            stack.add(container);
            initialized = true;
        }

        boolean isTop(Class<? extends IASTContainer> clazz)
        {
            if (stack.isEmpty())
            {
                return false;
            }

            return (stack.getLast().getClass().getSuperclass() == clazz);
        }

        void pop(int offset)
        {
            IASTContainer container = stack.removeLast();

            // if the container's empty, don't set an offset
            if (!container.isEmpty())
            {
                container.setEnd(offset);
            }
        }

        void terminate(int offset)
        {
            while (!stack.isEmpty())
            {
                pop(offset);
            }
        }

        private void add(IASTContainer parent, IASTContainer container)
        {
            /*
             * don't add the container to the parent if it's empty...
             *
             * this is done as a convienece so an IASTConverter can safely return 'null' if it doesn't know how to process
             * a statement and not have to worry about handling that case in their implementation.
             *
             * it is still added to the stack however so it can be 'popped' off when processing is done
             */
            if (!container.isEmpty())
            {
                if (container instanceof TerminatorContainer)
                {
                    stack.getFirst().add(container.get());
                }
                else
                {
                    parent.add(container.get());
                }
            }

            stack.add(container);
        }
    }
}
