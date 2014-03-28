package org.scriptkitty.ppi4j.ast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import org.scriptkitty.ppi4j.Element;
import org.scriptkitty.ppi4j.Statement;
import org.scriptkitty.ppi4j.Statement.Type;
import org.scriptkitty.ppi4j.Token;
import org.scriptkitty.ppi4j.ast.ASTBuildVisitor.StateManager;
import org.scriptkitty.ppi4j.ast.converters.IStatementConverter;
import org.scriptkitty.ppi4j.ast.state.BlockContainer;
import org.scriptkitty.ppi4j.ast.state.IASTContainer;
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
import org.scriptkitty.ppi4j.token.WordToken;
import org.scriptkitty.ppi4j.token.quotelike.QLWordsToken;

public class ASTConverter implements IASTConverter
{
    //~ Static fields/initializers

    private static final String main = "main";

    //~ Instance fields

    private IASTObjectCreator creator;
    
    private ConverterDelegate<IncludeStatement, IncludeContainer> includeDelegate;
    private ConverterDelegate<Statement, StatementContainer> stmtDelegate;
    
    
    private StateManager state;

    //~ Constructors

    public ASTConverter(IASTObjectCreator creator)
    {
        this.creator = creator;
        
        this.stmtDelegate = new ConverterDelegate<>(creator);
        this.includeDelegate = new ConverterDelegate<>(creator);
    }

    //~ Methods

    @Override public BlockContainer convert(BlockStructure struct)
    {
        boolean isBody = true;
        Element parent = struct.getParent();

        if ((parent instanceof CompoundStatement) && (((CompoundStatement) parent).getType() == Type.CONTINUE))
        {
            isBody = false;
        }

        return creator.createBlock(struct.getStartOffset(), isBody);
    }

    @Override public LoopContainer convert(CompoundStatement stmt)
    {
        LoopContainer container = LoopContainer.NULL;
        int start = stmt.getStartOffset();

        switch (stmt.getType())
        {
            case FOR:
            {
                container = creator.createFor(start);
                break;
            }
            case FOREACH:
            {
                container = creator.createForeach(start);
                break;
            }
            case IF:
            case WHILE:
            {
                WordToken token = (WordToken) stmt.getKeyword();

                if (token.isIfKeyword())
                {
                    // TODO: implement if
                    // container = creator.createIf(start);
                }
                else if (token.isUnlessKeyword())
                {
                    // TODO: implement unless
                    // container = creator.createUnless(start);
                }
                else if (token.isWhileKeyword())
                {
                    container = creator.createWhile(start);
                }
                else
                {
                    container = creator.createUntil(start);
                }

                break;
            }
            default:
            {
                // TODO: log some kind of error/warning on this
                break;
            }
        }

        return container;
    }

