package paToolkit4Java.parser;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    public static MethodDeclaration parseMethodStr(String methodStr) {
        String sourceStart = "public class A {";
//add a fake class A as a shell, to meet the requirement of ASTParser

        String sourceEnd = "}";

        String source = sourceStart + methodStr + sourceEnd;

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<MethodDeclaration> md = new ArrayList<MethodDeclaration>();

        cu.accept(new ASTVisitor() {
            //by add more visit method like the following below, then all king of statement can be visited.
            public boolean visit(MethodDeclaration node) {
                md.add(node);
                return false;
            }
        });
        return md.get(0);

    }


}
