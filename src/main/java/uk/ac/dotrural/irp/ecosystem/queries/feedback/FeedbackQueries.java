/**
 * 
 */
package uk.ac.dotrural.irp.ecosystem.queries.feedback;

/**
 * @author david corsar
 * 
 */
public class FeedbackQueries {
	public static String getFeedbackQuery(String feedbackUri) {
		return String
				.format(QueryReader.getString("FeedbackQueries.query.get"),
						feedbackUri);
	}

	public static String getFeedbackForUserQuery(String userUri) {
		return String.format(
				QueryReader.getString("FeedbackQueries.query.getForUser"),
				userUri);
	}

	public static String getDeleteFeedbackUpdate(String feedbackUri) {
		return String.format(
				QueryReader.getString("FeedbackQueries.update.delete"),
				feedbackUri, feedbackUri);
	}

	public static String getCreateFeedbackUpdate(String userUri,
			String journeyUri, String feedback, String feedbackUri) {
		return String.format(
				QueryReader.getString("FeedbackQueries.update.create"),
				feedbackUri, journeyUri, userUri, toXsdString(feedback));
	}

	public static String getUpdateFeedbackUpdate(String feedbackUri,
			String newFeedback) {
		return String
				.format(QueryReader.getString("FeedbackQueries.update"),
						feedbackUri, feedbackUri, toXsdString(newFeedback),
						feedbackUri);

	}

	public static String toXsdString(String str) {
		StringBuilder sb = new StringBuilder("\"");
		sb.append(str).append("\"^^xsd:string");
		return sb.toString();
	}
}