    @Override public IncludeContainer convert(IncludeStatement stmt)
    {
        IncludeContainer container = IncludeContainer.NULL;
        
        if (includeDelegate.converts(stmt))
        {
            container = includeDelegate.convert(stmt);
        }
        else
        {
            // TODO: handle require statements that don't have a bareword?
            Token token = stmt.getName();
            if (!token.isNull())
            {
                container = creator.createInclude(stmt.getStartOffset(), token);
            }
        }

        /*
         * XXX: note sure i like this but i'm not sure how to handle...        
         */
        if (stmt.isUseBase() || stmt.isUseParent())
        {
            addBaseClassesToPackage(stmt);
        }
        
        container.setType(stmt.getType());
        container.setDeclaringPackage(getCurrentPackageName());
        container.setModuleVersion(stmt.getModuleVersion().getContent());

        return container;
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.IASTConverter#convert(org.scriptkitty.ppi4j.statement.PackageStatement)
     */
    @Override public PackageContainer convert(PackageStatement stmt)
    {
        return creator.createPackage(stmt.getStartOffset(), stmt.getName());
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.IASTConverter#convert(org.scriptkitty.ppi4j.statement.ScheduledStatement)
     */
    @Override public SubContainer convert(ScheduledStatement stmt)
    {
        SubContainer container = creator.createScheduled(stmt.getStartOffset(), stmt.getName());
        addProperties(container, stmt);

        return container;
    }

    @Override public StatementContainer convert(Statement stmt)
    {
        StatementContainer container = StatementContainer.NULL;

        // we're really the terminator and not something like: if (...) { 1; }
        if (stmt.isTerminator() && (stmt.getNextSignificantSibling() == null))
        {
            container = creator.createTerminator(stmt.getStartOffset());
        }
        else
        {
            LinkedList<Element> elements = new LinkedList<>(stmt.getSigChildren());

            if (stmt.isComplete())
            {
                // strip off the ';' so we don't have to deal w/ it later
                elements.removeLast();
            }

            // there has to be at least one...
            Element first = elements.removeFirst();

            if (first instanceof WordToken)
            {
                container = handleWordToken(stmt.getStartOffset(), (WordToken) first, elements);
            }
        }

        return container;
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.IASTConverter#convert(org.scriptkitty.ppi4j.statement.SubStatement)
     */
    @Override public SubContainer convert(SubStatement stmt)
    {
        SubContainer container = creator.createSubroutine(stmt.getStartOffset(), stmt.getName());
        addProperties(container, stmt);

        return container;
    }

    /*
     * @see org.scriptkitty.ppi4j.ast.IASTConverter#createMainPackage()
     */
    @Override public PackageContainer createMainPackage()
    {
        return creator.createMainPackage();
    }

    @Override public ModuleContainer startDocument()
    {
        return creator.createModule();
    }

    final void setStateManager(StateManager state)
    {
        this.state = state;
    }

    private void addBaseClassesToPackage(IncludeStatement stmt)
    {
        List<String> classes = new ArrayList<>(); 
        PackageContainer container = state.getPackage();
        
        for (Element token : stmt.getArguments())
        {
            if (token instanceof QLWordsToken)
            {
                classes.addAll(((QLWordsToken) token).getLiteral());
            }
        }
        
        for (String clazz : classes)
        {
            container.addSuperClass(clazz);
        }
    }

    private void addProperties(SubContainer container, SubStatement stmt)
    {
        // don't set this if we're in a script
        if (!isMainPackage())
        {
            container.setDeclaringPackage(getCurrentPackageName());
        }

        // TODO: add support for prototypes, attributes, forward statements
    }

    private StatementContainer convertBuiltin(int start, Token bToken, LinkedList<Element> list)
    {
        // TODO: determine args
        return creator.createBuiltinCall(start, bToken);
    }

    private StatementContainer convertMethodCall(int start, Token cToken, LinkedList<Element> list)
    {
        // remove the '->' token before performing the conversion
        list.remove();

        // this ignores the fact that the syntax may be invalid, ie: Foo->1;
        Token mToken = (list.peek() instanceof Token) ? (Token) list.remove() : Token.NULL;

        // TODO: determine args
        return creator.createMethodCall(start, cToken, mToken);
    }

    private String getCurrentPackageName()
    {
        PackageContainer container = state.getPackage();

        if (container != null)
        {
            return container.getPackageName();
        }

        return null;
    }

    private StatementContainer handleWordToken(int start, WordToken token, LinkedList<Element> list)
    {
        if (token.isClassName())
        {
            return convertMethodCall(start, token, list);
        }

        if (token.isBuiltin())
        {
            return convertBuiltin(start, token, list);
        }

        if (token.isFunctionCall())
        {
            // need to handle module names that aren't qualified - ppi4j thinks they are function calls
            System.out.println("function call");
        }

        return StatementContainer.NULL;
    }
       
    private boolean isMainPackage()
    {
        return main.equals(getCurrentPackageName());
    }
    
    public class ConverterDelegate<S, C extends IASTContainer>
    {
        private IASTObjectCreator creator;

        private IStatementConverter<S, C> converter;

        @SuppressWarnings("rawtypes")
        private ServiceLoader<IStatementConverter> loader = ServiceLoader.load(IStatementConverter.class);

        public ConverterDelegate(IASTObjectCreator creator)
        {
            this.creator = creator;
        }

        public C convert(S stmt)
        {
            if (!converts(converter, stmt))
            {
                // safety check, should never happen...
                throw new RuntimeException("unable to convert [" + stmt + "] with converter [" + converter + "]");
            }
            
            C container = converter.convert(stmt, creator);

            // reset the converter
            converter.reset();
            converter = null; 
            
            return container;
        }

        public boolean converts(S stmt)
        {
            IStatementConverter<S, C> converter = getHandler(stmt.getClass());

            if (converts(converter, stmt))
            {
                this.converter = converter;
                return true;
            }

            return false;
        }

        private boolean converts(IStatementConverter<S, C> converter, S stmt)
        {
            return ((converter != null) && converter.converts(stmt));
        }

        private IStatementConverter<S, C> getHandler(Class<?> clazz)
        {
            for (IStatementConverter<S, C> handler : loader)
            {
                if (handler.canConvert(clazz))
                {
                    return handler;
                }
            }

            return null;
        }
    }

}