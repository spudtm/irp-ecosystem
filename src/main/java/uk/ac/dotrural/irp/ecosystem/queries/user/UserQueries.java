/**
 * 
 */
package uk.ac.dotrural.irp.ecosystem.queries.user;

import java.util.UUID;

/**
 * @author david corsar
 * 
 */
public class UserQueries {
  public static String getUserByEmailQuery(String email) {
    return String.format(QueryReader.getString("UserQueries.query.get.email"),
        email);
  }

	public static String getUserByUriQuery(String userUri) {
		return String.format(QueryReader.getString("UserQueries.query.get.uri"),
				userUri);
	}

	public static String getExistsQuery(String email) {
		return String.format(QueryReader.getString("UserQueries.query.exists"),
				email);
	}

	public static String getIsValidLoginCredentialsQuery(String email,
			String password) {
		return String.format(QueryReader.getString("UserQueries.query.login"),
				email, encodePassword(password));
	}

	public static String getCreateUserUpdate(String nickname, String email, String password) {
		String userUrl = QueryReader
				.getString("UserQueries.query.create.baseNS")
				+ UUID.randomUUID().toString();
		return getCreateUserUpdate(userUrl, nickname, email, password);
	}

	public static String getCreateUserUpdate(String userUri, String nickname, String email,
			String password) {
		return String.format(
				QueryReader.getString("UserQueries.update.create"), userUri, nickname,
				email, encodePassword(password));
	}

	public static String getDeleteUserUpdate(String userUri) {
		String query = String.format(
				QueryReader.getString("UserQueries.update.delete"), userUri,
				userUri);
		return query;
	}
	
	public static String getUpdateUserUpdate(String userUri, String nickname, String email, String password){
		return String.format(QueryReader.getString("UserQueries.update"), userUri, userUri, nickname, email, password, userUri);
	}

	private static String encodePassword(String password) {
		return password;
	}

}
