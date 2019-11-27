package paToolkit4Java.methodFinder;

import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javaToolkit.lib.utils.FileUtil.getFileContent;


public class MethodFinder {


    public static class MethodNode {

        MethodDeclaration node;
        String methodName;
        int startLineNum;

        public MethodNode(String methodName, MethodDeclaration node, int startLineNum) {
            this.methodName = methodName;
            this.node = node;
            this.startLineNum = startLineNum;
        }

        public int getStartLineNum() {
            return startLineNum;
        }

        public MethodDeclaration getNode() {
            return node;
        }
    }

    public MethodNode getMethodforGivenLineNum(String srcFilePath, int linenum) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);

        char[] fileContent = null;
        try {
            fileContent = getFileContent(srcFilePath).toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        parser.setSource(fileContent);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<MethodNode> methodNodeList = new ArrayList<>();

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodDeclaration node) {
                SimpleName methodName = node.getName();

                int startLineNum = cu.getLineNumber(node.getStartPosition());

                methodNodeList.add(new MethodNode(methodName.toString(), node, startLineNum));
                return false;
            }
        });

        methodNodeList.sort(Comparator.comparing(a -> a.startLineNum));

        MethodNode targetMethod = null;
        for (int i = 0; i < methodNodeList.size(); i++) {
            MethodNode m = methodNodeList.get(i);
            // from begin to the end the first method that
            if (linenum > m.startLineNum){
                targetMethod = m;
            }
        }
        return targetMethod;
    }

    public static void main(String[] args) {
        MethodFinder mf = new MethodFinder();
        MethodNode mn = mf.getMethodforGivenLineNum("/home/bowen/Desktop/research/Transformation4J/java-project/ct4j/src/main/java/paToolkit4Java/methodFinder/MethodFinder.java", 60);
        System.out.println(mn.getNode().toString());

    }


}

