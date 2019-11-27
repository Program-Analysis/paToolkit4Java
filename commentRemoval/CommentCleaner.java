package paToolkit4Java.commentRemoval;

import com.commentremover.app.CommentProcessor;
import com.commentremover.app.CommentRemover;
import com.commentremover.exception.CommentRemoverException;

public class CommentCleaner {

    public void internalCommentRemove () throws CommentRemoverException {

        // root dir is: /Users/user/Projects/MyProject
        // example for startInternalPath

        CommentRemover commentCleaner = new CommentRemover.CommentRemoverBuilder()
                .removeJava(true) // Remove Java file Comments....
                .removeJavaScript(true) // Remove JavaScript file Comments....
                .removeJSP(true) // etc.. goes like that
                .removeTodos(false) //  Do Not Touch Todos (leave them alone)
                .removeSingleLines(true) // Remove single line type comments
                .removeMultiLines(true) // Remove multiple type comments
                .preserveJavaClassHeaders(true) // Preserves class header comment
                .preserveCopyRightHeaders(true) // Preserves copyright comment
                .startInternalPath("src.main.app") // Starts from {rootDir}/src/main/app , leave it empty string when you want to start from root dir
                .setExcludePackages(new String[]{"src.main.java.app.pattern"}) // Refers to {rootDir}/src/main/java/app/pattern and skips this directory
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentCleaner);
        commentProcessor.start();
    }


    public void externalCommentRemove () throws CommentRemoverException {

        // example for externalPath

        CommentRemover commentCleaner = new CommentRemover.CommentRemoverBuilder()
                .removeJava(true) // Remove Java file Comments....
                .removeJavaScript(true) // Remove JavaScript file Comments....
                .removeJSP(true) // etc..
                .removeTodos(true) // Remove todos
                .removeSingleLines(false) // Do not remove single line type comments
                .removeMultiLines(true) // Remove multiple type comments
                .preserveJavaClassHeaders(true) // Preserves class header comment
                .preserveCopyRightHeaders(true) // Preserves copyright comment
                .startExternalPath("/Users/user/Projects/MyOtherProject")// Give it full path for external directories
                .setExcludePackages(new String[]{"src.main.java.model"}) // Refers to /Users/user/Projects/MyOtherProject/src/main/java/model and skips this directory.
                .build();

        CommentProcessor commentProcessor = new CommentProcessor(commentCleaner);
        commentProcessor.start();
    }
}
