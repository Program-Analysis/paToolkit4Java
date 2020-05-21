package paToolkit4Java.commentRemoval;

public class CommentCleaner {

	/**
	 * https://stackoverflow.com/a/32977808
	 * @param srcStr
	 * @return
	 */
	public static String removeComment(String srcStr) {
		return srcStr.replaceAll(
				"^([^\"\\r\\n]*?(?:(?<=')\"[^\"\\r\\n]*?|(?<!')\"[^\"\\r\\n]*?\"[^\"\\r\\n]*?)*?)(?<!/)/\\*[^\\*]*(?:\\*+[^/][^\\*]*)*?\\*+/",
				"");
	}
}
