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
        int endLineNum;

        public MethodNode(String methodName, MethodDeclaration node, int startLineNum, int endLineNum) {
            this.methodName = methodName;
            this.node = node;
            this.startLineNum = startLineNum;
            this.endLineNum = endLineNum;
        }

        public boolean equals(MethodNode obj) {
            if (this.node.toString().equals(obj.node.toString())) {
                return true;
            }
            return false;
        }

        public int getStartLineNum() {
            return startLineNum;
        }
        
        public String getMethodName() {
            return methodName;
        }

        public void setEndLineNum(int endLineNum) {
            this.endLineNum = endLineNum;
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
            System.out.printf("getMethodforGivenLineNum-getFileContent failed!\n%s", srcFilePath);
            e.printStackTrace();
            return null;
        }

        parser.setSource(fileContent);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<MethodNode> methodNodeList = new ArrayList<MethodNode>();

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodDeclaration node) {
                SimpleName methodName = node.getName();

                int startLineNum = cu.getLineNumber(node.getStartPosition());

                int endLineNum = cu.getLineNumber(node.getStartPosition() + node.getLength());

                methodNodeList.add(new MethodNode(methodName.toString(), node, startLineNum, endLineNum));
                return false;
            }
        });

        MethodNode targetMethod = null;
        for (int i = 0; i < methodNodeList.size(); i++) {
            MethodNode m = methodNodeList.get(i);
            // from begin to the end the first method that
            if (linenum >= m.startLineNum && linenum<=m.endLineNum) {
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

